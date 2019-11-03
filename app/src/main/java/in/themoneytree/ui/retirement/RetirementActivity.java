package in.themoneytree.ui.retirement;

import android.content.Context;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.data.general.ConstantData;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.CommonUtils;

/**
 * Created By  Archit
 * on 7/7/2019
 * for TheMoneyTree
 */
public class RetirementActivity extends BaseActivity {
    @BindView(R.id.fab_drawerOpen_retirement)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.btn_calculateCorpus_retirement)
    Button btnCalculateCorpusRetirement;
    @BindView(R.id.txt_q1_corpus_retirement)
    TextView txtQ1;
    @BindView(R.id.edt_q1_corpus_retirement)
    TextInputEditText edtQ1;
    @BindView(R.id.text_q1_corpus_retirement)
    TextInputLayout textQ1;
    @BindView(R.id.edt_q12_corpus_retirement)
    TextInputEditText edt12;
    @BindView(R.id.text_q12_corpus_retirement)
    TextInputLayout text12;
    @BindView(R.id.txt_q2_corpus_retirement)
    TextView txtQ2;
    @BindView(R.id.edt_q2_corpus_retirement)
    TextInputEditText edtQ2;
    @BindView(R.id.text_q2_corpus_retirement)
    TextInputLayout textQ2;
    @BindView(R.id.edt_q22_corpus_retirement)
    TextInputEditText edt22;
    @BindView(R.id.text_q22_corpus_retirement)
    TextInputLayout text22;
    @BindView(R.id.edt_q3_corpus_retirement)
    TextInputEditText edtQ3;
    @BindView(R.id.text_q3_corpus_retirement)
    TextInputLayout textQ3;
    @BindView(R.id.txt_q4a_corpus_retirement)
    TextView txtQ4a;
    @BindView(R.id.radioBtn1_q4a_retirement)
    RadioButton radioBtn1Q4a;
    @BindView(R.id.radioBtn2_q4a_retirement)
    RadioButton radioBtn2Q4a;
    @BindView(R.id.radioBtn3_q4a_retirement)
    RadioButton radioBtn3Q4a;
    @BindView(R.id.radioGroup_q4a_corpus_retirement)
    RadioGroup radioGroupQ4a;
    @BindView(R.id.txt_q5_corpus_retirement)
    TextView txtQ5;
    /*@BindView(R.id.radioBtn1_q5_retirement)
    RadioButton radioBtn1Q5;
    @BindView(R.id.radioBtn2_q5_retirement)
    RadioButton radioBtn2Q5;*/
    @BindView(R.id.radioGroup_q5_corpus_retirement)
    RadioGroup radioGroupQ5;
    @BindView(R.id.txt_q5a_corpus_retirement)
    TextView txtQ5a;
    @BindView(R.id.edt_q5a_corpus_retirement)
    TextInputEditText edtQ5a;
    @BindView(R.id.text_q5a_corpus_retirement)
    TextInputLayout textQ5a;
    @BindView(R.id.edt_q52a_corpus_retirement)
    TextInputEditText edt52a;
    @BindView(R.id.text_q52a_corpus_retirement)
    TextInputLayout text52a;
    @BindView(R.id.txt_q6_corpus_retirement)
    TextView txtQ6;
    /*@BindView(R.id.radioBtn1_q6_retirement)
    RadioButton radioBtn1Q6;
    @BindView(R.id.radioBtn2_q6_retirement)
    RadioButton radioBtn2Q6;*/
    @BindView(R.id.radioGroup_q6_corpus_retirement)
    RadioGroup radioGroupQ6;
    @BindView(R.id.txt_q7_corpus_retirement)
    TextView txtQ7;
    /*@BindView(R.id.radioBtn1_q7_retirement)
    RadioButton radioBtn1Q7;
    @BindView(R.id.radioBtn2_q7_retirement)
    RadioButton radioBtn2Q7;*/
    @BindView(R.id.radioGroup_q7_corpus_retirement)
    RadioGroup radioGroupQ7;
    @BindView(R.id.txt_q7a_corpus_retirement)
    TextView txtQ7a;
    @BindView(R.id.edt_q7a_corpus_retirement)
    TextInputEditText edtQ7a;
    @BindView(R.id.text_q7a_corpus_retirement)
    TextInputLayout textQ7a;
    @BindView(R.id.edt_q72a_corpus_retirement)
    TextInputEditText edt72a;
    @BindView(R.id.text_q72a_corpus_retirement)
    TextInputLayout text72a;
    @BindView(R.id.txt_q8_corpus_retirement)
    TextView txtQ8;
    /* @BindView(R.id.radioBtn1_q8_retirement)
     RadioButton radioBtn1Q8;
     @BindView(R.id.radioBtn2_q8_retirement)
     RadioButton radioBtn2Q8;*/
    @BindView(R.id.radioGroup_q8_corpus_retirement)
    RadioGroup radioGroupQ8;
    @BindView(R.id.txt_q8a_corpus_retirement)
    TextView txtQ8a;
    @BindView(R.id.edt_q8a_corpus_retirement)
    TextInputEditText edtQ8a;
    @BindView(R.id.text_q8a_corpus_retirement)
    TextInputLayout textQ8a;
    @BindView(R.id.edt_q82a_corpus_retirement)
    TextInputEditText edt82a;
    @BindView(R.id.text_q82a_corpus_retirement)
    TextInputLayout text82a;
    @BindView(R.id.edt_q83a_corpus_retirement)
    TextInputEditText edt83a;
    @BindView(R.id.text_q83a_corpus_retirement)
    TextInputLayout text83a;
    @BindView(R.id.edt_q84a_corpus_retirement)
    TextInputEditText edt84a;
    @BindView(R.id.text_q84a_corpus_retirement)
    TextInputLayout text84a;
    @BindView(R.id.constraint_corpusCalculate_retirement)
    ConstraintLayout constraintCorpus;
    @BindView(R.id.txt_corpusAmount_retirement)
    TextView txtCorpusAmount;
    private DrawerLayout drawer;
    private static final String TAG = "TAX ACTIVITY";
    private Context context = RetirementActivity.this;
    private boolean isCalculationActive = false;
    private long amount = -1;

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_retirement;
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_RETIREMENT_CORPUS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if (amount != -1) {
            btnCalculateCorpusRetirement.setText("Recalculate Retirement Corpus Amount");
        }
        btnCalculateCorpusRetirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCalculationActive) {
                    amount = calculateCorpusAmount();
                    if (amount != -1) {
                        txtCorpusAmount.setVisibility(View.VISIBLE);
                        txtCorpusAmount.setText("Corpus Amount is - " + amount);
                    }
                    isCalculationActive = false;
                    constraintCorpus.setVisibility(View.GONE);
                } else {
                    isCalculationActive = true;
                    constraintCorpus.setVisibility(View.VISIBLE);
                }
            }
        });
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private long calculateCorpusAmount() {
        try {
            int yearsToLiveAfterRetirement = 20;
            // Method used from The Times of India , Chandigarh July 6,2019
            // Pg 11 Retirement Planning
            long retirementCorpus = 0;
            int yearsLeft = (Integer.parseInt(edt12.getText().toString()) - Integer.parseInt(edtQ1.getText().toString()));
            long annualExpenses = (Integer.parseInt(edt22.getText().toString()) + Integer.parseInt(edtQ2.getText().toString()) * 12);
            int inflation = Integer.parseInt(edtQ3.getText().toString());
            float changeInSpending = 0.0f;
            HashMap<Integer, Float> hm = ConstantData.getSpendingChange(yearsToLiveAfterRetirement);
            for (int i = 0; i < radioGroupQ4a.getChildCount(); i++) {
                RadioButton child = (RadioButton) radioGroupQ4a.getChildAt(i);
                if (child.isChecked()) {
                    changeInSpending = hm.get(i);
                    break;
                }
            }
            if (changeInSpending == 0.0f) {
                CommonUtils.showToast(context, "Please Select Option for Question 4");
            }
            retirementCorpus = (long) (inflation * annualExpenses * changeInSpending);
            Log.d(TAG, inflation + " " + annualExpenses + " " + changeInSpending);
            //repaying any loans
            for (int i = 0; i < radioGroupQ5.getChildCount(); i++) {
                RadioButton child = (RadioButton) radioGroupQ5.getChildAt(i);
                if (i == 0 && child.isChecked()) {
                    //Yes
                    int years = Integer.parseInt(edtQ5a.getText().toString());
                    int emi = Integer.parseInt(edt52a.getText().toString());
                    retirementCorpus = retirementCorpus + years * emi * 12;
                    Log.d(TAG, years + " " + emi);
                }
            }
            //health Care
            for (int i = 0; i < radioGroupQ6.getChildCount(); i++) {
                RadioButton child = (RadioButton) radioGroupQ6.getChildAt(i);
                if (i == 1 && child.isChecked()) {
                    retirementCorpus = retirementCorpus + 1000000;//Add 10lacs
                }
            }
            for (int i = 0; i < radioGroupQ7.getChildCount(); i++) {
                RadioButton child = (RadioButton) radioGroupQ7.getChildAt(i);
                if (i == 0 && child.isChecked()) {
                    //Yes
                    retirementCorpus = retirementCorpus + Integer.parseInt(edt72a.getText().toString());
                }
            }
            for (int i = 0; i < radioGroupQ8.getChildCount(); i++) {
                RadioButton child = (RadioButton) radioGroupQ8.getChildAt(i);
                if (i == 0 && child.isChecked()) {
                    //Yes
                    retirementCorpus = retirementCorpus - (12 * (Integer.parseInt(edt82a.getText().toString()) + Integer.parseInt(edt83a.getText().toString()) + Integer.parseInt(edt84a.getText().toString())) * yearsToLiveAfterRetirement);
                }
            }
            return retirementCorpus;
        } catch (NullPointerException e) {
            CommonUtils.showToast(context, "Please fill All The Fields Correctly");
            return -1;
        } catch (NumberFormatException f) {
            CommonUtils.showToast(context, "Please fill All The Fields Correctly");
            return -1;
        } catch (Exception e) {
            CommonUtils.exceptionHandling(TAG, e);
            CommonUtils.showToast(context, "Please fill All The Fields Correctly");
            return -1;
        }
    }
}
