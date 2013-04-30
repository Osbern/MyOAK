package com.plow.myoak.presentation.contact;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.plow.myoak.R;
import com.plow.myoak.model.Node;

public class ContactPresentation extends LinearLayout {
	
	private static int[] colors = new int[] {0xffd2e1d4, 0xffb4cdb8};
	
	private Node resource;

	private CheckBox chkSelect;
	private ImageView image;
	private TextView txtVwname;
	private TextView txtVwSize;
    
	public ContactPresentation(Activity context, Node resource, int i) {
		super(context);
		this.resource = resource;

		chkSelect = new CheckBox(context);
		image = new ImageView(context);
		txtVwname = new TextView(context);
		txtVwSize = new TextView(context);
		
		txtVwname.setText("\t\t" + resource.getName());
		if (resource.isDirectory())
			image.setImageResource(R.drawable.folder_icon);
		else
			image.setImageResource(R.drawable.file_icon);
		if (resource.getSize() > 0)
			txtVwSize.setText(resource.getSize() + " Kb");
		
		if (i > 0)
			addView(chkSelect, 30, 50);
		else
			addView(new TextView(context), 30, 50);
		addView(image, 30, 50);
		addView(txtVwname, 200, 50);
		addView(txtVwSize, 100, 50);
		
		setBackgroundColor(colors[i % colors.length]);
		chkSelect.setTag(this);
	}

	public Node getResource() {
		return resource;
	}

	public boolean isDirectory() {
		return resource.isDirectory();
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		super.setOnClickListener(l);
		chkSelect.setOnClickListener(l);
	}
}
