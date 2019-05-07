package in.themoneytree.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static in.themoneytree.ui.common.UiConstants.REQUEST_CAMERA;
import static in.themoneytree.ui.common.UiConstants.SELECT_FILE;


public class SelectImage extends Activity {
    private static final int REQUEST_IMAGE_CAPTURE =REQUEST_CAMERA ;
    private static Context context;
    private static Activity activity;
    private static String userChoosenTask;
    private static File file = null;
    private static File phFile = null;
    private static Bitmap bitmap;
    private static String name = "abc";
    private static String currentPhotoPath;

    public static File selectImageFunctionality(Context context) {
        activity = (Activity) context;
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionUtility.checkPermission(context);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent(activity);
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent(activity);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        return phFile;
    }

    private static void cameraIntent(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            phFile = null;
            try {
                phFile = createImageFile();
            } catch (Exception ex) {//IOException
                // Error occurred while creating the File
                Log.i("Select Image", "IOException");
            }
            // Continue only if the File was successfully created
            if (phFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phFile));
                activity.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    private static File createImageFile() {// throws IOException
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image=null;
        try{
        image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );}
        catch(Exception e){
            Log.i("Select Image", "Exception aa ri hai-IOException");
        }

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private static void galleryIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @SuppressWarnings("deprecation")
    public static Bitmap onSelectFromGalleryResult(Intent data, Context context) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                createFileFromBitmap(bm, context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;
    }

    public static File createFileFromBitmap(Bitmap bitmap, Context context) {
        File filesDir = context.getFilesDir();

        file = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
        } catch (Exception e) {
        }
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                bitmap = onSelectFromGalleryResult(data, context);
            else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(currentPhotoPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
