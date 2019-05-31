package in.themoneytree.ui.riskprofile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.ui.home.HomeActivity;
import in.themoneytree.utils.CommonUtils;

public class RiskAnalyzerActivity extends AppCompatActivity {
    @BindView(R.id.recycler_question_riskAnalyzer)
    RecyclerView recyclerQuestion;
    @BindView(R.id.btn_score_riskAnalyzer)
    Button btnScore;
    @BindView(R.id.btn_skip_riskAnalyzer)
    Button btnSkip;
    private RiskQuestionAdapter questionAdapter;
    private static final String TAG = "RISK ANALYZER ACTIVITY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_analyzer);
        ButterKnife.bind(this);
        setRecyclerView();
        btnScore.setEnabled(true);
        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFinalRiskProfile(getSurveyResults());
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                finish();
            }
        });
    }

    private void showFinalRiskProfile(double riskScore) {
        if(riskScore==0.0){
            //doNothing
            return;
        }
        btnScore.setText("Your Score is "+riskScore);
        btnSkip.setEnabled(true);
        btnSkip.setText("Click here to get Recommended Stocks");
    }

    private double getSurveyResults() {
        int items=questionAdapter.getItemCount();
        double riskScore=1;//More the score greater the risk
        for (int i = 0; i < items; i++) {
            View view = recyclerQuestion.getChildAt(i);
            EditText answerEditText = view.findViewById(R.id.edt_userAnswer_itemQuestionList);
            if(answerEditText.getText()==null){
                CommonUtils.showToast(getApplicationContext(),"Please fill All Details");
                return 0.0;
            }if(answerEditText.getText().toString().matches("")){
                CommonUtils.showToast(getApplicationContext(),"Please fill All Details");
                return 0.0;
            }
            String answerValue = answerEditText.getText().toString();
            int value=1;
            try{
                value=Integer.parseInt(answerValue);
            }catch(Exception e){
                Log.d(TAG,"Incompatible value passed Exception in EditText");
            }
            double weight=1;
            switch (i){
                case 0:{
                    weight=0.3;
                    break;}
                case 1:{
                    weight=5;
                    break;}
                case 2:{
                    weight=0.5;
                    break;}
                case 3:{
                    weight=0.2;
                    break;}
                case 4:{break;}
                case 5:{break;}
                default:{
                }
            }
            riskScore=riskScore+value*weight;
        }
        return riskScore;
    }


    void setRecyclerView() {
        recyclerQuestion.setVisibility(View.VISIBLE);
        questionAdapter = new RiskQuestionAdapter(getApplicationContext(), getQuestionList(), new ClickListener() {
            @Override
            public void questionDescription(View v, int position) {
                CommonUtils.showToast(getApplicationContext(), "Question Description");
            }
        });
        questionAdapter.notifyDataSetChanged();
        Log.d(TAG, "IDhr bhi aa lia 2");
        if (questionAdapter == null) {
            Log.d(TAG, "Null aa raa");
        }
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.recyler_divider));
        recyclerQuestion.addItemDecoration(itemDecorator);
        recyclerQuestion.setAdapter(questionAdapter);
    }

    private List<String> getQuestionList() {
        List<String> questions = new ArrayList<>();
        questions.add("What is your Current Age?");
        questions.add("What is your Current Monthly Salary?");
        questions.add("What is your Current Monthy Expenditure?");
        questions.add("How many people are financially Dependent on you?");
        return questions;
    }


}
