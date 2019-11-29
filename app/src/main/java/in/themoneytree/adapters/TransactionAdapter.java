package in.themoneytree.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.themoneytree.R;
import in.themoneytree.data.model.transaction.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> bonds;
    private Context context;
    private final ClickListener listener;

    public TransactionAdapter(List<Transaction> bonds, Context context, ClickListener listener) {
        this.bonds = bonds;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fd_holding, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        String bondCode = bonds.get(position).toString();
        holder.txtFdCode.setText("FD-" + bondCode);
    }

    @Override
    public int getItemCount() {
        return bonds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBankLogo;
        public TextView txtFdCode, txtFdAmount, txtExpiryDate;

        public ViewHolder(View itemView) {
            super(itemView);
            imgBankLogo = itemView.findViewById(R.id.img_bankLogo_itemFdHolding);
            txtFdCode = itemView.findViewById(R.id.txt_fdCode_itemFdHolding);
            txtFdAmount = itemView.findViewById(R.id.txt_fdAmount_itemFdHolding);
            txtExpiryDate = itemView.findViewById(R.id.txt_dateExpiry_itemFdHolding);
        }
    }
}
