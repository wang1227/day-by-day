package com.lhtblog.daybyday;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Index extends Fragment implements OnClickListener {
	public TextView textView;
	Button button;
	private LocationClient mLocationClient;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tab1 = inflater.inflate(R.layout.tab1, container, false);
		init(tab1);
		mLocationClient = ((LocationApplication)getActivity().getApplication()).mLocationClient;
		((LocationApplication)getActivity().getApplication()).mLocationResult = (TextView) tab1.findViewById(R.id.tv_tab1_date);
		return tab1;
	}

	public void init(View v) {
		textView = (TextView) v.findViewById(R.id.tv_tab1_date);
		button = (Button) v.findViewById(R.id.btn_tab1_new);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		InitLocation();
		mLocationClient.start();
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Battery_Saving);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		// int span=5000;
		// option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	
}
