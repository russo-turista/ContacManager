package com.contactmanger.service;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageLoader {
	private Bitmap imgBitmap = null;
	private final String LOG_TAG = "myLogs";
	
	public Bitmap createBitmap(String imagePath) {
		File imgFile = new File(imagePath);
		if (imgFile.exists()) {
			imgBitmap = BitmapFactory.decodeFile(imgFile.getPath());
			Log.d(LOG_TAG, "is file exists");
			return imgBitmap;
		} else {
			Log.d(LOG_TAG, "is't file exists");
			return null;
		}
	}
}
