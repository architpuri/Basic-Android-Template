package in.themoneytree.ui.aboutus;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.fab_drawerOpen_aboutUs)
    FloatingActionButton drawerOpen;
    @BindView(R.id.txt_title_aboutUs)
    TextView txtTitle;
    @BindView(R.id.txt_info_aboutUs)
    TextView txtInfo;
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
        setUpPolicyTextView();
    }


    private Spanned getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(text);
        }
    }
    private void setUpPolicyTextView() {
        String s = getApplicationContext().getResources().getString(R.string.about_us);
        txtInfo.setText(getSpannedText(s));
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_ABOUT_US;
    }

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_about_us;
    }
}

