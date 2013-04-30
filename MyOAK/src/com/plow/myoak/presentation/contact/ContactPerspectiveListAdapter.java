package com.plow.myoak.presentation.contact;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.plow.myoak.R;

public class ContactPerspectiveListAdapter extends ArrayAdapter<ContactPresentation> {

	private List<ContactPresentation> resources;
	private OnClickListener listener;
	
	public ContactPerspectiveListAdapter(Activity context, OnClickListener listener, List<ContactPresentation> resources) {
		super(context, R.layout.file_perspective_fragment, resources);
	    this.resources = resources; 
	    this.listener = listener;
	}
	 
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		ContactPresentation res = resources.get(position);
		res.setOnClickListener(listener);
		return res;
	  }
}
