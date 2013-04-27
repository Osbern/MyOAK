package com.plow.myoak.presentation.model;

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
//		final int POS = position;
//	    View rowView = convertView;
//	    ResourcePresentation resourcePresentation = resources.get(position);
//	    
//	    if (rowView == null) {
//	      LayoutInflater inflater = context.getLayoutInflater();
//	      rowView = inflater.inflate(R.layout.file_perspective_fragment, null);
//	      ViewHolder viewHolder = new ViewHolder();
//	      viewHolder.txtVwname = (TextView) rowView.findViewById(R.id.name);
//	      viewHolder.image = (ImageView) rowView.findViewById(R.id.img);
//	      viewHolder.txtVwSize = (TextView) rowView.findViewById(R.id.size);
//	      viewHolder.txtVwDate = (TextView) rowView.findViewById(R.id.date);
//	      viewHolder.chkSelect = (CheckBox) rowView.findViewById(R.id.chkSelect);
//	      
//	      viewHolder.chkSelect.setOnClickListener(listener);
//	      viewHolder.txtVwname.setOnClickListener(listener);
//	      viewHolder.image.setOnClickListener(listener);
//	      viewHolder.txtVwSize.setOnClickListener(listener);
//	      viewHolder.txtVwDate.setOnClickListener(listener);
//	      rowView.setTag(viewHolder);
//	     
//	    }
//
//	    ViewHolder holder = (ViewHolder) rowView.getTag();
//	    holder.chkSelect.setTag(resources.get(position));
//	    holder.chkSelect.setChecked(resourcePresentation.isSelected()) ;
//	    
//	    holder.txtVwname.setText(resourcePresentation.getName()) ;
//	    holder.txtVwname.setTag(resources.get(position));
//	    
//	    holder.image.setImageResource(resourcePresentation.getIcon());
//	    holder.image.setTag(resources.get(position));
//	    
//	    holder.txtVwSize.setText(""+resourcePresentation.getSize()) ;
//	    holder.txtVwSize.setTag(resources.get(position));
//	    
//	    holder.txtVwDate.setText("" +resourcePresentation.getDate()) ;
//	    holder.txtVwDate.setTag(resources.get(position));
//	    
//	    //Changing the background
//	    int colorPos = position % colors.length;
//	    rowView.setBackgroundColor(colors[colorPos]);
//	    
//	    return rowView;
	  }

	
	 

}
