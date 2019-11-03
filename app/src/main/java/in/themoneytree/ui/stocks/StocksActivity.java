package in.themoneytree.ui.stocks;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.CommonUtils;

public class StocksActivity extends BaseActivity {
    @BindView(R.id.recycler_stockList_stocksActivity)
    RecyclerView recyclerStockList;
    @BindView(R.id.fab_drawerOpen_stocksActivity)
    FloatingActionButton fabDrawerOpen;
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
        setRecyclerView();
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
}
