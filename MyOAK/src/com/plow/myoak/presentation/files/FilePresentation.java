package com.plow.myoak.presentation.files;

import com.plow.myoak.R;
import com.plow.myoak.business.files.File;

public class FilePresentation  extends ResourcePresentation{
 
	private File file;
    
	public FilePresentation(File file) {
		super(file);
		icon= R.drawable.file_icon;
	}
	
	
}
