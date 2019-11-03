package in.themoneytree.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;

public class IncomeSourcesAdapter extends RecyclerView.Adapter<IncomeSourcesAdapter.ViewHolder> {
    private List<String> sources;
    private Context context;
    private final ClickListener listener;

    public IncomeSourcesAdapter(List<String> sources, Context context, ClickListener listener) {
        this.sources = sources;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IncomeSourcesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_income_sources, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeSourcesAdapter.ViewHolder holder, int position) {
        String sourceName=sources.get(position);
        holder.sourceName.setText("S-"+sourceName);
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
