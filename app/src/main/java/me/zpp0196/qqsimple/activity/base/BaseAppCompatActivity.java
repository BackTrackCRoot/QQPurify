package me.zpp0196.qqsimple.activity.base;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;

import me.zpp0196.qqsimple.R;
import me.zpp0196.qqsimple.util.CommUtil;

import static me.zpp0196.qqsimple.BuildConfig.APPLICATION_ID;
import static me.zpp0196.qqsimple.Common.PACKAGE_NAME_ALIPAY;
import static me.zpp0196.qqsimple.util.CommUtil.getPrefsDir;
import static me.zpp0196.qqsimple.util.CommUtil.getPrefsFile;
import static me.zpp0196.qqsimple.util.CommUtil.getThrowableMsg;
import static me.zpp0196.qqsimple.util.CommUtil.isInstalled;

/**
 * Created by zpp0196 on 2018/5/30 0030.
 */

@SuppressLint ("Registered")
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        initData(savedInstanceState);
    }

    @LayoutRes
    protected abstract int setContentViewId();

    protected void initData(Bundle savedInstanceState) {}

    public void openAlipay() {
        String qrcode = "FKX03149H8YOUWESHOCEC6";
        try {
            getPackageManager().getPackageInfo(PACKAGE_NAME_ALIPAY, PackageManager.GET_ACTIVITIES);
            openUrl("alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode=https://qr.alipay.com/" +
                    qrcode + "%3F_s%3Dweb-other&_t=");
        } catch (PackageManager.NameNotFoundException e) {
            openUrl("https://mobilecodec.alipay.com/client_download.htm?qrcode=" + qrcode);
        }
    }

    public void openCoolApk() {
        try {
            startActivity(getCoolapkIntent(APPLICATION_ID));
        } catch (Exception e) {
            openUrl("http://www.coolapk.com/apk/" + APPLICATION_ID);
        }
    }

    @SuppressLint ("WrongConstant")
    private Intent getCoolapkIntent(String packageName) {
        String str = "market://details?id=" + packageName;
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.setPackage("com.coolapk.market");
        intent.setFlags(0x10000000);
        return intent;
    }

    public void openGitHub() {
        openUrl(getString(R.string.about_github_url));
    }

    public void openReleases() {
        openUrl(getString(R.string.about_github_url) + "/releases");
    }

    public void openMyCoolapk() {
        openUrl("https://www.coolapk.com/u/1158829");
    }

    public void openCoderBlog() {
        openUrl("https://www.xcroot.com/");
    }

    public void openUrl(String url) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        try {
            startActivity(intent);
        } catch (Exception e) {
            showThrowableDialog(e);
        }
    }

    public void launchApp(String packageName) {
        if (isInstalled(this, packageName)) {
            try {
                startActivity(getPackageManager().getLaunchIntentForPackage(packageName));
            } catch (Exception e) {
                showThrowableDialog(e);
            }
        } else {
            Toast.makeText(this, packageName + " not find!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void showThrowableDialog(Throwable tr) {
        String msg = getThrowableMsg(tr);
        new MaterialDialog.Builder(this).cancelable(false)
                .title(R.string.title_appear_exception)
                .content(msg)
                .positiveText(R.string.button_close)
                .negativeText(R.string.button_copy)
                .onNegative((dialog, which) -> {
                    ClipData clip = ClipData.newPlainText("text", msg);
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    if (clipboardManager != null) {
                        clipboardManager.setPrimaryClip(clip);
                    }
                })
                .show();
    }

    public void showMsgDialog(@StringRes int title, @StringRes int msg) {
        new MaterialDialog.Builder(this).cancelable(false)
                .title(title)
                .content(msg)
                .positiveText(R.string.button_close)
                .show();
    }

    public SharedPreferences getPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    public SharedPreferences.Editor getEditor() {
        return getPrefs().edit();
    }

    @SuppressWarnings ({"deprecation", "ResultOfMethodCallIgnored"})
    @SuppressLint ({"SetWorldReadable", "WorldReadableFiles"})
    public void setWorldReadable() {
        if (getPrefsFile(this).exists()) {
            for (File file : new File[]{CommUtil.getDataDir(this), getPrefsDir(this), getPrefsFile(this)}) {
                file.setReadable(true, false);
                file.setExecutable(true, false);
            }
        }
    }
}
