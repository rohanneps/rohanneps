package com.example.win7beta;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class DrawerClickListener implements OnItemClickListener {
	
	Context mContext;
	MyApp.Pac[] pacsForAdapter;
	PackageManager pmForListener;
	public DrawerClickListener(Context c, MyApp.Pac[] pacs, PackageManager pm){
	mContext=c;
	pacsForAdapter=pacs;
	pmForListener=pm;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
			Intent launchIntent=pmForListener.getLaunchIntentForPackage(pacsForAdapter[pos].name);
			mContext.startActivity(launchIntent);
			}

}
