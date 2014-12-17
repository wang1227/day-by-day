package com.lhtblog.daybyday;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.litepal.crud.DataSupport;

import ColorPickerDialog.ColorPickerDialog;
import DatabaseTest.Info;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TimePicker;
import android.widget.Toast;

public class ContentInfo extends Activity implements OnClickListener {
	private int year;
	private int month;
	private int day;
	private int week;
	private int hour = 0;
	private int minutes = 0;
	private int textcolor = -16776702;
	private String[] weeks;
	private Button setdate, settime, SelectColor;
	private ColorPickerDialog dialog;
	private EditText title, mContent;
	private RadioGroup group;
	private String importent = "重要";
	private Intent intent;
	private String flag = null;
	private String id = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contentinfo);
		mContent = (EditText) findViewById(R.id.et_contentinfo_content);
		setdate = (Button) findViewById(R.id.btn_contentinfo_setdate);
		settime = (Button) findViewById(R.id.btn_contentinfo_settime);
		SelectColor = (Button) findViewById(R.id.btn_contentinfo_selectcolor);
		title = (EditText) findViewById(R.id.et_contentinfo_title);
		group = (RadioGroup) findViewById(R.id.rg_contentinfo_rg);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int radioButtonId = group.getCheckedRadioButtonId();
				RadioButton rb = (RadioButton) findViewById(radioButtonId);
				importent = rb.getText().toString();
			}
		});
		SelectColor.setOnClickListener(this);
		settime.setOnClickListener(this);
		setdate.setOnClickListener(this);
		weeks = new String[] { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		setDate();
	}

	protected void onStart() {
		super.onStart();
		intent = getIntent();
		flag = intent.getStringExtra("flag");
		if (flag.equals("更新")) {
			id = intent.getStringExtra("id");
			Info info = DataSupport.find(Info.class, Integer.valueOf(id));
			title.setText(info.getTitle());
			title.setTextColor(info.getTextcolor());
			setdate.setText(info.getDate());
			SelectColor.setBackgroundColor(info.getTextcolor());
			settime.setText(info.getAlarm());
			mContent.setText(info.getContent());
		}
	};

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
		switch (item.getItemId()) {
		case R.id.save:
			if (!TextUtils.isEmpty(title.getText())) {
				if (flag.equals("新建")) {
					Info info = new Info();
					info.setTitle(title.getText().toString().trim());
					info.setDate(String.valueOf(year)
							+ String.valueOf(month + 1) + String.valueOf(day));
					info.setContent(mContent.getText().toString().trim());
					info.setAlarm(String.valueOf(hour)
							+ String.valueOf(minutes));
					info.setYouxianji(importent);
					info.setTextcolor(textcolor);
					if (info.save())
						Toast.makeText(ContentInfo.this, "存储成功",
								Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(ContentInfo.this, "存储失败",
								Toast.LENGTH_LONG).show();
					finish();
				} else if (flag.equals("更新")) {
					ContentValues values = new ContentValues();
					values.put("title", title.getText().toString().trim());
					values.put("date",
							String.valueOf(year) + String.valueOf(month + 1)
									+ String.valueOf(day));
					values.put("content", mContent.getText().toString().trim());
					values.put("alarm",
							String.valueOf(hour) + String.valueOf(minutes));
					values.put("youxianji", importent);
					DataSupport.update(Info.class, values, Integer.valueOf(id));
					finish();
				}
			} else
				Toast.makeText(this, "标题不能为空！", 500).show();
			break;
		case R.id.delete:
			Toast.makeText(this, "删除", 500).show();
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_contentinfo_selectcolor: // 点击改变颜色按钮的事件
			dialog = new ColorPickerDialog(ContentInfo.this, title
					.getTextColors().getDefaultColor(), "Color Picker",
					new ColorPickerDialog.OnColorChangedListener() {
						@Override
						public void colorChanged(int color) { // 将改变的颜色用全局变量保存下来
							textcolor = color;
							title.setTextColor(color);
							SelectColor.setBackgroundColor(color);
							title.setHintTextColor(color);
						}
					});
			dialog.show();
			break;
		case R.id.btn_contentinfo_setdate:
			DatePickerDialog dpd = new DatePickerDialog(this, Datelistener,
					year, month, day);
			dpd.show();// 显示DatePickerDialog组件
			break;
		case R.id.btn_contentinfo_settime:
			TimePickerDialog tpd = new TimePickerDialog(this, Timelistener,
					hour, minutes, true);
			tpd.show(); // 同上，显示时间组件
			break;
		}

	}

	private TimePickerDialog.OnTimeSetListener Timelistener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			hour = hourOfDay;
			minutes = minute;
			updateTime();
		}

		private void updateTime() {
			if (hour < 10 && minutes < 10)
				settime.setText("0" + hour + ":0" + minutes);
			else if (hour < 10 && minutes >= 10)
				settime.setText("0" + hour + ":" + minutes);
			else if (hour >= 10 && minutes < 10)
				settime.setText(hour + ":0" + minutes);
			else
				settime.setText(hour + ":" + minutes);
		}
	};
	private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
		/**
		 * params：view：该事件关联的组件 params：myyear：当前选择的年 params：monthOfYear：当前选择的月
		 * params：dayOfMonth：当前选择的日
		 */
		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,
				int dayOfMonth) {

			// 修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
			year = myyear;
			month = monthOfYear;
			day = dayOfMonth;
			// 更新日期
			updateDate();

		}

		// 当DatePickerDialog关闭时，更新日期显示
		private void updateDate() {
			// 在TextView上显示日期
			setdate.setText(year + "年" + (month + 1) + "月" + day + "日");
		}
	};
}
