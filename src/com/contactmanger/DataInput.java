package com.contactmanger;


import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DataInput extends Activity implements android.view.View.OnClickListener{
	final String LOG_TAG = "myLogs";
	
	Button btnCancel;
	Button btnSend;
	Button deleteText;
	
	TextView lengthDescription;
	EditText editText;
	ImageView imgUpload;
	Bitmap itemPic = null;
	Uri imagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_photo);

		
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnSend = (Button) findViewById(R.id.btnSend);
		
		imgUpload = (ImageView) findViewById(R.id.imgUpload);
		deleteText = (Button) findViewById(R.id.deleteText);
		editText = (EditText) findViewById(R.id.editText);
		lengthDescription = (TextView) findViewById(R.id.lengthDescription);
		
		
		btnCancel.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		deleteText.setOnClickListener(this);
		
		Intent intent = getIntent();
		Log.d(LOG_TAG, "path image in DataInput Activity = " + intent.getStringExtra("imagePath"));
		imagePath = intent.getParcelableExtra("imagePath");
		//imgUpload.setImageURI(imagePath);
		
        try {
        	itemPic = Media.getBitmap(getContentResolver(), imagePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        imgUpload.setImageBitmap(itemPic);
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			/*Intent intent_allcontacts = new Intent(this, MainActivity.class);
	    	intent_allcontacts.putExtra("favorite", false);
	    	startActivity(intent_allcontacts);*/
			super.onBackPressed();
			break;
		case R.id.btnSend:
			break;
		default:
			break;
		}
		
	}
	
}
