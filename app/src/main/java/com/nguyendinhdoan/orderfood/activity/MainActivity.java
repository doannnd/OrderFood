package com.nguyendinhdoan.orderfood.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nguyendinhdoan.orderfood.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FONT_PATH = "fonts/NABILA.TTF";

    private Button loginButton;
    private Button registerButton;
    private TextView sloganTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupUI();
        addEvents();
    }

    private void initViews() {
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        sloganTextView = findViewById(R.id.slogan_text_view);
    }

    private void setupUI() {
        setupSlogan();
    }

    private void setupSlogan() {
        Typeface typefaceSlogan = Typeface.createFromAsset(getAssets(), FONT_PATH);
        sloganTextView.setTypeface(typefaceSlogan);
    }

    private void addEvents() {
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button: {
                launchLoginScreen();
                break;
            }
            case R.id.register_button: {
                launchRegisterScreen();
                break;
            }
        }
    }

    private void launchLoginScreen() {
        Intent intentLogin = LoginActivity.start(this);
        startActivity(intentLogin);
    }

    private void launchRegisterScreen() {
        Intent intentRegister = RegisterActivity.start(this);
        startActivity(intentRegister);
    }
}
