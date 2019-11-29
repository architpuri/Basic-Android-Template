package in.themoneytree.ui.home.stocktape;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.themoneytree.R;
import in.themoneytree.data.model.stock.Stock;

/**
 * Created By  Archit
 * on 11/15/2019
 * for TheMoneyTree
 */
public class StockScrollAdapter extends RecyclerView.Adapter<StockScrollAdapter.ViewHolder> {
    private List<Stock> stocks;
    private Context context;
    private final StockScrollListener listener;

    public StockScrollAdapter(Context context, List<Stock> stocks, StockScrollListener listener) {
        this.stocks = stocks;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public StockScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scroll_stock, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockScrollAdapter.ViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.stockName.setText(stock.getStockName());
        holder.stockChange.setText(stock.getPriceChange() + "");
        holder.stockCMP.setText(stock.getStockPrice() + "");
        if (stock.getPriceChange() < 0.0) {
            holder.stockTrend.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_down_red_24dp));
            holder.stockName.setTextColor(Color.parseColor("#EC3030"));
            holder.stockChange.setTextColor(Color.parseColor("#EC3030"));
            holder.stockCMP.setTextColor(Color.parseColor("#EC3030"));
        } else {
            holder.stockTrend.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_arrow_drop_up_green_24dp));
            holder.stockName.setTextColor(Color.parseColor("#8DCF5F"));
            holder.stockChange.setTextColor(Color.parseColor("#8DCF5F"));
            holder.stockCMP.setTextColor(Color.parseColor("#8DCF5F"));
        }
    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView stockTrend;
        public TextView stockName, stockCMP, stockChange;

        public ViewHolder(View itemView) {
            super(itemView);
            stockTrend = itemView.findViewById(R.id.img_trend_scrollStock);
            stockName = itemView.findViewById(R.id.txt_stockName_scrollStock);
            stockCMP = itemView.findViewById(R.id.txt_currentPrice_scrollStock);
            stockChange = itemView.findViewById(R.id.txt_change_scrollStock);
        }
    }
}
