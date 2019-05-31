package in.themoneytree.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;

public class LiquidFormAdapter extends RecyclerView.Adapter<LiquidFormAdapter.ViewHolder> {
    private List<String> liquids;
    private Context context;
    private final ClickListener listener;

    public LiquidFormAdapter(List<String> liquids, Context context, ClickListener listener) {
        this.liquids = liquids;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LiquidFormAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_liquid_form, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LiquidFormAdapter.ViewHolder holder, int position) {
        String accountNo=liquids.get(position);
        holder.txtBankAccount.setText("S-"+accountNo);
    }

    @Override
    public int getItemCount() {
        return liquids.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgBankLogo;
        public TextView txtBankAccount,txtBankBalance;
        public ViewHolder(View itemView) {
            super(itemView);
            imgBankLogo=itemView.findViewById(R.id.img_bankLogo_itemLiquidForm);
            txtBankAccount=itemView.findViewById(R.id.txt_bankAccount_itemLiquidForm);
            txtBankBalance=itemView.findViewById(R.id.txt_bankBalance_itemLiquidForm);
        }
    }
}
