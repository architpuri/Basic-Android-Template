package in.themoneytree.ui.login;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotActivity extends AppCompatActivity {
    @BindView(R.id.text_email)
    TextInputEditText edtForgotEmail;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.text_input_email)
    TextInputLayout textInputEmail;
    private boolean isEnabled = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable wrapDrawable = DrawableCompat.wrap(progressBar.getIndeterminateDrawable());
            DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.holo_red_dark), PorterDuff.Mode.SRC_IN);
        }
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_reset_password)
    public void onBtnResetPasswordClicked() {
        String email = edtForgotEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            CommonUtils
                    .wrongInputErrorMsg(textInputEmail, "Please enter email", edtForgotEmail);
        } else if (!CommonUtils.isValidEmail(email)) {
            CommonUtils
                    .wrongInputErrorMsg(textInputEmail, "Please check email format", edtForgotEmail);
        } else {
            progressBar.setVisibility(View.VISIBLE);
            //Allow Login first check from database
            resetUserPassword(email);
        }
    }

    private void resetUserPassword(String email) {
        btnDisableEnable();
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> resetPass = moneyService.resetPassword(email);
        resetPass.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful()) {
                    CommonUtils.showLongToast(getApplicationContext(), "Please Check Your Email");
                    progressBar.setVisibility(View.GONE);
                    btnDisableEnable();
                } else {
                    progressBar.setVisibility(View.GONE);
                    CommonUtils.showToast(getApplicationContext(), "Some error occured");
                    btnDisableEnable();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                CommonUtils.showToast(getApplicationContext(), "Internet Error");
                progressBar.setVisibility(View.GONE);
                btnDisableEnable();
            }
        });
    }


    @OnClick(R.id.btn_back)
    public void onBtnBackClicked() {
        finish();
    }

    private void btnDisableEnable() {
        if (isEnabled) {
            isEnabled = false;
            btnResetPassword.setEnabled(false);
            btnBack.setEnabled(false);
        } else {
            isEnabled = true;
            btnResetPassword.setEnabled(true);
            btnBack.setEnabled(true);
        }

    }

}

