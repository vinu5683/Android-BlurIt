package com.vinod.blurit.blurview;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public interface BlurFacade {

    /**
     * Enables/disables the blur. Enabled by default
     *
     * @param enabled true to enable, false otherwise
     * @return {@link BlurFacade}
     */
    BlurFacade setBlurEnabled(boolean enabled);

    /**
     * Can be used to stop blur auto update or resume if it was stopped before.
     * Enabled by default.
     *
     * @return {@link BlurFacade}
     */
    BlurFacade setBlurAutoUpdate(boolean enabled);

    /**
     * @param frameClearDrawable sets the drawable to draw before view hierarchy.
     *                           Can be used to draw Activity's window background if your root layout doesn't provide any background
     *                           Optional, by default frame is cleared with a transparent color.
     * @return {@link BlurFacade}
     */
    BlurFacade setFrameClearDrawable(@Nullable Drawable frameClearDrawable);

    /**
     * @param radius sets the blur radius
     *               Default value is {@link BlurController#DEFAULT_BLUR_RADIUS}
     * @return {@link BlurFacade}
     */
    BlurFacade setBlurRadius(float radius);

    /**
     * Sets the color overlay to be drawn on top of blurred content
     *
     * @param overlayColor int color
     * @return {@link BlurFacade}
     */
    BlurFacade setOverlayColor(@ColorInt int overlayColor);
}
