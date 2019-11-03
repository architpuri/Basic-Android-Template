package in.themoneytree.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
    private List<String> property;
    private Context context;
    private final ClickListener listener;

    public PropertyAdapter(List<String> property, Context context, ClickListener listener) {
        this.property = property;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PropertyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_property, parent, false);
        return new PropertyAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyAdapter.ViewHolder holder, int position) {
        String name=property.get(position);
        holder.txtLocation.setText(name);
    }

    @Override
    public int getItemCount() {
        return property.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLocation,txtArea,txtInvestmentAmount;
        public ViewHolder(View itemView) {
            super(itemView);
            txtLocation=itemView.findViewById(R.id.txt_location_itemProperty);
            txtArea=itemView.findViewById(R.id.txt_area_itemProperty);
            txtInvestmentAmount=itemView.findViewById(R.id.txt_investmentAmount_itemProperty);
        }
    }
}
