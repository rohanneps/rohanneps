package com.example.win7beta;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class fm extends ListActivity {

 

 private List<String> item = null;

 private List<String> path = null;

 private String root="/";

 private TextView myPath;

 

    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.fml);

        myPath = (TextView)findViewById(R.id.path);

        getDir(root);

    }

    

    private void getDir(String dirPath)

    {

     myPath.setText("Location: Phone" + dirPath);

     

     item = new ArrayList<String>();

     path = new ArrayList<String>();

     

     File f = new File(dirPath);

     File[] files = f.listFiles();

     

     if(!dirPath.equals(root))

     {



      item.add(root);

      path.add(root);

      

      item.add("<-");

      path.add(f.getParent());

            

     }

     

     for(int i=0; i < files.length; i++)

     {

       File file = files[i];

       path.add(file.getPath());

       if(file.isDirectory())

        item.add(file.getName() + "/");

       else

        item.add(file.getName());

     }



     ArrayAdapter<String> fileList =

      new ArrayAdapter<String>(this, R.layout.row, item);

     setListAdapter(fileList);

    }



 @Override

 protected void onListItemClick(ListView l, View v, int position, long id) {

  

  File file = new File(path.get(position));

  

  if (file.isDirectory())

  {

   if(file.canRead())

    getDir(path.get(position));

   else

   {

    new AlertDialog.Builder(this)

    

    .setTitle("[" + file.getName() + "] folder can't be read!")

    .setPositiveButton("OK", 

      new DialogInterface.OnClickListener() {

       

       @Override

       public void onClick(DialogInterface dialog, int which) {

        // TODO Auto-generated method stub

       }

      }).show();

   }

  }

  else

  { //getting file extension
	  String extension = "";
	  String fileName=file.getName();
	  int i = fileName.lastIndexOf('.');
	  if (i > 0) {
	      extension = fileName.substring(i+1);
	  }
	  Intent fileIntent = new Intent();
	  //comparing file extensions to open files
	  if (extension.compareToIgnoreCase("mp3")==0 || extension.compareToIgnoreCase("ogg")==0 || extension.compareToIgnoreCase("wav")==0){
		  File musicFile2Play = new File(file.getPath());
		  
		  fileIntent.setAction(android.content.Intent.ACTION_VIEW);
		  fileIntent.setDataAndType(Uri.fromFile(musicFile2Play), "audio/mp3");
		  startActivity(fileIntent);
	  }
	  if(extension.compareToIgnoreCase("mpeg")==0 || extension.compareToIgnoreCase("3gp")==0){
		  File videoFile2Play = new File(file.getPath());
		  
		  fileIntent.setAction(android.content.Intent.ACTION_VIEW);
		  fileIntent.setDataAndType(Uri.fromFile(videoFile2Play), "video/mpeg");
		  startActivity(fileIntent);
	  }
	  if(extension.compareToIgnoreCase("txt")==0 || extension.compareToIgnoreCase("csv")==0 || extension.compareToIgnoreCase("xml")==0){
		  File textFile = new File(file.getPath());
		  
		  fileIntent.setAction(android.content.Intent.ACTION_VIEW);
		  fileIntent.setDataAndType(Uri.fromFile(textFile), "text/plain");
		  startActivity(fileIntent);
	  }
	  if(extension.compareToIgnoreCase("png")==0 || extension.compareToIgnoreCase("gif")==0 || extension.compareToIgnoreCase("jpg")==0 || extension.compareToIgnoreCase("jpeg")==0 || extension.compareToIgnoreCase("bmp")==0){
		  File imageFile = new File(file.getPath());
		  
		  fileIntent.setAction(android.content.Intent.ACTION_VIEW);
		  fileIntent.setDataAndType(Uri.fromFile(imageFile), "image/png");
		  startActivity(fileIntent);
	  }
	  if(extension.compareToIgnoreCase("apk")==0){
File apkFile = new File(file.getPath());
		  
		  fileIntent.setAction(android.content.Intent.ACTION_VIEW);
		  fileIntent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
		  startActivity(fileIntent);
	  }
	  if(extension.compareToIgnoreCase("htm")==0 || extension.compareToIgnoreCase("html")==0 ||extension.compareToIgnoreCase("php")==0){
		  File webFile = new File(file.getPath());
		  		  
		  		  fileIntent.setAction(android.content.Intent.ACTION_VIEW);
		  		  fileIntent.setDataAndType(Uri.fromFile(webFile), "text/html");
		  		  startActivity(fileIntent);
		  	  }
   }

 // }

 }

}