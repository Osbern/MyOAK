package com.plow.myoak.presentation.model;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.plow.myoak.R;

public class FilePerspectiveListAdapter extends ArrayAdapter<ResourcePresentation> {

	private final Activity context;
	private List<ResourcePresentation> resources;
	private int[] colors = new int[] { 0x30FF0000, 0x300000FF };
	private OnClickListener listener;
	static class ViewHolder {
		public CheckBox chkSelect;
		public TextView txtVwname;
		public TextView txtVwSize;
		public TextView txtVwDate;
	    public ImageView image;
	}
	
	public FilePerspectiveListAdapter(Activity context, OnClickListener listener, List<ResourcePresentation> resources) {
		super(context, R.layout.file_perspective_fragment, resources);
	    this.context = context;
	    this.resources = resources; 
	    this.listener = listener;
	}
	 
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		final int POS = position;
	    View rowView = convertView;
	    ResourcePresentation resourcePresentation = resources.get(position);
	    if (rowView == null) {
	      LayoutInflater inflater = context.getLayoutInflater();
	      rowView = inflater.inflate(R.layout.file_perspective_fragment, null);
	      ViewHolder viewHolder = new ViewHolder();
	      viewHolder.txtVwname = (TextView) rowView.findViewById(R.id.name);
	      viewHolder.image = (ImageView) rowView.findViewById(R.id.img);
	      viewHolder.txtVwSize = (TextView) rowView.findViewById(R.id.size);
	      viewHolder.txtVwDate = (TextView) rowView.findViewById(R.id.date);
	      viewHolder.chkSelect = (CheckBox) rowView.findViewById(R.id.chkSelect);
	      
	      viewHolder.chkSelect.setOnClickListener(listener);
	      rowView.setTag(viewHolder);
	     
	    }

	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    holder.chkSelect.setTag(resources.get(position));
	    holder.txtVwname.setText(resourcePresentation.getName()) ;
	    holder.image.setImageResource(resourcePresentation.getIcon());
	    holder.txtVwSize.setText(""+resourcePresentation.getSize()) ;
	    holder.txtVwDate.setText(resourcePresentation.getDate().toGMTString()) ;
	    holder.chkSelect.setChecked(resourcePresentation.isSelected()) ;
	    //Changing the background
	    int colorPos = position % colors.length;
	    rowView.setBackgroundColor(colors[colorPos]);
	    
	    return rowView;
	  }

	
	 

}
