package com.plow.myoak.presentation.files;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.plow.myoak.R;
import com.plow.myoak.business.files.ResourceType;
import com.plow.myoak.utils.Factory;

public class FilePerspectiveFragment extends ListFragment implements android.view.View.OnClickListener, OnItemClickListener{

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface FilePerspectiveFragmentCallbacks {
		/**
		 * Callback for when an item has been selected.
		 * @param filePerspectiveFragment 
		 * @param l 
		 */
		public void onResourceItemSelected(FilePerspectiveFragment filePerspectiveFragment, ResourcePresentation resourcePresentation);
	}
	
	private FilePerspectiveFragmentCallbacks mCallbacks;

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
	    resources = Factory.getResources(null);
		FilePerspectiveListAdapter filePerspectiveListAdapter = new FilePerspectiveListAdapter(getActivity(), this, resources );
	    setListAdapter(filePerspectiveListAdapter);
	    //Activate action menus
	    setHasOptionsMenu(true); 
	    setMenuVisibility(false);
	    getListView().setClickable(true);
	    getListView().setOnItemClickListener(this);
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
	 
	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
		  super.onListItemClick(l, v, position, id);
			Log.d("onListItemClick","HELLO ************ *********!");
			// Notify the active callbacks interface (the activity, if the
			// fragment is attached to one) that an item has been selected.
			mCallbacks.onResourceItemSelected( this , resources.get(position));

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
		    case R.id.action_create:
		    	Toast.makeText(getActivity(), "Menu create selected", Toast.LENGTH_SHORT).show();
		    	return true;
		    case R.id.action_refresh:
		    	Toast.makeText(getActivity(), "Menu refresh selected", Toast.LENGTH_SHORT).show();
		    	return true;
		    default:
		    	return super.onOptionsItemSelected(item);
	    }

	  }

	//Handling CheckBox listener
	@Override
	public void onClick(View view) {
		CheckBox cb = (CheckBox) view ; 
        ResourcePresentation clickedresource = (ResourcePresentation) cb.getTag(); 
        clickedresource.setSelected(cb.isChecked());
        //Activate action menus
        setMenuVisibility(isOneResourceSelected());   
		getActivity().invalidateOptionsMenu();
		Toast.makeText(getActivity(), "Menu refresh selected", Toast.LENGTH_SHORT).show();
	} 
	
	private boolean isOneResourceSelected() {		
		boolean isOneResourceSelected = false;
		for (int i = 0; i < resources.size() && !isOneResourceSelected; i++) {
			isOneResourceSelected = resources.get(i).isSelected();
		} 
		return isOneResourceSelected;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ResourcePresentation clickedresource = resources.get(position);
		  Log.w("***************", "Item selected "+clickedresource.getType());
		  if(clickedresource.getType().equals(ResourceType.DIRECTORY)){
			  resources = ((DirectoryPresentation)clickedresource).getChildren();
			  FilePerspectiveListAdapter filePerspectiveListAdapter = new FilePerspectiveListAdapter(getActivity(), this, resources );
			  setListAdapter(filePerspectiveListAdapter);
			  Log.w("***************", "Item selected "+clickedresource.getName());
		  }

		
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Activities containing this fragment must implement its callbacks.
				if (!(activity instanceof FilePerspectiveFragmentCallbacks)) {
					throw new IllegalStateException(
							"Activity must implement fragment's FilePerspectiveFragmentCallbacks.");
				}
		mCallbacks = (FilePerspectiveFragmentCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
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
