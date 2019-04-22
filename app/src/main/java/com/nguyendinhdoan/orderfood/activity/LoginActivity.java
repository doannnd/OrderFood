package com.nguyendinhdoan.orderfood.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyendinhdoan.orderfood.utils.CommonUtils;
import com.nguyendinhdoan.orderfood.R;
import com.nguyendinhdoan.orderfood.model.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    public static final String TAG = "LOGIN_ACTIVITY";
    public static final String USER_TABLE_NAME = "users";

    private ConstraintLayout loginRootLayout;
    private Button loginButton;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    private TextInputLayout phoneLayout;
    private TextInputLayout passwordLayout;

    private DatabaseReference usersTable;

    public static Intent start(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupUI();
        addEvents();
    }

    private void setupUI() {
        initFirebaseDatabase();
    }

    private void initFirebaseDatabase() {
        usersTable = FirebaseDatabase.getInstance().getReference(USER_TABLE_NAME);
    }

    private void addEvents() {
        loginButton.setOnClickListener(this);
        passwordEditText.setOnEditorActionListener(this);
    }

    private void initViews() {
        loginRootLayout = findViewById(R.id.login_root_layout);
        loginButton = findViewById(R.id.login_button);
        phoneEditText = findViewById(R.id.phone_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        progressBar = findViewById(R.id.progress_bar);
        phoneLayout = findViewById(R.id.phone_layout);
        passwordLayout = findViewById(R.id.password_layout);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {
            requestLoginWithFirebase();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            CommonUtils.hideKeyboard(this);
            requestLoginWithFirebase();
            return true;
        }
        return false;
    }

    private void requestLoginWithFirebase() {
        final String phone = phoneEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            phoneLayout.setError(getString(R.string.empty_phone));
            phoneLayout.setErrorEnabled(true);
            phoneEditText.requestFocus();
            return;
        } else {
            phoneLayout.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError(getString(R.string.empty_password));
            passwordLayout.setErrorEnabled(true);
            passwordEditText.requestFocus();
            return;
        } else {
            passwordLayout.setErrorEnabled(false);
        }

        if (password.length() < 6) {
            passwordLayout.setError(getString(R.string.short_password));
            passwordLayout.setErrorEnabled(true);
            passwordEditText.requestFocus();
            return;
        } else {
            passwordLayout.setErrorEnabled(false);
        }

        progressBar.setVisibility(View.VISIBLE);
        usersTable.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        progressBar.setVisibility(View.INVISIBLE);

                        if (dataSnapshot.child(phone).exists()) {

                            // get information user form database
                            User user = dataSnapshot.child(phone).getValue(User.class);

                            // validate password
                            if (user != null && password.equals(user.getPassword())) {
                                Snackbar.make(loginRootLayout, getString(R.string.login_success),
                                        Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(loginRootLayout, getString(R.string.wrong_password),
                                        Snackbar.LENGTH_LONG).show();
                            }

                        } else {
                            Snackbar.make(loginRootLayout, getString(R.string.user_not_exist),
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "error: " + error.getMessage());
                        progressBar.setVisibility(View.INVISIBLE);
                        Snackbar.make(loginRootLayout, error.getMessage(),
                                Snackbar.LENGTH_LONG).show();
                    }
                }
        );

    }

}
