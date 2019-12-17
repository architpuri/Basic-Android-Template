package in.themoneytree.ui.expenditure;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.ApiConstants;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.data.model.expense.Expense;
import in.themoneytree.data.model.expense.ExpenseListResponse;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenditureActivity extends BaseActivity {
    @BindView(R.id.txt_title_expenditure)
    TextView txtTitle;
    @BindView(R.id.txt_monthlySpent_expenditure)
    TextView txtMonthlySpent;
    @BindView(R.id.btn_addExpense_expenditure)
    Button btnAddExpense;
    @BindView(R.id.edt_name_expenditure)
    TextInputEditText edtName;
    @BindView(R.id.text_name_expenditure)
    TextInputLayout textName;
    @BindView(R.id.edt_amount_expenditure)
    TextInputEditText edtAmount;
    @BindView(R.id.text_amount_expenditure)
    TextInputLayout textAmount;
    @BindView(R.id.spinner_expenseType_expenditure)
    Spinner spinnerExpenseType;
    @BindView(R.id.fab_drawerOpen_expenditure)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.recycler_expenses_expenditure)
    RecyclerView recyclerExpensesExpenditure;
    @BindView(R.id.constraint_add_expenditure)
    ConstraintLayout constraintAddExpenditure;
    @BindView(R.id.txt_monthlyAmount_expenditure)
    TextView txtMonthlyAmount;
    @BindView(R.id.txt_background_expenditure)
    TextView txtBackground;
    @BindView(R.id.btn_submitExpense_expenditure)
    Button btnSubmitExpense;
    private DrawerLayout drawer;
    private List<Expense> expenses;
    private boolean isAddScreenActive = false;
    private Integer expenseType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        recyclerExpensesExpenditure.setVisibility(View.GONE);
        constraintAddExpenditure.setVisibility(View.GONE);
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        getExpenseList();
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddScreenActive) {
                    isAddScreenActive = false;
                    constraintAddExpenditure.setVisibility(View.GONE);
                } else {
                    isAddScreenActive = true;
                    constraintAddExpenditure.setVisibility(View.VISIBLE);
                }
            }
        });
        btnSubmitExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wantToAddExpense()) {
                    addNewExpense();
                }
            }
        });
        setExpenseSpinner();
    }

    private void setExpenseSpinner() {
        ArrayAdapter aa = new ArrayAdapter(ExpenditureActivity.this, android.R.layout.simple_spinner_item,getExpenseTypes());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExpenseType.setAdapter(aa);
        spinnerExpenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expenseType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private boolean wantToAddExpense() {
        String name = edtName.getText().toString().trim();
        String amount = edtAmount.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            if (TextUtils.isEmpty(amount)) {
                return false;
            } else {
                CommonUtils
                        .wrongInputErrorMsg(textName, "Please enter name", edtName);
            }
        } else {
            if (TextUtils.isEmpty(amount)) {
                CommonUtils
                        .wrongInputErrorMsg(textAmount, "Please enter amount", edtAmount);
            } else {
                try {
                    double a = Double.parseDouble(amount);
                } catch (Exception e) {
                    CommonUtils
                            .wrongInputErrorMsg(textAmount, "Characters allowed are 0-9,single(.),-", edtAmount);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_expenditure;
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_EXPENDITURE;
    }

    void setUpRecycler() {
        recyclerExpensesExpenditure.setVisibility(View.VISIBLE);
        ExpenditureListAdapter expenditureAdapter = new ExpenditureListAdapter(getApplicationContext(), expenses);
        expenditureAdapter.notifyDataSetChanged();
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerExpensesExpenditure.addItemDecoration(itemDecorator);
        recyclerExpensesExpenditure.addItemDecoration(new DividerItemDecoration(ExpenditureActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerExpensesExpenditure.setAdapter(expenditureAdapter);
    }

    private void getExpenseList() {
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<ExpenseListResponse> expensesRequest = moneyService.getExpenditure(userId);
        expensesRequest.enqueue(new Callback<ExpenseListResponse>() {
            @Override
            public void onResponse(Call<ExpenseListResponse> call, Response<ExpenseListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getGeneralResponse().getStatusCode() == ApiConstants.SUCCESS) {
                        expenses = response.body().getExpenses();
                        if (expenses.size() == 0) {
                            txtBackground.setVisibility(View.VISIBLE);
                        } else {
                            setUpRecycler();
                            setMonthlyAmount();
                            txtBackground.setVisibility(View.GONE);
                        }
                    } else {
                        CommonUtils.showLongToast(ExpenditureActivity.this, "Some Error Occurred");
                    }
                }
            }

            @Override
            public void onFailure(Call<ExpenseListResponse> call, Throwable t) {
                CommonUtils.onFailureMessage(getApplicationContext(), "Expenditure List Show Fail");
            }
        });

    }

    private void addNewExpense() {
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> expenseAddRequest = moneyService.addNewExpense(userId, edtName.getText().toString(), Double.parseDouble(edtAmount.getText().toString()), -1, -1);
        expenseAddRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == ApiConstants.SUCCESS) {
                            setExpenseSpinner();
                    } else {
                        CommonUtils.showToast(getApplicationContext(), response.body().getMessage());
                    }
                } else {
                    CommonUtils.showToast(getApplicationContext(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                CommonUtils.onFailureMessage(getApplicationContext(), "Expenditure Add Fail");
            }
        });
    }

    private void setMonthlyAmount() {
        double amount = 0;
        for (Expense e : expenses) {
            amount = amount + e.getExpenseAmount();
        }
        txtMonthlyAmount.setText(amount + "");
    }

    private List<String> getExpenseTypes(){
        List<String> expenseTypes = new ArrayList<>();
        expenseTypes.add("Petrol");
        expenseTypes.add("Bill");
        expenseTypes.add("Food");
        expenseTypes.add("Others");
        return expenseTypes;
    }
}
