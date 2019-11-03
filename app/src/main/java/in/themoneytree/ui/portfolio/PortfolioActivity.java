package in.themoneytree.ui.portfolio;

import android.content.Intent;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.adapters.ClickListener;
import in.themoneytree.adapters.InvestmentInstrumentsAdapter;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.ui.instruments.InstrumentActivity;
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
    @BindView(R.id.fab_moreOptions_portfolio)
    FloatingActionButton fabMoreOptions;
    @BindView(R.id.txt_totalCorpusAmount_portfolio)
    TextView txtTotalAmount;
    @BindView(R.id.recycler_investmentInstruments_portfolio)
    RecyclerView recyclerInstruments;
    @BindView(R.id.constraint_completePortfolio_portfolio)
    ConstraintLayout constraintCompletePortfolio;
    @BindView(R.id.constraint_transactions_portfolio)
    ConstraintLayout constraintTransaction;
    private DrawerLayout drawer;
    private static final String TAG = "PORTFOLIO ACTIVITY";
    private boolean isPortfolioDetailVisible = false;
    private boolean isTransactionDetailVisible = false;
    private InvestmentInstrumentsAdapter instrumentAdapter;

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
                    constraintCompletePortfolio.setVisibility(View.GONE);
                } else {
                    isPortfolioDetailVisible = true;
                    btnCompletePortfolio.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                    constraintCompletePortfolio.setVisibility(View.VISIBLE);
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
                    constraintTransaction.setVisibility(View.GONE);
                } else {
                    isTransactionDetailVisible = true;
                    btnTransactions.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_arrow_drop_up_black), null);
                    constraintTransaction.setVisibility(View.VISIBLE);
                    fetchTransactions();
                }
            }
        });
    }

    private void fetchTransactions() {
        CommonUtils.showToast(getApplicationContext(), "No Transactions Done Till Now");
        setUpTransactionsRecycler();
    }

    private void setUpTransactionsRecycler() {
    }

    private void fetchCompletePortfolio() {
        //CommonUtils.showToast(getApplicationContext(), "No Details Available Currently");
        setUpInstrumentsRecycler();
    }

    private void setUpInstrumentsRecycler() {
        recyclerInstruments.setVisibility(View.VISIBLE);
        instrumentAdapter = new InvestmentInstrumentsAdapter(getInstrumentList(), getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
                ArrayList<String> instrument = (ArrayList<String>) getInstrumentList();
                String source = "PortfolioActivity";
                String destination;
                switch (instrument.get(position)) {
                    case "F.D.": {
                        destination = "Bonds";
                        break;
                    }
                    case "Mutual Fund": {
                        destination = "Mutual Funds";
                        break;
                    }
                    case "Cash": {
                        destination = "Liquid Form";
                        break;
                    }
                    case "PPF": {
                        destination = "Other Instruments";
                        break;
                    }
                    case "Stocks": {
                        destination = "Stock Holding";
                        break;
                    }
                    case "RD": {
                        destination = "Bonds";
                        break;
                    }
                    case "Metals": {
                        destination = "Metals";
                        break;
                    }
                    case "Property": {
                        destination = "Property";
                        break;
                    }
                    default: {
                        destination = "Other Instruments";
                    }
                }
                Intent intent = new Intent(getApplicationContext(), InstrumentActivity.class);
                intent.putExtra("Source", source);
                intent.putExtra("Destination", destination);
                startActivity(intent);
            }
        });
        instrumentAdapter.notifyDataSetChanged();
        Log.d(TAG, "IDhr bhi aa lia 2");
        if (instrumentAdapter == null) {
            Log.d(TAG, "Null aa raa");
        }
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerInstruments.addItemDecoration(itemDecorator);
        recyclerInstruments.setAdapter(instrumentAdapter);
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

    private void setUpPortfolioAllocationChart() {
        ArrayList<String> labels = (ArrayList<String>) getInstrumentList();
        ArrayList<Float> values = getPortfolioAllocation();
        barChart = HorizontalBarChartActivity.barChartDesigner(barChart, labels, values);
        if (barChart != null) {
            barChart.invalidate();
        } else {
            Log.d(TAG, "Bar Chart Null");
        }
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
