package com.example.flashme;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity{
	CheckBox _cBoxRandom;
	int _borderColor = Color.MAGENTA;
	int _textColor = Color.BLACK;
	ArrayAdapter<CharSequence> _clrAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		_cBoxRandom = (CheckBox)findViewById(R.id.cBoxRandom);

		Intent i = getIntent();
		if(savedInstanceState != null){
			_cBoxRandom.setChecked(savedInstanceState.getBoolean(Constants.KEY_RANDOM));
			_borderColor = savedInstanceState.getInt(Constants.KEY_BORDERCOLOR);
			_textColor = savedInstanceState.getInt(Constants.KEY_TEXTCOLOR);
		}
		else if(i != null){
			_cBoxRandom.setChecked(i.getBooleanExtra(Constants.KEY_RANDOM, false));
			_borderColor = i.getIntExtra(Constants.KEY_BORDERCOLOR, Color.MAGENTA);
			_textColor = i.getIntExtra(Constants.KEY_TEXTCOLOR, Color.BLACK);
		}
		
		LinkedList<String> ll = new LinkedList<String>();
		ll.add("Border Color");
		ll.add("Text Color");

		ColorExpandableListAdapter ela = new ColorExpandableListAdapter(this, ll);

		ExpandableListView elv = (ExpandableListView)findViewById(R.id.expandableListView1);

		elv.setAdapter(ela);
		
		LinearLayout lLayout1 = (LinearLayout)ela.getChildView(0, 0, false, null, null);
		TextView tv = (TextView)lLayout1.findViewById(R.id.colorTextView);
		ColorSeekChangeListener cscl = new ColorSeekChangeListener(tv, _borderColor, 0, this);

		SeekBar sb = (SeekBar)lLayout1.findViewById(R.id.seekBarRed);
		cscl.setSeekBarProgress(sb);
		sb.setOnSeekBarChangeListener(cscl);
		sb = (SeekBar)lLayout1.findViewById(R.id.seekBarGreen);
		cscl.setSeekBarProgress(sb);
		sb.setOnSeekBarChangeListener(cscl);
		sb = (SeekBar)lLayout1.findViewById(R.id.seekBarBlue);
		cscl.setSeekBarProgress(sb);
		sb.setOnSeekBarChangeListener(cscl);

		lLayout1 = (LinearLayout)ela.getChildView(1, 0, false, null, null);
		tv = (TextView)lLayout1.findViewById(R.id.colorTextView);
		cscl = new ColorSeekChangeListener(tv, _textColor, 1, this);
		
		sb = (SeekBar)lLayout1.findViewById(R.id.seekBarRed);
		cscl.setSeekBarProgress(sb);
		sb.setOnSeekBarChangeListener(cscl);
		sb = (SeekBar)lLayout1.findViewById(R.id.seekBarGreen);
		cscl.setSeekBarProgress(sb);
		sb.setOnSeekBarChangeListener(cscl);
		sb = (SeekBar)lLayout1.findViewById(R.id.seekBarBlue);
		cscl.setSeekBarProgress(sb);
		sb.setOnSeekBarChangeListener(cscl);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void set_borderColor(int color){
		_borderColor = color;
	}
	
	public void set_textColor(int color){
		_textColor = color;
	}
	
	@Override
	public void onBackPressed() {
		
		Intent intent = new Intent();
		intent.putExtra(Constants.KEY_RANDOM, _cBoxRandom.isChecked());
		intent.putExtra(Constants.KEY_BORDERCOLOR, _borderColor);
		intent.putExtra(Constants.KEY_TEXTCOLOR, _textColor);
		setResult(RESULT_OK, intent);
		super.onBackPressed();
		finish();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(Constants.KEY_RANDOM, _cBoxRandom.isChecked());
		outState.putInt(Constants.KEY_BORDERCOLOR, _borderColor);
		outState.putInt(Constants.KEY_TEXTCOLOR, _textColor);
	}
}
