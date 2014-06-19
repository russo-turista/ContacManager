package com.contactmanger;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DataInput extends Activity implements android.view.View.OnClickListener{
	final String LOG_TAG = "myLogs";
	final String HEADER_DIALOG = "Select image source";
	final int DIALOG_ITEMS = 0;
	
	final int CAMERA_REQUEST = 0;
	final int GALLERY_REQUEST = 1;
	
	String dataDialog[] = { "From Camera", "From SD Card" };
	
	Button btnCancel;
	Button btnSend;
	Button deleteText;
	TextView lengthDescription;
	EditText editText;
	ImageView imgUpload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_photo);

		showDialog(DIALOG_ITEMS);
		
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnSend = (Button) findViewById(R.id.btnSend);
		imgUpload = (ImageView) findViewById(R.id.imgUpload);
		
		deleteText = (Button) findViewById(R.id.deleteText);
		editText = (EditText) findViewById(R.id.editText);
		lengthDescription = (TextView) findViewById(R.id.lengthDescription);
		
		btnCancel.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		deleteText.setOnClickListener(this);
	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		if (id == DIALOG_ITEMS) {
			adb.setTitle(HEADER_DIALOG);
			adb.setItems(dataDialog, dialogClickListener);
		}
		return adb.create();
	}

OnClickListener dialogClickListener = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case GALLERY_REQUEST:
				Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
				photoGalleryIntent.setType("image/*");
				startActivityForResult(photoGalleryIntent, GALLERY_REQUEST);    
				break;
			case CAMERA_REQUEST:
				Intent photoCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(photoCameraIntent, CAMERA_REQUEST);    
				break;	
			default:
				break;
			}
			
			Log.d(LOG_TAG, "which = " + which);
		}
	};

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			Intent intent_allcontacts = new Intent(this, MainActivity.class);
	    	intent_allcontacts.putExtra("favorite", false);
	    	startActivity(intent_allcontacts);
			break;
		default:
			break;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
	    super.onActivityResult(requestCode, resultCode, data); 
	    
	    Bitmap itemPic = null;
	    if (requestCode == CAMERA_REQUEST || requestCode == GALLERY_REQUEST){
	    	if (resultCode == RESULT_OK){
	    		Uri selectedImage = data.getData();
	            try {
	            	itemPic = Media.getBitmap(getContentResolver(), selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            imgUpload.setImageURI(selectedImage);;
	            Log.d(LOG_TAG, "path image = " + selectedImage.getPath());
	    	}	
	    }
	    /*switch(requestCode) { 
	    case CAMERA_REQUEST:
	    	if (resultCode == RESULT_OK){
	    		itemPic = (Bitmap) data.getExtras().get("data");
	    		Uri selectedImage = data.getData();
	    		imgUpload.setImageBitmap(itemPic);
	    	}	
	    	break;
	    case GALLERY_REQUEST:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = data.getData();
	            try {
	            	itemPic = Media.getBitmap(getContentResolver(), selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            imgUpload.setImageBitmap(itemPic);
	        }
	        break;
	    default:
	    	break;
	    }*/
	    	
	}
}
