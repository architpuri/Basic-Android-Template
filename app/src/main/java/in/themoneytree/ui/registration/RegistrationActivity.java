package in.themoneytree.ui.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.ApiConstants;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.data.security.Encrypter;
import in.themoneytree.ui.login.LoginActivity;
import in.themoneytree.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.edt_username)
    TextInputEditText edtUsername;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.edt_fullName)
    TextInputEditText edtFullName;
    @BindView(R.id.edt_mobile)
    TextInputEditText edtMobile;
    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.progress_submit)
    ProgressBar progressSubmit;
    @BindView(R.id.txt_heading_registration)
    TextView txtHeadingRegistration;
    @BindView(R.id.text_username)
    TextInputLayout textUsername;
    @BindView(R.id.text_password)
    TextInputLayout textPassword;
    @BindView(R.id.text_fullName)
    TextInputLayout textFullName;
    @BindView(R.id.text_mobile)
    TextInputLayout textMobile;
    private final static String TAG = "REGISTRATION ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtUsername.getText().toString()) ||
                        TextUtils.isEmpty(edtPassword.getText().toString()) ||
                        TextUtils.isEmpty(edtFullName.getText().toString()) ||
                        TextUtils.isEmpty(edtMobile.getText().toString())) {
                    CommonUtils.showToast(RegistrationActivity.this, "Details Incomplete");
                } else if (edtPassword.getText().toString().length() < 6) {
                    CommonUtils.wrongInputErrorMsg(textPassword, "Password Length should be > 6", edtPassword);
                }else if(!CommonUtils.isValidEmail(edtUsername.getText().toString())) {
                    CommonUtils
                            .wrongInputErrorMsg(textUsername, "Please check email format", edtUsername);
                }else {
                    sendResponsesToServer();
                }
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUsername.setText("");
                edtPassword.setText("");
                edtFullName.setText("");
                edtMobile.setText("");
            }
        });
    }

    private void sendResponsesToServer() {
        progressSubmit.setVisibility(View.VISIBLE);
        String mobile = null;
        try {
            mobile = edtMobile.getText().toString();
        } catch (NullPointerException e) {
            mobile = "NA";
        } catch (Exception e) {
            CommonUtils.showToast(getApplicationContext(), "Check Mobile No");
        }
        if (mobile.length() != 10) {
            CommonUtils.showToast(getApplicationContext(), "Check Mobile No");
        }
        MoneyService colioService = ApiClient.getInstance();
        Call<GeneralResponse> sendUserDetailsRequest = colioService.sendUserDetails(
                edtUsername.getText().toString(),
                Encrypter.encryption(edtPassword.getText().toString()),
                edtFullName.getText().toString(),
                0,
                mobile);
        sendUserDetailsRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                progressSubmit.setVisibility(View.GONE);
                if (response.body().getStatusCode() == ApiConstants.CREATED) {
                    CommonUtils.showToast(RegistrationActivity.this, "Success-Check Email for Verification");
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                } else {
                    CommonUtils.showToast(RegistrationActivity.this, "Message - " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                progressSubmit.setVisibility(View.GONE);
                CommonUtils.failureShow(TAG, RegistrationActivity.this);
            }
        });
    }

}
