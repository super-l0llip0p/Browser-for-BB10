package cc.markc.purerifle.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import cc.markc.purerifle.Unit.BrowserUnit;

public class ClearDataService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        System.exit(0); // For remove all WebView thread
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        clearData();
        stopSelf();
        return START_STICKY;
    }

    private void clearData() {
        BrowserUnit.clearCache(this);
        BrowserUnit.clearCookie(this);
        BrowserUnit.clearFormData(this);
        BrowserUnit.clearHistory(this);
        BrowserUnit.clearPasswords(this);
    }
}
