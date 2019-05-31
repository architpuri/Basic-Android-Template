package in.themoneytree.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.CommonUtils;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.btn_shareList)
    Button btnShareList;
    @BindView(R.id.recycler_stockList_homeActivity)
    RecyclerView recyclerStockList;
    @BindView(R.id.fab_drawerOpen_homeActivity)
    FloatingActionButton fabDrawerOpen;
    private StockListAdapter stockAdapter;
    private DrawerLayout drawer;
    private static final String TAG = "HOME ACTIVITY";

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
        btnShareList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bit.do/Capstone_stocks"));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Link Not Working", Toast.LENGTH_SHORT);
                }
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
}
