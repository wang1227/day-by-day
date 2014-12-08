package com.lhtblog.daybyday;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ColorPickerDialog.ColorPickerDialog;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ContentInfo extends Activity implements OnClickListener {
	private int year;
	private int month;
	private int day;
	private int week;
	private String[] weeks;
	private Button setdate, settime, SelectColor;
	private ColorPickerDialog dialog;
	private EditText title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contentinfo);
		setdate = (Button) findViewById(R.id.btn_contentinfo_setdate);
		SelectColor = (Button) findViewById(R.id.btn_contentinfo_selectcolor);
		title = (EditText) findViewById(R.id.et_contentinfo_title);
		SelectColor.setOnClickListener(this);
		weeks = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		setDate();
	}

	public void setDate() {
		Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
		Date mydate = new Date(); // 获取当前日期Date对象
		mycalendar.setTime(mydate);// //为Calendar对象设置时间为当前日期
		year = mycalendar.get(Calendar.YEAR); // 获取Calendar对象中的年
		month = mycalendar.get(Calendar.MONTH);// 获取Calendar对象中的月
		day = mycalendar.get(Calendar.DAY_OF_MONTH);// 获取这个月的第几天
		week = mycalendar.get(Calendar.DAY_OF_WEEK);
		setdate.setText(year + "年" + (month + 1) + "月" + day + "日" + "  "
				+ weeks[week - 1]); // 显示当前的年月日
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		dialog = new ColorPickerDialog(ContentInfo.this, title.getTextColors()
				.getDefaultColor(), "Color Picker",
				new ColorPickerDialog.OnColorChangedListener() {
					@Override
					public void colorChanged(int color) {
						title.setTextColor(color);
						SelectColor.setBackgroundColor(color);
						title.setHintTextColor(color);
					}
				});
		dialog.show();
	}
}
