package com.contactmanger;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
	
	final String LOG_TAG = "myLogs";
	
	final String HEADER_DIALOG = "Select image source";
	final int DIALOG_ITEMS = 0;
	
	final int CAMERA_REQUEST = 0;
	final int GALLERY_REQUEST = 1;
	
	String dataDialog[] = { "From Camera", "From SD Card" };
	
	Button btnWelcomPage;
	Button btnUploadPhoto;
	ListView lvData;
	
	Boolean isFavorite;
	
	DB db;
	SimpleCursorAdapter scAdapter;
	Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*
		 * ListView lvMain = (ListView) findViewById(R.id.listContacts);
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, names);
		 * lvMain.setAdapter(adapter);
		 */

		db = new DB(this);
		db.open();

		cursor = db.getAllData();
		startManagingCursor(cursor);

		String[] from = new String[] { DB.COLUMN_IMG, DB.COLUMN_TXT };
		int[] to = new int[] { R.id.ivImg, R.id.tvText };

		scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
		lvData = (ListView) findViewById(R.id.listContacts);
		lvData.setAdapter(scAdapter);
		
		btnWelcomPage = (Button) findViewById(R.id.btnWelcome);
		btnWelcomPage.setOnClickListener(this);
		
		btnUploadPhoto = (Button) findViewById(R.id.btnUpload);
		btnUploadPhoto.setOnClickListener(this);
		
		Intent intent = getIntent();
		
		isFavorite = intent.getBooleanExtra("favorite", false);
		if (isFavorite) {
			Toast.makeText(this, isFavorite.toString(), Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(this, isFavorite.toString(), Toast.LENGTH_LONG)
					.show();
		}

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
					Log.d(LOG_TAG, "GALLERY_REQUEST = " + which);
					Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
					photoGalleryIntent.setType("image/*");
					startActivityForResult(photoGalleryIntent, GALLERY_REQUEST);    
					break;
				case CAMERA_REQUEST:
					Log.d(LOG_TAG, "CAMERA_REQUEST = " + which);
					Intent photoCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(photoCameraIntent, CAMERA_REQUEST);    
					break;	
				default:
					break;
				}
				
				Log.d(LOG_TAG, "which = " + which);
			}
		};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWelcome:
			Intent intent_welcomPage = new Intent(this, WelcomeActivity.class);
			startActivity(intent_welcomPage);
			// Toast.makeText(this, "allcontactsB", Toast.LENGTH_LONG).show();
			break;
		case R.id.btnFavoriteList:
			Intent intent_favorite = new Intent(this, MainActivity.class);
			intent_favorite.putExtra("favorite", true);
			startActivity(intent_favorite);
			// Toast.makeText(this, "favoriteB", Toast.LENGTH_LONG).show();
			break;
		case R.id.btnUpload:
			showDialog(DIALOG_ITEMS);
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
	            //imgUpload.setImageURI(selectedImage);
	            Log.d(LOG_TAG, "path image = " + selectedImage.getPath());
	            Intent intent_dataInput = new Intent(this, DataInput.class);
	            intent_dataInput.putExtra("imagePath", selectedImage);
				startActivity(intent_dataInput);
	    	}	
	    }
	}    
	 protected void onDestroy() {
		    super.onDestroy();
		    // закрываем подключение при выходе
		    db.close();
		  }

}
