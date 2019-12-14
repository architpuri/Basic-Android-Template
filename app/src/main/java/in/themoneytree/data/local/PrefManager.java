package in.themoneytree.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class PrefManager {
    private final static String PREFERENCE_NAME = "com.themoneytree.com";
    private static String TAG = "Pref Manager Java";
    private static SharedPreferences sharedPreferences;
    private static PrefManager prefManager;
    private final String FIRST_TIME_USER = "com.themoneytree.first_time_user";
    private final String ACCESS_TOKEN = "com.themoneytree.access_token";
    private final String USER_ID = "com.themoneytree.user_id";
    private final String PORTFOLIO_ID = "com.themoneytree.portfolio_id";
    private final String USER_EMAIL = "com.themoneytree.user_email";
    private final String USER_PASSWORD = "com.themoneytree.user_password";
    private final String USER_IMAGE_URL = "com.themoneytree.user_image_url";
    private final String USER_DISPLAY_NAME = "com.themoneytree.user_display_name";
    private final String USER_CONTACT_NO = "com.themoneytree.user_contact_no";
    private final String USER_BACKGROUND_IMG_URL = "com.themoneytree.user_background_img_url";
    private final String USER_TYPE = "com.themoneytree.user_type";
    private final String UNIQUE_IDENTITY = "com.themoneytree.unique_identity";
    private final String TAX_AMOUNT = "com.themoneytree.tax_amount";
    private final String RETIREMENT_CORPUS_AMOUNT = "com.themoneytree.retirement_corpus_amount";
    private final String RETIREMENT_CORPUS_GOAL = "com.themoneytree.retirement_corpus_goal";

    private PrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static PrefManager getInstance(Context context) {
        if (prefManager == null) {
            prefManager = new PrefManager(context);
        }
        return prefManager;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public String getUserImageUrl() {
        return sharedPreferences.getString(USER_IMAGE_URL, null);
    }

    public void setUserImageUrl(@Nullable String imgUrl) {
        if (imgUrl != null) sharedPreferences.edit().putString(USER_IMAGE_URL, imgUrl).apply();
    }

    public String getIdentity() {
        return sharedPreferences.getString(UNIQUE_IDENTITY, "-1");
    }

    public void setIdentity(String id) {
        if (id != null) sharedPreferences.edit().putString(UNIQUE_IDENTITY, id).apply();
    }


    public void setUserName(String userDisplayName) {
        sharedPreferences.edit().putString(USER_DISPLAY_NAME, userDisplayName).apply();
    }

    public String getUserDisplayName() {
        return sharedPreferences.getString(USER_DISPLAY_NAME, null);
    }


    public void setUserContact(String userPhoneNo) {
        sharedPreferences.edit().putString(USER_CONTACT_NO, userPhoneNo).apply();
    }

    public String getUserContactNo() {
        return sharedPreferences.getString(USER_CONTACT_NO, null);
    }

    public boolean isFirstTimeUser() {
        boolean value = sharedPreferences.getBoolean(FIRST_TIME_USER, false);
        return value;
    }

    public void setFirstTimeUser(boolean value) {
        sharedPreferences.edit().putBoolean(FIRST_TIME_USER, value).apply();
    }

    public String getAccessToken() {
        return sharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "");
    }

    public void setUserId(String userId) {
        sharedPreferences.edit().putString(USER_ID, userId).apply();
    }

    public String getPortfolioId() {
        return sharedPreferences.getString(PORTFOLIO_ID, "-1");
    }

    public void setPortfolioId(String portfolioId) {
        sharedPreferences.edit().putString(PORTFOLIO_ID, portfolioId).apply();
    }

    public String getUserEmail() {
        return sharedPreferences.getString(USER_EMAIL, null);
    }

    public void setUserEmail(String email) {
        sharedPreferences.edit().putString(USER_EMAIL, email).apply();
    }

    public String getUserPassword() {
        return sharedPreferences.getString(USER_PASSWORD, null);
    }

    public void setUserPassword(String password) {
        sharedPreferences.edit().putString(USER_PASSWORD, password).apply();
    }

    public void logoutUser() {
        sharedPreferences.edit().clear().apply();
    }

    public boolean isLoggedin() {
        return getAccessToken() != null;
    }

    public String getBackgroundImg() {
        return sharedPreferences.getString(USER_BACKGROUND_IMG_URL, null);
    }

    public void setBackgroundImg(String imgUrl) {
        sharedPreferences.edit().putString(USER_BACKGROUND_IMG_URL, imgUrl).apply();
    }

    public String getUserType() {
        return sharedPreferences.getString(USER_TYPE, null);
    }

    public void setUserType(String userType) {
        sharedPreferences.edit().putString(USER_TYPE, userType).apply();
    }

    public String getTaxAmount() {
        return sharedPreferences.getString(TAX_AMOUNT, null);
    }

    public void setTaxAmount(String taxAmount) {
        sharedPreferences.edit().putString(TAX_AMOUNT, taxAmount).apply();
    }

    public String getRetirementCorpusAmount() {
        return sharedPreferences.getString(RETIREMENT_CORPUS_AMOUNT, null);
    }

    public void setRetirementCorpusAmount(String corpusAmount) {
        sharedPreferences.edit().putString(RETIREMENT_CORPUS_AMOUNT, corpusAmount).apply();
    }

    public String getRetirementCorpusGoal() {
        return sharedPreferences.getString(RETIREMENT_CORPUS_GOAL, null);
    }

    public void setRetirementCorpusGoal(String corpusGoal) {
        sharedPreferences.edit().putString(RETIREMENT_CORPUS_GOAL, corpusGoal).apply();
    }
}
