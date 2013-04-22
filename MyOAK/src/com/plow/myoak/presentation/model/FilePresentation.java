package com.plow.myoak.presentation.model;

import com.plow.myoak.R;
import com.plow.myoak.model.File;

public class FilePresentation  extends ResourcePresentation{
 
	private File file;
    
	public FilePresentation(File file) {
		super(file);
		icon= R.drawable.file_icon;
	}
	
	
}
