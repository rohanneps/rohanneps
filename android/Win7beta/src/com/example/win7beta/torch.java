package com.example.win7beta;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class torch extends Activity {
	private boolean isFlashOn = false;
	private Camera camera;
	private Button button;

	@Override
	protected void onStop() {
		super.onStop();

		if (camera != null) {
			camera.release();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.torlay);
		button = (Button) findViewById(R.id.buttonFlashlight);
		Context context = this;
		PackageManager pm = context.getPackageManager();

		
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Log.e("err", "Device has no camera!");
			Toast.makeText(getApplicationContext(),
					"Your device doesn't have camera!",
					Toast.LENGTH_SHORT).show();

			return;
		}

		camera = Camera.open();
		final Parameters p = camera.getParameters();

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (isFlashOn) {
					Log.i("info", "torch is turned off!");
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(p);					
					isFlashOn = false;
					button.setText("Torch-ON");
				} else {
					Log.i("info", "torch is turned on!");
					p.setFlashMode(Parameters.FLASH_MODE_TORCH);
					camera.setParameters(p);					
					isFlashOn = true;
					button.setText("Torch-OFF");
				}
			}
		});

	}
}