package com.contactmanger.service;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapTask extends AsyncTask<String, Void, Bitmap> {
	private ImageView imageViewReference;
	private String dataPath;

	// Decode image in background.
	@Override
	protected Bitmap doInBackground(String... imagePath) {
		dataPath = imagePath[0];
		return new ImageLoader(dataPath).imgBitmap;
	}

	// Once complete, see if ImageView is still around and set bitmap.
	@Override
	protected void onPostExecute(Bitmap bitmap) {
		/*if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}*/
		//return bitmap;
	}
}
