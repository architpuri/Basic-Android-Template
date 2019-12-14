package in.themoneytree.ui.home.indices;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.themoneytree.R;
import in.themoneytree.data.model.stock.Stocks;

/**
 * Created By  Archit
 * on 11/19/2019
 * for TheMoneyTree
 */
public class IndicesAdapter extends RecyclerView.Adapter<IndicesAdapter.ViewHolder> {
    private List<Stocks> stocks;
    private Context context;

    public IndicesAdapter(Context context, List<Stocks> stocks) {
        this.stocks = stocks;
        this.context = context;
    }

    @NonNull
    @Override
    public IndicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndicesAdapter.ViewHolder holder, int position) {
        Stocks stock = stocks.get(position);
        holder.name.setText(stock.getStockName());
        holder.change.setText(stock.getPriceChange() + "");
        holder.value.setText(stock.getStockPrice() + "");
        holder.changePercent.setText(stock.getChangePercent() + " %");
        if (stock.getPriceChange() < 0.0) {
            holder.name.setTextColor(Color.parseColor("#EC3030"));
            holder.change.setTextColor(Color.parseColor("#EC3030"));
            holder.value.setTextColor(Color.parseColor("#EC3030"));
            holder.changePercent.setTextColor(Color.parseColor("#EC3030"));
        } else {
            holder.name.setTextColor(Color.parseColor("#8DCF5F"));
            holder.change.setTextColor(Color.parseColor("#8DCF5F"));
            holder.value.setTextColor(Color.parseColor("#8DCF5F"));
            holder.changePercent.setTextColor(Color.parseColor("#8DCF5F"));
        }
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, value, change, changePercent;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name_itemStockList);
            value = itemView.findViewById(R.id.txt_value_itemStockList);
            change = itemView.findViewById(R.id.txt_change_itemStockList);
            changePercent = itemView.findViewById(R.id.txt_changePercent_itemStockList);
        }
    }
}