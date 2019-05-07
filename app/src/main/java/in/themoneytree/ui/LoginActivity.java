package in.themoneytree.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_title)
    TextView loginTitle;
    @BindView(R.id.edt_user_id)
    TextInputEditText edtUserId;
    @BindView(R.id.textLayout_user)
    TextInputLayout textLayoutUser;
    @BindView(R.id.edt_user_password)
    TextInputEditText edtUserPassword;
    @BindView(R.id.textLayout_password)
    TextInputLayout textLayoutPassword;
    @BindView(R.id.text_forgot_password)
    TextView textForgotPassword;
    @BindView(R.id.btn_signin)
    Button btnSignin;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_registration)
    Button btnRegistration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }
}
