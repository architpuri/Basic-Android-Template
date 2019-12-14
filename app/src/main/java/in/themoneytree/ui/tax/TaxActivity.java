package in.themoneytree.ui.tax;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.data.general.ConstantData;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.CommonUtils;

public class TaxActivity extends BaseActivity {
    @BindView(R.id.fab_drawerOpen_tax)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.constraint_taxCalculate_tax)
    ConstraintLayout constraintTaxCalculate;
    @BindView(R.id.btn_calculateTax_tax)
    Button btnCalculateTax;
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
    @BindView(R.id.txt_calculateTax_tax)
    Button txtCalculateTax;
    @BindView(R.id.txt_valueTaxAmount_tax)
    TextView txtValueTax;
    private RadioGroup rgAge;
    private RadioGroup rgYear;
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
        setTaxCalculateLayout();
        setUpTaxSlabsLayout();
        setUpUserInterface();
        constraintTaxSlabs.setVisibility(View.GONE);
        constraintTaxCalculate.setVisibility(View.GONE);
        constraintTaxSave.setVisibility(View.GONE);
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        txtCalculateTax.setOnClickListener(new View.OnClickListener() {
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
        btnCalculateTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double taxValue = calculateTax();
                if (taxValue != -1) {
                    layoutShowHide("CALCULATE TAX");
                    txtValueTax.setText(taxValue + "");
                }
            }
        });
    }

    private void setUpUserInterface() {
        Double taxAmount = 0.0;
        try {
            taxAmount = Double.parseDouble(PrefManager.getInstance(getApplicationContext()).getTaxAmount());
        }catch (Exception e){
            e.printStackTrace();
        }
        txtValueTax.setText(""+taxAmount);
    }

    private void layoutShowHide(String type) {
        switch (type) {
            case "CALCULATE TAX": {
                if (isTaxCalculationActive) {
                    isTaxCalculationActive = false;
                    constraintTaxCalculate.setVisibility(View.GONE);
                    txtCalculateTax.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_down_black), null);
                } else {
                    isTaxCalculationActive = true;
                    constraintTaxCalculate.setVisibility(View.VISIBLE);
                    txtCalculateTax.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                }
                break;
            }
            case "TAX SLABS": {
                if (isTaxSlabsActive) {
                    //check whether to close the layout or show it
                    isTaxSlabsActive = false;
                    constraintTaxSlabs.setVisibility(View.GONE);
                    //constraintTaxSlabs.removeAllViews();
                    btnTaxSlabs.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                } else {
                    isTaxSlabsActive = true;
                    constraintTaxSlabs.setVisibility(View.VISIBLE);
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

    private void setTaxCalculateLayout() {
        setQuestionOne();
        setQuestionTwo();
    }

    private Double calculateTax() {
        int valueOne = getQuestionOne();
        int valueTwo = getQuestionTwo();
        Double taxableIncome = 0.0;
        TextInputEditText editText = findViewById(R.id.edt_taxableSalary_tax);
        TextInputLayout layout = findViewById(R.id.text_taxableSalary_tax);
        if (!CommonUtils.isNumber(layout, editText)) {
            return -1.0;
        } else {
            taxableIncome = taxableIncome + Double.parseDouble(editText.getText().toString());
        }
        TextInputEditText editText1 = findViewById(R.id.edt_interestIncome_tax);
        TextInputLayout layout1 = findViewById(R.id.text_interestIncome_tax);
        if (!CommonUtils.isNumber(layout1, editText1)) {
            return -1.0;
        } else {
            taxableIncome = taxableIncome + Double.parseDouble(editText1.getText().toString());
        }
        TextInputEditText editText2 = findViewById(R.id.edt_interestHome_tax);
        TextInputLayout layout2 = findViewById(R.id.text_interestHome_tax);
        if (!CommonUtils.isNumber(layout2, editText2)) {
            return -1.0;
        } else {
            taxableIncome = taxableIncome + Double.parseDouble(editText2.getText().toString());
        }
        TextInputEditText editText3 = findViewById(R.id.edt_interestLoan_tax);
        TextInputLayout layout3 = findViewById(R.id.text_interestLoan_tax);
        if (!CommonUtils.isNumber(layout3, editText3)) {
            return -1.0;
        } else {
            taxableIncome = taxableIncome + Double.parseDouble(editText3.getText().toString());
        }
        TextInputEditText editText4 = findViewById(R.id.edt_rentalIncome_tax);
        TextInputLayout layout4 = findViewById(R.id.text_rentalIncome_tax);
        if (!CommonUtils.isNumber(layout4, editText4)) {
            return -1.0;
        } else {
            taxableIncome = taxableIncome + Double.parseDouble(editText4.getText().toString());
        }
        if (taxableIncome < 500000) {
            return 0.0;
        }
        return finalTaxValue(taxableIncome);
    }

    private Double finalTaxValue(Double taxableIncome) {
        LinkedHashMap<Double, Double> taxSlabs = ConstantData.getTaxSlabsDouble();
        Double tax = 0.0;
        taxableIncome = taxableIncome - 500000;
        for (Map.Entry m : taxSlabs.entrySet()) {
            Double decrease = (Double) m.getKey();
            if (taxableIncome > decrease) {
                tax = tax + 500000 * ((Double) m.getValue() / 100);
                taxableIncome = taxableIncome - decrease;
            } else if (taxableIncome > 0) {
                tax = tax + taxableIncome * ((Double) m.getValue() / 100);
                taxableIncome = 0.0;
                break;
            } else {
                break;
            }
        }
        PrefManager.getInstance(getApplicationContext()).setTaxAmount(tax+"");
        return tax;
    }

    private void setQuestionOne() {
        rgYear = findViewById(R.id.radioGroup_financialYear_tax);
    }

    private void setQuestionTwo() {
        rgAge = findViewById(R.id.radioGroup_age_tax);
    }

    private int getQuestionOne() {
        int selectedId = rgYear.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radio1_financialYear_tax:
                return 1;
            case R.id.radio2_financialYear_tax:
                return 2;
            default:
                return -1;
        }
    }

    private int getQuestionTwo() {
        int selectedId = rgAge.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radio1_age_tax:
                return 1;
            case R.id.radio2_age_tax:
                return 2;
            case R.id.radio3_age_tax:
                return 3;
            default:
                return -1;
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
