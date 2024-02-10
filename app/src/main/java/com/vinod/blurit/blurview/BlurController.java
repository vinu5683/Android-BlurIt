package com.vinod.blurit.blurview;

import android.graphics.Canvas;

public interface BlurController extends BlurFacade {

    float DEFAULT_SCALE_FACTOR = 2f;
    float DEFAULT_BLUR_RADIUS = 6f;

    /**
     * Draws blurred content on given canvas
     *
     * @return true if BlurView should proceed with drawing itself and its children
     */
    boolean draw(Canvas canvas);

    /**
     * Must be used to notify Controller when BlurView's size has changed
     */
    void updateBlurViewSize();

    /**
     * Frees allocated resources
     */
    void destroy();
}
