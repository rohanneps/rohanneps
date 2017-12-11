package com.example.win7beta;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class msg extends Activity {

private static final int CONTACT_PICKER_RESULT=1 ;
private static final int CONTACT_PICKER_RESULT2=1 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msgl);
		 ImageView getContacts = (ImageView) findViewById(R.id.imageView2);
		    
		   
		    getContacts.setOnClickListener(new View.OnClickListener() {

		        @Override
		        public void onClick(View v) {
		            Intent i = new Intent(Intent.ACTION_PICK,
		                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
		            startActivityForResult(i, CONTACT_PICKER_RESULT);

		        }
		    });


		   
		}

		protected void onActivityResult(int reqCode, int resultCode, Intent data) {
		    super.onActivityResult(reqCode, resultCode, data);
		    if (resultCode == RESULT_OK) {
		        switch (reqCode) {
		        case CONTACT_PICKER_RESULT:
		            Cursor cursor = null;
		            String number = "";
		            String lastName = "";
		            try {

		                Uri result = data.getData();

		                // get the id from the uri
		                String id = result.getLastPathSegment();

		                // query
		                cursor = getContentResolver().query(
		                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
		                        null,
		                        ContactsContract.CommonDataKinds.Phone._ID
		                                + " = ? ", new String[] { id }, null);

		                // cursor = getContentResolver().query(Phone.CONTENT_URI,
		                // null, Phone.CONTACT_ID + "=?", new String[] { id },
		                // null);

		                int numberIdx = cursor.getColumnIndex(Phone.DATA);
		                if (cursor.moveToNext()) {
		                    number = cursor.getString(numberIdx);
		                    // lastName =
		                    // cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
		                } else {
		                    // WE FAILED
		                }
		            } catch (Exception e) {
		                
		                } finally {
		                	
		                if (cursor != null) {
		                    cursor.close();
		                } else {
		                }
		            }EditText numberEditText1 = (EditText) findViewById(R.id.editText1);
		            
		            if(number.length()<11)
		            {
		            	Toast t = Toast.makeText(getApplicationContext(),"Incorrect Number", Toast.LENGTH_SHORT);
		        		t.show();
		            
		            }
		            else
		            {
		            	
		            numberEditText1.setText(number);
		            }
		            // EditText lastNameEditText =
		            // (EditText)findViewById(R.id.last_name);
		            // lastNameEditText.setText(lastName);
		        }
		    
		    }
		ImageView send=  (ImageView)findViewById(R.id.imageView3);
			
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				EditText e1 =  (EditText)findViewById(R.id.editText1);
				String number = e1.getText().toString();
				EditText e2 =  (EditText)findViewById(R.id.editText2);
				String message = e2.getText().toString();
				SmsManager sms = SmsManager.getDefault(); 
				if (number.length()>0 && message.length()>0)  
				{
					sms.sendTextMessage(number,null, message, null,null);   
				Toast t = Toast.makeText(getApplicationContext(), "Message Sent",Toast.LENGTH_SHORT);
				t.show();	
				}
				else
					
                    Toast.makeText(getBaseContext(), 
                        "Please enter both phone number and message.", 
                        Toast.LENGTH_SHORT).show();
				
				
							
			}
		});	
		ImageView refr=(ImageView)findViewById(R.id.imageView1);
		refr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				EditText e1 =  (EditText)findViewById(R.id.editText1);
				e1.setText("");
				EditText e2 =  (EditText)findViewById(R.id.editText2);
				e2.setText("");			
							
			}
		});	
	}

	

}
