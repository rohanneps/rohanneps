package com.example.win7beta;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
public class imgStr extends Activity
{
      Bitmap bitmap;
      int lastImageRef;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.setimg);
            ImageView imagePreview1 = (ImageView)findViewById(R.id.imageView1);
            ImageView imagePreview2 = (ImageView)findViewById(R.id.imageView2);
            ImageView imagePreview3 = (ImageView)findViewById(R.id.imageView3);
            imagePreview1.setImageResource(R.drawable.image1);
            imagePreview2.setImageResource(R.drawable.image2);
            imagePreview3.setImageResource(R.drawable.image3);
            imagePreview1.setOnClickListener(new ImageView.OnClickListener(){
                  //@Override
                  public void onClick(View arg0) {
                	 	Toast.makeText(getApplicationContext(), "Background changed to image 1\n Please restart the app to implement", Toast.LENGTH_LONG).show();
                saveBack();
                        	
                        }

				private void saveBack() {
					String back="image1";
					SharedPreferences pref=getSharedPreferences("Back",MODE_PRIVATE);
					SharedPreferences.Editor editor=pref.edit();
					editor.putString("Insert", back);
					editor.commit();
					
				}

				
                  });
           
           
            imagePreview2.setOnClickListener(new ImageView.OnClickListener(){
                //@Override
                public void onClick(View arg0) {
                	Toast.makeText(getApplicationContext(), "Background changed to image 2\n Please restart the app to implement", Toast.LENGTH_LONG).show();
              saveBack();
                      	
                      }

				private void saveBack() {
					String back="image2";
					SharedPreferences pref=getSharedPreferences("Back",MODE_PRIVATE);
					SharedPreferences.Editor editor=pref.edit();
					editor.putString("Insert", back);
					editor.commit();
					
				}

				
                });    
            imagePreview3.setOnClickListener(new ImageView.OnClickListener(){
                //@Override
                public void onClick(View arg0) {
                	Toast.makeText(getApplicationContext(), "Background changed to image 3\n Please restart the app to implement", Toast.LENGTH_LONG).show();
              saveBack();
                      	
                      }

				private void saveBack() {
					String back="image3";
					SharedPreferences pref=getSharedPreferences("Back",MODE_PRIVATE);
					SharedPreferences.Editor editor=pref.edit();
					editor.putString("Insert", back);
					editor.commit();
				
				}

				
                });
}
      @Override
  	public boolean onKeyDown(int keyCode, KeyEvent event) {
          if (keyCode == KeyEvent.KEYCODE_BACK) {
          	Intent i=new Intent(getApplicationContext(),MainActivity.class);
          	startActivity(i);
          }
         return false;
      }
}