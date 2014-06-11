package com.contactmanger;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class WelcomeActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startpage);
		
		LayoutInflater ltInflater = getLayoutInflater();
        RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.mainRelLayout);
        View footerView = ltInflater.inflate(R.layout.footer, relLayout, true);
        //relLayout.addView(footerView);
	}

}
