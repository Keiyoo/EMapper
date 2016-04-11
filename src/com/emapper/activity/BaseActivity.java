package com.emapper.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;

public class BaseActivity extends Activity {
	private Dialog dialog;
	public PopupWindow mPopupWindow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public void openActivity(Context context, Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		context.startActivity(intent);
	}

	public void starActivity(Context context, Class<?> cls, String type) {
		Intent intent = new Intent();
		intent.setClass(context, cls);
		intent.putExtra("type", type);
		context.startActivity(intent);
	}
	public void showDialog(String title, String msg,
			android.content.DialogInterface.OnClickListener mOkOnClickListener,
			android.content.DialogInterface.OnClickListener nOkOnClickListener) {
		dialog = new AlertDialog.Builder(this).setTitle(title).setMessage(msg)
				.setPositiveButton(getString(R.string.ok), mOkOnClickListener)
				.setNeutralButton(getString(R.string.no), nOkOnClickListener)
				.create();
		dialog.show();
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}
	public void showDialog( String msg,
			android.content.DialogInterface.OnClickListener mOkOnClickListener
			) {
		dialog = new AlertDialog.Builder(this).setMessage(msg)
				.setPositiveButton(getString(R.string.ok1), mOkOnClickListener)
				.create();
		dialog.show();
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}
	public void showPop(View dropDownview,OnClickListener mOkOnClickListener,OnClickListener nOkOnClickListener) {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		} else {
			View view = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.popup_window, null);
			mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			mPopupWindow.showAsDropDown(dropDownview);
			TextView thickLine = (TextView) view.findViewById(R.id.text_thick);
			TextView thinLine = (TextView) view.findViewById(R.id.text_thin);
			thickLine.setOnClickListener(mOkOnClickListener );
			thinLine.setOnClickListener(nOkOnClickListener );
		}
	}
}
