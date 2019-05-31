package in.themoneytree.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;

public class OtherInstrumentsAdapter extends RecyclerView.Adapter<OtherInstrumentsAdapter.ViewHolder> {
    private List<String> instruments;
    private Context context;
    private final ClickListener listener;

    public OtherInstrumentsAdapter(List<String> instruments, Context context, ClickListener listener) {
        this.instruments=instruments;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OtherInstrumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_other_instruments, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherInstrumentsAdapter.ViewHolder holder, int position) {
    String name=instruments.get(position);
    holder.instrumentName.setText(name);
    }

    @Override
    public int getItemCount() {
        return instruments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView instrumentName,investmentAmount,investmentGrowth;
        public ViewHolder(View itemView) {
            super(itemView);
            instrumentName=itemView.findViewById(R.id.txt_instrumentName_itemOtherInstruments);
            investmentAmount=itemView.findViewById(R.id.txt_investmentAmount_itemOtherInstruments);
            investmentGrowth=itemView.findViewById(R.id.txt_investmentGrowth_itemOtherInstruments);
        }
    }
}
