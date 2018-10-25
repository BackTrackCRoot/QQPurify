package me.zpp0196.qqsimple.hook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import me.zpp0196.qqsimple.activity.MainActivity;
import me.zpp0196.qqsimple.hook.base.BaseHook;

import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static me.zpp0196.qqsimple.BuildConfig.APPLICATION_ID;
import static me.zpp0196.qqsimple.hook.comm.Classes.ApolloManager$CheckApolloInfoResult;
import static me.zpp0196.qqsimple.hook.comm.Classes.BaseActivity;
import static me.zpp0196.qqsimple.hook.comm.Classes.FileManagerUtil;
import static me.zpp0196.qqsimple.hook.comm.Classes.FrameHelperActivity;
import static me.zpp0196.qqsimple.hook.comm.Classes.QQAppInterface;
import static me.zpp0196.qqsimple.hook.comm.Classes.QQSettingMe;
import static me.zpp0196.qqsimple.hook.comm.Classes.QZoneHelper;
import static me.zpp0196.qqsimple.hook.comm.Classes.QzonePluginProxyActivity;
import static me.zpp0196.qqsimple.hook.comm.Maps.sidebarItem;
import static me.zpp0196.qqsimple.hook.util.HookUtil.isMoreThan755;
import static me.zpp0196.qqsimple.hook.util.HookUtil.isMoreThan770;

/**
 * Created by zpp0196 on 2018/5/11 0011.
 */

public class SidebarHook extends BaseHook {

    @Override
    public void init() {
        findAndHookConstructor(QQSettingMe, BaseActivity, QQAppInterface, FrameHelperActivity, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                hideViews(param);
            }
        });
        XC_MethodHook hideTask = hideView(LinearLayout.class, "a", "hide_sidebar_myDaily");
        // 隐藏打卡/每日任务
        findAndHookMethod(QQSettingMe, "F", hideTask);
        if (isMoreThan755()) {
            findAndHookMethod(QQSettingMe, "G", hideTask);
        }
        hideItems();
        hookOther();
    }

    private void hideItems() {
        // index: 0,  title: 了解会员特权
        // index: 1,  title: QQ钱包
        // index: 2,  title: 我的收藏
        // index: 3,  title: 我的文件
        // index: 4,  title: 我的相册
        // index: 5,  title: 个性装扮
        // index: 6,  title: 网上营业厅
        // index: 7,  title: 我的日程
        // index: 8,  title: 我的名片夹
        // index: 9,  title: 我的视频
        // index: 10, title: TIM免费云盘
        // index: 11, title: 免流量特权

        // 会员
        findAndHookMethod(QQSettingMe, "a", boolean.class, boolean.class, int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                View[] items = getObject(param.thisObject, View[].class, "a");
                hideView(items[0], "hide_sidebar_vip");
            }
        });

        Method method = findMethodIfExists(QQSettingMe, void.class, "a");
        hookMethod(method, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                View[] items = getObject(param.thisObject, View[].class, "a");
                // 我的名片夹
                hideView(items[8], "hide_sidebar_myCards");
                // 我的视频
                hideView(items[9], "hide_sidebar_myVideos");
                // 城市天气
                hideView(param.thisObject, LinearLayout.class, "b", "hide_sidebar_cityWeather");
            }
        });

        // 免流量特权
        findAndHookMethod(QQSettingMe, "Q", replaceNull("hide_sidebar_kingCard"));
    }

    private void hideViews(XC_MethodHook.MethodHookParam param) {
        final Object obj = param.thisObject;

        // 隐藏打卡/每日任务
        hideView(obj, LinearLayout.class, "a", "hide_sidebar_myDaily");
        // 隐藏二维码
        hideView(obj, ImageView.class, "d", "hide_sidebar_myQrCode");
        // 隐藏QQ信息
        if (getBool("hide_sidebar_qqInfo")) {
            View qqInfo = getObject(obj, View.class, "a");
            qqInfo.setVisibility(View.INVISIBLE);
        }

        // 隐藏侧滑栏
        View[] items = getObject(obj, View[].class, "a");
        for (View item : items) {
            ViewGroup viewGroup = (ViewGroup) item;
            TextView textView = (TextView) viewGroup.getChildAt(1);
            for (String key : sidebarItem.keySet()) {
                String value = sidebarItem.get(key);
                if (textView.getText()
                        .toString()
                        .contains(value)) {
                    hideView(viewGroup, key);
                }
            }
        }

        // 隐藏夜间模式
        View nightTheme = getObject(obj, View.class, "d");
        hideView(nightTheme, "hide_sidebar_nightTheme");
        // 隐藏天气
        hideView(obj, LinearLayout.class, "b", "hide_sidebar_cityWeather");

        Activity activity = (Activity) param.args[0];
        addQzoneEntry(activity, param);
        addEntryInSidebar(activity, (ViewGroup) nightTheme.getParent());
    }

    private void hookOther() {
        // 隐藏侧滑栏厘米秀
        findAndHookMethod(QQSettingMe, "a", ApolloManager$CheckApolloInfoResult, replaceNull("hide_sidebar_apollo"));
        if(!isMoreThan770()){
            // 隐藏我的文件里面的TIM推广
            findAndHookMethod(FileManagerUtil, "a", QQAppInterface, boolean.class, replaceFalse("hide_tim_in_myFile"));
        }
    }

    /**
     * 侧滑栏添加空间入口
     */
    @SuppressLint ("WrongConstant")
    private void addQzoneEntry(Activity activity, XC_MethodHook.MethodHookParam param) {
        if (!getBool("sidebar_add_qzoneEntry"))
            return;
        View view = getObject(param.thisObject, View.class, "a");
        view.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("newflag", true);
            intent.putExtra("refer", "schemeActiveFeeds");
            callStaticMethod(QzonePluginProxyActivity, "a", intent, "com.qzone.feed.ui.activity.QZoneFriendFeedActivity");
            intent.addFlags(0x30000000);
            Object qqAppInterface = getObject(param.thisObject, QQAppInterface, "a");
            String uin = (String) XposedHelpers.callMethod(qqAppInterface, "getCurrentAccountUin");
            callStaticMethod(QZoneHelper, "b", activity, uin, intent, -1);
        });
    }

    /**
     * 在侧滑栏添加进入模块入口
     */
    @SuppressLint ("ResourceType")
    private void addEntryInSidebar(Activity activity, ViewGroup bottomBtn) {
        if (!getBool("sidebar_add_moduleEntry"))
            return;
        View settingLayout = bottomBtn.getChildAt(0);
        settingLayout.setOnLongClickListener(v -> {
            Intent intent = new Intent(APPLICATION_ID);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            intent.setComponent(new ComponentName(APPLICATION_ID, MainActivity.class.getName()));
            activity.startActivity(intent);
            return false;
        });
    }

}
