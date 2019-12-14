package in.themoneytree.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import in.themoneytree.data.model.stock.StockListResponse;
import in.themoneytree.data.model.stock.Stocks;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.ui.home.indices.IndicesAdapter;
import in.themoneytree.ui.home.stocktape.StockScrollAdapter;
import in.themoneytree.ui.home.stocktape.StockScrollListener;
import in.themoneytree.ui.portfolio.PortfolioActivity;
import in.themoneytree.ui.retirement.RetirementActivity;
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
    List<Stocks> stocks = new ArrayList<>();
    List<Stocks> indices = new ArrayList<>();
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
        if(fetchStocks()){
            System.out.println(stocks.toString());
        }
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    private void setStocksBasedStuff(){
        setUpTape();
        fetchIndices();
    }

    private void setUpScreen() {
        setUpIndicesRecycler();
        setUpPortfolio();
        setUpCorpus();
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
        IndicesAdapter indicesAdapter = new IndicesAdapter(getApplicationContext(), indices);
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

    public void autoScrollAnother() {
        scrollCount = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recyclerStockTape.smoothScrollToPosition((scrollCount++));
                if (scrollCount == stockScrollAdapter.getItemCount() - 4) {
                    //stocks.addAll(stocks);
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
        if(stocks!=null && stocks.size()>0) {
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

    private boolean fetchStocks() {
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<StockListResponse> stocksRequest = moneyService.getAllStocks();
        stocksRequest.enqueue(new Callback<StockListResponse>() {
            @Override
            public void onResponse(Call<StockListResponse> call, Response<StockListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getGeneralResponse().getStatusCode() == ApiConstants.SUCCESS) {
                        List<Stocks> stockList = response.body().getStocks();
                        Log.d(TAG,response.body().toString());
                        if(stockList!=null && stockList.size()>0){
                            stocks = stockList;
                            setStocksBasedStuff();
                            Log.d(TAG,"Here1");
                        }else{
                            if(stockList==null){
                                Log.d(TAG,"Null it is");
                            }
                            Log.d(TAG,"Stock List");
                        }
                    }else{
                        Log.d(TAG,"General Response");
                    }
                }else{
                    Log.d(TAG,"Not Success");
                }
            }

            @Override
            public void onFailure(Call<StockListResponse> call, Throwable t) {
                CommonUtils.showToast(HomeActivity.this, "Failure Check Internet");
            }
        });
        /*
            if(stocks==null || stocks.size()==0){
                Log.d(TAG,"Here2");
                stocks = getStockListForTape();
                setStocksBasedStuff();
                return false;
            }
        */
        return true;
    }

    private boolean fetchIndices(){
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<StockListResponse> stocksRequest = moneyService.getIndices(userId);
        stocksRequest.enqueue(new Callback<StockListResponse>() {
            @Override
            public void onResponse(Call<StockListResponse> call, Response<StockListResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getGeneralResponse().getStatusCode() == ApiConstants.SUCCESS) {
                        List<Stocks> stockList = response.body().getStocks();
                        if(stockList!=null && stockList.size()>0){
                            indices = stockList;
                            setUpScreen();
                        }else{
                            if(stockList==null){
                                Log.d(TAG,"Null it is");
                            }
                            Log.d(TAG,"Stock List");
                        }
                    }else{
                        Log.d(TAG,"General Response");
                    }
                }else{
                    Log.d(TAG,"Not Success");
                }
            }

            @Override
            public void onFailure(Call<StockListResponse> call, Throwable t) {
                CommonUtils.showToast(HomeActivity.this, "Failure Check Internet");
            }
        });
        return true;
    }

    private void setUpCorpus(){
        TextView txtRetirementTitle = findViewById(R.id.txt_corpusTitle_homeActivity);
        txtRetirementTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RetirementActivity.class);
                startActivity(intent);
            }
        });
        TextView txtRetirementAmount = findViewById(R.id.txt_currentCorpus_homeActivity);
        TextView txtRetirementGoal = findViewById(R.id.txt_goalCorpus_homeActivity);
        TextView txtProgress = findViewById(R.id.txt_progress);
        ProgressBar progressBar = findViewById(R.id.progress);
        Double goal=0.0,amount=0.0;
        try {
            amount = Double.parseDouble(PrefManager.getInstance(getApplicationContext()).getRetirementCorpusAmount());
            goal = Double.parseDouble(PrefManager.getInstance(getApplicationContext()).getRetirementCorpusGoal());
        }catch(Exception e){
            e.printStackTrace();
        }
        int progressValue =0;
        if(goal!=0.0) {
            Double d = amount / goal;
            progressValue = d.intValue();
        }
        progressBar.setProgress(progressValue + 1);
        txtProgress.setText("" + progressValue + "%");
        txtRetirementAmount.setText("Current - "+amount);
        txtRetirementGoal.setText("Goal - "+goal);
    }

    private void setUpPortfolio(){
        TextView txtPortfolioTitle =findViewById(R.id.txt_portfolioTitle_homeActivity);
        txtPortfolioTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PortfolioActivity.class);
                startActivity(intent);
            }
        });
        setUpAllocationChart();
    }
}
