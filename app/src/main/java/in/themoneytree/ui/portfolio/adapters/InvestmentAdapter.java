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
import in.themoneytree.data.model.investments.Investment;
import in.themoneytree.ui.portfolio.PortfolioActivity;

public class InvestmentAdapter extends RecyclerView.Adapter<InvestmentAdapter.ViewHolder> {
    private List<Investment> investments;
    private Context context;
    private final ClickListener listener;

    public InvestmentAdapter(List<Investment> investments, Context context, ClickListener listener) {
        this.investments = investments;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvestmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_investments, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvestmentAdapter.ViewHolder holder, int position) {
        Investment investment=investments.get(position);
        holder.investmentName.setText(investment.getInvestmentName());
        holder.investmentAmount.setText(investment.getPrincipalAmount()+"");
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.itemEdit(v,position,investment.getInvestmentName());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return investments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView investmentName,investmentAmount;
        public ViewHolder(View itemView) {
            super(itemView);
            investmentName=itemView.findViewById(R.id.txt_name_itemInvestment);
            investmentAmount=itemView.findViewById(R.id.txt_amount_itemInvestment);
        }
    }
}
