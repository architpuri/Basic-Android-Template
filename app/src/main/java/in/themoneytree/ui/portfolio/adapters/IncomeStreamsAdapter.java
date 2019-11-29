package in.themoneytree.ui.portfolio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.themoneytree.R;
import in.themoneytree.adapters.ClickListener;
import in.themoneytree.data.model.incomestreams.IncomeStream;

public class IncomeStreamsAdapter extends RecyclerView.Adapter<IncomeStreamsAdapter.ViewHolder> {
    private List<IncomeStream> sources;
    private Context context;
    private final ClickListener listener;

    public IncomeStreamsAdapter(List<IncomeStream> sources, Context context, ClickListener listener) {
        this.sources = sources;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IncomeStreamsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_income_sources, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeStreamsAdapter.ViewHolder holder, int position) {
        IncomeStream source=sources.get(position);
        holder.sourceName.setText(source.getName());
        holder.sourceAmount.setText(source.getAmount()+"");
        holder.sourceType.setText(source.getStreamInfo());
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView sourceName,sourceAmount,sourceType;
        public ViewHolder(View itemView) {
            super(itemView);
            sourceName=itemView.findViewById(R.id.txt_sourceName_itemIncomeSources);
            sourceAmount=itemView.findViewById(R.id.txt_sourceAmount_itemIncomeSources);
            sourceType=itemView.findViewById(R.id.txt_sourceType_itemIncomeSources);
        }
    }
}
