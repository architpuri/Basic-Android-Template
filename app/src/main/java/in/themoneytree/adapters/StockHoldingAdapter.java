package in.themoneytree.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;

public class StockHoldingAdapter extends RecyclerView.Adapter<StockHoldingAdapter.ViewHolder> {
    private List<String> stocks;
    private Context context;
    private final ClickListener listener;

    public StockHoldingAdapter(List<String> stocks, Context context, ClickListener listener) {
        this.stocks = stocks;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock_holding, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String stockName=stocks.get(position);
        holder.txtStockSymbol.setText("S-"+stockName);
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgProfitLoss;
        public TextView txtStockSymbol,txtStockQuantity,txtDatePurchase;
        public ViewHolder(View itemView) {
            super(itemView);
            imgProfitLoss=itemView.findViewById(R.id.img_profitLoss_itemStockHolding);
            txtStockSymbol=itemView.findViewById(R.id.txt_stockSymbol_itemStockHolding);
            txtStockQuantity=itemView.findViewById(R.id.txt_quantity_itemStockHolding);
            txtDatePurchase=itemView.findViewById(R.id.txt_datePurchase_itemStockHolding);
        }
    }
}
