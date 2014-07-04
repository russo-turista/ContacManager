package com.contactmanger.activity;

import com.contactmanger.R;
import com.contactmanger.R.id;
import com.contactmanger.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class WelcomeActivity extends Activity implements OnClickListener {
	private static final String TAG = "myLogs";
	Button btnAllcontactList;
	Button btnFavoriteList;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startpage);
		
        btnAllcontactList = (Button) findViewById(R.id.btnAllcontactsLis);
        btnFavoriteList = (Button) findViewById(R.id.btnFavoriteList);
        
        btnAllcontactList.setOnClickListener(this);
        btnFavoriteList.setOnClickListener(this);
        Log.d(TAG, "WelcomeActivity load");
	}

	@Override
	public void onClick(View v) {
			Log.d(TAG, "onClick work");
		 switch (v.getId()) {
		    case R.id.btnAllcontactsLis:
		    	Intent intent_allcontacts = new Intent(this, ListViewActivity.class);
		    	intent_allcontacts.putExtra("favorite", false);
		    	startActivity(intent_allcontacts);
		      break;
		    case R.id.btnFavoriteList:
		    	Intent intent_favorite = new Intent(this, ListViewActivity.class);
		    	intent_favorite.putExtra("favorite", true);
		    	startActivity(intent_favorite);
		    	break;
		    default:
		      break;
		    }
		
	}

}
