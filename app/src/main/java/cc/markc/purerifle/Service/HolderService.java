package cc.markc.purerifle.Service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import cc.markc.purerifle.Browser.AlbumController;
import cc.markc.purerifle.Browser.BrowserContainer;
import cc.markc.purerifle.Browser.BrowserController;
import cc.markc.purerifle.R;
import cc.markc.purerifle.Unit.*;
import cc.markc.purerifle.View.NinjaContextWrapper;
import cc.markc.purerifle.View.NinjaWebView;

public class HolderService extends Service implements BrowserController {

    @Override
    public void updateAutoComplete() {
    }

    @Override
    public void updateBookmarks() {
    }

    @Override
    public void updateInputBox(String query) {
    }

    @Override
    public void updateProgress(int progress) {
    }

    @Override
    public void showAlbum(AlbumController albumController, boolean anim, boolean expand, boolean capture) {
    }

    @Override
    public void removeAlbum(AlbumController albumController) {
    }

    @Override
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
    }

    @Override
    public void showFileChooser(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
    }

    @Override
    public void onCreateView(WebView view, Message resultMsg) {
    }

    @Override
    public boolean onShowCustomView(View view, int requestedOrientation, IX5WebChromeClient.CustomViewCallback callback) {
        return true;
    }

    @Override
    public boolean onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback) {
        return true;
    }

    @Override
    public boolean onHideCustomView() {
        return true;
    }

    @Override
    public void onLongPress(String url) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NinjaWebView webView = new NinjaWebView(new NinjaContextWrapper(this));
        webView.setBrowserController(this);
        webView.setFlag(BrowserUnit.FLAG_NINJA);
        webView.setAlbumCover(null);
        webView.setAlbumTitle(getString(R.string.album_untitled));
        ViewUnit.bound(this, webView);

        webView.loadUrl(RecordUnit.getHolder().getURL());
        webView.deactivate();

        BrowserContainer.add(webView);
        updateNotification();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (IntentUnit.isClear()) {
            BrowserContainer.clear();
        }
        stopForeground(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateNotification() {
        Notification notification = NotificationUnit.getHBuilder(this).build();
        startForeground(NotificationUnit.HOLDER_ID, notification);
    }
}