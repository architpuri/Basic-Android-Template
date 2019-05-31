package in.themoneytree.ui.tax;

import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;

public class TaxActivity extends BaseActivity {
    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_tax;
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_TAX;
    }
}
