package com.example.musicvibes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class GlideImageGetter implements Html.ImageGetter {
    private final TextView textView;
    private final Context context;

    public GlideImageGetter(TextView textView, Context context) {
        this.textView = textView;
        this.context = context;
    }

    @Override
    public Drawable getDrawable(String source) {
        CustomDrawablePlaceHolder drawable = new CustomDrawablePlaceHolder();

        Glide.with(context)
                .load(source)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        int width = textView.getWidth();
                        int imgWidth = resource.getIntrinsicWidth();
                        int imgHeight = resource.getIntrinsicHeight();
                        float scale = (float) width / imgWidth;
                        int scaledHeight = (int) (imgHeight * scale);
                        resource.setBounds(0, 0, width, scaledHeight);
                        drawable.setBounds(0, 0, width, scaledHeight);
                        drawable.setDrawable(resource);
                        textView.setText(textView.getText()); // redraw
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                });

        return drawable;
    }
}