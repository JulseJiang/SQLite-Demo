package cn.edu.nsu.zxx.database;

import cn.edu.nsu.utils.DBHelper;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class StuListActivity extends Activity {

	private ListView listView;
	private DBHelper dbHelper;
	private SimpleCursorAdapter sca;
	private SQLiteDatabase db;
	private Cursor cursor;
	public static String TAG="Life";
	private EditText edt_stuNo;
	private EditText edt_stuName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stu_list);
		listView=(ListView) findViewById(R.id.listView1);
		dbHelper=new DBHelper(StuListActivity.this,"mydata.db",null,1);
		db=dbHelper.getReadableDatabase();
		
		freshTable();
//		cursor=db.rawQuery("select * from stu", null);
//		sca=new SimpleCursorAdapter(StuListActivity.this,R.layout.item_stu,cursor,new String[]{"stuNo","stuName"},new int[]{R.id.txtStuNo,R.id.txtStuName},0);
//		listView.setAdapter(sca);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle= cursor.getExtras();
				bundle.getString("stuNo");
//				Toast.makeText(StuListActivity.this, "点击", Toast.LENGTH_SHORT).show();
				Log.i(TAG, "cursor.getString(0)"+cursor.getString(0));
				Log.i(TAG, "cursor.getString(1)"+cursor.getString(1));
				Log.i(TAG, "cursor.getString(2)"+cursor.getString(2));
//				将xml文件解析为view
				LayoutInflater inflater=LayoutInflater.from(StuListActivity.this);
				View view = inflater.inflate(R.layout.item_alter,null);
				
				edt_stuNo=(EditText) view.findViewById(R.id.editText1);
				edt_stuName=(EditText) view.findViewById(R.id.editText2);
				
				edt_stuNo.setText(cursor.getString(1));
				edt_stuName.setText(cursor.getString(2));
				
				AlertDialog.Builder builder=new AlertDialog.Builder(StuListActivity.this);
				builder.setTitle("修改");
				builder.setView(view);
				builder.setPositiveButton("确认", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						db.execSQL("update stu set stuNo =? ,stuName=? where _id=?", 
								new String[]{
								edt_stuNo.getText().toString(),
								edt_stuName.getText().toString(),
								cursor.getString(0)});
						
						Toast.makeText(StuListActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
						freshTable();
					}
				});
				builder.setNegativeButton("取消", null);
				builder.create().show();
				
			}
			
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
//				db.execSQL("delete from stu where _id="+cursor.getString(0));
//				db.execSQL("delete from stu where _id=?", new String[]{cursor.getString(0)});
				int id=db.delete("stu", "_id=?", new String[]{cursor.getString(0)});
				if (id==1) {
					freshTable();
					Toast.makeText(StuListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
				}
				else {
					Toast.makeText(StuListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
				}
				
				return false;
			}
			
		});
	}
	public void freshTable(){
		cursor=db.rawQuery("select * from stu", null);
		sca=new SimpleCursorAdapter(StuListActivity.this,
				R.layout.item_stu,cursor,
				new String[]{"stuNo","stuName"},
				new int[]{R.id.txtStuNo,
				R.id.txtStuName},
				0);
		listView.setAdapter(sca);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stu_list, menu);
		return true;
	}

}
