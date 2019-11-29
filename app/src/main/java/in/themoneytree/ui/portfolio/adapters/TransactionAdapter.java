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
import in.themoneytree.data.model.transaction.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;
    private Context context;
    private final ClickListener listener;

    public TransactionAdapter(List<Transaction> transactions, Context context, ClickListener listener) {
        this.transactions = transactions;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        Transaction transaction=transactions.get(position);
        holder.transactionName.setText(transaction.getTransactionName());
        holder.transactionAmount.setText(transaction.getTransactionAmount()+"");
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView transactionName,transactionAmount;
        public ViewHolder(View itemView) {
            super(itemView);
            transactionName = itemView.findViewById(R.id.txt_name_itemTransaction);
            transactionAmount = itemView.findViewById(R.id.txt_amount_itemTransaction);
        }
    }
}
