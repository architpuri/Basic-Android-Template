package in.themoneytree.utils.upload;

import android.app.ProgressDialog;
import android.util.Log;

import java.io.File;

import in.themoneytree.ui.common.UiConstants;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
}
