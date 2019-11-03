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

public class MetalsAdapter extends RecyclerView.Adapter<MetalsAdapter.ViewHolder> {
    private List<String> metals;
    private Context context;
    private final ClickListener listener;

    public MetalsAdapter(List<String> metals, Context context, ClickListener listener) {
        this.metals = metals;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MetalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_metals, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MetalsAdapter.ViewHolder holder, int position) {
        String name=metals.get(position);
        holder.txtPurity.setText("S-"+name);
    }

    @Override
    public int getItemCount() {
        return metals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgMetalSymbol;
        public TextView txtPurity,txtWeightGrams;
        public ViewHolder(View itemView) {
            super(itemView);
            imgMetalSymbol=itemView.findViewById(R.id.img_metalSymbol_itemMetals);
            txtPurity=itemView.findViewById(R.id.txt_purity_itemMetals);
            txtWeightGrams=itemView.findViewById(R.id.txt_weightGram_itemMetals);
        }
    }
}
