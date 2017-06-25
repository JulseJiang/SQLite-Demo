package cn.edu.nsu.zxx.database;
/**
 * 作业，添加修改，删除
 */
import cn.edu.nsu.utils.DBHelper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private EditText edtNum,edtName;
	private Button btnInser,btnSelect;
	private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	  super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

        
        edtName=(EditText) findViewById(R.id.edtStuName);
        edtNum=(EditText) findViewById(R.id.edtStuNum);
        btnInser=(Button) findViewById(R.id.btnInser);
        btnSelect=(Button) findViewById(R.id.btnSelect);
        dbHelper=new DBHelper(MainActivity.this,"mydata.db",null,1);
        btnInser.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				SQLiteDatabase db=dbHelper.getReadableDatabase();
				String insert_sql="insert into stu(stuNo,stuName) values(?,?)";
				String stuNo=edtNum.getText().toString();
				String stuName=edtName.getText().toString();
				db.execSQL(insert_sql, new String[]{stuNo,stuName});
				db.close();
				edtNum.setText("");
				edtName.setText("");
				
			}});
        btnSelect.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,StuListActivity.class);
				startActivity(intent);
				
				
			}});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
