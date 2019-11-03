package in.themoneytree.ui.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.ApiConstants;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.data.model.user.User;
import in.themoneytree.data.model.user.UserResponse;
import in.themoneytree.ui.home.HomeActivity;
import in.themoneytree.ui.registration.RegistrationActivity;
import in.themoneytree.ui.riskprofile.GeneralRiskAnalyzerActivity;
import in.themoneytree.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN ACTIVITY";
    @BindView(R.id.edt_user_id)
    TextInputEditText edtUserEmail;
    @BindView(R.id.edt_user_password)
    TextInputEditText edtUserPassword;
    @BindView(R.id.btn_signin)
    Button btnSignIn;
    @BindView(R.id.textLayout_password)
    TextInputLayout textLayoutPassword;
    @BindView(R.id.textLayout_user)
    TextInputLayout textLayoutuserid;
    @BindView(R.id.text_forgot_password)
    TextView textForgotPassword;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_registration)
    Button btnRegistration;
    private Context context = LoginActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUserEmail.getText().toString().trim();
                String password = edtUserPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    CommonUtils
                            .wrongInputErrorMsg(textLayoutuserid, "Please enter email", edtUserEmail);
                } else if (!CommonUtils.isValidEmail(email)) {
                    CommonUtils
                            .wrongInputErrorMsg(textLayoutuserid, "Please check email format", edtUserEmail);
                } else if (TextUtils.isEmpty(password)) {
                    CommonUtils
                            .wrongInputErrorMsg(textLayoutPassword, "Please enter password", edtUserPassword);
                } else if (!CommonUtils.isValidPassword(password)) {
                    CommonUtils
                            .wrongInputErrorMsg(textLayoutPassword, "Please check password", edtUserPassword);
                } else {

                    progressBar.setVisibility(View.VISIBLE);
                    //Allow Login first check from database
                    //directLogin();
                    serverLogin(email, password);
                }
            }
        });
        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
            }
        });
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void serverLogin(String email, String password) {
        /*password =Encrypter.encryption(Encrypter.passwordManipulator(password));*/
        //Convert Byte Array  to String and then send;
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> loginRequest = moneyService.isValidLogin(email, password);
        loginRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == ApiConstants.SUCCESS) {
                        progressBar.setVisibility(View.GONE);
                        String msg = response.body().getMessage();
                        String userId = msg.substring(msg.indexOf("[") + 1, msg.indexOf("]"));
                        saveToSharedPreferences(email, password, userId);
                        subscribeToFirebaseTopic();
                        setUserDetails(LoginActivity.this);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        CommonUtils.showToast(LoginActivity.this, response.body().getMessage());
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    CommonUtils.showToast(LoginActivity.this, "MESSAGE - " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                CommonUtils.showToast(LoginActivity.this, "Check Internet");
            }
        });
    }

    private void subscribeToFirebaseTopic() {/*
        FirebaseMessaging.getInstance().subscribeToTopic("money")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg ="Subscription Failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/
        String msg = CommonUtils.subscribeToFirebaseTopic("money");
    }

    private void saveToSharedPreferences(String email, String password, String userId) {
        PrefManager.getInstance(getApplicationContext()).setUserId(userId);
        PrefManager.getInstance(getApplicationContext()).setUserEmail(email);
        PrefManager.getInstance(getApplicationContext()).setUserPassword(password);
        PrefManager.getInstance(getApplicationContext()).setFirstTimeUser(true);
    }

    private void setUserDetails(Context context) {
        MoneyService moneyService = ApiClient.getInstance();
        Integer userId = Integer.parseInt(PrefManager.getInstance(context).getUserId());
        Call<UserResponse> getUserRequest = moneyService.getUserInfo(userId);
        getUserRequest.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == ApiConstants.SUCCESS) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        User user = response.body().getuser();
                        Log.d(TAG, user.isUserEnabled() + "Enable");
                        if (user.isUserEnabled()) {
                            PrefManager.getInstance(context).setUserEmail(user.getUserName());
                            PrefManager.getInstance(context).setUserName(user.getFullName());
                            PrefManager.getInstance(context).setUserContact(user.getMobileNumber());
                            PrefManager.getInstance(context).setUserImageUrl(user.getUserImageUrl());
                            PrefManager.getInstance(context).setBackgroundImg(user.getUserBackgroundImageUrl());
                            PrefManager.getInstance(context).setUserType(user.getUserType() + "");
                            PrefManager.getInstance(context).setAccessToken(user.getUserAccessToken());
                            if (user.getPortfolioId() == -1) {
                                startActivity(new Intent(context, GeneralRiskAnalyzerActivity.class));//remove after permission
                            } else {
                                Intent intent = new Intent(context, HomeActivity.class);
                                intent.putExtra("source", TAG);
                                startActivity(intent);
                            }
                            //UiConstants.setUpPermissionList(context, HomeActivity.class);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            CommonUtils.showToast(getApplicationContext(), "Please first Verify Using Email Wait for 10 mins atleast");
                        }
                    } catch (Exception e) {
                        CommonUtils.exceptionHandling(TAG, e);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    CommonUtils.showToast(getApplicationContext(), "MESSAGE" + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                CommonUtils.failureShow(TAG, getApplicationContext());
            }
        });
    }

}
