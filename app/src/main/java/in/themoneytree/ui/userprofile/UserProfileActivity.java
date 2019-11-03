package in.themoneytree.ui.userprofile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.ApiConstants;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.data.model.user.User;
import in.themoneytree.data.model.user.UserResponse;
import in.themoneytree.ui.base.BaseActivity;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.ui.home.HomeActivity;
import in.themoneytree.utils.CommonUtils;
import in.themoneytree.utils.LoadImageFile;
import in.themoneytree.utils.upload.Common;
import in.themoneytree.utils.upload.ImageActivity;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends BaseActivity {
    @BindView(R.id.img_userBackground_userProfile)
    ImageView imgUserBg;
    @BindView(R.id.img_user_userProfile)
    ImageView imgUserProfile;
    @BindView(R.id.txt_password_userProfile)
    TextView txtPassword;
    @BindView(R.id.edt_oldPassword_userProfile)
    TextInputEditText edtOldPassword;
    @BindView(R.id.edt_newPassword_userProfile)
    TextInputEditText edtNewPassword;
    @BindView(R.id.btn_changePassword_userProfile)
    Button btnChangePassword;
    @BindView(R.id.linearLayout_permissions_userProfile)
    LinearLayout layoutPermissions;
    @BindView(R.id.btn_grantedPermissions_userProfile)
    Button btnGrantedPermissions;
    @BindView(R.id.btn_addDetails_userProfile)
    Button btnAddDetails;
    @BindView(R.id.textLayout_oldPassword_userProfile)
    TextInputLayout layoutOldPassword;
    @BindView(R.id.textLayout_newPassword_userProfile)
    TextInputLayout layoutNewPassword;
    @BindView(R.id.btn_addPermissions_userProfile)
    Button btnAdminGrantPermissions;
    @BindView(R.id.fab_drawerOpen_userProfile)
    FloatingActionButton drawerOpen;
    @BindView(R.id.progressBar_userBackground_userProfile)
    ProgressBar pBarUserBg;
    @BindView(R.id.progressBar_user_userProfile)
    ProgressBar pBarUserProfile;
    @BindView(R.id.progressBar_btnChangePassword_userProfile)
    ProgressBar pBarChangePass;
    private DrawerLayout drawer;
    private Boolean isHidden = true;
    private MultipartBody.Part bgImgPart = null;
    private MultipartBody.Part profileImgPart = null;
    private UiConstants constants;
    private boolean isPermissionLayoutSet = false;
    private String modifiedPermissionWord = "";
    private String originalPermissionWord;
    private String TAG = "USER PROFILE ACTIVITY";
    private String checkedItems = "";
    private boolean isEnabled =true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        originalPermissionWord = PrefManager.getInstance(getApplicationContext()).getAccessToken();
        LoadImageFile.loadCircularTransformedImageFromUrl(getApplicationContext(),
                imgUserProfile,
                PrefManager.getInstance(getApplicationContext()).getUserImageUrl(), R.drawable.img_person);
        imgUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UserProfileActivity.this, ImageActivity.class), UiConstants.SELECT_PROFILE_IMAGE);
            }
        });
        LoadImageFile.loadImageFromUrl(getApplicationContext(),
                imgUserBg,
                PrefManager.getInstance(getApplicationContext()).getBackgroundImg(), R.drawable.img_placeholder);
        imgUserBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(UserProfileActivity.this, ImageActivity.class), UiConstants.SELECT_BACKGROUND_IMAGE);
            }
        });
        layoutOldPassword.setVisibility(View.GONE);
        layoutNewPassword.setVisibility(View.GONE);
        btnChangePassword.setVisibility(View.GONE);
        btnAdminGrantPermissions.setVisibility(View.GONE);
        txtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    isHidden = false;
                    txtPassword.setText("Password");
                    layoutOldPassword.setVisibility(View.VISIBLE);
                    layoutNewPassword.setVisibility(View.VISIBLE);
                    btnChangePassword.setVisibility(View.VISIBLE);
                } else {
                    txtPassword.setText("Change Password");
                    isHidden = true;
                    txtPassword.setBackground(getResources().getDrawable(R.drawable.btn_outline_rectangle));
                    layoutOldPassword.setVisibility(View.GONE);
                    edtOldPassword.setText("");
                    layoutNewPassword.setVisibility(View.GONE);
                    edtNewPassword.setText("");
                    btnChangePassword.setVisibility(View.GONE);
                }
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = edtOldPassword.getText().toString();
                String newPassword = edtNewPassword.getText().toString();
                if (oldPassword != null || newPassword != null || oldPassword != "" || newPassword != "") {
                    if(CommonUtils.isValidPassword(newPassword)){
                        sendPasswordChangeRequest(oldPassword, newPassword);
                    }else{
                        CommonUtils
                                .wrongInputErrorMsg(layoutNewPassword, "Password Must Be more than 6", edtNewPassword);
                    }
                } else {
                    CommonUtils.showToast(getApplicationContext(),"Please Enter Old and New Password First");
                }
            }
        });
        btnAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showToast(getApplicationContext(),"Details can Be Added from it");
            }
        });

    /*    btnGrantedPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPermissionLayoutSet) {
                    getAllPossiblePermissions();
                    btnGrantedPermissions.setText("Change Permissions");
                } else {
                    detectPermissionChange();
                    btnGrantedPermissions.setText(R.string.granted_permission);
                }
            }
        });
        *//*if (Permissions.isUserPermitted(getApplicationContext(), UiConstants.PERMISSIONS_ALL_ACCESS)) {
            btnAdminGrantPermissions.setVisibility(View.VISIBLE);
        } else {
            btnAdminGrantPermissions.setVisibility(View.GONE);
        }*//*
        btnAdminGrantPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*//*
                startActivity(new Intent(getApplicationContext(), PermissionGrantActivity.class));*//*
            }
        });
    */
    }

    @Override
    public String getCurrentTag() {
        return UiConstants.TAG_USER_PROFILE;
    }

    @Override
    public boolean getBottomNavigation() {
        return false;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_user_profile;
    }
