package cc.markc.purerifle.Task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import cc.markc.purerifle.Activity.SettingsActivity;
import cc.markc.purerifle.R;
import cc.markc.purerifle.Unit.BrowserUnit;
import cc.markc.purerifle.View.NinjaToast;

import java.io.File;

public class ImportBookmarksTask extends AsyncTask<Void, Void, Boolean> {

    private SettingsActivity activity;
    private ProgressDialog dialog;
    private File file;
    private int count;

    public ImportBookmarksTask(SettingsActivity activity, File file) {
        this.activity = activity;
        this.dialog = null;
        this.file = file;
        this.count = 0;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(activity);
        dialog.setCancelable(false);
        dialog.setMessage(activity.getString(R.string.toast_wait_a_minute));
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        count = BrowserUnit.importBookmarks(activity, file);

        if (isCancelled()) {
            return false;
        }
        return count >= 0;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        dialog.hide();
        dialog.dismiss();

        if (result) {
            activity.setDBChanged(true);
            NinjaToast.show(activity, activity.getString(R.string.toast_import_bookmarks_successful) + count);
        } else {
            NinjaToast.show(activity, R.string.toast_import_bookmarks_failed);
        }
    }
}
