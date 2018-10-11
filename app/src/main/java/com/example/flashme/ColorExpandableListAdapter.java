package com.example.flashme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ColorExpandableListAdapter extends BaseExpandableListAdapter{
	private List<String> _headerNames;
	private Map<String, View> _headerMap = new HashMap<String, View>();
	private Map<String, View> _childMap;
	private Context _context;
	private Color _color;
	
	public ColorExpandableListAdapter(Context context, List<String> headerNames){
		_context = context;
		_headerNames = headerNames;
		_childMap = new HashMap<String, View>();
	}
	
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return _childMap.get(_headerNames.get(groupPosition));
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return childPosition;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(_childMap.containsKey(_headerNames.get(groupPosition))){
			convertView = _childMap.get(_headerNames.get(groupPosition));
		}else{
			LayoutInflater li = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.color_picker, null);
			_childMap.put(_headerNames.get(groupPosition), convertView);
		}
		
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		if(_headerMap.containsKey(_headerNames.get(groupPosition))){
			return _headerMap.get(_headerNames.get(groupPosition));
		}
		
		return _headerNames.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return _headerNames.size();
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			Log.i("getGroupView","convertView == Null");
			LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			Log.i("getGroupView","inflating layout");
			convertView = inflater.inflate(R.layout.header_item, null);
			((TextView)convertView.findViewById(R.id.headerText)).setText(_headerNames.get(groupPosition));;
			_headerMap.put(_headerNames.get(groupPosition), convertView);
		}
		
		Log.i("getGroupView","returning convertView");
		
		return convertView;
	}
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
}