/*
    private void detectPermissionChange() {
        String collegeId = PrefManager.getInstance(getApplicationContext()).getUserCollegeId();
        isPermissionLayoutSet = false;
        originalPermissionWord = PrefManager.getInstance(getApplicationContext()).getAccessToken();
        modifiedPermissionWord = "";
        int startIndex = 0;
        for (int i = 0; i < checkedItems.length(); i++) {
            Log.d(TAG, "Mzaa Permit WOrd");
            if (checkedItems.charAt(i) == ',') {
                String permit = checkedItems.substring(startIndex, i);
                Log.d(TAG, permit + "Permit WOrd");
                for (Permission p : permissions) {
                    if (p.getPermissionName().compareTo(permit) == 0) {
                        modifiedPermissionWord = modifiedPermissionWord + collegeId + p.getPermissionId() + ",";
                        break;
                    }
                }
                startIndex = i + 1;
            }
        }
        if (originalPermissionWord == null) {
            originalPermissionWord = "kuchbhi";
        }
        Log.d(TAG, modifiedPermissionWord + "WOrd");
        if (modifiedPermissionWord.compareTo(originalPermissionWord) != 0) {
            changeUserPermissionsAtServer();
        } else {
            CommonUtils.showToast(getApplicationContext(), "No Changes Made");
            layoutPermissions.setVisibility(View.GONE);
        }
    }

    private void changeUserPermissionsAtServer() {
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        String permissions = modifiedPermissionWord;
        byte[] encryptedPermissions = Encrypter.encryption(permissions);
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> changePermissionsRequest = moneyService.changeUserPermissions(userId, encryptedPermissions);
        changePermissionsRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.isSuccessful() || response.body().getStatusCode() == ApiConstants.SUCCESS) {
                        CommonUtils.showToast(getApplicationContext(), response.body().getMessage());
                    } else {
                        CommonUtils.showToast(getApplicationContext(), response.body().getMessage());
                    }
                } catch (Exception e) {
                    CommonUtils.exceptionHandling(TAG, e);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                CommonUtils.showToast(getApplicationContext(), "Failure");
            }
        });
    }

    private void setUpPermissionsLayout() {
        isPermissionLayoutSet = true;
        layoutPermissions.setVisibility(View.VISIBLE);
        Log.d(TAG, checkedItems);
        for (Permission p : permissions) {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(p.getPermissionName());
            if (originalPermissionWord == null) {
                originalPermissionWord = "kuchbhi";
            }
            if (originalPermissionWord.contains(p.getPermissionId() + "")) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
            layoutPermissions.addView(checkBox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        checkedItems = checkedItems + buttonView.getText().toString() + ",";
                    } else {
                        if (checkedItems.contains(buttonView.getText().toString())) {
                            String checkedItem = checkedItems.replace(buttonView.getText().toString() + ",", "");
                            checkedItems = checkedItem;
                        }
                    }
                }
            });
        }
    }

    private void getAllPossiblePermissions() {
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<PermissionListResponse> allPermissionsRequest = moneyService.getAllPermissions(userId);
        allPermissionsRequest.enqueue(new Callback<PermissionListResponse>() {
            @Override
            public void onResponse(Call<PermissionListResponse> call, Response<PermissionListResponse> response) {
                if (response.isSuccessful() || response.body().getGeneralResponse().getStatusCode() == ApiConstants.SUCCESS) {
                    permissions = response.body().getPermissions();
                    if (permissions != null) {
                        setUpPermissionsLayout();
                    }
                } else {
                    CommonUtils.showToast(getApplicationContext(), response.body().getGeneralResponse().getMessage());
                }
            }

            @Override
            public void onFailure(Call<PermissionListResponse> call, Throwable t) {
                CommonUtils.failureShow(TAG, getApplicationContext());
            }
        });
    }*/

    private void sendPasswordChangeRequest(String oldPassword, String newPassword) {
        pBarChangePass.setVisibility(View.VISIBLE);
        btnDisableEnable();
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> passwordChangeRequest = moneyService.changePassword(userId, oldPassword, newPassword);
        passwordChangeRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == ApiConstants.ACCEPTED) {
                        CommonUtils.showToast(getApplicationContext(), "Password Changed Successfully");
                        pBarChangePass.setVisibility(View.GONE);
                        btnDisableEnable();
                        startActivity(new Intent(UserProfileActivity.this, HomeActivity.class));
                    }else{
                        btnDisableEnable();
                        CommonUtils.showToast(getApplicationContext(),"Message"+response.body().getMessage());
                        pBarChangePass.setVisibility(View.GONE);
                    }
                }
                else{
                    btnDisableEnable();
                    CommonUtils.showToast(getApplicationContext(),"Not Successful");
                    pBarChangePass.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                CommonUtils.showToast(getApplicationContext(),"Failure");
                pBarChangePass.setVisibility(View.GONE);
                btnDisableEnable();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Activity Result");
        if (resultCode == RESULT_OK && requestCode == UiConstants.SELECT_BACKGROUND_IMAGE) {
            if (data.hasExtra("imagePath")) {
                Toast.makeText(this, data.getExtras().getString("imagePath"),
                        Toast.LENGTH_SHORT).show();
                pBarUserBg.setVisibility(View.VISIBLE);
                bgImgPart = Common.uploadFile(data.getExtras().getString("imagePath"),
                        UiConstants.FILETYPE_IMAGE);
                Log.d(TAG, "Activity Result-Back if inner");
                sendBackgroundImage();
            }
            Log.d(TAG, "Activity Result-Back if");
        } else if (resultCode == RESULT_OK && requestCode == UiConstants.SELECT_PROFILE_IMAGE) {
            if (data.hasExtra("imagePath")) {
                Toast.makeText(this, data.getExtras().getString("imagePath"),
                        Toast.LENGTH_SHORT).show();
                pBarUserProfile.setVisibility(View.VISIBLE);
                profileImgPart = Common.uploadFile(data.getExtras().getString("imagePath"),
                        UiConstants.FILETYPE_IMAGE);
                Log.d(TAG, "Activity Result-profile inner if");
                sendProfileImage();
            }
            Log.d(TAG, "Activity Result - profile if");
        } else {
            Log.d(TAG, "Else hai Activity Result bahr");
        }
        Log.d(TAG, "Activity Result bahr");
    }

    private void sendProfileImage() {
        pBarUserProfile.setVisibility(View.VISIBLE);
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> profileImageRequest = moneyService.sendProfileImage(userId, profileImgPart);
        profileImageRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                CommonUtils.showToast(getApplicationContext(), response.body().getMessage());
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == ApiConstants.SUCCESS) {
                        pBarUserProfile.setVisibility(View.GONE);
                        setUserDetails(getApplicationContext());
                    CommonUtils.showToast(getApplicationContext(),"Please Restart Application to See Changes");
                    }else{
                        CommonUtils.showToast(getApplicationContext(),"MESSAGE - "+response.body().getMessage());
                        pBarUserProfile.setVisibility(View.GONE);
                    }
                }else{
                    CommonUtils.showToast(getApplicationContext(),"Not Successful");
                    pBarUserProfile.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                CommonUtils.showToast(getApplicationContext(), "Failure");
                pBarUserProfile.setVisibility(View.GONE);
            }
        });
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
                            LoadImageFile.loadCircularTransformedImageFromUrl(getApplicationContext(),
                                    imgUserProfile,
                                    PrefManager.getInstance(getApplicationContext()).getUserImageUrl(), R.drawable.img_person);
                            LoadImageFile.loadImageFromUrl(getApplicationContext(),
                                    imgUserBg,
                                    PrefManager.getInstance(getApplicationContext()).getBackgroundImg(), R.drawable.img_placeholder);
                        } else {
                            CommonUtils.showToast(getApplicationContext(), "Please first Verify Using Email Wait for 10 mins atleast");
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

    private void sendBackgroundImage() {
        pBarUserBg.setVisibility(View.VISIBLE);
        Integer userId = Integer.parseInt(PrefManager.getInstance(getApplicationContext()).getUserId());
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> bgImageRequest = moneyService.sendBackgroundImage(userId, bgImgPart);
        bgImageRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                CommonUtils.showToast(getApplicationContext(), response.body().getMessage());
                if (response.isSuccessful()) {
                    if (response.body().getStatusCode() == ApiConstants.SUCCESS) {
                        pBarUserBg.setVisibility(View.GONE);
                        CommonUtils.showToast(getApplicationContext(),"Please Restart Application to See Changes");
                        setUserDetails(getApplicationContext());
                        LoadImageFile.loadImageFromUrl(getApplicationContext(),
                                imgUserBg,
                                PrefManager.getInstance(getApplicationContext()).getBackgroundImg(), R.drawable.img_placeholder);
                    }else{
                        pBarUserBg.setVisibility(View.GONE);
                        CommonUtils.showToast(getApplicationContext(),"Message"+response.body().getMessage());
                    }
                }
                else{
                    pBarUserBg.setVisibility(View.GONE);
                    CommonUtils.showToast(getApplicationContext(),"Not Successful");
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                CommonUtils.showToast(getApplicationContext(), "Failure");
                pBarUserBg.setVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.fab_drawerOpen_userProfile)
    public void onViewClicked() {
        drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    private void btnDisableEnable(){
        if(isEnabled){
            isEnabled=false;
            btnChangePassword.setEnabled(false);
        }else{
            isEnabled=true;
            btnChangePassword.setEnabled(true);
        }
    }
}
