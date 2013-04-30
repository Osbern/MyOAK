package com.plow.myoak;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.plow.myoak.model.Node;
import com.plow.myoak.presentation.PerspectiveDetailFragment;
import com.plow.myoak.presentation.PerspectiveListFragment;
import com.plow.myoak.presentation.contact.ContactPerspectiveFragment;
import com.plow.myoak.presentation.file.FilePerspectiveFragment;
import com.plow.myoak.utils.EngineUtils;
import com.plow.myoak.utils.MenuListProvider;


public class MyOAKMainActivity extends FragmentActivity implements 	PerspectiveListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private MenuListProvider menu;
	
	private FilePerspectiveFragment filePerspectiveFragment;
	private ContactPerspectiveFragment contactPerspectiveFragment;
	
	private AlertDialog alert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(ThreadPolicy.LAX);
		
		menu = new MenuListProvider();
		setContentView(R.layout.myoak);

		if (findViewById(R.id.pespective_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			
			mTwoPane = true;
			//If we are being restored from a previous state, 
			//then we don't need to do anything and should return.
			//Otherwise, we could end up with overlapping fragments
			if(savedInstanceState != null){
				return;
			}
			//Create un instance of Detail Fragment
			PerspectiveDetailFragment perspectiveDetailFragment = new PerspectiveDetailFragment(); 
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.pespective_detail_container, perspectiveDetailFragment);
			transaction.commit();
			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((PerspectiveListFragment) getSupportFragmentManager().findFragmentById(
					R.id.pespective_list)).setActivateOnItemClick(true);
		}

		filePerspectiveFragment = new FilePerspectiveFragment();
		contactPerspectiveFragment= new ContactPerspectiveFragment();
		
		//INTENT
		
	    Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();
	    
	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	    	Uri img = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);

	    	String[] projection = {MediaStore.Images.Media.DATA}; 
	    	Cursor cursor = managedQuery(img, projection, null, null, null); 
	    	int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
	    	cursor.moveToFirst(); 
	    	final String path = cursor.getString(column_index_data);

	    	if (Intent.ACTION_SEND.equals(action) && type != null) {
	    		if (type.startsWith("image/")) {
	    			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    			builder.setTitle("Enregistrer");
	    			builder.setMessage("Voulez-vous enregistrer cet fichier ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
	    				@Override
	    				public void onClick(DialogInterface dialogInterface, int i) {
	    					Log.w("PUT", path);

	    					EngineUtils.getEngine().put(path);
	    					alert.cancel();
	    				}
	    			}).setNegativeButton("Non", new DialogInterface.OnClickListener() {
	    				@Override
	    				public void onClick(DialogInterface dialogInterface, int i) {
	    					alert.cancel();
	    				}
	    			});
	    			alert = builder.create();
	    			alert.show();
	    		}
	    	}
	    }
	}
	
	//When an item in the menu 
	@Override
	public void onItemSelected(String id) {
		FragmentTransaction transaction;
		switch (Integer.parseInt(id)) {
		case 1: //Fichiers	
			transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.pespective_detail_container, filePerspectiveFragment);
			transaction.commit();
			break;
		
		case 2: //Contacts
			transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.pespective_detail_container, contactPerspectiveFragment);
			transaction.commit();
			break;
		default:
			break;
		}
		
	}
	
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.action_bar_items, menu);
	    return true;
	  }
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_home:
	    	Intent intent = new Intent(this, MyOAKMainActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(intent);	    
	        Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT).show();
	        return true;
	    case android.R.id.home:
	    	  Intent intent2 = new Intent(this, MyOAKMainActivity.class);
	    	  intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	  startActivity(intent2);
	    	  return true;
	    default:
	    	return super.onOptionsItemSelected(item);
	    }

	  }

	
}
