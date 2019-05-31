package in.themoneytree.ui.expenditure;

import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;

public class ExpenditureActivity extends BaseActivity {
    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_expenditure;
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_EXPENDITURE;
    }
}
