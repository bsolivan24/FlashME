package com.example.flashme;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

public class WordManager {
	
	private Random _random = new Random();
	//private Map<String, ArrayList<String>> _hashMap;
	private List<String> _wordList;
	private List<String> _ranList;
	private Stack<String> _prevStack = new Stack<String>();
	private Stack<String> _nextStack = new Stack<String>();
	private String _currText = "";
	private boolean _showRandom;
	//private int _nextKey = 0;
	private int _nextValue = 0;
	private int _maxStack = 50;
	private static WordManager _instance = null;
	public boolean _initialized;
	
	private WordManager(){
		_random.setSeed(System.currentTimeMillis());
	}
	
	public static WordManager getInstance(){
		if(_instance == null){
			Log.i("WordManager", "New instance created");
			_instance = new WordManager();
		}
		return _instance;
	}
	
	public static void destroyInstance(){
		_instance = null;
	}
	
	/*
	public void initialize(Resources resources, int resourceId){

		if(_initialized){
			return;
		}
		
		_initialized = true;
		
		_hashMap = new LinkedHashMap<String, ArrayList<String>>();

		try
		{
			// open the file for reading
			InputStream inStream = resources.openRawResource(resourceId);
			// if file the available for reading
			if (inStream != null)
			{
			    // prepare the file for reading
			    InputStreamReader inputreader = new InputStreamReader(inStream);
			    BufferedReader buffreader = new BufferedReader(inputreader);
			    
			    // read every line of the file into the line-variable, on line at the time
			    String line = "";
			    int totalLines = 0;
			    while((line = buffreader.readLine()) != null)
			    {
			    	if(line.isEmpty()){
			    		continue;
			    	}
			    	if(line.contains("-")){
			    		_hashMap.put(line, new ArrayList<String>());
			    	}
			    	else{
			    		Set<String> _keys = _hashMap.keySet();
			    		try
			    		{
			    			for(String key : _keys)
			    			{
			    				if(key.contains(line.toLowerCase().charAt(0) + ""))
			    				{
			    					_hashMap.get(key).add(line);
						    		totalLines++;
			    				}
			    			}
			    		}
			    		catch(Exception ex)
			    		{
			    			Log.e("Fail", "Handling get in hashtable");
			    		}
			    	}
			    }
			    Log.i("Action", "Added a total of " + totalLines +" lines added");
			    Log.i("Stream", "Closing stream");
			    inStream.close();
			}
		} 
		catch (Exception ex)
		{
    		Log.e("WordManager", "Stream fail");
		}
		Log.i("WordManager", "list size" + _hashMap.size());
	}
*/
	public void initialize(Resources resources, int resourceId){

		if(_initialized){
			return;
		}
		
		_initialized = true;
		
		_wordList = new LinkedList<String>();

		try
		{
			// open the file for reading
			InputStream inStream = resources.openRawResource(resourceId);
			// if file the available for reading
			if (inStream != null)
			{
			    // prepare the file for reading
			    InputStreamReader inputreader = new InputStreamReader(inStream);
			    BufferedReader buffreader = new BufferedReader(inputreader);
			    
			    // read every line of the file into the line-variable, on line at the time
			    String line = "";
			    int totalLines = 0;
			    while((line = buffreader.readLine()) != null)
			    {
			    	if(line.isEmpty()){
			    		continue;
			    	}
			    	if(line.contains("-")){
			    		//ignore header lines
			    	}
			    	else{
			    		try
			    		{
			    			if(!_wordList.contains(line)){
			    				_wordList.add(line);
			    				totalLines++;
			    			}
			    		}
			    		catch(Exception ex)
			    		{
			    			Log.e("Fail", "add in _wordList");
			    		}
			    	}
			    }
			    Log.i("Action", "Added a total of " + totalLines +" lines added");
			    Log.i("Stream", "Closing stream");
			    inStream.close();
			}
		} 
		catch (Exception ex)
		{
    		Log.e("WordManager", "Stream fail");
		}
		Log.i("WordManager", "list size" + _wordList.size());
	}
	/*	
	public String getNextWord(){
		//Check to see if we need to push this word onto stack
		if(_currText != null && _currText != ""){
			if(_prevStack.size() == _maxStack){
				//Remove bottom item
				Stack<String> tStack = new Stack<String>();
				int stackSize = _prevStack.size();
				for(int i=0; i < stackSize; i++){
					tStack.push(_prevStack.pop());
				}
				tStack.pop();
				//Reorder stack
				stackSize = tStack.size();
				for(int i=0; i < stackSize; i++){
					_prevStack.push(tStack.pop());
				}
				tStack = null;
			}
			_prevStack.push(new String(_currText));
		}
		
		if(_nextStack.size() > 0){
			_currText = _nextStack.pop();
		}
		else{
			_currText = getNextText();
		}
		
		return _currText;
	}
*/
	public String getNextWord(){
		//Check to see if we need to push this word onto stack
		if(_currText != null && _currText != ""){
			if(_prevStack.size() == _maxStack){
				//Remove bottom item
				Stack<String> tStack = new Stack<String>();
				int stackSize = _prevStack.size();
				for(int i=0; i < stackSize; i++){
					tStack.push(_prevStack.pop());
				}
				tStack.pop();
				//Reorder stack
				stackSize = tStack.size();
				for(int i=0; i < stackSize; i++){
					_prevStack.push(tStack.pop());
				}
				tStack = null;
			}
			_prevStack.push(new String(_currText));
		}
		
		if(_nextStack.size() > 0){
			_currText = _nextStack.pop();
		}
		else{
			_currText = getNextText();
		}
		
		return _currText;
	}
	
