package com.contactmanger;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
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
			Intent intent_dataInput = new Intent(this, DataInput.class);
			startActivity(intent_dataInput);
			break;
		default:
			break;
		}

	}
	 protected void onDestroy() {
		    super.onDestroy();
		    // закрываем подключение при выходе
		    db.close();
		  }

}
