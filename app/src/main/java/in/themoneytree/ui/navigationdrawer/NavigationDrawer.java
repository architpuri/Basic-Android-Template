package in.themoneytree.ui.navigationdrawer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.ApiConstants;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.user.User;
import in.themoneytree.data.model.user.UserResponse;
import in.themoneytree.ui.aboutus.AboutUsActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.ui.expenditure.ExpenditureActivity;
import in.themoneytree.ui.home.HomeActivity;
import in.themoneytree.ui.portfolio.PortfolioActivity;
import in.themoneytree.ui.privacypolicy.PrivacyPolicyActivity;
import in.themoneytree.ui.retirement.RetirementActivity;
import in.themoneytree.ui.splash.SplashActivity;
import in.themoneytree.ui.stocks.StocksActivity;
import in.themoneytree.ui.tax.TaxActivity;
import in.themoneytree.ui.userprofile.UserProfileActivity;
import in.themoneytree.utils.CommonUtils;
import in.themoneytree.utils.LoadImageFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NavigationDrawer extends AppCompatActivity {
    public static int navItemIndex = 0;
    public static String CURRENT_TAG;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName;
    private Handler mHandler;
    private Toolbar toolbar;
    private Button navigationDrawerBtn;
    private String userChoosenTask;
    private Bitmap bitmap;
    private Boolean isProfileClicked;
    private Intent intent;
    private static final String TAG = "NAVIGATION DRAWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer);
        //To load toolbar titles
        /*toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        mHandler = new Handler();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = navHeader.findViewById(R.id.text_name);

        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        imgNavHeaderBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*isProfileClicked = false;
                Bitmap bitmap = null;//pnga lia hai CommonUtils.addImageUsingAlertBuilder(getApplicationContext());
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                imgNavHeaderBg.setBackground(ob);
                File file =CommonUtils.fileFromBitmapBuilder(bitmap,getApplicationContext());
                //send this file to sever and return string url to save in shared preferences*/
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
            }
        });
        imgProfile = navHeader.findViewById(R.id.img_profile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*isProfileClicked = true;
                Bitmap bitmap = null;//pnga lia haiCommonUtils.addImageUsingAlertBuilder(getApplicationContext());
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                imgProfile.setBackground(ob);
                File file =CommonUtils.fileFromBitmapBuilder(bitmap,getApplicationContext());
                //send this file to sever and return string url to save in shared preferences*/
                startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
            }
        });
        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = getCurrentTag();
            //Load Home Activity
        }
    }

    private void setUpImgProfile() {
        LoadImageFile.loadCircularTransformedImageFromUrl(getApplicationContext(),
                imgProfile,
                PrefManager.getInstance(getApplicationContext()).getUserImageUrl(),
                0);
        Log.d(TAG, "msg" + PrefManager.getInstance(getApplicationContext()).getUserImageUrl());
    }

    private void setUpImgNavHeaderBg() {
        LoadImageFile.loadImageFromUrl(getApplicationContext(),
                imgNavHeaderBg,
                PrefManager.getInstance(getApplicationContext()).getBackgroundImg(),
                R.drawable.img_placeholder);
        Log.d(TAG, "MSG" + PrefManager.getInstance(getApplicationContext()).getBackgroundImg());
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "idhr bhi aaya3");
        //changeCurrentFocus();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.activity_drawer_menu, menu);
        return true;*/
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void setUpNavigationView() {
        changeCurrentFocus();
        setUserDetails(getApplicationContext());
        loadNavHeader();
        setUpNavigationViewBody();
    }

    private void loadNavHeader() {
        setUpImgNavHeaderBg();
        setUpImgProfile();
        txtName.setText(PrefManager.getInstance(getApplicationContext()).getUserDisplayName());
    }

    private void setUpNavigationViewBody() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home: {
                        if (getCurrentTag() == UiConstants.TAG_HOME) {
                        } else {
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        break;
                    }
                    case R.id.nav_portfolio: {
                        if (getCurrentTag() == UiConstants.TAG_EXPENDITURE) {
                        } else {
                            startActivity(new Intent(getApplicationContext(), PortfolioActivity.class));
                        }
                        break;
                    }
                    case R.id.nav_expenditure: {
                        if (getCurrentTag() == UiConstants.TAG_EXPENDITURE) {
                        } else {
                            startActivity(new Intent(getApplicationContext(), ExpenditureActivity.class));
                        }
                        break;
                    }
                    case R.id.nav_tax: {
                        if (getCurrentTag() == UiConstants.TAG_TAX) {
                        } else {
                            startActivity(new Intent(getApplicationContext(), TaxActivity.class));
                        }
                        break;
                    }
                    case R.id.nav_stocks: {
                        if (getCurrentTag() == UiConstants.TAG_STOCKS) {
                        } else {
                            startActivity(new Intent(getApplicationContext(), StocksActivity.class));
                        }
                        break;
                    }
                    case R.id.nav_retirement_corpus: {
                        if (getCurrentTag() == UiConstants.TAG_RETIREMENT_CORPUS) {
                        } else {
                            startActivity(new Intent(getApplicationContext(), RetirementActivity.class));
                        }
                        break;
                    }
                    case R.id.nav_sign_out: {
                        PrefManager.getInstance(getApplicationContext())
                                .logoutUser();
                        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                        finish();
                    }
                    break;
                    case R.id.nav_about_us:
                        if (getCurrentTag() == UiConstants.TAG_ABOUT_US) {
                            break;
                        } else {
                            startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        }
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        if (getCurrentTag() == UiConstants.TAG_PRIVACY_POLICY) {
                            break;
                        } else {
                            startActivity(new Intent(getApplicationContext(), PrivacyPolicyActivity.class));
                        }
                        drawer.closeDrawers();
                        return true;
                    default: {
                    }
                    drawer.closeDrawers();
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                /*if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }*/
                changeCurrentFocus();
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                changeCurrentFocus();
                super.onDrawerOpened(drawerView);
            }
        };

        actionBarDrawerToggle.syncState();
    }

    public abstract String getCurrentTag();

    public void changeCurrentFocus() {
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        int currentIndex = 0;
        switch (getCurrentTag()) {
            case UiConstants.TAG_HOME: {
                currentIndex = 0;
                break;
            }
            case UiConstants.TAG_PORTFOLIO: {
                currentIndex = 1;
                break;
            }
            case UiConstants.TAG_EXPENDITURE: {
                currentIndex = 2;
                break;
            }
            case UiConstants.TAG_TAX: {
                currentIndex = 3;
                break;
            }
            case UiConstants.TAG_STOCKS: {
                currentIndex = 4;
                break;
            }
            case UiConstants.TAG_SIGN_OUT: {
                currentIndex = 5;
                break;
            }
            case UiConstants.TAG_ABOUT_US: {
                currentIndex = 6;
                break;
            }
            case UiConstants.TAG_PRIVACY_POLICY: {
                currentIndex = 6;
                break;
            }
            default: {
                Log.d(TAG, "TAG Default");
                currentIndex = -1;
            }
        }
        ;
        if (currentIndex >= 0)
            navigationView.getMenu().getItem(currentIndex).setChecked(true);
    }


    private void setUserDetails(Context context) {
        MoneyService moneyService = ApiClient.getInstance();
        Integer userId = Integer.parseInt(PrefManager.getInstance(context).getUserId());
        Call<UserResponse> getUserRequest = moneyService.getUserInfo(userId);
        getUserRequest.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == ApiConstants.SUCCESS) {
                    try {
                        User user = response.body().getuser();
                        Log.d(TAG, user.isUserEnabled() + "Enable");
                        if (user.isUserEnabled()) {
                            PrefManager.getInstance(context).setUserEmail(user.getUserName());
                            PrefManager.getInstance(context).setUserName(user.getFullName());
                            PrefManager.getInstance(context).setUserContact(user.getMobileNumber());
                            PrefManager.getInstance(context).setUserImageUrl(user.getUserImageUrl());
                            PrefManager.getInstance(context).setBackgroundImg(user.getUserBackgroundImageUrl());
                            PrefManager.getInstance(context).setUserType(user.getUserType() + "");
                            PrefManager.getInstance(context).setAccessToken(user.getUserAccessToken());
                        } else {
                            CommonUtils.showToast(getApplicationContext(), "Error in Fetching Image");
                        }
                    } catch (Exception e) {
                        CommonUtils.exceptionHandling(TAG, e);
                    }
                } else {

                    CommonUtils.showToast(getApplicationContext(), "MESSAGE" + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                CommonUtils.failureShow(TAG, getApplicationContext());
            }
        });
    }
}
