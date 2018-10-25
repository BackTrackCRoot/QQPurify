package me.zpp0196.qqsimple.fragment;

import android.annotation.SuppressLint;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import me.zpp0196.qqsimple.R;
import me.zpp0196.qqsimple.activity.MainActivity;
import me.zpp0196.qqsimple.activity.item.AboutItem;
import me.zpp0196.qqsimple.fragment.base.BaseFragment;
import me.zpp0196.qqsimple.util.UpdateUtil;

import static me.zpp0196.qqsimple.BuildConfig.VERSION_CODE;
import static me.zpp0196.qqsimple.BuildConfig.VERSION_NAME;
import static me.zpp0196.qqsimple.Common.PACKAGE_NAME_QQ;
import static me.zpp0196.qqsimple.Common.PREFS_KEY_ENTER_DONATE_TIMES;
import static me.zpp0196.qqsimple.Common.PREFS_KEY_ENTER_MODULE_TIMES;

/**
 * Created by zpp0196 on 2018/5/26 0026.
 */

public class AboutFragment extends BaseFragment {

    private long eggsClickNum;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    public int getTitleId() {
        return R.string.nav_item_about;
    }

    private void setEggsClickNum() {
        eggsClickNum = new Random().nextInt(8) + 4;
        Log.i("AboutFragment", "eggsClickNum: " + eggsClickNum);
    }

    @SuppressLint("LogConditional")
    @Override
    protected void init() {
        addItems(getMainActivity());
        setEggsClickNum();

        TextView readme = findViewById(R.id.about_readme);
        TextView issues = findViewById(R.id.about_issues);
        TextView releases = findViewById(R.id.about_releases);
        readme.setMovementMethod(LinkMovementMethod.getInstance());
        issues.setMovementMethod(LinkMovementMethod.getInstance());
        releases.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void addItems(MainActivity mainActivity) {
        LinearLayout aboutLayout = findViewById(R.id.about_layout);

        AboutItem version = new AboutItem(mainActivity, R.drawable.ic_item_comm_info, R.string.about_item_version, String.format("%s(%s)", VERSION_NAME, VERSION_CODE));
        AboutItem author = new AboutItem(mainActivity, R.drawable.ic_item_about_author, R.string.about_item_author, getString(R.string.author_name));
        AboutItem coder = new AboutItem(mainActivity, R.drawable.ic_item_about_author, R.string.about_item_coder, getString(R.string.coder_name));
        AboutItem thanks = new AboutItem(mainActivity, R.drawable.ic_item_about_like, R.string.about_item_thanks);
        AboutItem github = new AboutItem(mainActivity, R.drawable.ic_item_about_github, R.string.about_item_github);
        AboutItem log = new AboutItem(mainActivity, R.drawable.ic_item_about_log, R.string.about_item_log);
        AboutItem donate = new AboutItem(mainActivity, R.drawable.ic_item_about_donate, R.string.about_item_donate);

        // 版本
        version.setOnClickListener(v -> {
            eggsClickNum -= 1;
            if (eggsClickNum <= 0) {
                new MaterialDialog.Builder(mainActivity).cancelable(false)
                        .title(R.string.about_egg_title)
                        .content(getEggsContent())
                        .positiveText(R.string.about_egg_close)
                        .show();
                setEggsClickNum();
            }
        });
        version.setOnLongClickListener(v -> {
            mainActivity.openCoolApk();
            return false;
        });
        // 作者
        author.setOnClickListener(v -> mainActivity.openMyCoolapk());
        //二次开发人员
        coder.setOnClickListener(view -> mainActivity.openCoderBlog());
        // 致谢
        thanks.setOnClickListener(v -> new MaterialDialog.Builder(mainActivity).title(R.string.about_item_thanks_title)
                .content(R.string.about_item_thanks_content)
                .positiveText(R.string.button_close)
                .show());
        // 源码
        github.setOnClickListener(v -> mainActivity.openGitHub());
        // 日志
        log.setOnClickListener(v -> UpdateUtil.showUpdateLog(mainActivity));
        // 捐赠
        donate.setOnClickListener(v -> {
            long enterDonate = getPrefs().getLong(PREFS_KEY_ENTER_DONATE_TIMES, 0);
            getEditor().putLong(PREFS_KEY_ENTER_DONATE_TIMES, enterDonate + 1)
                    .apply();
            mainActivity.openAlipay();
        });

        aboutLayout.addView(version);
        aboutLayout.addView(author);
        aboutLayout.addView(coder);
        aboutLayout.addView(thanks);
        aboutLayout.addView(github);
        aboutLayout.addView(log);
        aboutLayout.addView(donate);
    }

    @SuppressLint("SimpleDateFormat")
    private String getEggsContent() {
        String content = getString(R.string.about_egg_content);
        String contentHasDonate = getString(R.string.about_egg_content_has_donate);
        String contentNotDonate = getString(R.string.about_egg_content_not_donate);
        long qqInstallTime = getAppInstallTime(PACKAGE_NAME_QQ);
        long birthday = new GregorianCalendar(2018, 3 - 1, 17).getTimeInMillis();
        long now = Calendar.getInstance()
                .getTimeInMillis();
        long dayMillis = 1000 * 60 * 60 * 24;
        long age = now - birthday;
        long diff = now - qqInstallTime;
        String days = getString(R.string.time_days);
        String birthdayTime = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
        String installTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qqInstallTime);
        long enterModuleTimes = getPrefs().getLong(PREFS_KEY_ENTER_MODULE_TIMES, 0);
        long enterDonateTimes = getPrefs().getLong(PREFS_KEY_ENTER_DONATE_TIMES, 0);
        contentHasDonate = String.format(contentHasDonate, enterDonateTimes);
        String isDonate = enterDonateTimes == 0 ? contentNotDonate : contentHasDonate;
        content = String.format(content,
                age / dayMillis, days, birthdayTime,
                diff / dayMillis, days, installTime, enterModuleTimes, isDonate);
        return content;
    }

    private long getAppInstallTime(String packageName) {
        try {
            return getMainActivity().getPackageManager()
                    .getPackageInfo(packageName, 0).firstInstallTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
