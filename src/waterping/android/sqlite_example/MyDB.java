package waterping.android.sqlite_example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class MyDB {
	public static final String MyDBName="DB_Contact.db"; //Name Database
	public static final String MyTBName="TB_Contact"; // Name Table Database
	public static final int MyDBVer = 1 ; //version 
	
	public static final String Key_ID = "id";  //Field 
	public static final String Key_Name = "name";
	public static final String Key_Phone_No = "phone";
	
	private SQLiteHelper sqlitehelper;
	private SQLiteDatabase sqlitedatabase;
	private Context context;
	
	private static final String CreateDBScript = "create table " + MyTBName + "( " + Key_ID + 
			" INTEGER PRIMARY KEY, " + Key_Name + " TEXT, " + Key_Phone_No + " TEXT);";
	
	public  MyDB(Context c){
		context = c;
	}
	
	
	public MyDB openToRead() throws android.database.SQLException{
		
		sqlitehelper = new SQLiteHelper(context,MyDBName,null,MyDBVer);
		sqlitedatabase = sqlitehelper.getReadableDatabase();
		
		return this;
	}
	

	public MyDB openToWrite() throws android.database.SQLException{
		
		sqlitehelper = new SQLiteHelper(context,MyDBName,null,MyDBVer);
		sqlitedatabase = sqlitehelper.getWritableDatabase();
		
		return this;
	}
	
	public void close(){  
		sqlitehelper.close();
	}
	
	public String queryAll(){ 
		String[] columns = new String[]{Key_ID,Key_Name,Key_Phone_No};
		Cursor cursor = sqlitedatabase.query(MyTBName, columns, null, null, null, null, null);
		String result = "";
		for(cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()){
			result = result + cursor.getString(0) +":: " 
					+ cursor.getString(1) + 
					" : : " + cursor.getString(2) + "\n";
		}
		return result;
	}
	//insert data
	public long insert(String value1,String value2){ 
		
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(Key_Name, value1);
		contentvalues.put(Key_Phone_No, value2);
		
	return sqlitedatabase.insert(MyTBName, null, contentvalues);
	}
	//Update where name at phone_number
	public int update(String newvalue,String oldvalue)
		{
			ContentValues contentvalues=new ContentValues();
			contentvalues.put(Key_Phone_No, newvalue); 
			String[] parm=new String[]{oldvalue};
	return sqlitedatabase.update(MyTBName, contentvalues, Key_Name + "=?", parm); 
						
		}
	
	public int deleteAll()
	{
		return sqlitedatabase.delete(MyTBName,null, null);
	
	}
	//Delete from Name
	public int deleteRecord(String value)
	{
		String[] del_value=new String[] {value};
		return sqlitedatabase.delete(MyTBName, Key_Name+ "=?",del_value);
	}
	
	//Search from Name
	public String SearchProgram(String name){
		String[] columns = new String[]{Key_ID,Key_Name,Key_Phone_No};
		Cursor cursor = sqlitedatabase.query(MyTBName, columns, Key_Name+" LIKE '"+name+"'", null, null, null, null);
		String result = "";
		for(cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()){
			result = result + cursor.getString(0) +":: " 
					+ cursor.getString(1) + 
					" : : " + cursor.getString(2) + "\n";
		}
		return result;
	}
	
	
	
	
	public class SQLiteHelper extends SQLiteOpenHelper{

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// Create DB
			db.execSQL(CreateDBScript);
			Log.d("CREATE DB", "SUCCESS"); 
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
			
		}

	
		
	}
}
