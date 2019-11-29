package in.themoneytree.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
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
import in.themoneytree.data.model.stock.Stock;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.ui.home.indices.IndicesAdapter;
import in.themoneytree.ui.home.stocktape.StockScrollAdapter;
import in.themoneytree.ui.home.stocktape.StockScrollListener;
import in.themoneytree.utils.CommonUtils;
import in.themoneytree.utils.chart.HorizontalBarChartActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.fab_drawerOpen_homeActivity)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.recycler_stockTape_homeActivity)
    RecyclerView recyclerStockTape;

    List<Stock> stocks = new ArrayList<>();
    @BindView(R.id.recycler_stocks_layoutStockList)
    RecyclerView recyclerIndices;
    @BindView(R.id.chart_allocation_homeActivity)
    BarChart chartAllocation;
    private StockScrollAdapter stockScrollAdapter;
    int scrollCount = 0;
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
        } else if (source.matches("RISK_ANALYSIS_ACTIVITY")) {
            double generalScore = intent.getDoubleExtra("generalScore", 0.0);
            int timeScore = intent.getIntExtra("timeScore", 0);
            int riskScore = intent.getIntExtra("riskScore", 0);
            //setUpScreen(generalScore, timeScore, riskScore);
        } else {
            getPortfolio();
        }
        stocks = getStockListForTape();
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        setUpScreen();
        setRecyclerView();
        setUpTape();
    }

    private void setUpScreen() {
        setUpIndicesRecycler();
        setUpAllocationChart();
    }

    private ArrayList<Float> getPortfolioAllocation() {
        ArrayList<Float> values = new ArrayList<>();
        values.add(50f);
        values.add(20f);
        values.add(30f);
        values.add(10f);
        values.add(60f);
        values.add(70f);
        values.add(80f);
        values.add(55f);
        return values;
    }

    private List<String> getInstrumentList() {
        List<String> labels = new ArrayList<>();
        labels.add("F.D.");
        labels.add("Mutual Fund");
        labels.add("Cash");
        labels.add("PPF");
        labels.add("Stocks");
        labels.add("RD");
        labels.add("Metals");
        labels.add("Property");
        return labels;
    }

    private void setUpAllocationChart() {
        ArrayList<String> labels = (ArrayList<String>) getInstrumentList();
        ArrayList<Float> values = getPortfolioAllocation();
        chartAllocation = HorizontalBarChartActivity.barChartDesigner(chartAllocation, labels, values);
        if (chartAllocation != null) {
            chartAllocation.invalidate();
        } else {
            Log.d(TAG, "Bar Chart Null");
        }
    }

    private void setUpIndicesRecycler() {
        recyclerIndices.setVisibility(View.VISIBLE);
        IndicesAdapter indicesAdapter = new IndicesAdapter(getApplicationContext(), getStockListForTape());
        indicesAdapter.notifyDataSetChanged();
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerIndices.addItemDecoration(itemDecorator);
        recyclerIndices.addItemDecoration(new DividerItemDecoration(HomeActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerIndices.setAdapter(indicesAdapter);
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
        /*recyclerIndices.setVisibility(View.VISIBLE);
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
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerStockList.addItemDecoration(itemDecorator);
        recyclerStockList.addItemDecoration(new DividerItemDecoration(StocksActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerStockList.setAdapter(stockAdapter);*/
    }

    private List<String> getStockList() {
        List<String> stocks = new ArrayList<>();
        stocks.add("Hindustan Unilever Limited");
        stocks.add("Tata Steel");
        stocks.add("Infosys");
        stocks.add("Ambuja Cement");
        return stocks;
    }

    private void getPortfolio() {
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<PortfolioResponse> portfolioRequest = moneyService.getPortfolio(userId);
        portfolioRequest.enqueue(new Callback<PortfolioResponse>() {
            @Override
            public void onResponse(Call<PortfolioResponse> call, Response<PortfolioResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getGeneralResponse().getStatusCode() == ApiConstants.SUCCESS) {
                        Portfolio p = response.body().getportfolio();
                        // setUpScreen(p.getRetirementCorpusAmount(), p.getTimeScore(), p.getRiskScore());
                    } else {
                        CommonUtils.showLongToast(getApplicationContext(), response.body().getGeneralResponse().getMessage());
                    }
                } else {
                    CommonUtils.showLongToast(getApplicationContext(), response.message());
                }
            }

            @Override
            public void onFailure(Call<PortfolioResponse> call, Throwable t) {
                CommonUtils.onFailureMessage(getApplicationContext(), "Unable to Fetch Portfolio");
            }
        });
    }

    private List<Stock> getStockListForTape() {
        ArrayList<Stock> st = new ArrayList<>();
        st.add(new Stock(1, "Nifty 50", 11500.0, 50.0, 1.2));
        st.add(new Stock(1, "SENSEX 30", 37000.0, -550.0, -2.2));
        st.add(new Stock(1, "Ambuja", 1700.0, 50.0, 5.2));
        st.add(new Stock(1, "Zee TV", 254.0, 50.0, 3.2));
        st.add(new Stock(1, "IRCTC", 910.0, 50.0, 0.2));
        st.add(new Stock(1, "Infy", 647.80, -78.9, -0.89));
        st.add(new Stock(1, "MRF", 31456.5, -298.5, -0.87));
        st.add(new Stock(1, "M&M", 887.30, 102.5, 1.2));
        return st;
    }

    public void autoScrollAnother() {
        scrollCount = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recyclerStockTape.smoothScrollToPosition((scrollCount++));
                if (scrollCount == stockScrollAdapter.getItemCount() - 4) {
                    stocks.addAll(stocks);
                    stockScrollAdapter.notifyDataSetChanged();
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.postDelayed(runnable, 500);
        /*if(scrollCount<10) {
            autoScrollAnother();
        }*/
    }

    private void setUpTape() {
        stockScrollAdapter = new StockScrollAdapter(HomeActivity.this, stocks, new StockScrollListener() {
        });
        recyclerStockTape.setAdapter(stockScrollAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(HomeActivity.this) {
                    private static final float SPEED = 1500f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        autoScrollAnother();
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerStockTape.setLayoutManager(layoutManager);
        recyclerStockTape.setHasFixedSize(true);
        recyclerStockTape.setItemViewCacheSize(1000);
        recyclerStockTape.setDrawingCacheEnabled(true);
        recyclerStockTape.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerStockTape.setAdapter(stockScrollAdapter);
    }

}
