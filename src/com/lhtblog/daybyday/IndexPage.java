package com.lhtblog.daybyday;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import function.ListViewAdapter;
import function.SQLDbDao;
import function.SnCal;

public class IndexPage extends Fragment implements OnItemClickListener {
	private TextView city;
	private TextView date;
	private TextView shishi;
	private TextView weather;
	private TextView temple;
	private TextView tips;
	private int year;
	private int month;
	private int day;
	private Cursor c;
	String today;
	private ListView showdata;
	// handler接收天气json数据并解析，并刷新控件显示数据
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			try {
				JSONObject dataJson = new JSONObject((String) msg.obj);
				date.setText(dataJson.getString("date"));
				JSONArray results = dataJson.getJSONArray("results");
				JSONObject data = results.getJSONObject(0);
				city.setText(data.getString("currentCity"));
				JSONArray index = data.getJSONArray("index");
				JSONObject ganmao = index.getJSONObject(2);
				tips.setText(ganmao.getString("des"));
				JSONArray weather_data = data.getJSONArray("weather_data");
				JSONObject today = weather_data.getJSONObject(0);
				shishi.setText(today.getString("date"));
				weather.setText(today.getString("weather"));
				temple.setText(today.getString("temperature"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tab1 = inflater.inflate(R.layout.tab1, container, false);
		init(tab1);
		getMyDate();
		return tab1;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updatelistview();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		updatelistview();
		// 延时1秒，获取天气信息
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(1000);
					getWeather(MainActivity.city);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	public void updatelistview() {
		SQLDbDao sql = new SQLDbDao(getActivity());
		// sql.find(today);
		String[] columnNames = { "_id", "title" };
		c = sql.find(today);
		ListViewAdapter adapter = new ListViewAdapter(getActivity()
				.getApplicationContext(), R.layout.listview, c, columnNames,
				new int[] { R.id.tv_lv_id, R.id.tv_lv_show });
		showdata.setAdapter(adapter);
		showdata.setOnItemClickListener(this);
	}

	public void init(View v) {
		showdata = (ListView) v.findViewById(R.id.lv_tab1_show);
		// weatherPic = (ImageView) v.findViewById(R.id.iv_tab1_type);
		city = (TextView) v.findViewById(R.id.tv_tab1_city);
		date = (TextView) v.findViewById(R.id.tv_tab1_date);
		shishi = (TextView) v.findViewById(R.id.tv_tab1_shishiwendu);
		weather = (TextView) v.findViewById(R.id.tv_tab1_weather);
		temple = (TextView) v.findViewById(R.id.tv_tab1_temple);
		tips = (TextView) v.findViewById(R.id.tv_tab1_tips);
		// button = (Button) v.findViewById(R.id.btn_tab1_new);
		// button.setOnClickListener(this);
	}

	// 根据城市从百度天气API获取相应的天气信息
	public void getWeather(String citys) {
		citys.replace("市", "");
		final String city = citys;
		new Thread() {
			@Override
			public void run() {
				SnCal sn = new SnCal(city);
				String url = null;
				try {
					url = "http://api.map.baidu.com/telematics/v3/weather?location="
							+ city
							+ "&output=json&ak=sbiOMaZAHDWksopxYLKfihxT&sn="
							+ sn.getUrl();// 算加密后的key值
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// 用Get方法获取数据
				HttpGet request = new HttpGet(url);
				String result = null;
				Message msg = handler.obtainMessage();
				try {
					HttpResponse response = new DefaultHttpClient()
							.execute(request);
					if (response.getStatusLine().getStatusCode() == 200) {
						result = EntityUtils.toString(response.getEntity(),
								HTTP.UTF_8);
						// 数据获取后发送给handler处理数据
						msg.obj = result;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	// 得到系统当前的时间信息
	private void getMyDate() {
		Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
		Date mydate = new Date(); // 获取当前日期Date对象
		mycalendar.setTime(mydate);// //为Calendar对象设置时间为当前日期
		year = mycalendar.get(Calendar.YEAR); // 获取Calendar对象中的年
		month = mycalendar.get(Calendar.MONTH);// 获取Calendar对象中的月
		day = mycalendar.get(Calendar.DAY_OF_MONTH);// 获取这个月的第几天
		today = String.valueOf(year) + String.valueOf(month+1)
				+ String.valueOf(day);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		TextView tv = (TextView) view.findViewById(R.id.tv_lv_id);
		Intent intent = new Intent(getActivity(), ContentInfo.class);
		intent.putExtra("flag", "更新");
		intent.putExtra("id", tv.getText().toString());
		startActivity(intent);
	}

}
