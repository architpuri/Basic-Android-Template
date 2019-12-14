package in.themoneytree.ui.privacypolicy;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.themoneytree.R;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;

public class PrivacyPolicyActivity extends BaseActivity {
    @BindView(R.id.fab_drawerOpen_privacyPolicy)
    FloatingActionButton drawerOpen;
    @BindView(R.id.txt_info_privacyPolicy)
    TextView txtInfo;
    @BindView(R.id.txt_title_privacyPolicy)
    TextView txtTitlePrivacyPolicy;
    @BindView(R.id.txt_link_privacyPolicy)
    TextView txtLink;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setUpPolicyTextView();
        drawerOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        txtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://sites.google.com/view/money-tree-tech/privacy-policy";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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

    private Spanned getSpannedText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(text);
        }
    }

    private void setUpPolicyTextView() {
        String s = getApplicationContext().getResources().getString(R.string.privacy_policy);
        txtInfo.setText(getSpannedText(s));

    }

}
