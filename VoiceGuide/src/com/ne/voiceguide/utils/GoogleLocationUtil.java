package com.ne.voiceguide.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

/**
 * google��ͼ ��λ����
 * @ClassName: GoogleLocationUtil 
 * @Description: TODO 
 * @author HeZhichao
 * @date 2014��6��26�� ����3:11:08 
 *
 */
public class GoogleLocationUtil {

	private final static String TAG = "GoogleLocationUtil";
	
	private LocationClient mLocationClient;
	private Context mContext;
	private LocationListener locationListener;
	
	// These settings are the same as the settings for the map. They will in fact give you updates
    // at the maximal rates currently possible.
	public static LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    
	public static final LocationRequest REQUEST_ONE = LocationRequest.create()
    		.setNumUpdates(1)
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    
	public GoogleLocationUtil(Context mContext,LocationListener locationListener) {
		this.mContext = mContext;
		this.locationListener = locationListener;
	}
	
	//����LocationRequest
	public void LocationRequest(LocationRequest requst)
	{
		REQUEST = requst;
	}
	
	public LocationRequest getLocationRequest()
	{
		return REQUEST;
	}
	
	//����
	public void connect() {
    	if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    mContext,
                    new MyConnectionCallbacks(REQUEST),  // ConnectionCallbacks
                    new MyOnConnectionFailedListener()); // OnConnectionFailedListener
            
        }
        if(mLocationClient.isConnected()==false)
        	mLocationClient.connect();
    }
	public void connectOneTime() {
    	if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    mContext,
                    new MyConnectionCallbacks(REQUEST_ONE),  // ConnectionCallbacks
                    new MyOnConnectionFailedListener()); // OnConnectionFailedListener
            
        }
        if(mLocationClient.isConnected()==false)
        	mLocationClient.connect();
    }

	//�Ͽ�����
    public void disconnect() {
        if (mLocationClient != null&&mLocationClient.isConnected()) {
            mLocationClient.disconnect();
            mLocationClient=null;
        }
    }
    
    class MyConnectionCallbacks implements ConnectionCallbacks
    {

    	LocationRequest lr ;
    	
		public MyConnectionCallbacks(LocationRequest lr) {
			this.lr = lr;
		}
		@Override
		public void onConnected(Bundle arg0) {
			//��ʼ��ȡλ��
			Log.v(TAG,"MyConnectionCallbacks onConnected");
			mLocationClient.requestLocationUpdates(
					lr,
	                locationListener);  // LocationListener
		}

		@Override
		public void onDisconnected() {
			// ��ִ�� LocationClient.disconnect(); ֮ǰ������Ϊ���϶�ʧȥ����
			mLocationClient = null;
		}
    	
    }
    
    class MyOnConnectionFailedListener implements OnConnectionFailedListener
    {

		@Override
		public void onConnectionFailed(ConnectionResult arg0) {
			// TODO Auto-generated method stub
			
		}
		
    }
    /**
     * �ڱ�ʵ�� ��λ�仯 �ص�����
     */
//    class MyLocationListener implements LocationListener
//    {
//
//		@Override
//		public void onLocationChanged(Location arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//    	
//    }
}
