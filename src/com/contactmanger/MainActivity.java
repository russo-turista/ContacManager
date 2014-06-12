package com.contactmanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	Boolean isFavorite;
	Button btnWelcomPage;
	String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
		      "Костя", "Игорь", "Анна", "Денис", "Андрей" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		LayoutInflater ltInflater = getLayoutInflater();
		RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.contactsLayout);
		View headerView = ltInflater.inflate(R.layout.header, relLayout, true);
		
		 ListView lvMain = (ListView) findViewById(R.id.listContacts);

		    // создаем адаптер
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, names);

		    // присваиваем адаптер списку
		    lvMain.setAdapter(adapter);
		    
		View footerView = ltInflater.inflate(R.layout.footer, relLayout, true);
		
		 btnWelcomPage = (Button) findViewById(R.id.btnWelcome);
		 btnWelcomPage.setOnClickListener(this);
		
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
		    	//Toast.makeText(this, "Нажата кнопка allcontactsB", Toast.LENGTH_LONG).show();		    	
		      break;
		    case R.id.btnFavoriteList:
		    	Intent intent_favorite = new Intent(this, MainActivity.class);
		    	intent_favorite.putExtra("favorite", true);
		    	startActivity(intent_favorite);
		    	//Toast.makeText(this, "Нажата кнопка favoriteB", Toast.LENGTH_LONG).show();
		    	break;
		    default:
		      break;
		    }
		
	}

}
