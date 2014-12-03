package com.lhtblog.daybyday;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class Tab2 extends Fragment {
	TextView textView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View tab2= inflater.inflate(R.layout.detailed, container,false);
//		 textView=(TextView) tab1.findViewById(R.id.textView1);
//		 textView.setText("tab2");
		return tab2;
		
	}
}
