package in.themoneytree.utils.upload;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import in.themoneytree.BuildConfig;
import in.themoneytree.R;
import in.themoneytree.utils.CommonUtils;
import in.themoneytree.utils.SelectImage;


/**
 * Created by Archit Puri on 2/1/2019.
 */

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private Button pickImage, upload, editImage;
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String TAG = ImageActivity.class.getSimpleName();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private String mediaPath;

    private Button btnCapturePicture;

    private String mImageFileLocation = "";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    private String postPath;
    private Bitmap bitmap;
    private Bitmap bitmapR;//rotated
    private static File mediaFile;
    int rotate=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = findViewById(R.id.preview);
        pickImage = findViewById(R.id.pickImage);
        upload = findViewById(R.id.upload);
        editImage = findViewById(R.id.btn_editImage_imageActivity);
        storagePermissionCheck();
        pickImage.setOnClickListener(this);
        upload.setOnClickListener(this);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileUri != null)
                    rotateImage();
                else {
                    CommonUtils.showToast(getApplicationContext(), "Please Pick Image First");
                }
            }
        });
        Common.initDialog(getApplicationContext());
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.pickImage:
                new MaterialDialog.Builder(this)
                        .title(R.string.uploadImages)
                        .items(R.array.uploadImages)
                        .itemsIds(R.array.itemIds)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                switch (which) {
                                    case 0:
                                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
                                        break;
                                    case 1:
                                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                                        //captureImage();
                                        break;
                                    case 2:
                                        imageView.setImageResource(R.drawable.ic_launcher_background);
                                        break;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.upload:
                uploadFile();
                break;
        }
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO || requestCode == REQUEST_PICK_PHOTO) {
                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    fileUri=selectedImage;
                    processFileUri(selectedImage);
                }


            } else if (requestCode == CAMERA_PIC_REQUEST) {
                if (data != null) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    mediaFile=SelectImage.createFileFromBitmap(bitmap,getApplicationContext());
                    /*fileUri=Uri.fromFile(mediaFile);
                    postPath=fileUri.getPath().toString();
                    Log.d(TAG,"CAMERA -"+fileUri.getPath().toString());*/
                    //processFileUri(fileUri);
                    //Log.d(TAG,data.getStringExtra("CAMERA_URI"));
                    /*selectedImage = Uri.parse(data.getStringExtra("CAMERA_URI"));
                    fileUri=selectedImage;
                    Log.d(TAG,data.getStringExtra("CAMERA_URI"));*/
                }
                /*if (Build.VERSION.SDK_INT > 21) {
                    Glide.with(this).load(mImageFileLocation).into(imageView);
                    postPath = mImageFileLocation;

                }else{
                    Glide.with(this).load(fileUri).into(imageView);
                    postPath = fileUri.getPath();
                }*/
            }
        } else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Checking device has camera hardware or not
     */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            mediaFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                mediaFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.

            try {
                Uri outputUri = FileProvider.getUriForFile(
                        this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        mediaFile);
                callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                callCameraApplicationIntent.putExtra("CAMERA_URI",outputUri);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Uri outputUri = null;
            }

            // The following is a new line with a trying attempt
            try {
                callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (Exception e) {
                Log.v(TAG, e.getMessage());
            }

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }
    }

    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    /**
     * Receiving activity result method will be called after closing the camera
     * */

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    // Uploading Image/Video/**/
    private void uploadFile() {
        postPath();
        if (postPath == null || postPath.equals("")) {
            Toast.makeText(this, "Please Select an Image ", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent data = new Intent();
            data.putExtra("imagePath", postPath);
            setResult(RESULT_OK, data);
            finish();
            //Common.uploadFile(postPath,getApplicationContext(),Constants.FILETYPE_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage.setEnabled(true);
                upload.setEnabled(true);
                editImage.setEnabled(true);
            }
        }
    }

    void storagePermissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pickImage.setEnabled(false);
            upload.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            pickImage.setEnabled(true);
            upload.setEnabled(true);
        }
    }

    void rotateImage() {
        if(rotate>250){
            rotate=0;
        }else{
            rotate=90+rotate;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);

        bitmapR = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        imageView.setImageBitmap(bitmapR);
    }

    void postPath() {
        String methodName="POST_PATH ";
        Bitmap bmp = bitmap;
        if(rotate>0){
            bmp=bitmapR;
            Log.d(TAG,methodName+"Rotation");
        }
        Log.d(TAG,methodName+"No Rotation");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(mediaFile);
            //.JPEG
            bmp.compress(Bitmap.CompressFormat.WEBP, 40, fOut);
            fOut.flush();
            fOut.close();
            long fileSize = mediaFile.length();
            long fileSizeLimitInb=4000000;
            Log.d(TAG,"FileSize"+fileSize);//b-byte
            if(fileSize>fileSizeLimitInb){
                CommonUtils.showLongToast(getApplicationContext(),"File To Big To upload Please Selct a File below 4 MB");
            }else{
            fileUri= Uri.fromFile(mediaFile);
            postPath=fileUri.getPath().toString();
            }
        } catch (FileNotFoundException e1) {
            CommonUtils.showToast(getApplicationContext(),"Please Select Some Other Image");
            Log.d(TAG,methodName+"File Not Found EXCEPTION");
            e1.printStackTrace();
        } catch (IOException e) {
            CommonUtils.showToast(getApplicationContext(),"Please Select Some Other Image");
            Log.d(TAG,methodName+"IOEXCEPTION");
        }
        catch(Exception e){
            CommonUtils.showToast(getApplicationContext(),"Please Select Some Other Image");
            e.printStackTrace();
        }
    }

    void processFileUri(Uri imageUri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(imageUri, filePathColumn, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        mediaPath = cursor.getString(columnIndex);
        // Set the Image in ImageView for Previewing the Media
        bitmap = BitmapFactory.decodeFile(mediaPath);
        imageView.setImageBitmap(bitmap);
        cursor.close();
        //added
        mediaFile= SelectImage.createFileFromBitmap(bitmap,getApplicationContext());
        /*fileUri=Uri.fromFile(mediaFile);
        postPath=fileUri.getPath().toString();
        *//*postPath = mediaPath;*//* //commented*/
    }

}
