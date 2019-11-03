package in.themoneytree.ui.common;

import android.content.Context;

import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.user.User;
import in.themoneytree.data.model.user.UserResponse;
import in.themoneytree.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By  Archit
 * on 11/1/2019
 * for TheMoneyTree
 */
public class CommonFunctions {

    public static void refreshUserDetails(Context context) {
        MoneyService moneyService = ApiClient.getInstance();
        Integer userId = Integer.parseInt(PrefManager.getInstance(context).getUserId());
        Call<UserResponse> refreshUser = moneyService.getUserInfo(userId);
        refreshUser.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getGeneralResponse().getStatusCode() == 200) {
                        setUpUser(response.body().getuser(), context);
                    } else {
                        CommonUtils.showLongToast(context, response.body().getGeneralResponse().getMessage());
                    }
                } else {
                    CommonUtils.showLongToast(context, response.message());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                CommonUtils.onFailureMessage(context, "Unable To Refresh User Details");
            }
        });
    }

    static void setUpUser(User user, Context context) {
        PrefManager pm = PrefManager.getInstance(context);
        pm.setUserId(user.getUserId() + "");
        pm.setPortfolioId(user.getPortfolioId() + "");
        pm.setUserEmail(user.getUserName());
        pm.setUserPassword(user.getPassword());
        pm.setUserType(user.getUserType() + "");
        pm.setUserName(user.getFullName());
        pm.setUserImageUrl(user.getUserImageUrl());
        pm.setUserContact(user.getMobileNumber());
        pm.setBackgroundImg(user.getUserBackgroundImageUrl());
        pm.setFirstTimeUser(user.isUserEnabled());
        pm.setAccessToken(user.getUserAccessToken());
    }
}
