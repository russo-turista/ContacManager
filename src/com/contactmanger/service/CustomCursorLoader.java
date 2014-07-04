package com.contactmanger.service;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.contactmanger.database.DB;

public class CustomCursorLoader extends CursorLoader {
	private DB db;

	public CustomCursorLoader(Context context, DB db) {
		super(context);
		this.db = db;
	}

	@Override
	public Cursor loadInBackground() {
		Cursor cursor = db.getAllData();
		return cursor;
	}
}
