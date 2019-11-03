package in.themoneytree.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;

public class InvestmentInstrumentsAdapter extends RecyclerView.Adapter<InvestmentInstrumentsAdapter.ViewHolder> {
    private List<String> instruments;
    private Context context;
    private final ClickListener listener;
    private final String TAG="Investment Instru Adapter";

    public InvestmentInstrumentsAdapter(List<String> instruments, Context context, ClickListener listener) {
        this.instruments = instruments;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvestmentInstrumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_investment_intruments, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvestmentInstrumentsAdapter.ViewHolder holder, int position) {
        String name = instruments.get(position);
        holder.instrumentName.setText(name);
        holder.instrumentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemEdit(v,position,name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return instruments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView instrumentName, instrumentAmount;
        public Button instrumentDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            instrumentName = itemView.findViewById(R.id.txt_instrumentName_itemInstruments);
            instrumentAmount = itemView.findViewById(R.id.txt_instrumentAmount_itemInstruments);
            instrumentDetails = itemView.findViewById(R.id.btn_getDetails_itemInstruments);
            /*instrumentDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"CLICKED");
                }
            });*/
        }
    }
}
