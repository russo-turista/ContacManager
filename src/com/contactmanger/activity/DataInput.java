package com.contactmanger.activity;


import java.io.File;

import com.contactmanger.R;
import com.contactmanger.R.id;
import com.contactmanger.R.layout;
import com.contactmanger.database.DB;
import com.contactmanger.service.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleCursorAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DataInput extends Activity implements android.view.View.OnClickListener{
	private final String LOG_TAG = "myLogs";
	
	private Button btnCancel;
	private Button btnSend;
	private Button deleteText;
	
	private CheckBox chBoxIsFavorite;
	
	private TextView lengthDescription;
	private EditText editText;
	private ImageView imgUpload;
	private Bitmap itemPic = null;
	private Uri imageUri;
	
	private String imagePath;
	private boolean isFavorite = false;
	
	private ImageLoader imgBitmap = new ImageLoader();
	private DB db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_photo);
		
		db = new DB(this);
		db.open();


		
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnSend = (Button) findViewById(R.id.btnSend);
		deleteText = (Button) findViewById(R.id.deleteText);
		
		chBoxIsFavorite = (CheckBox) findViewById(R.id.isFavorite);
				
		imgUpload = (ImageView) findViewById(R.id.imgUpload);

		editText = (EditText) findViewById(R.id.editText);
		lengthDescription = (TextView) findViewById(R.id.lengthDescription);
		
		btnCancel.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		deleteText.setOnClickListener(this);
		
		chBoxIsFavorite.setOnCheckedChangeListener(isFavoriteChackBoxListner);
		
		Intent intent = getIntent();
		imageUri = intent.getParcelableExtra("imagePath");
		imagePath = getRealPathFromURI(imageUri);
		itemPic = imgBitmap.createBitmap(imagePath);
		
		if (itemPic != null){
			imgUpload.setImageBitmap(itemPic);
		}else {
			//
		}
	}

	public String getRealPathFromURI(Uri contentUri) {
        
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                        proj, 
                        null,      
                        null,     
                        null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			super.onBackPressed();
			break;
		case R.id.btnSend:
			if (editText.getText() != null || imagePath != null ){
				db.addRec(editText.getText().toString(), imagePath, isFavorite);
				Intent intent_mainActivity = new Intent(this, ListViewActivity.class);
				startActivity(intent_mainActivity);
			}
			break;
		default:
			break;
		}
	}
	OnCheckedChangeListener isFavoriteChackBoxListner = new OnCheckedChangeListener() {
	    public void onCheckedChanged(CompoundButton buttonView,
	        boolean isChecked) {
	    	Log.d(LOG_TAG, "is Favorite chackBox = " + isChecked);
	    }
	  };
	  @Override
		protected void onDestroy() {
			super.onDestroy();
			db.close();
		}
}
