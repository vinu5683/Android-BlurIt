package com.vinod.blurit.blurview;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

// Used in edit mode and in case if no BlurController was set
public class NoOpController implements BlurController {
    @Override
    public boolean draw(Canvas canvas) {
        return true;
    }

    @Override
    public void updateBlurViewSize() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public BlurFacade setBlurRadius(float radius) {
        return this;
    }

    @Override
    public BlurFacade setOverlayColor(int overlayColor) {
        return this;
    }

    @Override
    public BlurFacade setFrameClearDrawable(@Nullable Drawable windowBackground) {
        return this;
    }

    @Override
    public BlurFacade setBlurEnabled(boolean enabled) {
        return this;
    }

    @Override
    public BlurFacade setBlurAutoUpdate(boolean enabled) {
        return this;
    }
}
