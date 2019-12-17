package in.themoneytree.ui.stocks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import in.themoneytree.data.model.stock.StockListResponse;
import in.themoneytree.data.model.stock.Stocks;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StocksActivity extends BaseActivity {
    @BindView(R.id.recycler_stockList_stocksActivity)
    RecyclerView recyclerStockList;
    @BindView(R.id.fab_drawerOpen_stocksActivity)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.txt_valueTimeScore_stocks)
    TextView txtValueTime;
    @BindView(R.id.txt_valueRiskScore_stocks)
    TextView txtValueRisk;
    @BindView(R.id.txt_investorType_stocks)
    TextView txtInvestorType;
    @BindView(R.id.progressBar_stocksBackground_stocksActivity)
    ProgressBar progressBar;
    private List<Stocks> recommendedStocks = new ArrayList<>();
    private Integer timeScore = 0;
    private Integer riskScore = 0;
    private StockListAdapter stockAdapter;
    private DrawerLayout drawer;
    private static final String TAG = "STOCKS ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        getScreenData();
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_STOCKS;
    }

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_stocks;
    }

    void setUpScreen() {
        setRecyclerView();
        txtValueRisk.setText("" + riskScore);
        txtValueTime.setText("" + timeScore);
    }

    void getScreenData() {
        fetchScores();
    }

    void setRecyclerView() {
        recyclerStockList.setVisibility(View.VISIBLE);
        stockAdapter = new StockListAdapter(getApplicationContext(), recommendedStocks, new StockClickListener() {
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
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerStockList.addItemDecoration(itemDecorator);
        recyclerStockList.addItemDecoration(new DividerItemDecoration(StocksActivity.this, DividerItemDecoration.HORIZONTAL));
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

    private boolean fetchRecommendedStocks() {
        progressBar.setVisibility(View.VISIBLE);
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<StockListResponse> stocksRequest = moneyService.getRecommendedStocks(userId, timeScore, riskScore);
        stocksRequest.enqueue(new Callback<StockListResponse>() {
            @Override
            public void onResponse(Call<StockListResponse> call, Response<StockListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getGeneralResponse().getStatusCode() == ApiConstants.SUCCESS) {
                        txtInvestorType.setText(response.body().getGeneralResponse().getMessage());
                        List<Stocks> stockList = response.body().getStocks();
                        if (stockList != null && stockList.size() > 0) {
                            recommendedStocks = stockList;
                            setUpScreen();
                        } else {
                            if (stockList == null) {
                                Log.d(TAG, "Null it is");
                            }
                            Log.d(TAG, "Stock List");
                        }
                    } else {
                        Log.d(TAG, "General Response");
                    }
                } else {
                    Log.d(TAG, "Not Success");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<StockListResponse> call, Throwable t) {
                CommonUtils.showToast(StocksActivity.this, "Failure Check Internet");
                progressBar.setVisibility(View.GONE);
            }
        });
        return true;
    }

    private boolean fetchScores() {
        Integer portfolioId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getPortfolioId());
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<PortfolioResponse> portfolioRequest = moneyService.getPortfolio(userId);
        portfolioRequest.enqueue(new Callback<PortfolioResponse>() {
            @Override
            public void onResponse(Call<PortfolioResponse> call, Response<PortfolioResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getGeneralResponse().getStatusCode() == ApiConstants.SUCCESS) {
                        Portfolio portfolio = response.body().getportfolio();
                        riskScore = portfolio.getRiskScore();
                        timeScore = portfolio.getTimeScore();
                        fetchRecommendedStocks();
                    }
                }
            }

            @Override
            public void onFailure(Call<PortfolioResponse> call, Throwable t) {
                CommonUtils.onFailureMessage(getApplicationContext(), "Error in Fetching Stocks");
            }
        });
        return true;
    }
}
