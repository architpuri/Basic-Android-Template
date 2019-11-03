package in.themoneytree.data.api;

import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.data.model.expense.ExpenseListResponse;
import in.themoneytree.data.model.portfolio.PortfolioResponse;
import in.themoneytree.data.model.user.UserResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MoneyService {
    //User ----------------------------------------------------------------------------------
    @GET("api/users/{userName}/login/{password}")
    Call<GeneralResponse> isValidLogin(@Path("userName") String userName,
                                       @Path("password") String password);


    @FormUrlEncoded
    @POST("/api/users/register")
    Call<GeneralResponse> sendUserDetails(@Field("userName") String userName,
                                          @Field("encryptedPassword") byte[] encryptedPassword,
                                          @Field("fullName") String fullName,
                                          @Field("userType") int userType,
                                          @Field("mobileNumber") String mobileNumber);

    @GET("/api/users/{userId}")
    Call<UserResponse> getUserInfo(@Path("userId") Integer userId);

    @GET("/api/users/{userId}/userName/{askId}")
    Call<GeneralResponse> getUsernameById(@Path("userId") Integer userId,
                                          @Path("askId") Integer askId);


    @FormUrlEncoded
    @POST("/api/users/{userId}/password/change")
    Call<GeneralResponse> changePassword(@Path("userId") Integer userId,
                                         @Field("oldPassword") String oldPassword,
                                         @Field("newPassword") String newPassword);

    @FormUrlEncoded
    @POST("/api/users/password/reset")
    Call<GeneralResponse> resetPassword(@Field("userName") String userName);

    @Multipart
    @POST("/api/users/{userId}/image/bg/change")
    Call<GeneralResponse> sendBackgroundImage(@Path("userId") Integer userId,
                                              @Part MultipartBody.Part attachedMedia);

    @Multipart
    @POST("/api/users/{userId}/image/change")
    Call<GeneralResponse> sendProfileImage(@Path("userId") Integer userId,
                                           @Part MultipartBody.Part attachedMedia);


    //Common ----------------------------------------------------------------------------------
    @POST("/api/{userId}/notify/topic")
    @FormUrlEncoded
    Call<GeneralResponse> notifyUsers(@Path("userId") Integer userId,
                                      @Field("notificationTopic") String topic,
                                      @Field("notificationTitle") String title,
                                      @Field("notificationBody") String body);

    //Portfolio -------------------------------------------------------------------------------
    @GET("/api/{userId}/portfolios")
    Call<PortfolioResponse> getPortfolio(@Path("userId") Integer userId);

    @POST("/api/{userId}/portfolios/new")
    @FormUrlEncoded
    Call<GeneralResponse> sendScores(@Path("userId") Integer userId,
                                     @Field("riskScore") Integer riskScore,
                                     @Field("timeScore") Integer timeScore,
                                     @Field("retirementCorpusAmount") Double retirementCorpusAmount);

    //Expenses -------------------------------------------------------------------------------
    @GET("/api/{userId}/expenses")
    Call<ExpenseListResponse> getExpenditure(@Path("userId") Integer userId);

    @FormUrlEncoded
    @POST("/api/{userId}/expenses/new")
    Call<GeneralResponse> addNewExpense(@Path("userId") Integer userId,
                                          @Field("expenseName") String expenseName,
                                          @Field("expenseAmount") Double expenseAmount,
                                          @Field("paymentType") Integer paymentType,
                                          @Field("paymentSourceId") Integer paymentSourceId);

}
