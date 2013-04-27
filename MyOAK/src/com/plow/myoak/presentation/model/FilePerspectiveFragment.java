package com.plow.myoak.presentation.model;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.plow.myoak.R;
import com.plow.myoak.utils.Factory;

public class FilePerspectiveFragment extends ListFragment implements OnClickListener {
	
	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	
	
	 List<ResourcePresentation> resources ;
	 @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    View header = getActivity().getLayoutInflater().inflate(R.layout.file_perspective_header, null);
	    getListView().addHeaderView(header);
	    
	    resources = Factory.getResources(getActivity(), null);
		FilePerspectiveListAdapter filePerspectiveListAdapter = new FilePerspectiveListAdapter(getActivity(), this, resources);
	    setListAdapter(filePerspectiveListAdapter);
	    
	    //Activate action menus
	    setHasOptionsMenu(true); 
	    setMenuVisibility(false);
	    getListView().setClickable(true);
	    getListView().setTextFilterEnabled(true); 
	    
	  }

	 @Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			if (mActivatedPosition != ListView.INVALID_POSITION) {
				// Serialize and persist the activated item position.
				outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
			}
		}
	  
	//Handling ActionBar menus
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.file_perspective_menu, menu);
	}
	
	//Handling ActionBar actions
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.action_delete:
		    	Toast.makeText(getActivity(), "Menu Delete selected", Toast.LENGTH_SHORT).show();
		    	return true;
		    case R.id.action_create_file:
		    	Toast.makeText(getActivity(), "Menu create selected", Toast.LENGTH_SHORT).show();
		    	return true;
		    case R.id.action_create_folder:
		    	Toast.makeText(getActivity(), "Menu create selected", Toast.LENGTH_SHORT).show();
		    	return true;
		    case R.id.action_refresh:
		    	Toast.makeText(getActivity(), "Menu refresh selected", Toast.LENGTH_SHORT).show();
		    	return true;
		    default:
		    	return super.onOptionsItemSelected(item);
	    }
	  }

	@Override
	public void onClick(View view) {
		if (view instanceof ResourcePresentation) {
			ResourcePresentation res = (ResourcePresentation) view;
			if (res.getResource().isDirectory()) {
				resources = Factory.getResources(getActivity(), res);
				setListAdapter(new FilePerspectiveListAdapter(getActivity(), this, resources));
			}
		}
		else if(view instanceof CheckBox) {
			CheckBox cb = (CheckBox) view ; 
	        ResourcePresentation clickedresource = (ResourcePresentation) cb.getTag(); 
	        clickedresource.setSelected(cb.isChecked());
	        //Activate action menus
	        setMenuVisibility(isOneResourceSelected());   
			getActivity().invalidateOptionsMenu();
			Toast.makeText(getActivity(), "Menu refresh selected", Toast.LENGTH_SHORT).show();
		}
	} 
	
	private boolean isOneResourceSelected() {		
		boolean isOneResourceSelected = false;
		for (int i = 0; i < resources.size() && !isOneResourceSelected; i++) {
			isOneResourceSelected = resources.get(i).isSelected();
		} 
		return isOneResourceSelected;
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}
	
}
