package com.ne.voiceguide.fragment;

//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.google.android.gms.location.LocationListener;
import com.ne.voiceguide.MainActivity;
import com.ne.voiceguide.R;
//import com.ne.voiceguide.activity.CityActivity;
//import com.ne.voiceguide.adapter.BigSceneListAdapter;
import com.ne.voiceguide.adapter.CityBeanListAdapter;
import com.ne.voiceguide.bean.CityBean;
//import com.ne.voiceguide.util.BaiduLocationUtil;

import com.ne.voiceguide.utils.GoogleLocationUtil;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * 
 * @ClassName: HikingFragment 
 * @author ���ǳ�
 * @Description: TODO 
 * @date 2014��7��8�� ����2:55:39
 */
public class HikingFragment extends Fragment {

	private final static String TAG = "HikingFragment";
	int mNum;  
	private Context mContext ; 
	private LinearLayout hiking_Location ;
	private ListView citybean_listview = null;
	private CityBeanListAdapter mCityBeanListAdapter = null;
	
	private GoogleLocationUtil mGoogleLocationUtil ;
//	private BaiduLocationUtil mLocationUtil;
//	private MyBDLocationListenner mMyBDLocationListenner;
	private AlertDialog.Builder builder;
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		super.onCreate(savedInstanceState);  
		
	}  


	@Override  
	public View onCreateView(LayoutInflater inflater, ViewGroup container,  
			Bundle savedInstanceState) {  
		Log.v(TAG, "onCreateView");
		mContext = inflater.getContext();
		View view = inflater.inflate(R.layout.fragment_hiking, container, false);

		citybean_listview = (ListView)view.findViewById(R.id.citybean_listview);
		mCityBeanListAdapter = new CityBeanListAdapter(mContext);
		citybean_listview.setAdapter(mCityBeanListAdapter);
		Log.v(TAG, "citybean_listview");

		citybean_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				CityBean mCityBean = mCityBeanListAdapter.getItem(position);
//				Intent intent = new Intent(mContext,CityActivity.class); // ��ת�����о�������ҳ�� 
//				Bundle bundle = new Bundle();                           //����Bundle����   
//				bundle.putString("cityName", mCityBean.getCityName());     //װ������  
//				bundle.putInt("cityID", mCityBean.getCityID());
//				bundle.putString("cityPinyin", mCityBean.getCityPinyin());
//				intent.putExtras(bundle);                            //��Bundle����Intent����   
//				startActivity(intent);                                     //��ʼ�л� 
			}
		});

		hiking_Location = (LinearLayout)view.findViewById(R.id.hiking_Location);
		hiking_Location.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v(TAG, "hiking_Location is clicked");
				mGoogleLocationUtil.connectOneTime();
//				mLocationUtil.requestLocation();
				
			}
		});
//		mMyBDLocationListenner = new MyBDLocationListenner();
//		mLocationUtil = new BaiduLocationUtil(mContext, mMyBDLocationListenner);
		mGoogleLocationUtil = new GoogleLocationUtil(mContext,new LocationListener() 
		{
			private CityBean mCityBean;
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				Log.v(TAG, "onLocationChanged");
				if (location == null)
					return ;
				mCityBean = mCityBeanListAdapter.getNearestCity(location.getLatitude(),location.getLongitude());
				//			cityName = location.getCity();
				Log.v(TAG, "city:"+location.getLatitude());
//							Log.v(TAG, "city:"+location.getCity());
				
			}
		});
		return view;  
	}  

//	/**
//	 * ��λSDK��������
//	 */
//	public class MyBDLocationListenner implements BDLocationListener {
//
//		private String cityName;
//		private CityBean mCityBean;
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null)
//				return ;
//			mCityBean = mCityBeanListAdapter.getNearestCity(location.getLatitude(),location.getLongitude());
////			cityName = location.getCity();
//			Log.v(TAG, "city:"+location.getLatitude());
////			Log.v(TAG, "city:"+location.getCity());
//			builder = new Builder(mContext);
//			builder.setMessage("ȷ��������");
//			builder.setTitle("����ĳ���  "+mCityBean.getCityName());
//			builder.setPositiveButton("ȷ��", new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					Intent intent = new Intent(mContext,CityActivity.class); // ��ת�����о�������ҳ�� 
//					Bundle bundle = new Bundle();                           //����Bundle����   
//					bundle.putString("cityName", mCityBean.getCityName());     //װ������  
//					bundle.putInt("cityID", mCityBean.getCityID());
//					intent.putExtras(bundle);                               //��Bundle����Intent����   
//					startActivity(intent);                                     //��ʼ�л� 
//					dialog.dismiss();
//
//				}
//			});
//			builder.setNegativeButton("ȡ��", new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.dismiss();
//				}
//			});
//			builder.create().show();
//		}
//
//		public void onReceivePoi(BDLocation poiLocation) {
//			if (poiLocation == null){
//				return ;
//			}
//		}
//	}

	@Override  
	public void onActivityCreated(Bundle savedInstanceState) {  
		super.onActivityCreated(savedInstanceState);     
	}  


	@Override  
	public void onDestroyView(){  
		System.out.println(mNum + "mNumDestory");  
		super.onDestroyView();  
	}  

	@Override  
	public void onDestroy(){  
//		mLocationUtil.stop();
//		mLocationUtil=null;
		super.onDestroy();   
	}  

}
