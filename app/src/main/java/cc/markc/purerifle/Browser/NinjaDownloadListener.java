package cc.markc.purerifle.Browser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.URLUtil;

import com.tencent.smtt.sdk.DownloadListener;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import cc.markc.purerifle.R;
import cc.markc.purerifle.Unit.BrowserUnit;
import cc.markc.purerifle.Unit.IntentUnit;

public class NinjaDownloadListener implements DownloadListener {

    private Context context;

    public NinjaDownloadListener(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onDownloadStart(final String url, String userAgent, final String contentDisposition, final String mimeType, long contentLength) {
        final Context holder = IntentUnit.getContext();
        AndPermission.with((holder instanceof Activity) ? holder : context)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        BrowserUnit.download(
                                (holder instanceof Activity) ? holder : context,
                                url,
                                contentDisposition,
                                mimeType);
                    }
                }).start();
    }
}
