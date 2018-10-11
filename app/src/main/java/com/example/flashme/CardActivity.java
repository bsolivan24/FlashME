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

public class CardActivity extends Activity 
{
	private String _currText = "";
	boolean _showRandom = true;
	int _borderColor = Color.MAGENTA;
	int _textColor = Color.BLACK;
	boolean _savedState = false;
	private CardView _cardView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.i("CardActivity", "onCreate");
		
		setContentView(R.layout.activity_cards);
		
		Intent i = getIntent();
		
		if(savedInstanceState != null){
			
			WordManager.getInstance().loadFromBundle(savedInstanceState);
			
			_showRandom = savedInstanceState.getBoolean(Constants.KEY_RANDOM);
			_savedState = savedInstanceState.getBoolean(Constants.KEY_SAVED_STATE);
			_currText = savedInstanceState.getString(Constants.KEY_CARD_TEXT);
			_borderColor = savedInstanceState.getInt(Constants.KEY_BORDERCOLOR, _borderColor);
			_textColor = savedInstanceState.getInt(Constants.KEY_TEXTCOLOR, _textColor);
			
		}
		
		if(i != null && !_savedState){
			WordManager.getInstance().loadFromIntent(i);
			_borderColor = i.getIntExtra(Constants.KEY_BORDERCOLOR, Color.MAGENTA);
			_textColor = i.getIntExtra(Constants.KEY_TEXTCOLOR, Color.BLACK);
		}
		
		Button nxtButton = (Button)findViewById(R.id.btnNext);
		nxtButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(_cardView._transitioning){
					return;
				}
				
				_currText = WordManager.getInstance().getNextWord();
				_cardView.TransitionForwardTo(_currText);
			}
		});

		Button prevButton = (Button)findViewById(R.id.btnPrev);
		prevButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(_cardView._transitioning){
					return;
				}
				
				try{
					_currText = WordManager.getInstance().getPrevWord();
					_cardView.TransitionBackwardTo(_currText);
				}catch(Exception ex){
					//Most likely word manager has nothing left to display
					if(ex == null || ex.getMessage() == null){
						Log.e("Error", "Error getting previous message");
					}
					else{
						Log.e("Error", ex.getMessage());
					}
				}
			}
		});


		_cardView = (CardView)findViewById(R.id.cardView1);
		_cardView.SetCardBackground(_borderColor);
		_cardView.SetTextColor(_textColor);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		WordManager.getInstance().saveToBundle(outState);
		outState.putBoolean(Constants.KEY_SAVED_STATE, true);
		outState.putInt(Constants.KEY_BORDERCOLOR, _borderColor);
		outState.putInt(Constants.KEY_TEXTCOLOR, _textColor);
	};
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		WordManager.getInstance().initialize(getResources(), R.raw.wordlist);
		_cardView.invalidate();
		if(_savedState){
			_cardView.invalidate();
			_savedState = false;
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		_cardView.DrawText(_currText);
	}
	
	@Override
	protected void onDestroy() {
		WordManager.destroyInstance();
		super.onDestroy();
	}
}