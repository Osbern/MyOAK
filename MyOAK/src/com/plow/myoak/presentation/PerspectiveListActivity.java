package com.plow.myoak.presentation;


import com.plow.myoak.R;
import com.plow.myoak.utils.MenuListProvider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link PerspectiveDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link PerspectiveListFragment} and the item details (if present) is a
 * {@link PerspectiveDetailFragment}.
 * <p>
 * This activity also implements the required {@link PerspectiveListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class PerspectiveListActivity extends FragmentActivity implements
		PerspectiveListFragment.Callbacks {

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
		setContentView(R.layout.activity_perspective_list);

		if (findViewById(R.id.item_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((PerspectiveListFragment) getSupportFragmentManager().findFragmentById(
					R.id.perspective_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link PerspectiveListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(PerspectiveDetailFragment.ARG_ITEM_ID, id);
			PerspectiveDetailFragment fragment = new PerspectiveDetailFragment();
			fragment.setMenu(menu);
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, PerspectiveDetailActivity.class);
			detailIntent.putExtra(PerspectiveDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
