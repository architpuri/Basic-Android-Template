package in.themoneytree.ui.portfolio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.adapters.ClickListener;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.incomestreams.IncomeStream;
import in.themoneytree.data.model.investments.Investment;
import in.themoneytree.data.model.transaction.Transaction;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.ui.portfolio.adapters.IncomeStreamsAdapter;
import in.themoneytree.ui.portfolio.adapters.InvestmentAdapter;
import in.themoneytree.ui.portfolio.adapters.TransactionAdapter;
import in.themoneytree.ui.portfolio.portfoliofile.PortfolioEditor;
import in.themoneytree.ui.portfolio.portfoliofile.PortfolioMaker;
import in.themoneytree.ui.portfolio.portfoliofile.PortfolioReader;
import in.themoneytree.ui.portfolio.tools.AllocationCalculator;
import in.themoneytree.ui.portfolio.tools.CashFlowCalculator;
import in.themoneytree.utils.CommonUtils;
import in.themoneytree.utils.UniqueIdGenerator;
import in.themoneytree.utils.chart.HorizontalBarChartActivity;
import in.themoneytree.utils.upload.Common;

public class PortfolioActivity extends BaseActivity {
    @BindView(R.id.fab_drawerOpen_portfolio)
    FloatingActionButton fabDrawerOpen;
    @BindView(R.id.chart_allocation_portfolio)
    BarChart barChart;
    @BindView(R.id.fab_moreOptions_portfolio)
    FloatingActionButton fabMoreOptions;
    @BindView(R.id.txt_totalCorpusAmount_portfolio)
    TextView txtTotalAmount;
    @BindView(R.id.recycler_incomeStreams_portfolio)
    RecyclerView recyclerIncomeStreams;
    @BindView(R.id.recycler_investmentInstruments_portfolio)
    RecyclerView recyclerInvestments;
    @BindView(R.id.recycler_transactions_portfolio)
    RecyclerView recyclerTransactions;
    private DrawerLayout drawer;
    private static final String TAG = "PORTFOLIO ACTIVITY";
    private TransactionAdapter transactionAdapter;
    private InvestmentAdapter investmentsAdapter;
    private IncomeStreamsAdapter streamsAdapter;
    private String filePath;
    private int positionInvestmentSpinner = -1;
    private Integer userId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        filePath = getFilesDir().getPath().toString() + "/portfolio.json";
        fabDrawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        setUpUserInterface();
        fabMoreOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpOptions();
            }
        });
    }

    private void setUpUserInterface() {
        try {
            File fi = new File(filePath);
            if (!fi.exists()) {
                PortfolioMaker.makeNewFile(filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PortfolioReader.readPortfolio(filePath);
        setUpPortfolioAllocationChart();
        setUpCashFlow();
        setUpInvestments();
        setUpTransactions();
        setUpIncomeStreams();
        txtTotalAmount.setText(AllocationCalculator.getTotalInvestment(getInvestments()) + "");
    }

    private void setUpTransactions() {
        List<Transaction> transactions = getTransactions();
        if (transactions == null || transactions.size() > 0)
            setUpTransactionsRecycler(transactions);
        else {
            CommonUtils.showToast(getApplicationContext(), "No Transactions Done Till Now");
        }
    }

    private void setUpInvestments() {
        try {
            Log.d(TAG, "Crash Yhaan Shure");
            List<Investment> investments = getInvestments();
            if (investments == null || investments.size() == 0)
                CommonUtils.showToast(getApplicationContext(), "No Investments Done Till Now");
            else {
                setUpInvestmentsRecycler(investments);
                setTotalInvestments(investments);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpInvestmentsRecycler(List<Investment> investments) {
        recyclerInvestments.setVisibility(View.VISIBLE);
        investmentsAdapter = new InvestmentAdapter(investments, getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
                modifyDialog("Investment", getInvestments().get(position).getInvestmentId());
            }
        });
        investmentsAdapter.notifyDataSetChanged();
        Log.d(TAG, "IDhr bhi aa lia 2");
        if (investmentsAdapter == null) {
            Log.d(TAG, "Null aa raa");
        }
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerInvestments.addItemDecoration(itemDecorator);
        recyclerInvestments.setAdapter(investmentsAdapter);
    }

    private void setUpIncomeStreams() {
        List<IncomeStream> incomeStreams = getIncomeStreams();
        if (incomeStreams == null || incomeStreams.size() == 0) {
            Log.d(TAG, "No Income Stream");
            CommonUtils.showToast(getApplicationContext(), "No Income Stream Added Till Now");
        } else {
            setUpIncomeStreamRecycler(incomeStreams);
        }
    }

    private void setUpIncomeStreamRecycler(List<IncomeStream> incomeStreams) {
        recyclerIncomeStreams.setVisibility(View.VISIBLE);
        streamsAdapter = new IncomeStreamsAdapter(incomeStreams, getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
            }
        });
        streamsAdapter.notifyDataSetChanged();
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerIncomeStreams.addItemDecoration(itemDecorator);
        recyclerIncomeStreams.setAdapter(streamsAdapter);
    }

    private List<IncomeStream> getIncomeStreams() {
        List<IncomeStream> streams = PortfolioReader.getIncomeStreams();
        return streams;
    }

    private void setUpCashFlow() {
        TextView txtInflowMonthly = findViewById(R.id.txt_inflowCashMonthly_portfolio);
        TextView txtInflowYearly = findViewById(R.id.txt_inflowCashYearly_portfolio);
        TextView txtOutflowMonthly = findViewById(R.id.txt_outflowCashMonthly_portfolio);
        TextView txtOutflowYearly = findViewById(R.id.txt_outflowCashYearly_portfolio);
        TextView txtNetMonthly = findViewById(R.id.txt_netCashMonthly_portfolio);
        TextView txtNetYearly = findViewById(R.id.txt_netCashYearly_portfolio);
        Pair<Double, Double> p = CashFlowCalculator.getCashFlow(getIncomeStreams(), getTransactions());
        if (p == null) {
            return;
        }
        txtInflowMonthly.setText(p.first + "");
        txtInflowYearly.setText(p.first * 12 + "");
        txtOutflowMonthly.setText(p.second + "");
        txtOutflowYearly.setText(p.second * 12 + "");
        txtNetMonthly.setText((p.first - p.second) + "");
        txtNetYearly.setText((p.first - p.second) * 12 + "");
    }


    private void setUpPortfolioAllocationChart() {

        /*ArrayList<String> labels = (ArrayList<String>) getInstrumentList();
        ArrayList<Float> values = getPortfolioAllocation();
        */
        Pair<ArrayList<String>, ArrayList<Double>> p = AllocationCalculator.getPortfolioAllocation(getInvestments());
        if (p == null) {
            return;
        }
        ArrayList<String> labels = p.first;
        ArrayList<Double> valueDouble = p.second;
        if (valueDouble != null && valueDouble.size() > 0) {
            ArrayList<Float> values = new ArrayList<>();
            for (Double d : valueDouble) {
                values.add(Float.parseFloat(d.toString()));
            }
            barChart = HorizontalBarChartActivity.barChartDesigner(barChart, labels, values);
            if (barChart != null) {
                barChart.invalidate();
            } else {
                Log.d(TAG, "Bar Chart Null");
            }
        } else {
            barChart.setVisibility(View.GONE);
        }
    }


    private List<Transaction> getTransactions() {
        List<Transaction> transactions = PortfolioReader.getTransactions();
        return transactions;
    }

    private void setUpTransactionsRecycler(List<Transaction> transactions) {
        recyclerTransactions.setVisibility(View.VISIBLE);
        transactionAdapter = new TransactionAdapter(getTransactions(), getApplicationContext(), new ClickListener() {
            @Override
            public void itemEdit(View v, int position, String itemName) {
            }
        });
        transactionAdapter.notifyDataSetChanged();
        Log.d(TAG, "IDhr bhi aa lia 2");
        if (transactionAdapter == null) {
            Log.d(TAG, "Null aa raa");
        }
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerTransactions.addItemDecoration(itemDecorator);
        recyclerTransactions.setAdapter(transactionAdapter);
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

    private List<Investment> getInvestments() {
        List<Investment> investments = PortfolioReader.getInvestments();
        return investments;
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
        return null;

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

    private void investmentDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_investment, (ViewGroup) findViewById(R.id.root_dialogInvestment));
        final TextInputEditText name = layout.findViewById(R.id.edt_investmentName_dialogInvestment);
        final TextInputEditText amount = layout.findViewById(R.id.edt_investmentAmount_dialogInvestment);
        final TextInputEditText rate = layout.findViewById(R.id.edt_investmentRate_dialogInvestment);
        final TextInputEditText expiryDate = layout.findViewById(R.id.edt_investmentExpiryDate_dialogInvestment);
        final TextInputEditText extraInfo = layout.findViewById(R.id.edt_investmentExtra_dialogInvestment);
        final TextInputLayout tName = layout.findViewById(R.id.text_investmentName_dialogInvestment);
        final TextInputLayout tAmount = layout.findViewById(R.id.text_investmentAmount_dialogInvestment);
        final TextInputLayout tRate = layout.findViewById(R.id.text_investmentRate_dialogInvestment);
        final TextInputLayout tExpiryDate = layout.findViewById(R.id.text_expiryDate_dialogInvestment);
        final TextInputLayout tExtraInfo = layout.findViewById(R.id.text_investmentExtra_dialogInvestment);

        setUpInvestmentSpinner(layout);
        List<TextInputEditText> edits = new ArrayList<TextInputEditText>();
        edits.add(name);
        edits.add(amount);
        edits.add(rate);
        edits.add(expiryDate);
        edits.add(extraInfo);
        List<TextInputLayout> layouts = new ArrayList<>();
        layouts.add(tName);
        layouts.add(tAmount);
        layouts.add(tRate);
        layouts.add(tExpiryDate);
        layouts.add(tExtraInfo);
        //Building dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("Save",null); /*new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                *//*if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount) && CommonUtils.isNumber(tRate, rate)) {
                    Investment investments = new Investment(UniqueIdGenerator.getId(getApplicationContext()), name.getText().toString(),
                            Double.parseDouble(amount.getText().toString()), expiryDate.getText().toString(),
                            Double.parseDouble(rate.getText().toString()), positionInvestmentSpinner,
                            extraInfo.getText().toString());
                    PortfolioEditor.addInvestment(filePath, investments);
                    Log.d(TAG,"Saved - "+investments.getInvestmentName());
                    updateUI();
                    dialog.dismiss();
                }else{
                    if(CommonUtils.checkEmptyInput(edits,layouts)){
                        Log.d(TAG, "List is BackChodi");
                    }else {
                        Log.d(TAG, "BackChodi");
                    }
                }*//*
            }
        });*/
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUI();
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount) && CommonUtils.isNumber(tRate, rate)) {
                            Investment investments = new Investment(UniqueIdGenerator.getId(getApplicationContext()), name.getText().toString(),
                                    Double.parseDouble(amount.getText().toString()), expiryDate.getText().toString(),
                                    Double.parseDouble(rate.getText().toString()), positionInvestmentSpinner,
                                    extraInfo.getText().toString());
                            PortfolioEditor.addInvestment(filePath, investments);
                            Log.d(TAG,"Saved - "+investments.getInvestmentName());
                            updateUI();
                            dialog.dismiss();
                        }else{
                            if(CommonUtils.checkEmptyInput(edits,layouts)){
                                Log.d(TAG, "List is BackChodi");
                            }else {
                                Log.d(TAG, "BackChodi");
                            }
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private void transactionDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_transaction, (ViewGroup) findViewById(R.id.root_dialogTransaction));
        final TextInputEditText name = layout.findViewById(R.id.edt_transactionName_dialogTransaction);
        final TextInputEditText amount = layout.findViewById(R.id.edt_transactionAmount_dialogTransaction);
        final TextInputEditText method = layout.findViewById(R.id.edt_transactionMethod_dialogTransaction);
        final TextInputEditText receiver = layout.findViewById(R.id.edt_transactionReceiver_dialogTransaction);
        final TextInputEditText date = layout.findViewById(R.id.edt_transactionDate_dialogTransaction);
        final TextInputEditText info = layout.findViewById(R.id.edt_transactionInfo_dialogTransaction);
        final TextInputLayout tName = layout.findViewById(R.id.text_transactionName_dialogTransaction);
        final TextInputLayout tAmount = layout.findViewById(R.id.text_transactionAmount_dialogTransaction);
        final TextInputLayout tMethod = layout.findViewById(R.id.text_transactionMethod_dialogTransaction);
        final TextInputLayout tReceiver = layout.findViewById(R.id.text_transactionReceiver_dialogTransaction);
        final TextInputLayout tDate = layout.findViewById(R.id.text_transactionDate_dialogTransaction);
        final TextInputLayout tInfo = layout.findViewById(R.id.text_transactionInfo_dialogTransaction);
        List<TextInputEditText> edits = new ArrayList<>();
        edits.add(name);
        edits.add(amount);
        edits.add(receiver);
        edits.add(date);
        edits.add(info);
        List<TextInputLayout> layouts = new ArrayList<>();
        layouts.add(tName);
        layouts.add(tAmount);
        layouts.add(tMethod);
        layouts.add(tReceiver);
        layouts.add(tDate);
        layouts.add(tInfo);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("Save", null);/*new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount)) {
                    Transaction transact = new Transaction(UniqueIdGenerator.getId(getApplicationContext()), Double.parseDouble(amount.getText().toString()),
                            name.getText().toString(), method.getText().toString(),
                            receiver.getText().toString(), date.getText().toString(),
                            info.getText().toString());
                    PortfolioEditor.addTransaction(filePath, transact);
                    updateUI();
                    dialog.dismiss();
                }
            }
        });*/
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUI();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount)) {
                            Transaction transact = new Transaction(UniqueIdGenerator.getId(getApplicationContext()), Double.parseDouble(amount.getText().toString()),
                                    name.getText().toString(), method.getText().toString(),
                                    receiver.getText().toString(), date.getText().toString(),
                                    info.getText().toString());
                            PortfolioEditor.addTransaction(filePath, transact);
                            updateUI();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private void incomeStreamDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_income_stream, (ViewGroup) findViewById(R.id.root_dialogIncomeStream));
        final TextInputEditText name = layout.findViewById(R.id.edt_streamName_dialogIncomeStream);
        final TextInputEditText amount = layout.findViewById(R.id.edt_streamAmount_dialogIncomeStream);
        final TextInputEditText frequency = layout.findViewById(R.id.edt_streamFrequency_dialogIncomeStream);
        final TextInputEditText type = layout.findViewById(R.id.edt_streamType_dialogIncomeStream);
        final TextInputEditText info = layout.findViewById(R.id.edt_streamInfo_dialogIncomeStream);
        final TextInputLayout tName = layout.findViewById(R.id.text_streamName_dialogIncomeStream);
        final TextInputLayout tAmount = layout.findViewById(R.id.text_streamAmount_dialogIncomeStream);
        final TextInputLayout tFrequency = layout.findViewById(R.id.text_streamFrequency_dialogIncomeStream);
        final TextInputLayout tType = layout.findViewById(R.id.text_streamType_dialogIncomeStream);
        final TextInputLayout tInfo = layout.findViewById(R.id.text_streamInfo_dialogIncomeStream);
        List<TextInputEditText> edits = new ArrayList<TextInputEditText>();
        edits.add(name);
        edits.add(amount);
        edits.add(frequency);
        edits.add(type);
        edits.add(info);
        List<TextInputLayout> layouts = new ArrayList<TextInputLayout>();
        layouts.add(tName);
        layouts.add(tAmount);
        layouts.add(tFrequency);
        layouts.add(tType);
        layouts.add(tInfo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("Save",null);/* new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount) && CommonUtils.isNumber(tFrequency, frequency) && CommonUtils.isNumber(tType, type)) {
                    IncomeStream stream = new IncomeStream(UniqueIdGenerator.getId(getApplicationContext()), userId, name.getText().toString(),
                            Double.parseDouble(amount.getText().toString()), Integer.parseInt(frequency.getText().toString()),
                            info.getText().toString(), Integer.parseInt(type.getText().toString()));
                    PortfolioEditor.addIncomeStream(filePath, stream);
                    updateUI();
                    dialog.dismiss();
                }
            }
        });*/
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUI();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount) && CommonUtils.isNumber(tFrequency, frequency) && CommonUtils.isNumber(tType, type)) {
                            IncomeStream stream = new IncomeStream(UniqueIdGenerator.getId(getApplicationContext()), userId, name.getText().toString(),
                                    Double.parseDouble(amount.getText().toString()), Integer.parseInt(frequency.getText().toString()),
                                    info.getText().toString(), Integer.parseInt(type.getText().toString()));
                            PortfolioEditor.addIncomeStream(filePath, stream);
                            updateUI();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private void setUpOptions() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_portfolio_options, (ViewGroup) findViewById(R.id.root_dialogOptions));
        //Building dialog
        final TextView txtStream = layout.findViewById(R.id.txt_stream_dialogOptions);
        final TextView txtInvestment = layout.findViewById(R.id.txt_investment_dialogOptions);
        final TextView txtTransaction = layout.findViewById(R.id.txt_transaction_dialogOptions);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        txtStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PortfolioReader.readPortfolio(filePath);
                incomeStreamDialog();
            }
        });
        txtInvestment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                investmentDialog();
            }
        });
        txtTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionDialog();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateUI() {
        //CashFlow Currently = IncomeStreams - expenditure
        setUpUserInterface();
    }

    private void setUpInvestmentSpinner(View layout) {
        Spinner spin = layout.findViewById(R.id.spinner_investmentType_dialogInvestment);
        ArrayAdapter aa = new ArrayAdapter(PortfolioActivity.this, android.R.layout.simple_spinner_item, getInstrumentList());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                positionInvestmentSpinner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void editInvestment(Integer investmentId) {
        List<Investment> investments = getInvestments();
        for (Investment investment : investments) {
            if (investment.getInvestmentId() == investmentId) {
                editInvestmentDialog(investment);
                break;
            }
        }
    }

    private void editIncomeStream(Integer streamId) {
        List<IncomeStream> investments = getIncomeStreams();
        for (IncomeStream investment : investments) {
            if (investment.getStreamId() == streamId) {
                editIncomeStreamDialog(investment);
                break;
            }
        }
    }

    private void editTransaction(Integer transactionId) {
        List<Transaction> transactions = getTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionId() == transactionId) {
                editTransactionDialog(transaction);
                break;
            }
        }
    }

    private void editInvestmentDialog(Investment investment) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_investment, (ViewGroup) findViewById(R.id.root_dialogInvestment));
        final TextInputEditText name = layout.findViewById(R.id.edt_investmentName_dialogInvestment);
        final TextInputEditText amount = layout.findViewById(R.id.edt_investmentAmount_dialogInvestment);
        final TextInputEditText rate = layout.findViewById(R.id.edt_investmentRate_dialogInvestment);
        final TextInputEditText expiryDate = layout.findViewById(R.id.edt_investmentExpiryDate_dialogInvestment);
        final TextInputEditText extraInfo = layout.findViewById(R.id.edt_investmentExtra_dialogInvestment);
        name.setText(investment.getInvestmentName());
        amount.setText(investment.getPrincipalAmount() + "");
        rate.setText(investment.getInterestRate() + "");
        expiryDate.setText(investment.getExpiryDate());
        extraInfo.setText(investment.getExtraInfo());
        final TextInputLayout tName = layout.findViewById(R.id.text_investmentName_dialogInvestment);
        final TextInputLayout tAmount = layout.findViewById(R.id.text_investmentAmount_dialogInvestment);
        final TextInputLayout tRate = layout.findViewById(R.id.text_investmentRate_dialogInvestment);
        final TextInputLayout tExpiryDate = layout.findViewById(R.id.text_expiryDate_dialogInvestment);
        final TextInputLayout tExtraInfo = layout.findViewById(R.id.text_investmentExtra_dialogInvestment);
        setUpInvestmentSpinner(layout);
        List<TextInputEditText> edits = new ArrayList<TextInputEditText>();
        edits.add(name);
        edits.add(amount);
        edits.add(rate);
        edits.add(expiryDate);
        edits.add(extraInfo);
        List<TextInputLayout> layouts = new ArrayList<>();
        layouts.add(tName);
        layouts.add(tAmount);
        layouts.add(tRate);
        layouts.add(tExpiryDate);
        layouts.add(tExtraInfo);
        Spinner spin = layout.findViewById(R.id.spinner_investmentType_dialogInvestment);
        spin.setSelection(investment.getCategory());
        positionInvestmentSpinner = investment.getCategory();
        //Building dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("Save",null);/*, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount) && CommonUtils.isNumber(tRate, rate)) {
                    Investment investments = new Investment(investment.getInvestmentId(), name.getText().toString(),
                            Double.parseDouble(amount.getText().toString()), expiryDate.getText().toString(),
                            Double.parseDouble(rate.getText().toString()), positionInvestmentSpinner,
                            extraInfo.getText().toString());
                    if (PortfolioEditor.removeInvestment(filePath, investment.getInvestmentId())) {
                        PortfolioEditor.addInvestment(filePath, investments);
                        updateUI();
                    }
                    dialog.dismiss();
                }
            }
        });*/

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount) && CommonUtils.isNumber(tRate, rate)) {
                            Investment investments = new Investment(investment.getInvestmentId(), name.getText().toString(),
                                    Double.parseDouble(amount.getText().toString()), expiryDate.getText().toString(),
                                    Double.parseDouble(rate.getText().toString()), positionInvestmentSpinner,
                                    extraInfo.getText().toString());
                            if (PortfolioEditor.removeInvestment(filePath, investment.getInvestmentId())) {
                                PortfolioEditor.addInvestment(filePath, investments);
                                updateUI();
                            }
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private void editTransactionDialog(Transaction transaction) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_transaction, (ViewGroup) findViewById(R.id.root_dialogTransaction));
        final TextInputEditText name = layout.findViewById(R.id.edt_transactionName_dialogTransaction);
        final TextInputEditText amount = layout.findViewById(R.id.edt_transactionAmount_dialogTransaction);
        final TextInputEditText method = layout.findViewById(R.id.edt_transactionMethod_dialogTransaction);
        final TextInputEditText receiver = layout.findViewById(R.id.edt_transactionReceiver_dialogTransaction);
        final TextInputEditText date = layout.findViewById(R.id.edt_transactionDate_dialogTransaction);
        final TextInputEditText info = layout.findViewById(R.id.edt_transactionInfo_dialogTransaction);
        name.setText(transaction.getTransactionName());
        amount.setText(transaction.getTransactionAmount() + "");
        method.setText(transaction.getTransactionMethod());
        receiver.setText(transaction.getTransactionReceiver());
        date.setText(transaction.getTransactionDate());
        info.setText(transaction.getTransactionInfo());
        final TextInputLayout tName = layout.findViewById(R.id.text_transactionName_dialogTransaction);
        final TextInputLayout tAmount = layout.findViewById(R.id.text_transactionAmount_dialogTransaction);
        final TextInputLayout tMethod = layout.findViewById(R.id.text_transactionMethod_dialogTransaction);
        final TextInputLayout tReceiver = layout.findViewById(R.id.text_transactionReceiver_dialogTransaction);
        final TextInputLayout tDate = layout.findViewById(R.id.text_transactionDate_dialogTransaction);
        final TextInputLayout tInfo = layout.findViewById(R.id.text_transactionInfo_dialogTransaction);
        List<TextInputEditText> edits = new ArrayList<>();
        edits.add(name);
        edits.add(amount);
        edits.add(receiver);
        edits.add(date);
        edits.add(info);
        List<TextInputLayout> layouts = new ArrayList<>();
        layouts.add(tName);
        layouts.add(tAmount);
        layouts.add(tMethod);
        layouts.add(tReceiver);
        layouts.add(tDate);
        layouts.add(tInfo);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("Save", null);/*new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount)) {
                    Transaction transact = new Transaction(UniqueIdGenerator.getId(getApplicationContext()), Double.parseDouble(amount.getText().toString()),
                            name.getText().toString(), method.getText().toString(),
                            receiver.getText().toString(), date.getText().toString(),
                            info.getText().toString());
                    if (PortfolioEditor.removeTransaction(filePath, transaction.getTransactionId())) {
                        PortfolioEditor.addTransaction(filePath, transact);
                        updateUI();
                    }
                    dialog.dismiss();
                }

            }
        });*/
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUI();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount)) {
                            Transaction transact = new Transaction(UniqueIdGenerator.getId(getApplicationContext()), Double.parseDouble(amount.getText().toString()),
                                    name.getText().toString(), method.getText().toString(),
                                    receiver.getText().toString(), date.getText().toString(),
                                    info.getText().toString());
                            if (PortfolioEditor.removeTransaction(filePath, transaction.getTransactionId())) {
                                PortfolioEditor.addTransaction(filePath, transact);
                                updateUI();
                            }
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private void editIncomeStreamDialog(IncomeStream incomeStream) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_income_stream, (ViewGroup) findViewById(R.id.root_dialogIncomeStream));
        final TextInputEditText name = layout.findViewById(R.id.edt_streamName_dialogIncomeStream);
        final TextInputEditText amount = layout.findViewById(R.id.edt_streamAmount_dialogIncomeStream);
        final TextInputEditText frequency = layout.findViewById(R.id.edt_streamFrequency_dialogIncomeStream);
        final TextInputEditText type = layout.findViewById(R.id.edt_streamType_dialogIncomeStream);
        final TextInputEditText info = layout.findViewById(R.id.edt_streamInfo_dialogIncomeStream);
        final TextInputLayout tName = layout.findViewById(R.id.text_streamName_dialogIncomeStream);
        final TextInputLayout tAmount = layout.findViewById(R.id.text_streamAmount_dialogIncomeStream);
        final TextInputLayout tFrequency = layout.findViewById(R.id.text_streamFrequency_dialogIncomeStream);
        final TextInputLayout tType = layout.findViewById(R.id.text_streamType_dialogIncomeStream);
        final TextInputLayout tInfo = layout.findViewById(R.id.text_streamInfo_dialogIncomeStream);
        name.setText(incomeStream.getName());
        amount.setText(incomeStream.getAmount() + "");
        frequency.setText(incomeStream.getFrequency() + "");
        type.setText(incomeStream.getStreamType() + "");
        info.setText(incomeStream.getStreamInfo());
        List<TextInputEditText> edits = new ArrayList<TextInputEditText>();
        edits.add(name);
        edits.add(amount);
        edits.add(frequency);
        edits.add(type);
        edits.add(info);
        List<TextInputLayout> layouts = new ArrayList<TextInputLayout>();
        layouts.add(tName);
        layouts.add(tAmount);
        layouts.add(tFrequency);
        layouts.add(tType);
        layouts.add(tInfo);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("Save",null);/* new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount) && CommonUtils.isNumber(tFrequency, frequency) && CommonUtils.isNumber(tType, type)) {
                    IncomeStream stream = new IncomeStream(UniqueIdGenerator.getId(getApplicationContext()), userId, name.getText().toString(),
                            Double.parseDouble(amount.getText().toString()), Integer.parseInt(frequency.getText().toString()),
                            info.getText().toString(), Integer.parseInt(type.getText().toString()));
                    if (PortfolioEditor.removeIncomeStream(filePath, incomeStream.getStreamId())) {
                        PortfolioEditor.addIncomeStream(filePath, stream);
                        updateUI();
                        dialog.dismiss();
                    }
                }
            }
        });*/
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUI();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (CommonUtils.checkEmptyInput(edits, layouts) && CommonUtils.isNumber(tAmount, amount) && CommonUtils.isNumber(tFrequency, frequency) && CommonUtils.isNumber(tType, type)) {
                            IncomeStream stream = new IncomeStream(UniqueIdGenerator.getId(getApplicationContext()), userId, name.getText().toString(),
                                    Double.parseDouble(amount.getText().toString()), Integer.parseInt(frequency.getText().toString()),
                                    info.getText().toString(), Integer.parseInt(type.getText().toString()));
                            if (PortfolioEditor.removeIncomeStream(filePath, incomeStream.getStreamId())) {
                                PortfolioEditor.addIncomeStream(filePath, stream);
                                updateUI();
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });
        dialog.show();
    }

    private void modifyDialog(String type, Integer id) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_modify, (ViewGroup) findViewById(R.id.root_dialogModify));
        final TextView edit = layout.findViewById(R.id.txt_edit_dialogModify);
        final TextView delete = layout.findViewById(R.id.txt_delete_dialogModify);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case "Investment":
                        editInvestment(id);
                        break;
                    case "Transaction":
                        editTransaction(id);
                        break;
                    case "IncomeStream":
                        editIncomeStream(id);
                        break;
                    default:
                        System.out.println("Default kaise" + "ModifyDialog");
                        break;
                }
                dialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSureDialog(type, id);
                dialog.dismiss();
            }
        });
    }

    private void areYouSureDialog(String type, Integer id) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this " + type)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (type) {
                            case "Investment":
                                if (deleteInvestment(id)) {
                                    break;
                                } else {
                                    CommonUtils.onFailureMessage(getApplicationContext(), "Some Error");
                                }
                                break;
                            case "Transaction":
                                if (deleteTransaction(id)) {
                                    break;
                                } else {
                                    CommonUtils.onFailureMessage(getApplicationContext(), "Some Error");
                                }
                                break;
                            case "IncomeStream":
                                if (deleteStream(id)) {
                                    break;
                                } else {
                                    CommonUtils.onFailureMessage(getApplicationContext(), "Some Error");
                                }
                                break;
                            default:
                                break;
                        }
                        updateUI();
                        CommonUtils.showLongToast(getApplicationContext(), type + " Deleted");
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private boolean deleteInvestment(Integer investmentId) {
        return PortfolioEditor.removeInvestment(filePath, investmentId);
    }

    private boolean deleteTransaction(Integer transactionId) {
        return PortfolioEditor.removeTransaction(filePath, transactionId);
    }

    private boolean deleteStream(Integer investmentId) {
        return PortfolioEditor.removeInvestment(filePath, investmentId);
    }

    private void setTotalInvestments(List<Investment> investments){
        double amount =0.0;
        if(investments != null && investments.size()>0) {
            for (Investment i : investments) {
                amount = amount + i.getPrincipalAmount();
            }
            PrefManager.getInstance(getApplicationContext()).setRetirementCorpusAmount(amount+"");
        }
    }
}
