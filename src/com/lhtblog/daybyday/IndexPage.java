package com.lhtblog.daybyday;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import function.SnCal;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class IndexPage extends Fragment implements OnClickListener {
	private TextView city;
	private TextView date;
	private TextView shishi;
	private TextView weather;
	private TextView temple;
	private TextView tips;
	private Button button;
	private Handler handler;
	private ImageView weatherPic;
	private ListView showdata;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tab1 = inflater.inflate(R.layout.tab1, container, false);
		init(tab1);
		return tab1;
	}

	public void init(View v) {
		showdata=(ListView) v.findViewById(R.id.lv_tab1_show);
		weatherPic=(ImageView) v.findViewById(R.id.iv_tab1_type);
		city=(TextView) v.findViewById(R.id.tv_tab1_city);
		date = (TextView) v.findViewById(R.id.tv_tab1_date);
		shishi = (TextView) v.findViewById(R.id.tv_tab1_shishiwendu);
		weather=(TextView) v.findViewById(R.id.tv_tab1_weather);
		temple=(TextView) v.findViewById(R.id.tv_tab1_temple);
		tips=(TextView) v.findViewById(R.id.tv_tab1_tips);
		button = (Button) v.findViewById(R.id.btn_tab1_new);
		button.setOnClickListener(this);
		
		handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				try {
					JSONObject dataJson=new JSONObject((String)msg.obj);
					date.setText(dataJson.getString("date"));
					JSONArray results=dataJson.getJSONArray("results");
					JSONObject data=results.getJSONObject(0);
					city.setText(data.getString("currentCity"));
					JSONArray index=data.getJSONArray("index");
					JSONObject ganmao=index.getJSONObject(2);
					tips.setText(ganmao.getString("des"));
					JSONArray weather_data=data.getJSONArray("weather_data");
					JSONObject today=weather_data.getJSONObject(0);
					shishi.setText(today.getString("date"));
					weather.setText(today.getString("weather"));
					temple.setText(today.getString("temperature"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
	}
	
	public void getWeather(String citys) {
		citys.replace("市", "");
		final String city=citys;
		
		new Thread() {
			public void run() {
				SnCal sn=new SnCal(city);
				String url = null;
				try {
					url = "http://api.map.baidu.com/telematics/v3/weather?location="
							+ city + "&output=json&ak=sbiOMaZAHDWksopxYLKfihxT&sn="+sn.getUrl();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				HttpGet request = new HttpGet(url);
				String result = null;
				Message msg=handler.obtainMessage();
				try {
					HttpResponse response = new DefaultHttpClient()
							.execute(request);
					if (response.getStatusLine().getStatusCode() == 200) {
						result = EntityUtils.toString(response.getEntity(),
								HTTP.UTF_8);
						msg.obj=result;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
//		getWeather(MainActivity.city);
		Intent intent=new Intent(getActivity(),ContentInfo.class);
		startActivity(intent);
	}


}
