package in.themoneytree.ui.stocks;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;
import in.themoneytree.data.model.stock.Stocks;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.ViewHolder>  {
    private List<Stocks> stocks;
    private Context context;
    private final StockClickListener listener;

    public StockListAdapter(Context context, List<Stocks> stocks, StockClickListener listener) {
        this.stocks = stocks;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public StockListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stock_recommendation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockListAdapter.ViewHolder holder, int position) {
        Stocks stock=stocks.get(position);
        holder.stockName.setText(stock.getStockName());
        holder.stockCMP.setText(stock.getStockPrice()+"");

    }

    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView stockImage;
        public TextView stockName,stockSymbol,stockCMP,stockActionType,stockActionDuration;
        public Button stockNotify,stockActionDone;
        public ViewHolder(View itemView) {
            super(itemView);
            stockImage=itemView.findViewById(R.id.img_stock_itemStockRecommendation);
            stockSymbol=itemView.findViewById(R.id.txt_stockSymbol_itemStockRecommendation);
            stockName=itemView.findViewById(R.id.txt_stockName_itemStockRecommendation);
            stockCMP=itemView.findViewById(R.id.txt_currentPrice_itemStockRecommendation);
            stockActionType=itemView.findViewById(R.id.txt_actionType_itemStockRecommendation);
            stockActionDuration=itemView.findViewById(R.id.txt_actionDuration_itemStockRecommendation);
            stockNotify=itemView.findViewById(R.id.btn_notify_itemStockRecommendation);
            stockActionDone=itemView.findViewById(R.id.btn_actionDone_itemStockRecommendation);
        }
    }
}
