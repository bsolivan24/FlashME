package com.example.flashme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Bitmap.Config;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class CardView  extends ImageView{

	String _text = "";
	String _nextText = "";
	int _borderColor = Color.MAGENTA;
	int _textColor = Color.BLACK;
	Bitmap _backImg;
	Bitmap _textImg;
	Bitmap _nextImg;
	Rect _textRect;
	Rect _nextRect;
	Paint _paint;
	int _cardPadding = 0;
	Handler _handler;
	Thread _transitionThread;
	boolean _transitioning;
	
	public CardView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Init();
	}
	
	public CardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Init();
	}
	
	public CardView(Context context) {
		super(context);
		Init();
	}
	
	private void Init(){
		_backImg =  BitmapFactory.decodeResource(getResources(), R.drawable.road);
		_handler = new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(Message msg) {
				
				switch(msg.what){
					case 1234:

						Rect[] rects = (Rect[])msg.obj;
						_textRect = rects[0];
						_nextRect = rects[1];
						invalidate();
						break;
					case 1235:
						
						_text = _nextText;
						_textImg = _nextImg;
						_textRect = _nextRect;
						_nextText = "";
						_nextImg = null;
						_nextRect = null;
						invalidate();
						_transitioning = false;
						break;
					default:	
						super.handleMessage(msg);	
				}
			}
		};
	}
	
	public void SetCardBackground(int color){
		_borderColor = color;
	}
	
	public void SetTextColor(int color){
		_textColor = color;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		boolean boolBImg = checkForNull(_backImg, "_backImg");
		
		if(boolBImg){
			return;
		}
		
		Rect bRect = new Rect(0,0, _backImg.getWidth(), _backImg.getHeight());
		canvas.drawBitmap(_backImg,
						bRect,
						canvas.getClipBounds(),
						_paint);
		
		boolean boolPaint = checkForNull(_paint, "_paint");
		
		if(boolBImg || boolPaint){
			return;
		}

		if(_textImg != null && _textRect != null){
			canvas.drawBitmap(_textImg,
					new Rect(0,0,_textImg.getWidth(),
					_textImg.getHeight()),
					_textRect,
					_paint);
		}

		if(_nextImg != null && _nextRect != null){
			canvas.drawBitmap(_nextImg,
					new Rect(0,0,_nextImg.getWidth(),
					_nextImg.getHeight()),
					_nextRect,
					_paint);
		}
	}
	
	private boolean checkForNull(Object o, String objName){
		if(o == null){
			if(objName != null && objName != "")
				Log.e("Error", objName + " is null");
			return true;
		}
		return false;
	}
	
	private Bitmap CreateTextBitmap(int canvasWidth, int canvasHeight, int color, Paint paint, String text){
		
		//TODO: FIX SCREEN ROTATION
		if(canvasWidth <= 0 || canvasHeight <=0){
			return null;
		}
		
		paint = new Paint();
		paint.setTextSize(canvasWidth / 5);

		int widthPadding = canvasWidth / 6;
		
		//Get bounds for bitmap that will contain text
		RectF cardBorder = new RectF(0,
				0, 
				canvasWidth, 
				canvasHeight);
		
		//Setup text alignment
		paint.setTextAlign(Align.CENTER);
		float x = cardBorder.width() / 2.0f;
		float y = (cardBorder.height() / 2.0f) + (canvasHeight / 10f);
		
		//Prepare canvas for text
		Bitmap textBitmap = Bitmap.createBitmap((int)cardBorder.width(), (int)cardBorder.height(), Config.ARGB_8888);
		Canvas textCanvas = new Canvas(textBitmap);

		Style prevStyle = paint.getStyle();
		float prevStroke = paint.getStrokeWidth();
		paint.setStrokeWidth(canvasWidth / 15);

		//Setup up card background
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		//TODO: FIX DRAWING OF CORNERS
		textCanvas.drawRoundRect(cardBorder, 90f, 90f, paint);
		paint.setStyle(Style.STROKE);
		paint.setColor(_borderColor);
		//TODO: FIX DRAWING OF CORNERS
		textCanvas.drawRoundRect(cardBorder, 90f, 90f, paint);
		paint.setStrokeWidth(prevStroke);
		paint.setStyle(prevStyle);

		//Color for background of text
		paint.setColor(_textColor);
		textCanvas.drawText(text, x, y, paint);
		
		return textBitmap;
	}

	public void DrawText(String text){
		_text = text;
		
		if(_paint == null){
			_paint = new Paint();
		}
		if(_cardPadding == 0){
			_cardPadding = getWidth() / 15;
		}
		
		if(_text != null && _text != ""){
			_textImg = CreateTextBitmap(getWidth(), getHeight(), _borderColor, _paint, _text);
			_textRect = new Rect(_cardPadding, _cardPadding,
					getWidth() - _cardPadding, getHeight() - _cardPadding);
			checkForNull(_textImg, "_textImg");
			checkForNull(_textRect, "_textRect");
		}
		invalidate();
	}
	
	public void TransitionCard(String text, boolean forward){

		if(_paint == null){
			_paint = new Paint();
		}
		if(_cardPadding == 0){
			_cardPadding = getWidth() / 15;
		}

		_nextText = text;

		_nextImg = CreateTextBitmap(getWidth(), getHeight(), _borderColor, _paint, text);
		_nextRect = new Rect(_cardPadding, _cardPadding,
				getWidth() - _cardPadding,
				getHeight() - _cardPadding);
		
		if(_text != null && _text != ""){
			_textImg = CreateTextBitmap(getWidth(), getHeight(), _borderColor, _paint, _text);
		}
		else{
			_textImg = null;
		}
		
		_textRect = new Rect(_nextRect);

		TransitionRunnable runnable = new TransitionRunnable();
		
		if(forward){
			_nextRect.left -= getWidth();
			_nextRect.right -= getWidth();
			runnable.setLeft(false);
		}
		else{
			_nextRect.left += getWidth();
			_nextRect.right += getWidth();
			runnable.setLeft(true);
		}

		runnable.setHandler(_handler);
		runnable.setRects(_textRect, _nextRect);
		runnable.setSleepTime(20);
		runnable.setStep(10);
		
		_transitionThread = new Thread(runnable);
		_transitionThread.start();
	}

	public void TransitionBackwardTo(String text){
		
		if(_transitioning){
			return;
		}
		_transitioning = true;
		
		TransitionCard(text, false);
	}
	
	public void TransitionForwardTo(String text){
		
		if(_transitioning){
			return;
		}
		_transitioning = true;
		
		TransitionCard(text, true);
	}
	
	private float[] getBottomLeft(float width, float height, Rect textRect,Paint paint, String text){
		paint.setTextAlign(Align.LEFT);
		
		//Draw on the far left of bitmap
		float x = -(width / 100f);//textRect.exactCenterX();
		
		//Draw on the bottom of bitmap
		float offset = (height / 5f) / 100f;
		if(hasDescent(text)){
			offset -= -offset + paint.descent();
		}
		float y = textRect.height() + offset;//Math.abs(paint.ascent());//Math.abs(textRect.exactCenterY());
		
		float[] f = new float[]{x,y};
		return f;
	}
	
	private boolean hasDescent(String text){
		boolean has = false;
		for(char c : text.toCharArray()){
			switch(c){
				case 'g':
				case 'j':
				case 'p':
				case 'q':
				case 'y':
					has = true;
					break;
				default:
					has = false;
			}
			if(has){
				break;
			}
		}
		return has;
	}
}
