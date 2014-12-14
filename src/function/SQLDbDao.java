package function;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/*
 * SQLite操作类，存放对数据库增删改查的方法
 */
public class SQLDbDao {
	DatabaseHelper dbh;

	public SQLDbDao(Context context) {
		this.dbh = new DatabaseHelper(context);
	}

	public void save(String date, String title, String contents, String alarm,
			String youxian, int textcolor, String isAlarm) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		db.execSQL("insert into info(date,title,contents,alarm,youxianji,textcolor,openalarm) values ('"
				+ date
				+ "','"
				+ title
				+ "','"
				+ contents
				+ "','"
				+ alarm
				+ "','" + youxian + "','" + textcolor + "','" + isAlarm + "');");
		db.close();
	}

	public void delete(String id) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		db.execSQL("delete from info where id=?", new Object[] { id });
		db.close();
	}

	// public void update()
//	public Map<String, Object> getDetails(String id) {
//		SQLiteDatabase db = dbh.getReadableDatabase();
//		Map<String, Object> map = new HashMap<String, Object>();
//		Cursor c = db.rawQuery("select * from info where _id=?",
//				new String[] { id });
//		map.put("title", c.getString(c.getColumnIndex("title")));
//		map.put("date", c.getString(c.getColumnIndex("date")));
//		map.put("contents", c.getString(c.getColumnIndex("contents")));
//		map.put("alarm", c.getString(c.getColumnIndex("alarm")));
//		map.put("youxianji", c.getString(c.getColumnIndex("youxianji")));
//		map.put("textcolor", c.getInt(c.getColumnIndex("textcolor")));
//		map.put("openalarm", c.getString(c.getColumnIndex("openalarm")));
//		return map;
//	}
	public Cursor find(String date) {
		SQLiteDatabase db = dbh.getReadableDatabase();
		Cursor c = db.rawQuery("select * from info where date=?",
				new String[] { date });
		return c;
	}
}
