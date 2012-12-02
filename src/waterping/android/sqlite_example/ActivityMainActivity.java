package waterping.android.sqlite_example;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityMainActivity extends Activity implements OnClickListener {
	MyDB mydb = new MyDB(this);
	TextView listdata,txtdescription;
	Button sm, show;
	EditText edname, edtel;
	String contentRead;
	String[] values;
	String name, tel;

	Button btnsearch, btninsert, btnupdate, btndelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_activity_main);
		initWidget();

		btninsert.setOnClickListener(this);
		btnsearch.setOnClickListener(this);
		btnupdate.setOnClickListener(this);
		btndelete.setOnClickListener(this);

		mydb.openToWrite();
		mydb.deleteAll();
		mydb.insert("Cola", "000-00000000");
		mydb.insert("Est", "111-11111111");
		mydb.insert("David", "081-6785432");
		mydb.insert("Stick", "081-12345678");
		
		mydb.close();

		RetreiveAll();
	}
	private void initWidget(){
		listdata = (TextView) findViewById(R.id.textView2);
		btninsert = (Button) findViewById(R.id.btninsert);
		btnsearch = (Button) findViewById(R.id.btnsearch);
		btnupdate = (Button) findViewById(R.id.btnUpdate);
		btndelete = (Button) findViewById(R.id.btnDelete);

		edname = (EditText) findViewById(R.id.editText1);
		edtel = (EditText) findViewById(R.id.editText2);
	}
	private void RetreiveAll() {
		mydb.openToRead();
		contentRead = mydb.queryAll();
		mydb.close();
		listdata.setText(contentRead);
		
	}

	@Override
	public void onClick(View v) {
		mydb.openToWrite();

		switch (v.getId()) {

		case R.id.btninsert: // Event insert
			mydb.insert(edname.getText().toString(), edtel.getText().toString());
			RetreiveAll();
			break;

		case R.id.btnUpdate: // Event update
			mydb.update(edtel.getText().toString(), edname.getText().toString());
			RetreiveAll();
			break;

		case R.id.btnDelete:// Event Delete
			mydb.deleteRecord(edname.getText().toString());
			RetreiveAll();
			break;
			
		case R.id.btnsearch:// Event Search
			contentRead = mydb.SearchProgram(edname.getText().toString());
			listdata.setText(contentRead);
			break;

		}
		
	}

}
