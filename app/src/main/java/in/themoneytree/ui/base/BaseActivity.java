package in.themoneytree.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import in.themoneytree.R;
import in.themoneytree.ui.navigationdrawer.NavigationDrawer;

public abstract class BaseActivity extends NavigationDrawer {
    private static final String TAG="BASE ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.activity_frame);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = inflater.inflate(getLayout(), null, false);
        frameLayout.addView(activityView);
        if (getBottomNavigation()) {
            //Add bottomNavigation
        }

    }

    public abstract boolean getBottomNavigation();

    public abstract int getLayout();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
