package cc.markc.purerifle.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.util.List;

import cc.markc.purerifle.R;
import cc.markc.purerifle.Task.ExportBookmarksTask;
import cc.markc.purerifle.Task.ImportBookmarksTask;
import cc.markc.purerifle.Unit.BrowserUnit;
import cc.markc.purerifle.Unit.CommonUnit;
import cc.markc.purerifle.Unit.IntentUnit;
import cc.markc.purerifle.View.NinjaToast;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener, SharedPreferences.OnSharedPreferenceChangeListener, View.OnClickListener {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUnit.setImmerseStatusBar(this, R.color.background_dark);
        setContentView(R.layout.activity_settings);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        initView();
    }

    private void initView() {
        AppCompatSpinner mSearchEngineSpinner = findViewById(R.id.search_engine_spinner);
        AppCompatSpinner mNotificationPrioritySpinner = findViewById(R.id.notification_priority_spinner);
        AppCompatSpinner mTabPositionSpinner = findViewById(R.id.tab_position_spinner);
        AppCompatSpinner mVolumeControlSpinner = findViewById(R.id.volume_control_spinner);
        AppCompatSpinner mBrowserUserAgentSpinner = findViewById(R.id.browser_user_agent_spinner);
        AppCompatSpinner mRenderingSpinner = findViewById(R.id.rendering_spinner);
        SwitchCompat mOmniboxControlSwitch = findViewById(R.id.omnibox_control_switch);
        SwitchCompat mLoadImageSwitch = findViewById(R.id.load_image_switch);
        SwitchCompat mAcceptCookieSwitch = findViewById(R.id.accept_cookie_switch);
        SwitchCompat mRunJavascriptSwitch = findViewById(R.id.run_javascript_switch);
        SwitchCompat mGetLocationSwitch = findViewById(R.id.get_location_switch);
        SwitchCompat mAutoOpenNewTabSwitch = findViewById(R.id.auto_open_new_tab_switch);
        SwitchCompat mShowScrollBarSwitch = findViewById(R.id.show_scroll_bar_switch);
        SwitchCompat mReflowSwitch = findViewById(R.id.text_reflow_switch);
        SwitchCompat mAdblockSwitch = findViewById(R.id.adblock_switch);
        SwitchCompat mSavePasswordSwitch = findViewById(R.id.save_password_switch);
        SwitchCompat clearDataOnExitSwitch = findViewById(R.id.clear_data_on_exit_switch);
        Button mBackupBookmarkButton = findViewById(R.id.backup_bookmark_button);
        Button mRestoreBookmarkButton = findViewById(R.id.restore_bookmark_button);
        Button mClearDataButton = findViewById(R.id.clear_data_button);
        Button mRelatedAgreementsButton = findViewById(R.id.related_agreements_button);
        Button mCheckUpdateButton = findViewById(R.id.check_update_button);

        mSearchEngineSpinner.setSelection(getSPString(R.string.sp_search_engine, 3), true);
        mNotificationPrioritySpinner.setSelection(getSPString(R.string.sp_notification_priority, 0), true);
        mTabPositionSpinner.setSelection(getSPString(R.string.sp_anchor, 1), true);
        mVolumeControlSpinner.setSelection(getSPString(R.string.sp_volume, 2), true);
        mBrowserUserAgentSpinner.setSelection(getSPString(R.string.sp_user_agent, 0), true);
        mRenderingSpinner.setSelection(getSPString(R.string.sp_rendering, 0), true);
        mOmniboxControlSwitch.setChecked(getSPBoolean(R.string.sp_omnibox_control, true));
        mLoadImageSwitch.setChecked(getSPBoolean(R.string.sp_images, true));
        mAcceptCookieSwitch.setChecked(getSPBoolean(R.string.sp_cookies, true));
        mRunJavascriptSwitch.setChecked(getSPBoolean(R.string.sp_javascript, true));
        mGetLocationSwitch.setChecked(getSPBoolean(R.string.sp_location, false));
        mAutoOpenNewTabSwitch.setChecked(getSPBoolean(R.string.sp_multiple_windows, false));
        mShowScrollBarSwitch.setChecked(getSPBoolean(R.string.sp_scroll_bar, true));
        mReflowSwitch.setChecked(getSPBoolean(R.string.sp_text_reflow, true));
        mAdblockSwitch.setChecked(getSPBoolean(R.string.sp_ad_block, true));
        mSavePasswordSwitch.setChecked(getSPBoolean(R.string.sp_passwords, false));
        clearDataOnExitSwitch.setChecked(getSPBoolean(R.string.sp_clear_quit, false));

        mSearchEngineSpinner.setOnItemSelectedListener(this);
        mNotificationPrioritySpinner.setOnItemSelectedListener(this);
        mTabPositionSpinner.setOnItemSelectedListener(this);
        mVolumeControlSpinner.setOnItemSelectedListener(this);
        mBrowserUserAgentSpinner.setOnItemSelectedListener(this);
        mRenderingSpinner.setOnItemSelectedListener(this);
        mOmniboxControlSwitch.setOnCheckedChangeListener(this);
        mLoadImageSwitch.setOnCheckedChangeListener(this);
        mAcceptCookieSwitch.setOnCheckedChangeListener(this);
        mRunJavascriptSwitch.setOnCheckedChangeListener(this);
        mGetLocationSwitch.setOnCheckedChangeListener(this);
        mAutoOpenNewTabSwitch.setOnCheckedChangeListener(this);
        mShowScrollBarSwitch.setOnCheckedChangeListener(this);
        mReflowSwitch.setOnCheckedChangeListener(this);
        mAdblockSwitch.setOnCheckedChangeListener(this);
        mSavePasswordSwitch.setOnCheckedChangeListener(this);
        clearDataOnExitSwitch.setOnCheckedChangeListener(this);
        mBackupBookmarkButton.setOnClickListener(this);
        mRestoreBookmarkButton.setOnClickListener(this);
        mClearDataButton.setOnClickListener(this);
        mRelatedAgreementsButton.setOnClickListener(this);
        mCheckUpdateButton.setOnClickListener(this);
    }

    private int getSPString(int name, int defaultValue) {
        return Integer.valueOf(mSharedPreferences.getString(getString(name), String.valueOf(defaultValue)));
    }

    private void putSPString(int name, int value) {
        mSharedPreferences.edit().putString(getString(name), String.valueOf(value)).apply();
    }

    private boolean getSPBoolean(int name, boolean defaultValue) {
        return mSharedPreferences.getBoolean(getString(name), defaultValue);
    }

    private void putSPBoolean(int name, boolean value) {
        mSharedPreferences.edit().putBoolean(getString(name), value).apply();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.search_engine_spinner:
                putSPString(R.string.sp_search_engine, i);
                break;
            case R.id.notification_priority_spinner:
                putSPString(R.string.sp_notification_priority, i);
                break;
            case R.id.tab_position_spinner:
                putSPString(R.string.sp_anchor, i);
                break;
            case R.id.volume_control_spinner:
                putSPString(R.string.sp_volume, i);
                break;
            case R.id.browser_user_agent_spinner:
                putSPString(R.string.sp_user_agent, i);
                break;
            case R.id.rendering_spinner:
                putSPString(R.string.sp_rendering, i);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
        switch (compoundButton.getId()) {
            case R.id.omnibox_control_switch:
                putSPBoolean(R.string.sp_omnibox_control, b);
                break;
            case R.id.load_image_switch:
                putSPBoolean(R.string.sp_images, b);
                break;
            case R.id.accept_cookie_switch:
                putSPBoolean(R.string.sp_cookies, b);
                break;
            case R.id.run_javascript_switch:
                putSPBoolean(R.string.sp_javascript, b);
                break;
            case R.id.get_location_switch:
                if (b) {
                    AndPermission.with(this)
                            .runtime()
                            .permission(Permission.ACCESS_FINE_LOCATION)
                            .onGranted(new Action<List<String>>() {
                                @Override
                                public void onAction(List<String> data) {
                                    putSPBoolean(R.string.sp_location, b);
                                }
                            })
                            .onDenied(new Action<List<String>>() {
                                @Override
                                public void onAction(List<String> data) {
                                    compoundButton.setChecked(false);
                                }
                            }).start();
                } else {
                    putSPBoolean(R.string.sp_location, b);
                }
                break;
            case R.id.auto_open_new_tab_switch:
                putSPBoolean(R.string.sp_multiple_windows, b);
                break;
            case R.id.show_scroll_bar_switch:
                putSPBoolean(R.string.sp_scroll_bar, b);
                break;
            case R.id.text_reflow_switch:
                putSPBoolean(R.string.sp_text_reflow, b);
                break;
            case R.id.adblock_switch:
                putSPBoolean(R.string.sp_ad_block, b);
                break;
            case R.id.save_password_switch:
                putSPBoolean(R.string.sp_passwords, b);
                break;
            case R.id.clear_data_on_exit_switch:
                putSPBoolean(R.string.sp_clear_quit, b);
                break;
        }
    }

    private boolean isSPChanged = false;
    private boolean isDBChanged = false;

    public void setDBChanged(boolean b) {
        isDBChanged = b;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            IntentUnit.setDBChange(isDBChanged);
            IntentUnit.setSPChange(isSPChanged);
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        isSPChanged = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backup_bookmark_button:
                new ExportBookmarksTask(this).execute();
                break;
            case R.id.restore_bookmark_button:
                Intent importBookmarks = new Intent(Intent.ACTION_GET_CONTENT);
                importBookmarks.setType(IntentUnit.INTENT_TYPE_TEXT_PLAIN);
                importBookmarks.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(importBookmarks, IntentUnit.REQUEST_BOOKMARKS);
                break;
            case R.id.clear_data_button:
                clearData();
                break;
            case R.id.related_agreements_button:
                Intent relatedAgreements = new Intent();
                relatedAgreements.putExtra(IntentUnit.OPEN, BrowserUnit.BASE_URL + BrowserUnit.RELATED_AGREEMENTS);
                setResult(AppCompatActivity.RESULT_OK, relatedAgreements);
                finish();
                break;
            case R.id.check_update_button:
                Intent intent = new Intent();
                intent.putExtra(IntentUnit.OPEN, "https://markc.cc/p/9fba637c.html");
                setResult(AppCompatActivity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    private void clearData() {
        ProgressDialog dialog;
        dialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.toast_wait_a_minute));
        dialog.show();

        BrowserUnit.clearCache(this);
        BrowserUnit.clearCookie(this);
        BrowserUnit.clearFormData(this);
        BrowserUnit.clearHistory(this);
        BrowserUnit.clearPasswords(this);

        dialog.hide();
        dialog.dismiss();

        isDBChanged = true;
        NinjaToast.show(this, R.string.toast_clear_successful);
    }

    @Override
    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IntentUnit.REQUEST_BOOKMARKS) {
            if (resultCode != AppCompatActivity.RESULT_OK || data == null || data.getData() == null) {
                NinjaToast.show(this, R.string.toast_import_bookmarks_failed);
            } else {
                File file = new File(data.getData().getPath());
                new ImportBookmarksTask(this, file).execute();
            }
        }
    }
}
