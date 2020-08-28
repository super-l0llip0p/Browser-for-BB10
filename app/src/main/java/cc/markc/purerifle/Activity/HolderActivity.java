package cc.markc.purerifle.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cc.markc.purerifle.Database.Record;
import cc.markc.purerifle.R;
import cc.markc.purerifle.Unit.IntentUnit;
import cc.markc.purerifle.View.NinjaToast;

import java.util.*;

public class HolderActivity extends Activity {

    private static final int TIMER_SCHEDULE_DEFAULT = 512;

    private Record first = null;
    private Record second = null;
    private Timer timer = null;
    private boolean background = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || getIntent().getData() == null) {
            finish();
            return;
        }

        first = new Record();
        first.setTitle(getString(R.string.album_untitled));
        first.setURL(getIntent().getData().toString());
        first.setTime(System.currentTimeMillis());

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (first != null && second == null) {
                    Intent toActivity = new Intent(HolderActivity.this, BrowserActivity.class);
                    toActivity.putExtra(IntentUnit.OPEN, first.getURL());
                    startActivity(toActivity);
                }
                HolderActivity.this.finish();
            }
        };
        timer = new Timer();
        timer.schedule(task, TIMER_SCHEDULE_DEFAULT);
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (intent == null || intent.getData() == null || first == null) {
            finish();
            return;
        }

        if (timer != null) {
            timer.cancel();
        }

        second = new Record();
        second.setTitle(getString(R.string.album_untitled));
        second.setURL(intent.getData().toString());
        second.setTime(System.currentTimeMillis());

        Intent toActivity = new Intent(HolderActivity.this, BrowserActivity.class);
        if (first.getURL().equals(second.getURL())) {
            toActivity.putExtra(IntentUnit.OPEN, first.getURL());
        } else {
            toActivity.putExtra(IntentUnit.OPEN, second.getURL());
        }
        startActivity(toActivity);
        finish();
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }

        if (background) {
            NinjaToast.show(this, R.string.toast_load_in_background);
        }

        first = null;
        second = null;
        timer = null;
        background = false;
        super.onDestroy();
    }
}
