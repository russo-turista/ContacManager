package com.contactmanger.service;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageLoader {
	public static String IMAGEDIR = "Contacts_manager_thumb";
	public Bitmap imgBitmap;
	private String imgPath;
	private String imgName;
	private int reqWidth;
	private int reqHeight;
	private final String LOG_TAG = "myLogs";
	
	public ImageLoader (String imgPath){
		this.imgBitmap = createBitmap(imgPath);
	}

	public ImageLoader(String imgPath, String imgName) {
		this.imgPath = imgPath;
		this.imgName  = imgName;
		this.imgBitmap = saveThumbnailFile(imgPath, imgName);
	}

	public ImageLoader(String imgPath, int reqWidth, int reqHeight) {
		this.reqHeight = reqHeight;
		this.reqWidth = reqWidth;
		this.imgPath = imgPath;
		this.imgBitmap = decodeImage();
	}

	private Bitmap createBitmap(String imgPath) {
		File imgFile = new File(imgPath);
		if (imgFile.exists()) {
			imgBitmap = BitmapFactory.decodeFile(imgFile.getPath());
			Log.d(LOG_TAG, "is file exists");
			return imgBitmap;
		} else {
			Log.d(LOG_TAG, "is't file exists");
			return null;
		}
	}

	private int calculateImageSize(BitmapFactory.Options options, int reqWidth,
			int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	private Bitmap decodeImage() {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imgPath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateImageSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(imgPath, options);
	}

	public Bitmap saveThumbnailFile(String imgPath, String imgName) {
		File imageStorageDir = new File(
				Environment.getExternalStorageDirectory() + File.separator
						+ IMAGEDIR);
		if (!imageStorageDir.exists()) {
			imageStorageDir.mkdirs();
			Log.d(LOG_TAG,
					"Folder created at: " + imageStorageDir.toString());
		}
		Bitmap imgBitmap = createBitmap(imgPath);
		if (imgBitmap != null) {
			String filename = imgName;
			File sdCard = Environment.getExternalStorageDirectory();
			String imageStorageFolder = File.separator
					+ IMAGEDIR + File.separator;
			File destinationFile = new File(sdCard, imageStorageFolder
					+ filename);
			Log.d(LOG_TAG, "the destination for image file is: " + destinationFile);
			try {
				FileOutputStream out = new FileOutputStream(destinationFile);
				imgBitmap.compress(Bitmap.CompressFormat.PNG, 60, out);
				out.flush();
				out.close();
			} catch (Exception e) {
				Log.e(LOG_TAG, "ERROR:" + e.toString());
			}
			return imgBitmap;
		}
		return null;
	}
	

}
