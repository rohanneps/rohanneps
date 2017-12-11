package com.example.win7beta;

import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;

public class Inbox extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		openInbox();
	}
	public void openInbox() {
		String application_name = "com.android.mms";
		try {
		Intent intent = new Intent("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");

		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		List<ResolveInfo> resolveinfo_list = this.getPackageManager()
		.queryIntentActivities(intent, 0);

		for (ResolveInfo info : resolveinfo_list) {
		if (info.activityInfo.packageName
		.equalsIgnoreCase(application_name)) {
		launchComponent(info.activityInfo.packageName,
		info.activityInfo.name);
		break;
		}
		}
		} catch (ActivityNotFoundException e) {
		Toast.makeText(
		this.getApplicationContext(),
		"There was a problem loading the application: "
		+ application_name, Toast.LENGTH_SHORT).show();
		}
		}

		private void launchComponent(String packageName, String name) {
		Intent launch_intent = new Intent("android.intent.action.MAIN");
		launch_intent.addCategory("android.intent.category.LAUNCHER");
		launch_intent.setComponent(new ComponentName(packageName, name));
		launch_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(launch_intent);
		}
		
			
	}


