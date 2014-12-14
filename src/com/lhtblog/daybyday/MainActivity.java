package com.lhtblog.daybyday;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import function.DatabaseHelper;

public class MainActivity extends Activity {
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	ActionMode mActionMode;
	public static String city;
	private LocationClient mLocationClient;

	// private static final String INSTANCESTATE_TAB = "今日";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 加载定位服务
		mLocationClient = ((LocationApplication) getApplication()).mLocationClient;
		// 加载viewpager
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(2);// 预告加载的页面数量
		final ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE
		// | ActionBar.DISPLAY_SHOW_HOME);
		mTabsAdapter = new TabsAdapter(MainActivity.this, mViewPager);
		mTabsAdapter.addTab(bar.newTab().setText("今日"), IndexPage.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("所有"), SecondPage.class, null);
		bar.setSelectedNavigationItem(0);
		// bar.setSelectedNavigationItem(PreferenceManager
		// .getDefaultSharedPreferences(this).getInt(INSTANCESTATE_TAB, 0));
		InitLocation();
		mLocationClient.start();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/*
	 * 该方法调用了百度LBS服务，提交了相关设置
	 */
	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		// int span=5000;
		// option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	// 保存页面的状态
	// protected void onPause() {
	// super.onPause();
	// SharedPreferences.Editor editor = PreferenceManager
	// .getDefaultSharedPreferences(this).edit();
	// editor.putInt(INSTANCESTATE_TAB, getActionBar()
	// .getSelectedNavigationIndex());
	// editor.commit();
	// }

	public void setActionMode(ActionMode actionMode) {
		mActionMode = actionMode;
	}

	public ActionMode getActionMode() {
		return mActionMode;
	}

	public Fragment getFragment(int tabIndex) {
		return mTabsAdapter.getItem(tabIndex);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.indexmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.checkupdate:
			Toast.makeText(this, "当前已经是最新版本！", Toast.LENGTH_LONG).show();
			break;
		case R.id.about:
			break;
		case R.id.menu_additem:	// 点击新建按钮跳转到添加/修改界面
			Intent intent = new Intent(this, ContentInfo.class);
			intent.putExtra("flag", "新建");
			intent.putExtra("id", "-1");
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class TabsAdapter extends FragmentPagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {
		private final Context mContext;
		private final ActionBar mActionBar;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;

			TabInfo(Class<?> _class, Bundle _args) {
				clss = _class;
				args = _args;
			}
		}

		public TabsAdapter(Activity activity, ViewPager pager) {
			super(activity.getFragmentManager());
			mContext = activity;
			mActionBar = activity.getActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			tab.setTag(info);
			tab.setTabListener(this);
			mTabs.add(info);
			mActionBar.addTab(tab);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			if (info.fragment == null) {
				info.fragment = Fragment.instantiate(mContext,
						info.clss.getName(), info.args);
			}
			return info.fragment;
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			mActionBar.setSelectedNavigationItem(position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object tag = tab.getTag();
			for (int i = 0; i < mTabs.size(); i++) {
				if (mTabs.get(i) == tag) {
					mViewPager.setCurrentItem(i);
				}
			}
			if (!tab.getText().equals("今日")) {
				ActionMode actionMode = ((MainActivity) mContext)
						.getActionMode();
				if (actionMode != null) {
					actionMode.finish();
				}
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}

}
