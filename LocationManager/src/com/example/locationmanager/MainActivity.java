package com.example.locationmanager;

import android.app.Activity;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mTextView;
	private LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.city_text);
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);
		setLocationOption();
		mLocationClient.start();
	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			mTextView.setText(location.getLatitude()+"---"+location.getLongitude()+"---"+location.getCity());
		}
		@Override
		public void onReceivePoi(BDLocation arg0) {
		}
	}

	@Override
	public void onDestroy() {
		mLocationClient.stop();
		super.onDestroy();
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setPriority(LocationClientOption.GpsFirst); // gps
		option.setPoiNumber(10);
		option.disableCache(true);
		mLocationClient.setLocOption(option);
	}
}
