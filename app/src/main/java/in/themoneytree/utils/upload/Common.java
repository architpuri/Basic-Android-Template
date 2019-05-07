package in.themoneytree.utils.upload;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import in.themoneytree.R;
import in.themoneytree.data.api.ApiClient;
import in.themoneytree.data.api.MoneyService;
import in.themoneytree.data.model.GeneralResponse;
import in.themoneytree.ui.common.UiConstants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Common {
    private static final String TAG = "COMMON";
    public static ProgressDialog pDialog;

    public static MultipartBody.Part uploadFile(String filePath, int fileType) {
        //showpDialog();

        File file = null;
        try {
            file = new File(filePath);
        } catch (Exception e) {
            Log.d("FILE UPLOAD", e.toString());
        }
        if (file == null) {
            Log.d("FILE UPLOAD", "FILE NULL AA RI HAI");
            return null;
        }
        MultipartBody.Part filePart;
        Log.d(TAG, "FILEPATH - " + filePath);
        if (fileType == UiConstants.FILETYPE_PDF) {
            filePart = MultipartBody.Part.createFormData("attachedMedia",
                    file.getName(),
                    RequestBody.create(MediaType.parse("application/pdf"),
                            file));
        } else if (fileType == UiConstants.FILETYPE_IMAGE) {
            try {
                String extension = filePath.substring(filePath.lastIndexOf("."));
                filePart = MultipartBody.Part.createFormData("attachedMedia",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image/" + extension),
                                file));
            } catch (Exception e) {
                Log.d(TAG, "Problem with Image Extension");
                filePart = MultipartBody.Part.createFormData("attachedMedia",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image/*"),
                                file));
            }


        } else {
            filePart = MultipartBody.Part.createFormData("attachedMedia",
                    file.getName(),
                    RequestBody.create(MediaType.parse("*/*"),
                            file));

        }
        //hidepDialog();
        return filePart;
    }

    /*public static void sendFileTest(final Context context, MultipartBody.Part filePart) {
        MoneyService moneyService = ApiClient.getInstance();
        Call<GeneralResponse> call = moneyService.sendFile(filePart);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        hidepDialog();
                        GeneralResponse serverResponse = response.body();
                        Toast.makeText(context, serverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    hidepDialog();
                    Toast.makeText(context, "problem uploading image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                hidepDialog();
                Log.v("Response gotten is", t.getMessage());
                Toast.makeText(context, "problem uploading image " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }*/

    protected static void initDialog(Context context) {

        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.loading));
        pDialog.setCancelable(true);
    }


    protected static void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected static void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }
}
