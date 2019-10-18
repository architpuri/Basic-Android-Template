package in.themoneytree.ui.editinstrument;

import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;

public class EditInstrumentActivity extends BaseActivity {
    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_coming_soon;
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_PORTFOLIO;
    }
}
