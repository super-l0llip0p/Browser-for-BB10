package cc.markc.purerifle.Unit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import cc.markc.purerifle.R;

public class ViewUnit {

    public static void bound(Context context, View view) {
        int windowWidth = getWindowWidth(context);
        int windowHeight = getWindowHeight(context);
        int statusBarHeight = getStatusBarHeight(context);
        int dimen48dp = context.getResources().getDimensionPixelOffset(R.dimen.layout_height_48dp);

        int widthSpec = View.MeasureSpec.makeMeasureSpec(windowWidth, View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(windowHeight - statusBarHeight - dimen48dp, View.MeasureSpec.EXACTLY);

        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public static Bitmap capture(View view, float width, float height, boolean scroll, Bitmap.Config config) {
        if (!view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }

        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, config);
        bitmap.eraseColor(Color.WHITE);

        Canvas canvas = new Canvas(bitmap);
        int left = view.getLeft();
        int top = view.getTop();
        if (scroll) {
            left = view.getScrollX();
            top = view.getScrollY();
        }
        int status = canvas.save();
        canvas.translate(-left, -top);

        float scale = width / view.getWidth();
        canvas.scale(scale, scale, left, top);

        view.draw(canvas);
        canvas.restoreToCount(status);

        Paint alphaPaint = new Paint();
        alphaPaint.setColor(Color.TRANSPARENT);

        canvas.drawRect(0f, 0f, 1f, height, alphaPaint);
        canvas.drawRect(width - 1f, 0f, width, height, alphaPaint);
        canvas.drawRect(0f, 0f, width, 1f, alphaPaint);
        canvas.drawRect(0f, height - 1f, width, height, alphaPaint);
        canvas.setBitmap(null);

        return bitmap;
    }

    public static float dp2px(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(id, null);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    public static int getWindowHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getWindowWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static void setElevation(View view, float elevation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(elevation);
        }
    }

    public static int GRIDVIEW_ITEM = 0x20123456;

    public static void setGridViewItemWidth(View view, ViewGroup parent, int itemWidthDp,
                                            int hSpacingDp, int vSpacingDp, boolean isSquare) {
        if (parent instanceof GridView && null == view.getTag(GRIDVIEW_ITEM)) {
            if (0 == parent.getWidth()) {
                return;
            }
            GridView gridView = (GridView) parent;
            float density = gridView.getContext().getResources().getDisplayMetrics().density;
            if (null == gridView.getTag(GRIDVIEW_ITEM)) {
                int parentWidth = gridView.getWidth() - gridView.getPaddingStart() - gridView.getPaddingEnd();
                int count = (int) (parentWidth / ((itemWidthDp + hSpacingDp) * density));
                gridView.setHorizontalSpacing((int) (density * hSpacingDp));
                gridView.setVerticalSpacing((int) (density * vSpacingDp));
                if (count <= 0) {
                    count = 1;
                }
                gridView.setNumColumns(count);
                gridView.setTag(GRIDVIEW_ITEM, count);
            }

            if (null == view.getTag(GRIDVIEW_ITEM)) {
                int parentWidth = gridView.getWidth() - gridView.getPaddingStart() - gridView.getPaddingEnd();
                int count = (int) gridView.getTag(GRIDVIEW_ITEM);
                int itemWidth = (int) ((parentWidth - (density * hSpacingDp) * (count - 1)) / count);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = itemWidth;
                if (isSquare) {
                    layoutParams.height = itemWidth;
                }
                view.setLayoutParams(layoutParams);
            }
        }
    }
}