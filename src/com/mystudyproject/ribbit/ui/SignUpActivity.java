package com.mystudyproject.ribbit.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.mystudyproject.ribbit.R;
import com.mystudyproject.ribbit.RibbitApplication;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	protected Button mSignUpButton;
	protected Button mCancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//always call before setcontentview
		setContentView(R.layout.activity_sign_up);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		mUsername = (EditText) findViewById(R.id.usernameField);
		mPassword = (EditText) findViewById(R.id.passwordField);
		mEmail = (EditText) findViewById(R.id.emailField);
		
		mCancelButton = (Button) findViewById(R.id.cancelButton);
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mSignUpButton = (Button) findViewById(R.id.signupButton);
		
		mSignUpButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = mUsername.getText().toString().trim();
				String password = mPassword.getText().toString().trim();
				String email = mEmail.getText().toString().trim();
				
				if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
					AlertDialog.Builder builder= new AlertDialog.Builder(SignUpActivity.this)
										.setMessage(R.string.signup_error_message)
										.setTitle(R.string.signup_error_title)
										.setPositiveButton(android.R.string.ok, null);
					
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				else{
					setProgressBarIndeterminateVisibility(true);
					//create the new user by using Parse.com's API
					ParseUser newUser = new ParseUser();
					newUser.setUsername(username);
					newUser.setPassword(password);
					newUser.setEmail(email);
					newUser.signUpInBackground(new SignUpCallback() {
						@Override
						public void done(ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							if(e == null){
								//Success!

								RibbitApplication.updateParseInstallation(
										ParseUser.getCurrentUser());
								
								Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}
							else{
								AlertDialog.Builder builder= new AlertDialog.Builder(SignUpActivity.this)
								.setMessage(e.getMessage())
								.setTitle(R.string.signup_error_title)
								.setPositiveButton(android.R.string.ok, null);
			
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
				}
				
			}
		});
	}


}
