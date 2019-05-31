package in.themoneytree.ui.portfolio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.CommonUtils;
import in.themoneytree.utils.chart.HorizontalBarChartActivity;

public class PortfolioActivity extends BaseActivity {
    @BindView(R.id.fab_drawerOpen_portfolio)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.chart_allocation_portfolio)
    BarChart barChart;
    @BindView(R.id.btn_completePortfolio_portfolio)
    Button btnCompletePortfolio;
    @BindView(R.id.btn_transactionHistory_portfolio)
    Button btnTransactions;
    @BindView(R.id.fab_drawerOpen_moreOptions)
    FloatingActionButton fabDrawerOpenMoreOptions;
    private DrawerLayout drawer;
    private static final String TAG = "PORTFOLIO ACTIVITY";
    private boolean isPortfolioDetailVisible = false;
    private boolean isTransactionDetailVisible = false;

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
        setUpPortfolioAllocationChart();
        btnCompletePortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPortfolioDetailVisible) {
                    isPortfolioDetailVisible = false;
                    btnCompletePortfolio.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_drop_down_black), null);
                } else {
                    isPortfolioDetailVisible = true;
                    btnCompletePortfolio.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                    fetchCompletePortfolio();
                }
            }
        });
        btnTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTransactionDetailVisible) {
                    isTransactionDetailVisible = false;
                    btnTransactions.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_drop_down_black), null);
                } else {
                    isTransactionDetailVisible = true;
                    btnTransactions.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                    fetchTransactions();
                }
            }
        });
    }

    private void fetchTransactions() {
        CommonUtils.showToast(getApplicationContext(), "No Transactions Done Till Now");
    }

    private void fetchCompletePortfolio() {
        CommonUtils.showToast(getApplicationContext(), "No Details Available Currently");
    }

    private void setUpPortfolioAllocationChart() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("F.D.");
        labels.add("Mutual Fund");
        labels.add("Cash");
        labels.add("PPF");
        labels.add("Stocks");
        labels.add("RD");
        labels.add("Securities");
        labels.add("Property");
        ArrayList<Float> values = new ArrayList<>();
        values.add(50f);
        values.add(20f);
        values.add(30f);
        values.add(10f);
        values.add(60f);
        values.add(70f);
        values.add(80f);
        values.add(55f);
        barChart = HorizontalBarChartActivity.barChartDesigner(barChart, labels, values);
        if (barChart != null) {
            barChart.invalidate();
        } else {
            Log.d(TAG, "Bar Chart Null");
        }
    }

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_portfolio;
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_PORTFOLIO;
    }
}
