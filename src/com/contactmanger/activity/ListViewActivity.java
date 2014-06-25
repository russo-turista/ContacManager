package com.contactmanger.activity;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.contactmanger.R;
import com.contactmanger.database.DB;
import com.contactmanger.service.CustomBaseAdapter;

public class ListViewActivity extends Activity implements View.OnClickListener{
	
	private final String LOG_TAG = "myLogs";
	
	private final String HEADER_DIALOG = "Select image source";
	private final int DIALOG_ITEMS = 0;
	
	private final int CAMERA_REQUEST = 0;
	private final int GALLERY_REQUEST = 1;
	
	private String dataDialog[] = { "From Camera", "From SD Card" };
	
	private Button btnWelcomPage;
	private Button btnUploadPhoto;
	private ListView listContacts;
	
	private Boolean isFavorite;
	
	private DB db;
	private CustomBaseAdapter cbAdapter;
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		db = new DB(this);
		db.open();
		cursor = db.getAllData();
		//startManagingCursor(cursor);
		
		/*String[] from = new String[] {DB.COLUMN_DESCTRIPTION };
		int[] to = new int[] {R.id.itemText };*/

		//scAdapter = new SimpleCursorAdapter(this, R.layout.item, cursor, from, to);
		cbAdapter = new CustomBaseAdapter(this, cursor);
		listContacts = (ListView) findViewById(R.id.listContacts);
		listContacts.setAdapter(cbAdapter);
		
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
			Intent intent_favorite = new Intent(this, ListViewActivity.class);
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
	            Log.d(LOG_TAG, "path image = " + selectedImage.getPath());
	            Intent intent_dataInput = new Intent(this, DataInput.class);
	            intent_dataInput.putExtra("imagePath", selectedImage);
				startActivity(intent_dataInput);
	    	}	
	    }
	}
	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// закрываем подключение при выходе
		db.close();
	}

	/*@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new CustomCursorLoader(this, db);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		//cbAdapter.swapCursor(cursor);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}*/

}
