package in.themoneytree.ui.expenditure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.themoneytree.R;
import in.themoneytree.data.model.expense.Expense;

/**
 * Created By  Archit
 * on 10/19/2019
 * for TheMoneyTree
 */
public class ExpenditureListAdapter extends RecyclerView.Adapter<ExpenditureListAdapter.ViewHolder> {
    private List<Expense> expenses;
    private Context context;

    public ExpenditureListAdapter(Context context, List<Expense> expenses) {
        this.expenses = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenditureListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expenditure, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureListAdapter.ViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.expenseName.setText(expense.getExpenseName());
        holder.expenseAmount.setText(expense.getExpenseAmount());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView expenseName, expenseAmount;

        public ViewHolder(View itemView) {
            super(itemView);
            expenseName = itemView.findViewById(R.id.txt_expenditureName_itemExpenditure);
            expenseAmount = itemView.findViewById(R.id.txt_expenditureAmount_itemExpenditure);
        }
    }
}

