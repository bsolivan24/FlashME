package com.example.flashme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

	boolean _random = false;
	int _borderColor = Color.MAGENTA;
	int _textColor = Color.BLACK;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null){
			_random = savedInstanceState.getBoolean(Constants.KEY_RANDOM);
			_borderColor = savedInstanceState.getInt(Constants.KEY_BORDERCOLOR);
			_textColor = savedInstanceState.getInt(Constants.KEY_TEXTCOLOR);
		}
		
		setContentView(R.layout.activity_main);
		Button btnStart = (Button)findViewById(R.id.btnStart);
		Button btnMenu = (Button)findViewById(R.id.btnMenu);
		
		btnStart.setOnClickListener(this);
		btnMenu.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnStart){
			//Start CardActivity
			Intent intent = new Intent(this, CardActivity.class);
			intent.putExtra(Constants.KEY_RANDOM, _random);
			intent.putExtra(Constants.KEY_BORDERCOLOR,  _borderColor);
			intent.putExtra(Constants.KEY_TEXTCOLOR, _textColor);
			startActivity(intent);
		}else if(v.getId() == R.id.btnMenu){
			//Start MenuActivity
			Intent intent = new Intent(this, MenuActivity.class);
			intent.putExtra(Constants.KEY_RANDOM, _random);
			intent.putExtra(Constants.KEY_BORDERCOLOR,  _borderColor);
			intent.putExtra(Constants.KEY_TEXTCOLOR,  _textColor);
			final int result = 1;
			startActivityForResult(intent, result);
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putBoolean(Constants.KEY_RANDOM, _random);
		outState.putInt(Constants.KEY_BORDERCOLOR, _borderColor);
		outState.putInt(Constants.KEY_TEXTCOLOR, _textColor);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null){
			_random = data.getBooleanExtra(Constants.KEY_RANDOM, false);
			_borderColor = data.getIntExtra(Constants.KEY_BORDERCOLOR, Color.MAGENTA);
			_textColor = data.getIntExtra(Constants.KEY_TEXTCOLOR, Color.MAGENTA);
		}
		else{
		}
	}
}
