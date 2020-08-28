package cc.markc.purerifle.Task;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import cc.markc.purerifle.R;
import cc.markc.purerifle.Unit.BrowserUnit;
import cc.markc.purerifle.Unit.TencentX5Unit;
import cc.markc.purerifle.View.NinjaToast;
import cc.markc.purerifle.View.NinjaWebView;

public class ScreenshotTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private ProgressDialog dialog;
    private NinjaWebView webView;
    private String title;
    private String path;

    public ScreenshotTask(Context context, NinjaWebView webView) {
        this.context = context;
        this.dialog = null;
        this.webView = webView;
        this.title = null;
        this.path = null;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context, R.style.ProgressDialogTheme);
        dialog.setCancelable(false);
        dialog.setMessage(context.getString(R.string.toast_wait_a_minute));
        dialog.show();

        title = webView.getTitle();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Bitmap bitmap = TencentX5Unit.snapshotWholePage(webView, Bitmap.Config.ARGB_8888);
            path = BrowserUnit.screenshot(context, bitmap, title);
        } catch (Exception e) {
            path = null;
        }
        return path != null && !path.isEmpty();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        dialog.hide();
        dialog.dismiss();

        if (result) {
            NinjaToast.show(context, context.getString(R.string.toast_screenshot_successful) + path);
        } else {
            NinjaToast.show(context, R.string.toast_screenshot_failed);
        }
    }
}
