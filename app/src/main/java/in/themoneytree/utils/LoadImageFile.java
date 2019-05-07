package in.themoneytree.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import in.themoneytree.R;
import in.themoneytree.ui.common.UiConstants;

/**
 * Created By Archit Puri
 * for The Money Tree
 * on 02/30/2019 at 1:44 PM
 */
public class LoadImageFile {

    public static void loadCircularTransformedImageFromUrl(Context context, ImageView imageView,
                                                           String url, int placeholder) {
        if (placeholder == 0) {
            placeholder = R.drawable.img_person;
        }
        if (url != null) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.img_person)
                    .error(R.drawable.img_person)
                    .transform(new CircularImageTransformation())
                    .into(imageView);
        } else {
            imageView.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.img_person));
        }

    }

    public static void loadImageFromUrl(Context context, ImageView imageView, String url, int placeholder) {
        if (placeholder == UiConstants.DEFAULT_PLACEHOLDER) {
            placeholder = R.drawable.img_placeholder;
        }
        if (url != null) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.img_placeholder)
                    .fit()
                    .into(imageView);
        } else {
            imageView.setImageDrawable(context.getResources()
                    .getDrawable(R.drawable.img_placeholder));
        }
    }
}
