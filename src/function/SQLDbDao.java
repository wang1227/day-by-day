package function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

	public void delete(int id) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		db.execSQL("delete from info where id=?", new Object[] { id });
		db.close();
	}

	// public void update()
	public List<Map<String, Object>> ShowTitle(String today) {
		SQLiteDatabase db = dbh.getReadableDatabase();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Cursor c = db.rawQuery("select * from info where date=?",
				new String[] { today });
		while (c.moveToNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", c.getInt(c.getColumnIndex("id")));
			map.put("title", c.getString(c.getColumnIndex("title")));
			list.add(map);
		}
		return list;
	}
}
