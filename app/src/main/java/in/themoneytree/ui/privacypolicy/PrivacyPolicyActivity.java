package in.themoneytree.ui.privacypolicy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;

public class PrivacyPolicyActivity extends BaseActivity {
    @BindView(R.id.fab_drawerOpen_privacyPolicy)
    FloatingActionButton drawerOpen;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        drawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_PRIVACY_POLICY;
    }

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_privacy_policy;
    }

}
