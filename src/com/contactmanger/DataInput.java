package com.contactmanger;


import java.io.File;

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
		Bitmap imgBitmap = null;
		imagePath = getRealPathFromURI(imageUri);
		//imgUpload.setImageURI(imagePath);
		Log.d(LOG_TAG, "path imageFile in DataInput Activity = " + imagePath);
		File imgFile = new  File(imagePath);
		Log.d(LOG_TAG, "exists? imageFile in DataInput Activity = " + imgFile.exists());
		if(imgFile.exists()){
			imgBitmap = BitmapFactory.decodeFile(imgFile.getPath());
			Log.d(LOG_TAG, "imageBitmap in DataInput Activity = " + imgBitmap.getHeight());
			imgUpload.setImageBitmap(imgBitmap);
		}
        /*try {
        	//itemPic = Media.getBitmap(getContentResolver(), imagePath);
        	//itemPic = BitmapFactory.decodeFile("path of your img1");
        	imgUpload.setImageURI(Uri.fromFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
        //imgUpload.setImageBitmap(itemPic);
	}

	public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                        proj, // Which columns to return
                        null,       // WHERE clause; which rows to return (all rows)
                        null,       // WHERE clause selection arguments (none)
                        null); // Order-by clause (ascending by name)
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
				Intent intent_mainActivity = new Intent(this, MainActivity.class);
				startActivity(intent_mainActivity);
				db.close();
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
	
}
