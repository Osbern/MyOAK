package com.plow.myoak.presentation.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.plow.myoak.R;
import com.plow.myoak.model.Node;
import com.plow.myoak.utils.EngineUtils;
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
	
	
	 private List<ResourcePresentation> resources ;
	 private ResourcePresentation currentRes;
	 
	 private AlertDialog alert;
	 private AlertDialog.Builder builder;
	 
	 
	 @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    View header = getActivity().getLayoutInflater().inflate(R.layout.file_perspective_header, null);
	    getListView().addHeaderView(header);
	    
	    currentRes = null;
	    refresh();

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
		final List<Node> selected = new ArrayList<Node>();
		for (ResourcePresentation res : resources) {
			if (res.isSelected())
				selected.add(res.getResource());
		}
		
	    switch (item.getItemId()) {
		    case R.id.action_delete:
		    	builder = new AlertDialog.Builder(getActivity());
		    	builder.setTitle("Supprimer");
		    	builder.setMessage("Voulez-vous supprimer cette Intervention ?").setPositiveButton("Oui", new DialogInterface.OnClickListener() {
		    		@Override
		    		public void onClick(DialogInterface dialogInterface, int i) {
		    			for (Node n : selected) {
				    		Toast.makeText(getActivity(), EngineUtils.getEngine().rm(n), Toast.LENGTH_SHORT).show();
				    	}
		    			alert.cancel();
				    	refresh();
				    	setMenuVisibility(false); 
		    		}
		    	}).setNegativeButton("Non", new DialogInterface.OnClickListener() {
		    		@Override
		    		public void onClick(DialogInterface dialogInterface, int i) {
		    			alert.cancel();
		    		}
		    	});
		    	alert = builder.create();
		    	alert.show();
		    	return true;
		    case R.id.action_create_file:
		    	Toast.makeText(getActivity(), "Menu create selected", Toast.LENGTH_SHORT).show();
		    	return true;
		    case R.id.action_create_folder:
		    	final EditText name = new EditText(getActivity());
		    	builder = new AlertDialog.Builder(getActivity());
		    	builder.setTitle("Supprimer");
		    	builder.setMessage("Nom du dossier").setView(name).setPositiveButton("Créer", new DialogInterface.OnClickListener() {
		    		@Override
		    		public void onClick(DialogInterface dialogInterface, int i) {
		    			Toast.makeText(getActivity(), EngineUtils.getEngine().mkdir(name.getText().toString()), Toast.LENGTH_SHORT).show();
		    			alert.cancel();
				    	refresh();
				    	setMenuVisibility(false); 
		    		}
		    	}).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
		    		@Override
		    		public void onClick(DialogInterface dialogInterface, int i) {
		    			alert.cancel();
		    		}
		    	});
		    	alert = builder.create();
		    	alert.show();
		    	Toast.makeText(getActivity(), "Menu create selected", Toast.LENGTH_SHORT).show();
		    	return true;
		    case R.id.action_refresh:
		    	refresh();
		    	setMenuVisibility(false); 
		    	return true;
		    default:
		    	return super.onOptionsItemSelected(item);
	    }
	  }
	
	private void refresh() {
		resources = Factory.getResources(getActivity(), currentRes);
		setListAdapter(new FilePerspectiveListAdapter(getActivity(), this, resources));
	}

	@Override
	public void onClick(View view) {
		if (view instanceof ResourcePresentation) {
			currentRes = (ResourcePresentation) view;
			if (currentRes.isDirectory())
				refresh();
			else {
				String name = currentRes.getResource().getName();
				String extension = MimeTypeMap.getFileExtensionFromUrl(name);
				String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
				
				if (mime.startsWith("audio")) {
					final MediaPlayer mediaPlayer = new MediaPlayer();
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					try {
						mediaPlayer.setDataSource(EngineUtils.WEBDAV + "/" + name);
						mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
							@Override
							public void onPrepared(MediaPlayer mp) {
								mediaPlayer.start();
							}
						});
						mediaPlayer.prepareAsync();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					String buffer = EngineUtils.getEngine().get(currentRes.getResource());
					FileOutputStream outputStream;
					
					try {
					  outputStream = getActivity().openFileOutput(name, Context.MODE_WORLD_READABLE);
					  outputStream.write(buffer.getBytes());
					  outputStream.close();
					} catch (Exception e) {
					  e.printStackTrace();
					}
					
					File file = new File(getActivity().getFilesDir(), currentRes.getResource().getName());
					if (extension.isEmpty())
						extension = "txt";
					String mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
					
					Intent i = new Intent();
					i.setAction(android.content.Intent.ACTION_VIEW);
					i.setDataAndType(Uri.fromFile(file), mimetype);
					startActivity(i);
				}
			}
		}
		else if(view instanceof CheckBox) {
			CheckBox cb = (CheckBox) view ; 
	        ResourcePresentation clickedresource = (ResourcePresentation) cb.getTag(); 
	        clickedresource.setSelected(cb.isChecked());
	        //Activate action menus
	        setMenuVisibility(isOneResourceSelected());   
			getActivity().invalidateOptionsMenu();
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
