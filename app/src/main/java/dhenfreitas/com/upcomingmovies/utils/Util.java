package dhenfreitas.com.upcomingmovies.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;
import dhenfreitas.com.upcomingmovies.R;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Diego on 05/12/2017.
 */

public class Util {

    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Typeface font(final Activity activity, String typeFont) {

        switch (typeFont) {
            case Verbose.LATO_BLACK:
                return Typeface.createFromAsset(activity.getAssets(), "Lato-Black.ttf");

            case Verbose.LATO_LIGHT:
                return Typeface.createFromAsset(activity.getAssets(), "Lato-Light.ttf");
        }
        return null;
    }

    public static void setImage(final Activity activity, final ImageView imgV, final String url) {

        Transformation blurTransformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                Bitmap blurred = Blur.fastblur(activity, source, 2);
                source.recycle();
                return blurred;
            }

            @Override
            public String key() {
                return "blur()";
            }
        };

        Picasso.with(activity)
                .load("https://descontou.com/img/placeholder.png") // thumbnail url goes here
                .placeholder(R.drawable.placeholder)
                .transform(blurTransformation)
                .into(imgV, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.with(activity)
                                .load(url) // image url goes here
                                .placeholder(imgV.getDrawable())
                                .into(imgV);
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

}
