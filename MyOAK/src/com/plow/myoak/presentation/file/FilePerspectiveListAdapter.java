package com.plow.myoak.presentation.file;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.plow.myoak.R;

public class FilePerspectiveListAdapter extends ArrayAdapter<ResourcePresentation> {

	private List<ResourcePresentation> resources;
	private OnClickListener listener;
	
	public FilePerspectiveListAdapter(Activity context, OnClickListener listener, List<ResourcePresentation> resources) {
		super(context, R.layout.file_perspective_fragment, resources);
	    this.resources = resources; 
	    this.listener = listener;
	}
	 
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		ResourcePresentation res = resources.get(position);
		res.setOnClickListener(listener);
		return res;
	  }
}
