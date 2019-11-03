package in.themoneytree.ui.riskprofile.risk;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.ApiConstants;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.ui.common.CommonFunctions;
import in.themoneytree.ui.home.HomeActivity;
import in.themoneytree.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By  Archit
 * on 10/20/2019
 * for TheMoneyTree
 */
public class RiskAnalysisActivity extends AppCompatActivity {

    @BindView(R.id.radioGroup_q1_profileRisk)
    RadioGroup radioGroupQ1ProfileRisk;
    @BindView(R.id.radioGroup_q2_profileRisk)
    RadioGroup radioGroupQ2ProfileRisk;
    @BindView(R.id.radioGroup_q3_profileRisk)
    RadioGroup radioGroupQ3ProfileRisk;
    @BindView(R.id.radioGroup_q4_profileRisk)
    RadioGroup radioGroupQ4ProfileRisk;
    @BindView(R.id.radioGroup_q5_profileRisk)
    RadioGroup radioGroupQ5ProfileRisk;
    @BindView(R.id.txt_result_riskHorizon)
    TextView txtResultRiskHorizon;
    @BindView(R.id.btn_proceed_riskHorizon)
    Button btnProceedRiskHorizon;
    int finalValue = 0;
    int timeValue = 0;
    int q1Value = -1;
    int q2Value = -1;
    int q3Value = -1;
    int q4Value = -1;
    int q5Value = -1;
    private final String TAG = "RISK_ANALYSIS_ACTIVITY";
    double generalScore = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_risk);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        timeValue = intent.getIntExtra("timeScore", -1);
        generalScore = intent.getDoubleExtra("generalScore", 0.0);
        setUpRadioButtonQ1();
        setUpRadioButtonQ2();
        setUpRadioButtonQ3();
        setUpRadioButtonQ4();
        setUpRadioButtonQ5();
        btnProceedRiskHorizon.setEnabled(false);
        btnProceedRiskHorizon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendScoresToServer();
            }
        });
    }

    private void sendScoresToServer() {
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> sendScoresRequest = moneyService.sendScores(userId, finalValue, timeValue, generalScore);
        sendScoresRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == ApiConstants.CREATED) {
                        CommonFunctions.refreshUserDetails(getApplicationContext());
                        openHomeScreen();
                    }
                } else {
                    CommonUtils.showLongToast(getApplicationContext(), "Unable To Send Scores");
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                CommonUtils.showLongToast(getApplicationContext(), "MESSAGE" + "Unable To Send Scores Check Internet Connection");
            }
        });
    }

    private void openHomeScreen() {
        Intent intent = new Intent(RiskAnalysisActivity.this, HomeActivity.class);
        intent.putExtra("source", TAG);
        intent.putExtra("timeScore", timeValue);
        intent.putExtra("riskScore", finalValue);
        intent.putExtra("generalScore", generalScore);
        startActivity(intent);
        finish();
    }

    private void setUpRadioButtonQ1() {
        radioGroupQ1ProfileRisk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1_q1_profileRisk:
                        q1Value = 1;
                        setUpValue();
                        break;
                    case R.id.radioBtn2_q1_profileRisk:
                        q1Value = 3;
                        setUpValue();
                        break;
                    case R.id.radioBtn3_q1_profileRisk:
                        q1Value = 7;
                        setUpValue();
                        break;
                    case R.id.radioBtn4_q1_profileRisk:
                        q1Value = 10;
                        setUpValue();
                        break;
                }
            }
        });
    }

    private void setUpRadioButtonQ2() {
        radioGroupQ2ProfileRisk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1_q2_profileRisk:
                        q2Value = 0;
                        setUpValue();
                        break;
                    case R.id.radioBtn2_q2_profileRisk:
                        q2Value = 4;
                        setUpValue();
                        break;
                    case R.id.radioBtn3_q2_profileRisk:
                        q2Value = 8;
                        setUpValue();
                        break;
                }
            }
        });
    }

    private void setUpRadioButtonQ3() {
        radioGroupQ3ProfileRisk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1_q3_profileRisk:
                        q3Value = 3;
                        setUpValue();
                        break;
                    case R.id.radioBtn2_q3_profileRisk:
                        q3Value = 6;
                        setUpValue();
                        break;
                    case R.id.radioBtn3_q3_profileRisk:
                        q3Value = 8;
                        setUpValue();
                        break;
                }
            }
        });
    }

    private void setUpRadioButtonQ4() {
        radioGroupQ4ProfileRisk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1_q4_profileRisk:
                        q4Value = 0;
                        setUpValue();
                        break;
                    case R.id.radioBtn2_q4_profileRisk:
                        q4Value = 2;
                        setUpValue();
                        break;
                    case R.id.radioBtn3_q4_profileRisk:
                        q4Value = 5;
                        setUpValue();
                        break;
                    case R.id.radioBtn4_q4_profileRisk:
                        q4Value = 8;
                        setUpValue();
                        break;
                }
            }
        });
    }

    private void setUpRadioButtonQ5() {
        radioGroupQ5ProfileRisk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1_q5_profileRisk:
                        q5Value = 0;
                        setUpValue();
                        break;
                    case R.id.radioBtn2_q5_profileRisk:
                        q5Value = 3;
                        setUpValue();
                        break;
                    case R.id.radioBtn3_q5_profileRisk:
                        q5Value = 6;
                        setUpValue();
                        break;
                    case R.id.radioBtn4_q5_profileRisk:
                        q5Value = 8;
                        setUpValue();
                        break;
                    case R.id.radioBtn5_q5_profileRisk:
                        q5Value = 10;
                        setUpValue();
                        break;
                }
            }
        });
    }

    private void setUpValue() {
        if (q1Value != -1 && q2Value != -1 && q3Value != -1 && q4Value != -1 && q5Value != -1) {
            btnProceedRiskHorizon.setEnabled(true);
            finalValue = q1Value + q2Value + q3Value + q4Value + q5Value;
            txtResultRiskHorizon.setText("Risk Horizon Score : " + finalValue);
        }
    }

}
