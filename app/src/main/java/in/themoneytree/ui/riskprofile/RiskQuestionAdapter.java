package in.themoneytree.ui.riskprofile;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;

public class RiskQuestionAdapter extends RecyclerView.Adapter<RiskQuestionAdapter.ViewHolder> {
    private List<String> questions;
    private Context context;
    private final ClickListener listener;

    public RiskQuestionAdapter(Context context, List<String> questions, ClickListener listener) {
        this.questions = questions;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public RiskQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RiskQuestionAdapter.ViewHolder holder, int position) {
        String question=questions.get(position);
        holder.questionRiskProfile.setText(question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView questionRiskProfile;
        public EditText userAnswerRiskProfile;
        public ViewHolder(View itemView) {
            super(itemView);
            questionRiskProfile=itemView.findViewById(R.id.txt_question_itemQuestionList);
            userAnswerRiskProfile=itemView.findViewById(R.id.edt_userAnswer_itemQuestionList);
            questionRiskProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.questionDescription(v,getAdapterPosition());
                }
            });
        }
    }
}
