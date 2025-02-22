package com.ltqh.qh.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;

public class CardViewBaseImpl implements CardViewImpl {

    private final RectF mCornerRect = new RectF();

    @Override
    public void initStatic() {
        // Draws a round rect using 7 draw operations. This is faster than using
        // canvas.drawRoundRect before JBMR1 because API 11-16 used alpha mask textures to draw
        // shapes.
        RoundRectDrawableWithShadow.sRoundRectHelper =
                (canvas, bounds, cornerRadius, paint) -> {
                    final float twoRadius = cornerRadius * 2;
                    final float innerWidth = bounds.width() - twoRadius - 1;
                    final float innerHeight = bounds.height() - twoRadius - 1;
                    if (cornerRadius >= 1f) {
                        // increment corner radius to account for half pixels.
                        float roundedCornerRadius = cornerRadius + .5f;
                        mCornerRect.set(-roundedCornerRadius, -roundedCornerRadius, roundedCornerRadius,
                                roundedCornerRadius);
                        int saved = canvas.save();
                        canvas.translate(bounds.left + roundedCornerRadius,
                                bounds.top + roundedCornerRadius);
                        canvas.drawArc(mCornerRect, 180, 90, true, paint);
                        canvas.translate(innerWidth, 0);
                        canvas.rotate(90);
                        canvas.drawArc(mCornerRect, 180, 90, true, paint);
                        canvas.translate(innerHeight, 0);
                        canvas.rotate(90);
                        canvas.drawArc(mCornerRect, 180, 90, true, paint);
                        canvas.translate(innerWidth, 0);
                        canvas.rotate(90);
                        canvas.drawArc(mCornerRect, 180, 90, true, paint);
                        canvas.restoreToCount(saved);
                        //draw top and bottom pieces
                        canvas.drawRect(bounds.left + roundedCornerRadius - 1f, bounds.top,
                                bounds.right - roundedCornerRadius + 1f,
                                bounds.top + roundedCornerRadius, paint);

                        canvas.drawRect(bounds.left + roundedCornerRadius - 1f,
                                bounds.bottom - roundedCornerRadius,
                                bounds.right - roundedCornerRadius + 1f, bounds.bottom, paint);
                    }
                    // center
                    canvas.drawRect(bounds.left, bounds.top + cornerRadius,
                            bounds.right, bounds.bottom - cornerRadius, paint);
                };
    }

    @Override
    public void initialize(CardViewDelegate cardView, Context context,
                           ColorStateList backgroundColor, float radius, float elevation, float maxElevation,
                           ColorStateList shadowColorStart, ColorStateList shadowColorEnd) {
        RoundRectDrawableWithShadow background = createBackground(context, backgroundColor, radius,
                elevation, maxElevation, shadowColorStart, shadowColorEnd);
        background.setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        cardView.setCardBackground(background);
        updatePadding(cardView);
    }

    private RoundRectDrawableWithShadow createBackground(Context context,
                                                         ColorStateList backgroundColor, float radius, float elevation,
                                                         float maxElevation, ColorStateList shadowColorStart, ColorStateList shadowColorEnd) {
        return new RoundRectDrawableWithShadow(context.getResources(), backgroundColor, radius,
                elevation, maxElevation, shadowColorStart, shadowColorEnd);
    }

    @Override
    public void updatePadding(CardViewDelegate cardView) {
        Rect shadowPadding = new Rect();
        getShadowBackground(cardView).getMaxShadowAndCornerPadding(shadowPadding);
        cardView.setMinWidthHeightInternal((int) Math.ceil(getMinWidth(cardView)),
                (int) Math.ceil(getMinHeight(cardView)));
        cardView.setShadowPadding(shadowPadding.left, shadowPadding.top,
                shadowPadding.right, shadowPadding.bottom);
    }

    @Override
    public void onCompatPaddingChanged(CardViewDelegate cardView) {
        // NO OP
    }

    @Override
    public void onPreventCornerOverlapChanged(CardViewDelegate cardView) {
        getShadowBackground(cardView).setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        updatePadding(cardView);
    }

    @Override
    public void setBackgroundColor(CardViewDelegate cardView, @Nullable ColorStateList color) {
        getShadowBackground(cardView).setColor(color);
    }

    @Override
    public ColorStateList getBackgroundColor(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getColor();
    }

    @Override
    public void setRadius(CardViewDelegate cardView, float radius) {
        getShadowBackground(cardView).setCornerRadius(radius);
        updatePadding(cardView);
    }

    @Override
    public float getRadius(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getCornerRadius();
    }

    @Override
    public void setElevation(CardViewDelegate cardView, float elevation) {
        getShadowBackground(cardView).setShadowSize(elevation);
    }

    @Override
    public float getElevation(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getShadowSize();
    }

    @Override
    public void setMaxElevation(CardViewDelegate cardView, float maxElevation) {
        getShadowBackground(cardView).setMaxShadowSize(maxElevation);
        updatePadding(cardView);
    }

    @Override
    public float getMaxElevation(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMaxShadowSize();
    }

    @Override
    public float getMinWidth(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMinWidth();
    }

    @Override
    public float getMinHeight(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMinHeight();
    }

    private RoundRectDrawableWithShadow getShadowBackground(CardViewDelegate cardView) {
        return ((RoundRectDrawableWithShadow) cardView.getCardBackground());
    }
}
