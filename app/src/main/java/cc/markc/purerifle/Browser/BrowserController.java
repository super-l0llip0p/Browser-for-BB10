package cc.markc.purerifle.Browser;

import android.net.Uri;
import android.os.Message;
import android.view.View;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public interface BrowserController {
    void updateAutoComplete();

    void updateBookmarks();

    void updateInputBox(String query);

    void updateProgress(int progress);

    void showAlbum(AlbumController albumController, boolean anim, boolean expand, boolean capture);

    void removeAlbum(AlbumController albumController);

    void openFileChooser(ValueCallback<Uri> uploadMsg);

    void showFileChooser(ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);

    void onCreateView(WebView view, Message resultMsg);

    boolean onShowCustomView(View view, int requestedOrientation, IX5WebChromeClient.CustomViewCallback callback);

    boolean onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback callback);

    boolean onHideCustomView();

    void onLongPress(String url);
}
