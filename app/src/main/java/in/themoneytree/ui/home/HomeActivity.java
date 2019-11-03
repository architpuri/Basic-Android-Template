package in.themoneytree.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.ApiConstants;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.portfolio.Portfolio;
import in.themoneytree.data.model.portfolio.PortfolioResponse;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.recycler_stockList_homeActivity)
    RecyclerView recyclerStockList;
    @BindView(R.id.fab_drawerOpen_homeActivity)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.txt_riskGeneral_homeActivity)
    TextView txtRiskGeneral;
    @BindView(R.id.txt_riskTime_homeActivity)
    TextView txtRiskTime;
    @BindView(R.id.txt_riskScore_homeActivity)
    TextView txtRiskScore;
    private StockListAdapter stockAdapter;
    private DrawerLayout drawer;
    private static final String TAG = UiConstants.TAG_HOME;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        if (source.matches("LOGIN ACTIVITY") || source.matches("SPLASH_ACTIVITY")) {
            getPortfolio();
        } else if(source.matches("RISK_ANALYSIS_ACTIVITY")){
            double generalScore = intent.getDoubleExtra("generalScore",0.0);
            int timeScore = intent.getIntExtra("timeScore",0);
            int riskScore = intent.getIntExtra("riskScore",0);
            setUpScreen(generalScore,timeScore,riskScore);
        }else{
            getPortfolio();
        }
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        setRecyclerView();
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_HOME;
    }

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    void setRecyclerView() {
        recyclerStockList.setVisibility(View.VISIBLE);
        stockAdapter = new StockListAdapter(getApplicationContext(), getStockList(), new StockClickListener() {
            @Override
            public void stockDetails(View v, int position) {
                CommonUtils.showToast(getApplicationContext(), "Stock Details");
            }
        });
        stockAdapter.notifyDataSetChanged();
        Log.d(TAG, "IDhr bhi aa lia 2");
        if (stockAdapter == null) {
            Log.d(TAG, "Null aa raa");
        }
        DividerItemDecoration itemDecorator = new DividerItemDecoration(HomeActivity.this, DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(HomeActivity.this, R.drawable.recyler_divider));
        recyclerStockList.addItemDecoration(itemDecorator);
        recyclerStockList.addItemDecoration(new DividerItemDecoration(HomeActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerStockList.setAdapter(stockAdapter);
    }

    private List<String> getStockList() {
        List<String> stocks = new ArrayList<>();
        stocks.add("Hindustan Unilever Limited");
        stocks.add("Tata Steel");
        stocks.add("Infosys");
        stocks.add("Ambuja Cement");
        return stocks;
    }

    private void getPortfolio(){
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<PortfolioResponse> portfolioRequest= moneyService.getPortfolio(userId);
        portfolioRequest.enqueue(new Callback<PortfolioResponse>() {
            @Override
            public void onResponse(Call<PortfolioResponse> call, Response<PortfolioResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getGeneralResponse().getStatusCode()== ApiConstants.SUCCESS){
                        Portfolio p = response.body().getportfolio();
                        setUpScreen(p.getRetirementCorpusAmount(),p.getTimeScore(),p.getRiskScore());
                    }else{
                        CommonUtils.showLongToast(getApplicationContext(),response.body().getGeneralResponse().getMessage());
                    }
                }else{
                    CommonUtils.showLongToast(getApplicationContext(),response.message());
                }
            }

            @Override
            public void onFailure(Call<PortfolioResponse> call, Throwable t) {
                CommonUtils.onFailureMessage(getApplicationContext(),"Unable to Fetch Portfolio");
            }
        });
    }

    private void setUpScreen(double generalScore,Integer timeScore,Integer riskScore){
        txtRiskGeneral.setText("General Score is "+generalScore);
        txtRiskTime.setText("Time Score is "+timeScore);
        txtRiskScore.setText("Risk Score is "+riskScore);
    }
}
