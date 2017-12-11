package com.example.win7beta;


import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class camera extends Activity implements OnClickListener {
 
    Button btnTackPic;
    TextView tvHasCamera, tvHasCameraApp;
    ImageView ivThumbnailPhoto;
    Bitmap bitMap;
    static int TAKE_PICTURE = 1;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camlay);
 
        // Get reference to views
        tvHasCamera = (TextView) findViewById(R.id.tvHasCamera);
        tvHasCameraApp = (TextView) findViewById(R.id.tvHasCameraApp);
        btnTackPic = (Button) findViewById(R.id.btnTakePic);
        ivThumbnailPhoto = (ImageView) findViewById(R.id.ivThumbnailPhoto);
 
        // Does your device have a camera?
        if(hasCamera()){
            tvHasCamera.setBackgroundColor(0xFF00CC00);
            tvHasCamera.setText("You have Camera");
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "Sorry, you phone doesn't support Camera", 
        			   Toast.LENGTH_LONG).show();

        }
 
        // Do you have Camera Apps?
        if(hasDefualtCameraApp(MediaStore.ACTION_IMAGE_CAPTURE)){
            tvHasCameraApp.setBackgroundColor(0xFF00CC00);
            tvHasCameraApp.setText("The Preview will be displayed here");
        }
        else
        {
        	Toast.makeText(getApplicationContext(), "Sorry,  you phone doesn't have a camera Application",Toast.LENGTH_LONG).show();
        }
 
        // add onclick listener to the button
        btnTackPic.setOnClickListener(this);
 
    }
 
    // on button "btnTackPic" is clicked
    @Override
    public void onClick(View view) {
 
        // create intent with ACTION_IMAGE_CAPTURE action 
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
        // start camera activity
        startActivityForResult(intent, TAKE_PICTURE);
 
    }
 
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
 
        if (requestCode == TAKE_PICTURE && resultCode== RESULT_OK && intent != null){
            // get bundle
            Bundle extras = intent.getExtras();
 
            // get bitmap
            bitMap = (Bitmap) extras.get("data");
            ivThumbnailPhoto.setImageBitmap(bitMap);
            //store image
            super.onActivityResult(requestCode, resultCode, intent);
            Bitmap bm = (Bitmap) intent.getExtras().get("data");
            MediaStore.Images.Media.insertImage(getContentResolver(), bm, null, null);
            
            
                ByteArrayOutputStream baos = new ByteArrayOutputStream();  
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
                byte[] b = baos.toByteArray();  
 
        }
    }
 
    // method to check if you have a Camera
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
 
    // method to check you have Camera Apps
    private boolean hasDefualtCameraApp(String action){
        final PackageManager packageManager = getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
 
        return list.size() > 0;
 
    }
}