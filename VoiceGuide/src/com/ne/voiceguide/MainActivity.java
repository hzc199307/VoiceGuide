package com.ne.voiceguide;

import java.io.IOException;

import com.ne.voiceguide.R;
import com.ne.voiceguide.fragment.HikingFragment;
import com.ne.voiceguide.DBHelper.DBHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity"; 
	
	protected boolean _active = true;
	protected int _splashTime = 3000;

	final ScaleAnimation animation =new ScaleAnimation(1.0f, 1.25f, 1.0f, 1.25f, 
			Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); 
	final AlphaAnimation alphaAnimation =new AlphaAnimation(1.0f, 0.0f); 
	private ImageView mImageView;
	private FrameLayout fl_welcome;

	private Handler handler = new Handler() {  
		@Override  
		public void handleMessage(Message msg) {  
			if (msg.what == View.INVISIBLE) {  
				animation.cancel();
				mImageView.setAnimation(alphaAnimation);
				alphaAnimation.setDuration(1000);//设置动画持续时间 
				alphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态  
				alphaAnimation.startNow();
				alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation arg0) {
						fl_welcome.setVisibility(View.INVISIBLE);
						
					}
				});
				
			}  
		}  
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		onDB();

		mImageView = (ImageView)findViewById(R.id.helloImage);
		fl_welcome = (FrameLayout)findViewById(R.id.fl_welcome);
		animation.setDuration(3000);//设置动画持续时间 
		animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态  
		mImageView.setAnimation(animation); 
		mImageView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				_active = false;
				Log.v(TAG, "onTouchEvent");
				return true;
			}
		});
		/** 开始动画 */ 
		animation.startNow();

		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while(_active && (waited < _splashTime)) {
						sleep(100);
						if(_active) {
							waited += 100;
						}
					}

				} catch(InterruptedException e) {
					// do nothing
				} finally {
					Message msg = new Message();  
					msg.what = View.INVISIBLE;  
					handler.sendMessage(msg);
				}
			}
		};
		splashTread.start();

		initView();
		//		if (savedInstanceState == null) {
		//			getSupportFragmentManager().beginTransaction()
		//			.add(R.id.container, new PlaceholderFragment())
		//			.commit();
		//		}
	}
	/**
	 * 对数据库的操作
	 */
	public void onDB() {
		DBHelper mDBHelper = new DBHelper(this);
		try {
			if(mDBHelper.createDataBase())

				;//mTextView.setText("hashahahhahahha");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Cursor mCursor = mDBHelper
				.query("select * from city where cityid=1 ",
						null);
		mCursor.moveToFirst();
		//mTextView.setText(mCursor.getString(2));
	}

	//定义FragmentTabHost对象
	private FragmentTabHost mTabHost;

	//定义一个布局
	private LayoutInflater layoutInflater;

	//定义数组来存放Fragment界面
	private Class fragmentArray[] = {HikingFragment.class,HikingFragment.class,HikingFragment.class};

	//定义数组来存放按钮图片
	private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_selfinfo_btn,R.drawable.tab_more_btn};

	//Tab选项卡的文字
	private String mTextviewArray[] = {"发现旅程", "我的", "更多"};
	//
	private void initView(){
		//实例化布局对象
		layoutInflater = LayoutInflater.from(this);
				
		//实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);	
		
		//得到fragment的个数
		int count = fragmentArray.length;	
				
		for(int i = 0; i < count; i++){	
			//为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
			//将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			//设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.home_btn_bg);
		}
	}
				
	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index){
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
	
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		
		TextView textView = (TextView) view.findViewById(R.id.textview);		
		textView.setText(mTextviewArray[index]);
	
		return view;
	}

	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//
	//		// Inflate the menu; this adds items to the action bar if it is present.
	//		getMenuInflater().inflate(R.menu.main, menu);
	//		return true;
	//	}
	//
	//	@Override
	//	public boolean onOptionsItemSelected(MenuItem item) {
	//		// Handle action bar item clicks here. The action bar will
	//		// automatically handle clicks on the Home/Up button, so long
	//		// as you specify a parent activity in AndroidManifest.xml.
	//		int id = item.getItemId();
	//		if (id == R.id.action_settings) {
	//			return true;
	//		}
	//		return super.onOptionsItemSelected(item);
	//	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//Log.v(TAG, "onTouchEvent");
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			_active = false;
			//Log.v(TAG, "onTouchEvent");
			Toast.makeText(this, "onTouchEvent", Toast.LENGTH_SHORT);
		}
		return super.onTouchEvent(event);
	}
}
