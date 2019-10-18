package in.themoneytree.ui.expenditure;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
import in.themoneytree.ui.login.LoginActivity;
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
    private DrawerLayout drawer;
    private List<Expense> expenses;
    private boolean isAddScreenActive=false;

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
                if(isAddScreenActive){
                    isAddScreenActive=false;
                    constraintAddExpenditure.setVisibility(View.GONE);
                }else{
                    isAddScreenActive=true;
                    constraintAddExpenditure.setVisibility(View.VISIBLE);
                }
            }
        });

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
                        expenses=response.body().getExpenses();
                        setUpRecycler();
                    }else{
                        CommonUtils.showLongToast(ExpenditureActivity.this,"Some Error Occurred");
                    }
                    }
            }

            @Override
            public void onFailure(Call<ExpenseListResponse> call, Throwable t) {
                CommonUtils.showToast(ExpenditureActivity.this, "Check Internet");
            }
        });

    }
}
