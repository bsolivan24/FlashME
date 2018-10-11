package com.example.flashme;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class TransitionRunnable implements Runnable{
	
	Rect _currImgRect;
	Rect _prevImgRect;
	Rect _startRect;
	long _sleep;
	int _step;
	boolean _left = false;
	Handler _handler;
	
	public Rect[] getRects(){
		return new Rect[] {_currImgRect, _prevImgRect};
	}
	
	public void setRects(Rect currImgRect, Rect prevImgRect){
		_currImgRect = new Rect(currImgRect);
		_startRect = new Rect(_currImgRect);
		_prevImgRect = new Rect(prevImgRect);
	}
	
	public void setSleepTime(long time){
		_sleep = time;
	}
	
	public void setStep(int step){
		_step = step;
		if(_left){
			_step = -Math.abs(_step);
		}
	}
	
	public void setHandler(Handler handler){
		_handler = handler;
	}
	
	public void setLeft(boolean left){
		_left = left;
		if(left){
			_step = -Math.abs(_step);
		}
	}
	
	@Override
	public void run() {
		Log.i("Running", "Started");
		
		while(getWhileCheck()){
			_currImgRect.offset(_step, 0);
			_prevImgRect.offset(_step, 0);

			try {
				Thread.sleep(_sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Message msg = new Message();
			msg.obj = new Rect[]{_currImgRect, _prevImgRect};
			msg.what = 1234;

			_handler.sendMessage(msg);
		}
		
		Message msg = new Message();
		msg.what = 1235;
		
		_handler.sendMessage(msg);
	}
	
	private boolean getWhileCheck(){
		if(_left){
			return _prevImgRect.left > _startRect.left;
		}
		return _prevImgRect.left < _startRect.left;
	}
}
