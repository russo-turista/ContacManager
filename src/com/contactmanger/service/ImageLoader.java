package com.contactmanger.service;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageLoader {
	public Bitmap imgBitmap;
	private String imgPath;
	private final String LOG_TAG = "myLogs";

	public ImageLoader(String imgPath){
		this.imgPath = imgPath;
		this.imgBitmap = createBitmap(imgPath);
	}
	
	private Bitmap createBitmap(String imagePath) {
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
