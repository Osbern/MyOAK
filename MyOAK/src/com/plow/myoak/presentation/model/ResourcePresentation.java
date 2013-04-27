package com.plow.myoak.presentation.model;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plow.myoak.R;
import com.plow.myoak.model.Node;

public class ResourcePresentation extends LinearLayout {
	
	private static int[] colors = new int[] {0xffd2e1d4, 0xffb4cdb8};
	
	private Node resource;

	private CheckBox chkSelect;
	private ImageView image;
	private TextView txtVwname;
	private TextView txtVwSize;
	private TextView txtVwDate;
    
	public ResourcePresentation(Activity context, Node resource, int i) {
		super(context);
		this.resource = resource;

		chkSelect = new CheckBox(context);
		image = new ImageView(context);
		txtVwname = new TextView(context);
		txtVwSize = new TextView(context);
		txtVwDate = new TextView(context);
		
		txtVwname.setText("\t\t" + resource.getName());
		if (resource.isDirectory())
			image.setImageResource(R.drawable.folder_icon);
		else
			image.setImageResource(R.drawable.file_icon);
		if (resource.getSize() > 0)
			txtVwSize.setText(resource.getSize() + " Kb");
		txtVwDate.setText(resource.getDate().toGMTString());
		
		addView(chkSelect, 30, 40);
		addView(image, 30, 40);
		addView(txtVwname, 200, 40);
		addView(txtVwSize, 100, 40);
		addView(txtVwDate, 200, 40);
		
		setBackgroundColor(colors[i % colors.length]);
	}

	public Node getResource() {
		return resource;
	}

	public boolean isDirectory() {
		return resource.isDirectory();
	}
}
