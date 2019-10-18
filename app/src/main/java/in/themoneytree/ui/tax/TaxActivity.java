package in.themoneytree.ui.tax;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.data.general.ConstantData;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;

public class TaxActivity extends BaseActivity {
    @BindView(R.id.txt_title_tax)
    TextView txtTitle;
    @BindView(R.id.txt_financialYear_tax)
    TextView txtFinancialYear;
    @BindView(R.id.radio1_financialYear_tax)
    RadioButton radio1FinancialYear;
    @BindView(R.id.radio2_financialYear_tax)
    RadioButton radio2FinancialYear;
    @BindView(R.id.radioGroup_financialYear_tax)
    RadioGroup radioGroupFinancialYear;
    @BindView(R.id.txt_age_tax)
    TextView txtAge;
    @BindView(R.id.radio1_age_tax)
    RadioButton radio1Age;
    @BindView(R.id.radio2_age_tax)
    RadioButton radio2Age;
    @BindView(R.id.radioGroupH_age_tax)
    RadioGroup radioGroupHAge;
    @BindView(R.id.radio3_age_tax)
    RadioButton radio3Age;
    @BindView(R.id.radioGroup_age_tax)
    RadioGroup radioGroupAge;
    @BindView(R.id.edt_taxableSalary_tax)
    TextInputEditText edtTaxableSalary;
    @BindView(R.id.text_taxableSalary_tax)
    TextInputLayout textTaxableSalary;
    @BindView(R.id.edt_interestIncome_tax)
    TextInputEditText edtInterestIncome;
    @BindView(R.id.text_interestIncome_tax)
    TextInputLayout textInterestIncome;
    @BindView(R.id.edt_interestHome_tax)
    TextInputEditText edtInterestHome;
    @BindView(R.id.text_interestHome_tax)
    TextInputLayout textInterestHome;
    @BindView(R.id.edt_interestLoan_tax)
    TextInputEditText edtInterestLoan;
    @BindView(R.id.text_interestLoan_tax)
    TextInputLayout textInterestLoan;
    @BindView(R.id.edt_rentalIncome_tax)
    TextInputEditText edtRentalIncome;
    @BindView(R.id.text_rentalIncome_tax)
    TextInputLayout textRentalIncome;
    @BindView(R.id.fab_drawerOpen_tax)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.constraint_taxCalculate_tax)
    ConstraintLayout constraintTaxCalculate;
    @BindView(R.id.btn_calculateTax_tax)
    Button btnCalculateTax;
    @BindView(R.id.txt_taxSaved_tax)
    TextView txtTaxSaved;
    @BindView(R.id.txt_taxSavedAmount_tax)
    TextView txtTaxSavedAmount;
    @BindView(R.id.txt_recommendationTypes_tax)
    TextView txtRecommendationTypes;
    @BindView(R.id.linear_recommendations_tax)
    LinearLayout linearRecommendations;
    @BindView(R.id.constraint_taxSave_tax)
    ConstraintLayout constraintTaxSave;
    @BindView(R.id.btn_saveTax_tax)
    Button btnSaveTax;
    @BindView(R.id.btn_taxSlabs_tax)
    Button btnTaxSlabs;
    @BindView(R.id.constraint_taxSlabs_tax)
    ConstraintLayout constraintTaxSlabs;
    @BindView(R.id.linear_taxSlabs_tax)
    LinearLayout linearTaxSlabs;
    private DrawerLayout drawer;
    private static final String TAG = "TAX ACTIVITY";
    private boolean isTaxCalculationActive = false;//To show hide layout
    private boolean isTaxSlabsActive = false;
    private boolean isTaxSuggestionsActive = false;//To show hide btns
    private Context context = TaxActivity.this;

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_tax;
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_TAX;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        btnCalculateTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutShowHide("CALCULATE TAX");
            }
        });
        btnTaxSlabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutShowHide("TAX SLABS");
            }
        });
        btnSaveTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutShowHide("SAVE TAX");
            }
        });
    }

    private void layoutShowHide(String type) {
        switch (type) {
            case "CALCULATE TAX": {
                if (isTaxCalculationActive) {
                    //check whether to close the layout or show it
                    isTaxCalculationActive = false;
                    constraintTaxCalculate.setVisibility(View.GONE);
                    btnCalculateTax.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black), null);
                } else {
                    isTaxCalculationActive = true;
                    constraintTaxCalculate.setVisibility(View.VISIBLE);
                    btnCalculateTax.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                }
                break;
            }
            case "TAX SLABS": {
                if (isTaxSlabsActive) {
                    //check whether to close the layout or show it
                    isTaxSlabsActive = false;
                    constraintTaxSlabs.setVisibility(View.GONE);
                    constraintTaxSlabs.removeAllViews();
                    btnTaxSlabs.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                } else {
                    isTaxSlabsActive = true;
                    constraintTaxSlabs.setVisibility(View.VISIBLE);
                    setUpTaxSlabsLayout();
                    btnTaxSlabs.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black), null);
                }
                break;
            }
            case "SAVE TAX": {
                if (isTaxSuggestionsActive) {
                    //check whether to close the layout or show it
                    isTaxSuggestionsActive = false;
                    constraintTaxSave.setVisibility(View.GONE);
                    linearRecommendations.removeAllViews();
                    btnSaveTax.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                } else {
                    isTaxSuggestionsActive = true;
                    constraintTaxSave.setVisibility(View.VISIBLE);
                    setUpTaxSaveLayout();
                    btnSaveTax.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black), null);
                }
                break;
            }
            default: {
                Log.d(TAG, "Layout Show Hide Default Case");
            }
        }
    }

    private void setUpTaxSaveLayout() {
        //To Add the checkboxes
        ArrayList<String> instruments = taxSavingInstruments();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 25, 10, 0);
        for (int i = 0; i < instruments.size(); i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(instruments.get(i));
            //cb.setTextSize((int) getResources().getDimension(R.dimen.textSize_25));
            cb.setTextColor(Color.BLACK);
            cb.setLayoutParams(lp);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onSelection(buttonView, isChecked);
                }
            });
            linearRecommendations.addView(cb);
        }

    }

    private void onSelection(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, buttonView.getText() + "TEXT" + isChecked);
    }

    private void setUpTaxSlabsLayout() {
        LinkedHashMap<String, Float> taxSlabs = ConstantData.getTaxSlabs();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20, 25, 10, 0);
        for (Map.Entry m : taxSlabs.entrySet()) {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(m.getKey() + " \t " + m.getValue());
            tv.setTextColor(Color.BLACK);
            tv.setLayoutParams(lp);
            linearTaxSlabs.addView(tv);
        }
    }

    private ArrayList<String> taxSavingInstruments() {
        ArrayList<String> instruments = new ArrayList<>();
        instruments.add("Life Insurance");
        instruments.add("Health Insurance");
        instruments.add("ULIPs");
        instruments.add("New Pension Scheme (NPS)");
        instruments.add("Equity-linked Tax Saving Scheme (ELSS)");
        instruments.add("Public Provident Fund (PPF)");
        instruments.add("National Saving Certificates (NSC)");
        return instruments;
    }
}
