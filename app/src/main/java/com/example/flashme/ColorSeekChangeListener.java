package com.example.flashme;

import android.graphics.Color;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

public class ColorSeekChangeListener implements SeekBar.OnSeekBarChangeListener{

	TextView _textView;
	int _color;
	MenuActivity _parent;
	int _type;
	
	public ColorSeekChangeListener(TextView tv, int initColor, int type, MenuActivity parent){
		_textView = tv;
		_color = initColor;
		_parent = parent;
		_type = type;
		_textView.setBackgroundColor(initColor);
	}
	
	public void setSeekBarProgress(SeekBar seekBar){
		// TODO Auto-generated method stub
		//int alpha = _color >> 24 & 0xFF;
		int red = (_color >> 16) & 0xFF;
		int green = (_color >> 8) & 0xFF;
		int blue = _color & 0xFF;

		
		if(seekBar.getId() == R.id.seekBarRed){
			seekBar.setProgress(red);
		}else if(seekBar.getId() == R.id.seekBarGreen){
			seekBar.setProgress(green);
		}else if(seekBar.getId() == R.id.seekBarBlue){
			seekBar.setProgress(blue);
		}
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		//int alpha = _color >> 24 & 0xFF;
		int red = (_color >> 16) & 0xFF;
		int green = (_color >> 8) & 0xFF;
		int blue = _color & 0xFF;
		
		if(seekBar.getId() == R.id.seekBarRed){
			red = seekBar.getProgress();
		}else if(seekBar.getId() == R.id.seekBarGreen){
			green = seekBar.getProgress();
		}else if(seekBar.getId() == R.id.seekBarBlue){
			blue = seekBar.getProgress();
		}
		
		
		_color = 255;//alpha;
		_color = _color << 8;
		_color += red;
		_color = _color << 8;
		_color += green;
		_color = _color << 8;
		_color += blue;
		
		_textView.setBackgroundColor(_color);
		if(_type == 0){
			_parent.set_borderColor(_color);
		}else if(_type == 1){
			_parent.set_textColor(_color);
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

}
