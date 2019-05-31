package in.themoneytree.ui.splash;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import in.themoneytree.R;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.ui.home.HomeActivity;
import in.themoneytree.ui.login.LoginActivity;


public class SplashActivity extends AppCompatActivity {
    private static int splash_time_out = 1500;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView = findViewById(R.id.text_splash);
                textView.setVisibility(View.VISIBLE);
                new SplashBackground().execute();
            }
        }, splash_time_out);

    }

    public class SplashBackground extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Do something before this
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Check if shared preferences contain valid login id & password
            boolean isValid = false;//PrefManager.getInstance().isLoggedin();
            if (PrefManager.getInstance(getApplicationContext()) != null) {
                if (PrefManager.getInstance(getApplicationContext()).getAccessToken() != null) {
                    isValid = true;
                }
            }
            return isValid;
        }

        @Override
        protected void onPostExecute(Boolean isValid) {
            super.onPostExecute(isValid);

            if (isValid) {
                //getDetails from Shared Prefernces id,passwd,idtype=1 == student
                //already logged in
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                finish();
            } else {
                //new Login
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }
    }
}
