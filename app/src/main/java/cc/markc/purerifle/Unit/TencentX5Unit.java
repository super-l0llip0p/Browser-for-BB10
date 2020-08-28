package cc.markc.purerifle.Unit;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.tencent.smtt.sdk.WebView;

public class TencentX5Unit {

    public static Bitmap snapshotWholePage(WebView webView, Bitmap.Config config) {
        int width = webView.computeHorizontalScrollRange();
        int height = webView.computeVerticalScrollRange();
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(
                (float) width / (float) webView.getContentWidth(),
                (float) height / (float) webView.getContentHeight()
        );

        if (webView.getX5WebViewExtension() == null) {
            return null;
        }
        webView.getX5WebViewExtension().snapshotWholePage(canvas, false, false);
        return bitmap;
    }
}
