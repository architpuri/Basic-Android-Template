package in.themoneytree.ui.riskprofile.time;

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
import in.themoneytree.ui.riskprofile.risk.RiskAnalysisActivity;

/**
 * Created By  Archit
 * on 10/20/2019
 * for TheMoneyTree
 */
public class TimeAnalysisActivity extends AppCompatActivity {
    @BindView(R.id.radioGroup_q1_profileTime)
    RadioGroup radioGroupQ1ProfileTime;
    @BindView(R.id.radioGroup_q2_profileTime)
    RadioGroup radioGroupQ2ProfileTime;
    @BindView(R.id.txt_result_timeHorizon)
    TextView txtResultTimeHorizon;
    @BindView(R.id.btn_proceed_timeHorizon)
    Button btnProceedTimeHorizon;
    int finalValue = 0;
    double generalScore = 0.0;
    int q1Value = -1;
    int q2Value = -1;
    private final String TAG = "TIME_ANALYSIS_ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_time);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String source = intent.getStringExtra("source");
        generalScore = intent.getDoubleExtra("generalScore",0.0);
        setUpRadioButtonQ1();
        setUpRadioButtonQ2();
        btnProceedTimeHorizon.setEnabled(false);
        btnProceedTimeHorizon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeAnalysisActivity.this, RiskAnalysisActivity.class);
                intent.putExtra("source", TAG);
                intent.putExtra("timeScore", finalValue);
                intent.putExtra("generalScore",generalScore);
                startActivity(intent);
            }
        });
    }

    private void setUpRadioButtonQ1() {

        radioGroupQ1ProfileTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1_q1_profileTime:
                        q1Value = 1;
                        setUpValue();
                        break;
                    case R.id.radioBtn2_q1_profileTime:
                        q1Value = 3;
                        setUpValue();
                        break;
                    case R.id.radioBtn3_q1_profileTime:
                        q1Value = 7;
                        setUpValue();
                        break;
                    case R.id.radioBtn4_q1_profileTime:
                        q1Value = 10;
                        setUpValue();
                        break;
                }
            }
        });
    }

    private void setUpRadioButtonQ2() {

        radioGroupQ2ProfileTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtn1_q2_profileTime:
                        q2Value = 0;
                        setUpValue();
                        break;
                    case R.id.radioBtn2_q2_profileTime:
                        q2Value = 1;
                        setUpValue();
                        break;
                    case R.id.radioBtn3_q2_profileTime:
                        q2Value = 4;
                        setUpValue();
                        break;
                    case R.id.radioBtn4_q2_profileTime:
                        q2Value = 8;
                        setUpValue();
                        break;
                }
            }
        });
    }

    private void setUpValue() {
        if (q2Value != -1 && q1Value != -1) {
            btnProceedTimeHorizon.setEnabled(true);
            finalValue = q1Value + q2Value;
            txtResultTimeHorizon.setText("Time Horizon Score : " + finalValue);
        }
    }

}
