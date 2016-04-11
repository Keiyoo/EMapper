package com.emapper.activity;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import com.android.gis.API;
import com.android.gis.Workspace;
import com.emapper.util.ResPathCenter;

public class LogoActivity extends BaseActivity implements AnimationListener{
	private Animation animation = null;
//	private MapInitHelper mapInitHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setContentView(R.layout.activity_logo);
		RelativeLayout splashDefault = ((RelativeLayout) findViewById(R.id.splashDefault));
		animation = AnimationUtils.loadAnimation(this, R.anim.splashfade);
		animation.setAnimationListener(this);
		splashDefault.startAnimation(animation);
		
		//工作空间路径设置（激活需要）
		EApplication application = EApplication.getInstance();
		ResPathCenter pathCenter = application.resPathCenter;
		String path = pathCenter.getWorkspaceFolderPath();
		API.init(this, path);
//		mapInitHelper = new MapInitHelper(this);
//		mapInitHelper.workspaceInit();
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub

		if(API.LS_IsRegistered()){
			openActivity(this, FirstActivity.class);
		}
		else
		{
			
			openActivity(this, ActivationActivity.class);
		}
		
		finish();
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

}
