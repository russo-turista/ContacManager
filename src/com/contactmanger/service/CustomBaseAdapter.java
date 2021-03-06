package com.contactmanger.service;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.contactmanger.R;
import com.contactmanger.database.DB;

public class CustomBaseAdapter extends BaseAdapter {
	private Context ctx;
	private DB db;
	private LayoutInflater lInflater;
	private Cursor cursor;
	private final String LOG_TAG = "myLogs";
	CustomAsyncTask customATask;

	public CustomBaseAdapter(Context context, Cursor cursor) {
		super();
		this.cursor = cursor;
		ctx = context;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Contacts getItem(int position) {

		if (cursor.moveToPosition(position)) {
			String imgPath = cursor.getString(cursor
					.getColumnIndex(DB.COLUMN_IMG));
			String descr = cursor.getString(cursor
					.getColumnIndex(DB.COLUMN_DESCRIPTION));
			Boolean isFavorite = Boolean.valueOf(cursor.getString(cursor
					.getColumnIndex(DB.COLUMN_ISFAVORITE)));
			return new Contacts(imgPath, descr, isFavorite);
		} else {
			throw new CursorIndexOutOfBoundsException(
					"Cant move cursor to postion");
		}
	}

	@Override
	public long getItemId(int position) {
		Contacts contactsItemId = getItem(position);
		return contactsItemId.get_id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		customATask = new CustomAsyncTask();
		View view = convertView;
		String imgPath = getItem(position).getImagePath();
		if (view == null) {
			view = lInflater.inflate(R.layout.item, parent, false);
		}
		// customATask.execute(imgPath);
		//Bitmap imgBitmap = new ImageLoader(imgPath).imgBitmap;
		BitmapTask bitmapTask = new BitmapTask();
		bitmapTask.execute(imgPath);
		try {
			((ImageView) view.findViewById(R.id.itemImg)).setImageBitmap(bitmapTask.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		//((ImageView) view.findViewById(R.id.itemImg)).setImageBitmap(imgBitmap);
		Log.d(LOG_TAG, getItem(position).getDescription());
		((TextView) view.findViewById(R.id.itemText)).setText(getItem(position)
				.getDescription());
		return view;
	}

	class CustomAsyncTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);

		}

		@Override
		protected Bitmap doInBackground(String... imgPath) {
			Bitmap imgBitmap = null;
			return new ImageLoader(imgPath[0], 100, 100).imgBitmap;

		}

	}
}
