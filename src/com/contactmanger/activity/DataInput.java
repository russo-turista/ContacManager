package com.contactmanger.activity;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.contactmanger.R;
import com.contactmanger.database.DB;
import com.contactmanger.service.BitmapTask;
import com.contactmanger.service.ImageLoader;

public class DataInput extends Activity implements
		android.view.View.OnClickListener {
	private final String LOG_TAG = "myLogs";

	private Button btnCancel;
	private Button btnSend;
	private Button deleteText;

	private CheckBox chBoxIsFavorite;

	private TextView lengthDescription;
	private EditText editText;
	private ImageView imgUpload;
	private Bitmap itemPic = null;
	private String imageName = "";
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
		imagePath = getRealPathFromURI(imageUri);
		imageName = setThumbnailName(imagePath);
		Log.d(LOG_TAG, "imageName= " + imageName);
		Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show();
		
		imgUpload.setImageBitmap(new ImageLoader(imagePath, imageName).imgBitmap);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			super.onBackPressed();
			break;
		case R.id.btnSend:
			if (editText.getText() != null || imagePath != null) {
				db.addRec(editText.getText().toString(), imageName, isFavorite);
				Intent intent_mainActivity = new Intent(this,
						ListViewActivity.class);
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

	public String getRealPathFromURI(Uri contentUri) {

		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	public String setThumbnailName(String filePath){
		if (filePath != null){
			String arrWords[] = filePath.split("/");
			return "thumb_" + arrWords[arrWords.length - 1];
		}
		return "thumb_no_name";
	}
	public Bitmap getThumbnailBitmap(Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };

		// This method was deprecated in API level 11
		// Cursor cursor = managedQuery(contentUri, proj, null, null, null);

		// Cursor cursor = managedQuery(uri, proj, null, null, null);
		CursorLoader cursorLoader = new CursorLoader(DataInput.this, uri, proj,
				null, null, null);
		Cursor cursor = cursorLoader.loadInBackground();
		/*
		 * Log.d(LOG_TAG, "MediaStore.Images.Media._ID before" +
		 * cursor.getColumnName(0)); Log.d(LOG_TAG,
		 * "MediaStore.Images.Media._ID before" + cursor.getColumnName(1));
		 */

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
		Log.d(LOG_TAG, "MediaStore.Images.Media._ID after");
		cursor.moveToFirst();
		long imageId = cursor.getLong(column_index);
		// cursor.close();

		Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(
				getContentResolver(), imageId,
				MediaStore.Images.Thumbnails.MINI_KIND,
				(BitmapFactory.Options) null);

		return bitmap;
	}

	public String getThumbnailPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media._ID };
		String result = null;
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

		cursor.moveToFirst();
		long imageId = cursor.getLong(column_index);
		cursor.close();

		cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(
				getContentResolver(), imageId,
				MediaStore.Images.Thumbnails.MINI_KIND, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = cursor.getString(cursor
					.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));
			cursor.close();
		}
		return result;
	}

}
