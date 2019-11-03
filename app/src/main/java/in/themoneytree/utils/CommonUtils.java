package in.themoneytree.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.ApiConstants;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.local.PrefManager;
import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.data.model.user.User;
import in.themoneytree.data.model.user.UserResponse;
import in.themoneytree.ui.common.UiConstants;
import in.themoneytree.utils.upload.Common;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

public class CommonUtils {
    public static int fileTypeSelection = 0;
    private static String timeAndDate;
    private static String timeAndDateFormat;
    private static String TAG = "CommonUtils JAVA";
    private static String message = "Not Set";
    //To Validate EmailId & Password
    private static String patternReqd;
    private static String userName;

    //To Check Internet Connectivity
    public static boolean checkInternetConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    //Normal Check Constraints - ID
    public static boolean isValidEmail(String input) {
        patternReqd = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        /*||input.matches(patternReqd2))*/
        return input.matches(patternReqd)/*||input.matches(patternReqd2))*/ && input.length() > 0;
    }

    //Normal Check Constraints - PASSWORD
    public static boolean isValidPassword(String input) {
        return input.length() > 4;
    }

    //To display input error msgs
    //noInputErrorMsg(layoutidObj type TextInputLayout,String to Display,Focus EditText);
    public static void wrongInputErrorMsg(final TextInputLayout textInputLayout,
                                          String display, TextInputEditText editText) {
        textInputLayout.setError(display);
        Log.d("CommonUtils", display);
        //requestFocus(editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textInputLayout.setError("");
            }
        });
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }


    public Bitmap addImageUsingAlertBuilder(Context context) {
        return null;
    }//bitmap return krna hai

    public static void selectDateAndTimeDialog(final Context context) {
        int mYear = 0, mMonth = 0, mDay = 0;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dayAdd;
                        String monthAdd;
                        if (dayOfMonth < 10) {
                            dayAdd = "0" + dayOfMonth;
                        } else {
                            dayAdd = "" + dayOfMonth;
                        }
                        if ((monthOfYear + 1) < 10) {
                            monthAdd = "0" + (monthOfYear + 1);
                        } else {
                            monthAdd = "" + (monthOfYear + 1);
                        }
                        timeAndDate = "On " + dayAdd + "-" + monthAdd + "-" + year;
                        timeAndDateFormat = year + "-" + monthAdd + "-" + dayAdd;
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                new TimePickerDialog.OnTimeSetListener() {

                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay,
                                                          int minute) {
                                        String minAdd;
                                        String hourAdd;
                                        if (minute < 10)
                                            minAdd = "0" + minute;
                                        else {
                                            minAdd = "" + minute;
                                        }
                                        if (hourOfDay < 10) {
                                            hourAdd = "0" + hourOfDay;
                                        } else {
                                            hourAdd = "" + hourOfDay;
                                        }
                                        timeAndDate = timeAndDate + " At " + hourAdd + ":" + minAdd;
                                        timeAndDateFormat = timeAndDateFormat + "-" + hourAdd + ":" + minAdd;
                                    }
                                }, mHour, mMinute, true);
                        timePickerDialog.show();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        // Launch Time Picker Dialog
        // call get time and date
    }

    public static String updatedTimeAndDate() {
        return timeAndDate;
    }

    public static String getFormatedTimeAndDate() {
        return timeAndDateFormat;
    }

    /*public static File fileFromBitmapBuilder(Bitmap bitmap, Context context) {
        return SelectImage.createFileFromBitmap(bitmap, context);
    }*/


    public static String subscribeToFirebaseTopic(String topic) {
        String msg = null;/*
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscription Failed";
                        }
                        Log.d(TAG, msg);

                    }
                });*/
        msg="Subscription Failed";
        return msg;
    }

    public static String sendPushNotification(String notificationTopic, String title, String body, Context context) {

        Integer userId = Integer.parseInt(PrefManager.getInstance(context).getUserId());
        if (userId == null) {
            setMsg("userId is Null");
            return getMsg();
        }
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> notifyRequest = moneyService.notifyUsers(userId, notificationTopic, title, body);
        notifyRequest.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    setMsg(response.body().getMessage());
                } catch (Exception e) {
                    setMsg(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                setMsg("Failure");
            }
        });

        return getMsg();
    }


    static void setMsg(String msg) {
        message = msg;
    }

    static String getMsg() {
        return message;
    }

    public static void exceptionHandling(String TAG, Exception e) {
        e.printStackTrace();
        if (e instanceof NullPointerException) {
            Log.d(TAG, "COMMON UTILS-------------------------EXCEPTION-HANDLING-------- " +
                    "\n --------------------EXCEPTION-HANDLING------------------------------------ " +
                    "\n NULL POINTER EXCEPTION " +
                    "\n LINE NO : "
                    + e.getStackTrace()[0].getLineNumber() +
                    "\n CAUSE : " + e.getCause());
        } else if (e instanceof NoSuchElementException) {
            Log.d(TAG, "COMMON UTILS-------------------------EXCEPTION-HANDLING-------- " +
                    "\n --------------------EXCEPTION-HANDLING------------------------------------ " +
                    "\n NO SUCH ELEMENT EXCEPTION " +
                    "\n LINE NO : "
                    + e.getStackTrace()[0].getLineNumber() +
                    "\n CAUSE : " + e.getCause());
        } else {
            Log.d(TAG, "COMMON UTILS-------------------------EXCEPTION-HANDLING-------- " +
                    "\n --------------------EXCEPTION-HANDLING------------------------------------ " +
                    "\n UNKNOWN EXCEPTION" +
                    "\n LINE NO : "
                    + e.getStackTrace()[0].getLineNumber() +
                    "\n CAUSE : " + e.getCause());
            e.printStackTrace();
        }
    }

    public static void failureShow(String TAG, Context context) {
        if (checkInternetConnectivity(context)) {
            showToast(context, "Failure");
        } else {
            showLongToast(context, "Check Internet Connection");
        }
    }
    public static void onFailureMessage(Context context,String message) {
        if (checkInternetConnectivity(context)) {
            showToast(context,message);
        } else {
            showLongToast(context, "Check Internet Connection");
        }
    }

    public static void selectFileTypeDialog(Context context) {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_file_type);
        Button btnImage = dialog.findViewById(R.id.btn_camera_fileType);
        Button btnPdf = dialog.findViewById(R.id.btn_pdf_fileType);
        Button btnVideo = dialog.findViewById(R.id.btn_video_fileType);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileTypeSelection = UiConstants.SELECT_IMAGE;
            }
        });
        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileTypeSelection = UiConstants.SELECT_FILE;
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileTypeSelection = UiConstants.SELECT_VIDEO;
            }
        });
        dialog.show();
    }

    //Use with Select File Type Dialog function
    public static int getFileTypeSelection() {
        return fileTypeSelection;
    }

    public static MultipartBody.Part getFilePartFromResult(int requestCode, int resultCode, Intent data, Context context) {
        MultipartBody.Part filePart = null;
        if (resultCode == RESULT_OK && requestCode == UiConstants.SELECT_FILE) {
            if (data.hasExtra("filePath")) {
                Toast.makeText(context, data.getExtras().getString("filePath"),
                        Toast.LENGTH_SHORT).show();
                filePart = Common.uploadFile(data.getExtras().getString("filePath"),
                        UiConstants.FILETYPE_PDF);
            }
        } else if (resultCode == RESULT_OK && requestCode == UiConstants.SELECT_IMAGE) {
            if (data.hasExtra("imagePath")) {
                Toast.makeText(context, data.getExtras().getString("imagePath"),
                        Toast.LENGTH_SHORT).show();
                filePart = Common.uploadFile(data.getExtras().getString("imagePath"),
                        UiConstants.FILETYPE_IMAGE);
            }
        } else if (resultCode == RESULT_OK && requestCode == UiConstants.SELECT_VIDEO) {
            if (data.hasExtra("videoPath")) {
                Toast.makeText(context, data.getExtras().getString("videoPath"),
                        Toast.LENGTH_SHORT).show();
                filePart = Common.uploadFile(data.getExtras().getString("videoPath"),
                        UiConstants.FILETYPE_VIDEO);

            }
        }
        return filePart;
    }

    public static File getFileFromPath(String path) {
        File file = new File(path);
        return file;
    }


    private void setUserDetails(final Context context) {
        MoneyService moneyService = ApiClient.getInstance();
        Integer userId = Integer.parseInt(PrefManager.getInstance(context).getUserId());
        Call<UserResponse> getUserRequest = moneyService.getUserInfo(userId);
        getUserRequest.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == ApiConstants.SUCCESS) {
                    try {
                        User user = response.body().getuser();
                        PrefManager.getInstance(context).setUserEmail(user.getUserName());
                        PrefManager.getInstance(context).setUserName(user.getFullName());
                        PrefManager.getInstance(context).setUserContact(user.getMobileNumber());
                        PrefManager.getInstance(context).setUserImageUrl(user.getUserImageUrl());
                        PrefManager.getInstance(context).setBackgroundImg(user.getUserBackgroundImageUrl());
                        PrefManager.getInstance(context).setUserType(user.getUserType() + "");
                        PrefManager.getInstance(context).setAccessToken(user.getUserAccessToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "Exception");
                    }
                } else {
                    Log.d("HOME ACTIVITY", response.message());
                }
                //Constants.setUpPermissionList(context);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }


    public static String listToString(List<String> obj) {
        String result = "";
        for (int i = 0; i < obj.size(); i++) {
            result = result + obj.get(i) + "-";
        }
        return result;
    }

    public static List<String> stringToList(String input, int extractLength) {
        List<String> result = new ArrayList<>();
        while (input.length() > extractLength - 1) {
            result.add(input.substring(0, extractLength));
            input = input.substring(extractLength + 1);
        }
        return result;
    }

    public static void responseTime(final Context context, int timeInSec) {
        new CountDownTimer(timeInSec * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                showToast(context, "Not -send Internet/Server Error");
            }
        }.start();

    }
}