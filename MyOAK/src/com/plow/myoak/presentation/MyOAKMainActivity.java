package com.plow.myoak.presentation;


import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.plow.myoak.R;
import com.plow.myoak.presentation.model.DirectoryPresentation;
import com.plow.myoak.presentation.model.FilePerspectiveFragment;
import com.plow.myoak.presentation.model.FilePerspectiveListAdapter;
import com.plow.myoak.presentation.model.ResourcePresentation;
import com.plow.myoak.utils.MenuListProvider;


public class MyOAKMainActivity extends FragmentActivity implements 	PerspectiveListFragment.Callbacks
                                                                    , FilePerspectiveFragment.FilePerspectiveFragmentCallbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private MenuListProvider menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
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

		// TODO: If exposing deep links into your app, handle intents here.
	}
	
	//When an item in the menu 
	@Override
	public void onItemSelected(String id) {
		FilePerspectiveFragment filePerspectiveFragment = new FilePerspectiveFragment();		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.pespective_detail_container, filePerspectiveFragment);
		transaction.commit();
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

	@Override
	public void onResourceItemSelected(FilePerspectiveFragment filePerspectiveFragment, ResourcePresentation resourcePresentation) {
		  Toast.makeText(this, "Item selected "+ resourcePresentation.getName(), Toast.LENGTH_SHORT).show();
		  if(resourcePresentation.isDirectory()){
			  List<ResourcePresentation> resources = ((DirectoryPresentation)resourcePresentation).getChildren();
			  
			  FilePerspectiveListAdapter filePerspectiveListAdapter = new FilePerspectiveListAdapter(this, filePerspectiveFragment, resources );
			  filePerspectiveFragment.setListAdapter(filePerspectiveListAdapter);
			  Toast.makeText( this, "Item selected "+ (resourcePresentation.isDirectory() ? "D" : "F"), Toast.LENGTH_SHORT).show();
		  }
		
	} 
}
