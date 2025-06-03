package com.example.musicvibes;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomDrawablePlaceHolder extends Drawable {
    private Drawable drawable;

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (drawable != null) drawable.draw(canvas);
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void setAlpha(int alpha) {}

    @Override
    public void setColorFilter(@Nullable android.graphics.ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return android.graphics.PixelFormat.TRANSPARENT;
    }
}