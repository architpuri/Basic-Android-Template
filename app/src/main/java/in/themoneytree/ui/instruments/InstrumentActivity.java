package in.themoneytree.ui.instruments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.adapters.ClickListener;
import in.themoneytree.adapters.FdAdapter;
import in.themoneytree.adapters.LiquidFormAdapter;
import in.themoneytree.adapters.MetalsAdapter;
import in.themoneytree.adapters.OtherInstrumentsAdapter;
import in.themoneytree.adapters.PropertyAdapter;
import in.themoneytree.adapters.StockHoldingAdapter;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.ui.editinstrument.EditInstrumentActivity;

public class InstrumentActivity extends BaseActivity {
    @BindView(R.id.txt_title_instrument)
    TextView txtTitle;
    @BindView(R.id.recycler_components_instrument)
    RecyclerView recyclerComponents;
    @BindView(R.id.fab_drawerOpen_instrument)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.fab_add_instrument)
    FloatingActionButton fabAdd;
    private DrawerLayout drawer;
    private String titleText;
    private final String TAG = "INSTRUMENT ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String source = intent.getStringExtra("Source");
        getTitleText(intent.getStringExtra("Destination"));
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        txtTitle.setText(titleText + " you have invested in");
    }

    private void getTitleText(String destination) {
        setUpRecycler();
        switch (destination) {
            case "F.D.": {
                titleText = "Bonds";
                setUpBondsRecycler();
                break;
            }
            case "Mutual Fund": {
                titleText = "Mutual Funds";
                setUpMutualFundRecycler();
                break;
            }
            case "Cash": {
                titleText = "Liquid Form";
                setUpLiquidRecycler();
                break;
            }
            case "PPF": {
                titleText = "Other Instruments";
                setUpOtherRecycler();
                break;
            }
            case "Stocks": {
                titleText = "Stock Holding";
                setUpStocksRecycler();
                break;
            }
            case "RD": {
                titleText = "Bonds";
                setUpBondsRecycler();
                break;
            }
            case "Metals": {
                titleText = "Metals";
                setUpMetalsRecycler();
                break;
            }
            case "Property": {
                setUpPropertyRecycler();
                titleText = "Property";
                break;
            }
            default: {
                titleText = "Other Instruments";
            }
        }
    }

    private void setUpOtherRecycler() {
        OtherInstrumentsAdapter adapter = new OtherInstrumentsAdapter(getBonds(), getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
                Intent intent = new Intent(getApplicationContext(), EditInstrumentActivity.class);
                intent.putExtra("Source", TAG);
                intent.putExtra("Destination", "EDIT INSTRUMENT");
            }
        });
        recyclerComponents.setAdapter(adapter);
    }

    private void setUpMutualFundRecycler() {
        setUpStocksRecycler();
    }

    private void setUpLiquidRecycler() {
        LiquidFormAdapter adapter = new LiquidFormAdapter(getBonds(), getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
                Intent intent = new Intent(getApplicationContext(), EditInstrumentActivity.class);
                intent.putExtra("Source", TAG);
                intent.putExtra("Destination", "EDIT INSTRUMENT");
            }
        });
        recyclerComponents.setAdapter(adapter);
    }

    private void setUpStocksRecycler() {
        StockHoldingAdapter adapter = new StockHoldingAdapter(getBonds(), getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
                Intent intent = new Intent(getApplicationContext(), EditInstrumentActivity.class);
                intent.putExtra("Source", TAG);
                intent.putExtra("Destination", "EDIT INSTRUMENT");
            }
        });
        recyclerComponents.setAdapter(adapter);
    }

    private void setUpPropertyRecycler() {
        PropertyAdapter adapter = new PropertyAdapter(getBonds(), getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
                Intent intent = new Intent(getApplicationContext(), EditInstrumentActivity.class);
                intent.putExtra("Source", TAG);
                intent.putExtra("Destination", "EDIT INSTRUMENT");
            }
        });
        recyclerComponents.setAdapter(adapter);
    }


    private void setUpMetalsRecycler() {
        MetalsAdapter adapter = new MetalsAdapter(getBonds(), getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
                Intent intent = new Intent(getApplicationContext(), EditInstrumentActivity.class);
                intent.putExtra("Source", TAG);
                intent.putExtra("Destination", "EDIT INSTRUMENT");
            }
        });
        recyclerComponents.setAdapter(adapter);
    }


    private void setUpBondsRecycler() {
        FdAdapter adapter = new FdAdapter(getBonds(), getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
                Intent intent = new Intent(getApplicationContext(), EditInstrumentActivity.class);
                intent.putExtra("Source", TAG);
                intent.putExtra("Destination", "EDIT INSTRUMENT");
            }
        });
        recyclerComponents.setAdapter(adapter);
    }

    private List<String> getBonds() {
        List<String> bonds = new ArrayList<>();
        bonds.add("6161548163");
        bonds.add("668616516");
        return bonds;
    }


    private void setUpRecycler() {
        recyclerComponents.setVisibility(View.VISIBLE);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerComponents.addItemDecoration(itemDecorator);
    }

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_instrument;
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_PORTFOLIO;
    }
}
