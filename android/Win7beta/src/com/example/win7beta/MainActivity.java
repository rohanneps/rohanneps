package com.example.win7beta;


import java.io.File;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	public static final String CALCULATOR_PACKAGE ="com.android.calculator2";
    public static final String CALCULATOR_CLASS ="com.android.calculator2.Calculator";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBackg();
		playsound();
		
		Thread mT=null;
		Runnable mRT=new CountDownRunner();
		mT=new Thread(mRT);
		mT.start();
		ImageView s=(ImageView)findViewById(R.id.imageView1);
		final Context context = getApplicationContext();
		
		s.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				click();
				  Intent inent = new Intent(context, msg.class);
				  
			       startActivity(inent);
			}
		});	
		final ImageView start=(ImageView)findViewById(R.id.imageView2);
		//registerForContextMenu(start);
		
		 
         start.setOnClickListener(new OnClickListener() {  
          
          @Override  
          public void onClick(View v) {  
        	  startClick();
           //Creating the instance of PopupMenu  
           PopupMenu popup = new PopupMenu(MainActivity.this,start);  
           //Inflating the Popup using xml file  
           popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());  
          
           //registering popup with OnMenuItemClickListener  
           popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
            public boolean onMenuItemClick(MenuItem item) {  

        		switch(item.getItemId()){
        		
        		case R.id.item1:
        			Intent i = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
        			startActivity(i);
        		break;
        		
        		case R.id.item2:
        			 Intent in = new Intent(getApplicationContext(),camera.class);
        				
        		       startActivity(in);
        		break;
        				
        		case R.id.item3:
        			startActivity(new Intent(Settings.ACTION_SETTINGS));
        		break;
        	
        		case R.id.item4:
        			

        			Intent galleryIntent = new Intent(
        			                    Intent.ACTION_PICK,
        			                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        			startActivityForResult(galleryIntent ,0);
        		break;
        		case R.id.item5:
        			Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setComponent(new ComponentName(
                    CALCULATOR_PACKAGE,
                    CALCULATOR_CLASS));

                  startActivity(intent);
        			break;
        		case R.id.item6:
        			Intent a = new Intent(context,MyApp.class);
  				  
 			       startActivity(a);
        		break;
        		case R.id.item7:
        			 Intent inent1 = new Intent(context, fm.class);
   				  
  			       startActivity(inent1);
        		break;  
        		case R.id.item8:
        			Intent cal = new Intent(Intent.ACTION_VIEW);
        			cal.setData(Uri.parse("content://com.android.calendar/time"));  

        			startActivity(cal);
        			break;
        		case R.id.item9:
        			Intent co = new Intent(AlarmClock.ACTION_SET_ALARM); 
        			startActivity(co); 
        			break;
        
        		case R.id.item10:
        			Intent ic=new Intent(getApplicationContext(),internetConnection.class);
        			startActivity(ic);
        			break;
        		case R.id.item11:
        			String myUri =  "geo:50.08818,14.42021?z=11";
        			Intent map = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(myUri));
        			startActivity(map);
        			break;
        		case R.id.item12:
        			Intent m = new Intent();
        			m.setAction(android.content.Intent.ACTION_VIEW);
        			File file = new File("");
        			m.setDataAndType(Uri.fromFile(file), "audio/*");
        			startActivity(m);
        			break;
        		case R.id.item13:
        			  Intent back = new Intent(getApplicationContext(), imgStr.class);
					  
				       startActivity(back);
				       setBackg();
				       break;
        		}
				return false;  
            }});  
           
           popup.show();//showing popup menu  
          }  
         });
		
		ImageView c=(ImageView)findViewById(R.id.imageButton1);
		c.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				click();
				  Intent inent1 = new Intent(context, fm.class);
				  
			       startActivity(inent1);
			}
		});
		ImageView play=(ImageView)findViewById(R.id.imageView14);
		play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
    			try {
    				click();
    			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
    			} catch (android.content.ActivityNotFoundException anfe) {
    				click();
    			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
    			}
			}
		});
		
		ImageView brow=(ImageView)findViewById(R.id.imageView12);
		brow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				click();
				Intent intr = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
    			startActivity(intr);
    		
			}
		});
		ImageView google=(ImageView)findViewById(R.id.imageView13);
		google.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				 startClick();
				 Intent ser = new Intent(context, googleSearch.class);
				  
			       startActivity(ser);
    		
			}
		});
		ImageView fb=(ImageView)findViewById(R.id.imageView11);
		fb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				 startClick();
				Intent fb = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://root"));
    			startActivity(fb);
			}
		});
		
		ImageView torch=(ImageView)findViewById(R.id.imageView4);
		torch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				click();
				  Intent tinent = new Intent(context, torch.class);
				  
			       startActivity(tinent);
			}
		});
		
		ImageView gm=(ImageView)findViewById(R.id.imageView10);
		gm.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				click();
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
    			sendIntent.setType("plain/text");
    			sendIntent.setData(Uri.parse("test@gmail.com"));
    			sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
    			sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "test@gmail.com" });
    			sendIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
    			sendIntent.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from my demo app :-)");
    			startActivity(sendIntent);
			}
		});
		ImageView wifi=(ImageView)findViewById(R.id.imageView8);
		wifi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				 startClick();
				 startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			}
		});
		ImageView bluetooth=(ImageView)findViewById(R.id.imageView9);
		bluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				 startClick();
				startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
			}
		});
		ImageView dial=(ImageView)findViewById(R.id.imageView5);
		dial.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				click();
				Intent i = new Intent(Intent.ACTION_DIAL, null);
				  
			       startActivity(i);
			}
		});
		ImageView apps=(ImageView)findViewById(R.id.imageView6);
		apps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				click();
				Intent a = new Intent(context,MyApp.class);
				  
			       startActivity(a);
			}
		});
		
		ImageView inbox=(ImageView)findViewById(R.id.imageView7);
		inbox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				click();
				Intent in = new Intent(context,Inbox.class);
				
			       startActivity(in);
			}
		});
		
	}
	private void setBackg() {
		String background=getBackground();
		RelativeLayout r1=(RelativeLayout) findViewById(R.id.RelativeLayout1); 
		if(background.compareToIgnoreCase("IMAGE1")== 0){
			 r1.setBackgroundResource(R.drawable.image1);
		}else if(background.compareToIgnoreCase("IMAGE2")== 0){
			 r1.setBackgroundResource(R.drawable.image2);
		}else if(background.compareToIgnoreCase("IMAGE3")== 0){
			 r1.setBackgroundResource(R.drawable.image3);
		}
        setContentView(r1);
		
	}
	private String getBackground() {
		SharedPreferences pref=getSharedPreferences("Back",MODE_PRIVATE);
		String a=pref.getString("Insert","image1");
		return a;
	}
	private void playsound() {
		 try{   
		        AssetFileDescriptor descriptor = getAssets().openFd("start.mp3");
		        long start = descriptor.getStartOffset();
		        long end = descriptor.getLength();
		        MediaPlayer mediaPlayer=new MediaPlayer();
		        mediaPlayer.setDataSource(descriptor.getFileDescriptor(), start, end);
		        mediaPlayer.prepare();
		        mediaPlayer.start();  
		       } catch (Exception e) {
		           e.printStackTrace();
		       }
		
	}
	private void click() {
		 try{   
		        AssetFileDescriptor descriptor = getAssets().openFd("double.mp3");
		        long start = descriptor.getStartOffset();
		        long end = descriptor.getLength();
		        MediaPlayer mediaPlayer=new MediaPlayer();
		        mediaPlayer.setDataSource(descriptor.getFileDescriptor(), start, end);
		        mediaPlayer.prepare();
		        mediaPlayer.start();  
		       } catch (Exception e) {
		           e.printStackTrace();
		       }
		
	}
	private void startClick() {
		 try{   
		        AssetFileDescriptor descriptor = getAssets().openFd("click.mp3");
		        long start = descriptor.getStartOffset();
		        long end = descriptor.getLength();
		        MediaPlayer mediaPlayer=new MediaPlayer();
		        mediaPlayer.setDataSource(descriptor.getFileDescriptor(), start, end);
		        mediaPlayer.prepare();
		        mediaPlayer.start();  
		       } catch (Exception e) {
		           e.printStackTrace();
		       }
	}
		
	public void openInbox() {
		click();
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
	public void doWork()
	{
		runOnUiThread(new Runnable() {
			public void run(){
				try{
					TextView dt=(TextView)findViewById(R.id.as);
					Date d=new Date();
					int hr=d.getHours();
					int min=d.getMinutes();
					int sec=d.getSeconds();
					String cT=hr+":"+min+":"+sec;
							dt.setText(cT);
							Context dcontext=getApplicationContext();
							DatePicker dp=new DatePicker(dcontext);
							
							int days = Math.abs(dp.getDayOfMonth());
							int months = Math.abs(dp.getMonth())+1;
							int years = Math.abs(dp.getYear());
										
							TextView day = (TextView)findViewById(R.id.textView4);
							day.setText(days+"/"+ months+"/"+years);

				}
				catch(Exception e){}
			}
		});
	}
	class CountDownRunner implements Runnable{
		@Override
		public void run(){
			while(!Thread.currentThread().isInterrupted()){
				try{
					doWork();
					Thread.sleep(1000);
				}
				catch(Exception e){
					Thread.currentThread().interrupt();
				}
			}
		}

}
 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	 try{   
 		        AssetFileDescriptor descriptor = getAssets().openFd("error.mp3");
 		        long start = descriptor.getStartOffset();
 		        long end = descriptor.getLength();
 		        MediaPlayer mediaPlayer=new MediaPlayer();
 		        mediaPlayer.setDataSource(descriptor.getFileDescriptor(), start, end);
 		        mediaPlayer.prepare();
 		        mediaPlayer.start();  
 		       } catch (Exception e) {
 		           e.printStackTrace();
 		       }
        }
       return false;
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
	
		switch(item.getItemId()){
		
	
		
		case R.id.item6:
			new AlertDialog.Builder(this).setTitle("About").setIcon(R.drawable.startbutton).setMessage("This is a simple Android Launcher Application").setNeutralButton("Ok",new DialogInterface.OnClickListener() {
				
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			}).show();
			break;
		
		}
	
		return super.onOptionsItemSelected(item);
	}


}
