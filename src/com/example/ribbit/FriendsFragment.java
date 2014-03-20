package com.example.ribbit;

import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;


/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */

public class FriendsFragment extends ListFragment{
	
	public static final String TAG = FriendsFragment.class.getSimpleName();
	
	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mFriendsRelation;
	protected ParseUser mCurrentUser;
	/*
	 * Called when the fragment is created for the first time*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_friends,
				container, false);//kind of setContent view
		
	
		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mCurrentUser = ParseUser.getCurrentUser();
		mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
		query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		mFriendsRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> friends, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				if(e == null){
				mFriends = friends;
				

				String [] usernames = new String[mFriends.size()];
				int i = 0;
				for(ParseUser user : mFriends){
					usernames[i] = user.getUsername();
					i++;
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(),
						android.R.layout.simple_list_item_1, usernames);
				setListAdapter(adapter);
				}
				else {
					AlertDialog.Builder builder= new AlertDialog.Builder(getListView().getContext())
					.setMessage(e.getMessage())
					.setTitle(R.string.error_title)
					.setPositiveButton(android.R.string.ok, null);

					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});
	}
}
