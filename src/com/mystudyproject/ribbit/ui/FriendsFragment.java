package com.mystudyproject.ribbit.ui;

import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.mystudyproject.ribbit.R;
import com.mystudyproject.ribbit.adapters.UserAdapter;
import com.mystudyproject.ribbit.utils.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */

public class FriendsFragment extends Fragment {

	public static final String TAG = FriendsFragment.class.getSimpleName();

	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mFriendsRelation;
	protected ParseUser mCurrentUser;
	protected GridView mGridView;

	/*
	 * Called when the fragment is created for the first time
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.user_grid, container,
				false);// kind of setContent view

		mGridView = (GridView) rootView.findViewById(R.id.friendsGrid);

		// look of the emptyTextView is an extra credit.
		TextView emptyTextView = (TextView) rootView
				.findViewById(android.R.id.empty);
		mGridView.setEmptyView(emptyTextView);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		mCurrentUser = ParseUser.getCurrentUser();
		mFriendsRelation = mCurrentUser
				.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

		getActivity().setProgressBarIndeterminateVisibility(true);

		ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
		query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		mFriendsRelation.getQuery().findInBackground(
				new FindCallback<ParseUser>() {

					@Override
					public void done(List<ParseUser> friends, ParseException e) {
						getActivity().setProgressBarIndeterminateVisibility(
								false);
						if (e == null) {
							mFriends = friends;

							String[] usernames = new String[mFriends.size()];
							int i = 0;
							for (ParseUser user : mFriends) {
								usernames[i] = user.getUsername();
								i++;
							}

							if (mGridView.getAdapter() == null) {
								UserAdapter adapter = new UserAdapter(
										getActivity(), mFriends);
								mGridView.setAdapter(adapter);
							} else {
								((UserAdapter) mGridView.getAdapter())
										.refill(mFriends);
							}
						} else {
							AlertDialog.Builder builder = new AlertDialog.Builder(
									getActivity())
									.setMessage(e.getMessage())
									.setTitle(R.string.error_title)
									.setPositiveButton(android.R.string.ok,
											null);

							AlertDialog dialog = builder.create();
							dialog.show();
						}
					}
				});
	}
}