	public String getPrevWord(){
		if(_prevStack.size() > 0){
			_nextStack.push(new String(_currText));
			_currText = _prevStack.pop();
		}else{
			throw new IllegalStateException("No prev text available");
		}
		return _currText;
	}
/*
	private String getNextText(){
		String text = "";
		try
		{
			if(_showRandom)
			{
				_nextKey = _random.nextInt(_hashMap.size());
				Log.i("Action", "Getting next value from " + _nextKey);
				_nextValue = _random.nextInt(_hashMap.get(_hashMap.keySet().toArray()[_nextKey]).size());
			}
			
			Log.i("Action", "Getting text");
			text = _hashMap.get(_hashMap.keySet().toArray()[_nextKey]).get(_nextValue);
			
			if(!_showRandom)
			{
				if(++_nextValue >= _hashMap.get(_hashMap.keySet().toArray()[_nextKey]).size())
				{
					_nextValue = 0;
					_nextKey++;
					if(_nextKey >= _hashMap.keySet().size())
					{
						_nextKey = 0;
					}
				}
			}
		}
		catch(Exception ex)
		{
			Log.e("Broke", "" + ex.getMessage());
		}
		return text;
	}
	*/
	
	private String getNextText(){
		String text = "";
		try
		{
			if(_showRandom)
			{
				if(_ranList == null){
					_ranList = new LinkedList<String>();
				}
				
				if(_ranList.size() == 0){
					LinkedList<String> tList = new LinkedList<String>(_wordList);
					while(tList.size() > 0){
						String item = tList.get(_random.nextInt(tList.size()));
						_ranList.add(item);
						tList.remove(item);
					}
					_nextValue = 0;
					return "####";
				}
				text = _ranList.get(_nextValue++);
				if(_nextValue >= _ranList.size()){
					_ranList.clear();
					_ranList = null;
				}
			}else{
				text = _wordList.get(_nextValue++);
				if(_nextValue >= _wordList.size()){
					_nextValue = 0;
				}
			}
		}
		catch(Exception ex)
		{
			Log.e("Broke", "" + ex.getMessage());
		}
		return text;
	}
	
	public void setRandom(boolean random){
		_showRandom = random;
	}
	
	//TODO: get a new method other than getting the nextkey and value
	// because if the are words added or removed then we are in trouble
	public void loadFromBundle(Bundle savedInstanceState){
		_showRandom = savedInstanceState.getBoolean(Constants.KEY_RANDOM);
		//_nextKey = savedInstanceState.getInt(Constants.KEY_NEXT_KEY);
		_nextValue = savedInstanceState.getInt(Constants.KEY_NEXT_VALUE);
		_currText = savedInstanceState.getString(Constants.KEY_CARD_TEXT);
		
		String ret = "";
		int inc = 0;
		while(ret != null){
			ret = savedInstanceState.getString(Constants.KEY_NEXT_STACK + inc);
			if(ret != null){
				_nextStack.push(ret);
				inc++;
			}
		}
		ret = "";
		inc = 0;
		while(ret != null){
			ret = savedInstanceState.getString(Constants.KEY_PREV_STACK + inc);
			if(ret != null){
				_prevStack.push(ret);
				inc++;
			}
		}
	}
	
	public void loadFromIntent(Intent i){
		_showRandom = i.getBooleanExtra(Constants.KEY_RANDOM, false);
	}
	
	public void saveToBundle(Bundle outState){
		//outState.putInt(Constants.KEY_NEXT_KEY, _nextKey);
		outState.putInt(Constants.KEY_NEXT_VALUE, _nextValue);
		outState.putBoolean(Constants.KEY_RANDOM, _showRandom);
		outState.putString(Constants.KEY_CARD_TEXT, _currText);
		
		int stackSize = _prevStack.size();
		for(int i=stackSize - 1; i >= 0; i--){
			outState.putString(Constants.KEY_PREV_STACK + i, _prevStack.pop());
		}
		stackSize = _nextStack.size();
		for(int i=stackSize - 1; i >= 0; i--){
			outState.putString(Constants.KEY_NEXT_STACK + i, _nextStack.pop());
		}
	}
}
