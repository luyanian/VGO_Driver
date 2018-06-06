package com.lanhi.vgo.driver.common;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lanhi.vgo.driver.api.ApiConstants;

import org.w3c.dom.Text;

public class ImageViewAttrAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }

    @BindingAdapter({"app:imageUrl", "app:placeHolder", "app:error"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        GlideApp.with(imageView.getContext())
                .load(ApiConstants.HOST + url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .into(imageView);
    }
}