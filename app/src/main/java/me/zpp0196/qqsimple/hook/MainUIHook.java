package me.zpp0196.qqsimple.hook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import me.zpp0196.qqsimple.hook.base.BaseHook;
import me.zpp0196.qqsimple.hook.util.HookUtil;

import static android.view.View.GONE;
import static me.zpp0196.qqsimple.hook.comm.Classes.BannerManager;
import static me.zpp0196.qqsimple.hook.comm.Classes.BaseActivity;
import static me.zpp0196.qqsimple.hook.comm.Classes.BusinessInfoCheckUpdate$RedTypeInfo;
import static me.zpp0196.qqsimple.hook.comm.Classes.CardController;
import static me.zpp0196.qqsimple.hook.comm.Classes.CommonCardEntry;
import static me.zpp0196.qqsimple.hook.comm.Classes.Contacts;
import static me.zpp0196.qqsimple.hook.comm.Classes.Conversation;
import static me.zpp0196.qqsimple.hook.comm.Classes.ConversationNowController;
import static me.zpp0196.qqsimple.hook.comm.Classes.FriendFragment;
import static me.zpp0196.qqsimple.hook.comm.Classes.Leba;
import static me.zpp0196.qqsimple.hook.comm.Classes.LebaQZoneFacePlayHelper;
import static me.zpp0196.qqsimple.hook.comm.Classes.LocalSearchBar;
import static me.zpp0196.qqsimple.hook.comm.Classes.MainEntryAni;
import static me.zpp0196.qqsimple.hook.comm.Classes.MainFragment;
import static me.zpp0196.qqsimple.hook.comm.Classes.PopupMenuDialog;
import static me.zpp0196.qqsimple.hook.comm.Classes.PopupMenuDialog$MenuItem;
import static me.zpp0196.qqsimple.hook.comm.Classes.PopupMenuDialog$OnClickActionListener;
import static me.zpp0196.qqsimple.hook.comm.Classes.PopupMenuDialog$OnDismissListener;
import static me.zpp0196.qqsimple.hook.comm.Classes.RecentBaseData;
import static me.zpp0196.qqsimple.hook.comm.Classes.RecentEfficientItemBuilder;
import static me.zpp0196.qqsimple.hook.comm.Classes.RecentOptPopBar;
import static me.zpp0196.qqsimple.hook.comm.Classes.SimpleSlidingIndicator;
import static me.zpp0196.qqsimple.hook.comm.Classes.TListView;
import static me.zpp0196.qqsimple.hook.comm.Classes.URLImageView;
import static me.zpp0196.qqsimple.hook.comm.Maps.popouItem;
import static me.zpp0196.qqsimple.hook.util.HookUtil.isMoreThan735;
import static me.zpp0196.qqsimple.hook.util.HookUtil.isMoreThan758;
import static me.zpp0196.qqsimple.hook.util.HookUtil.isMoreThan765;

/**
 * Created by zpp0196 on 2018/3/12.
 */

class MainUIHook extends BaseHook {

    @Override
    public void init() {
        hideMenuUAF();
        hideMainFragmentTab();
        hideDivider();
        hidePopupMenu();
        hideConversationContent();
        hideContactsContent();
        hideLebaContent();
    }

    /**
     * 隐藏菜单更新和反馈
     */
    private void hideMenuUAF() {
        if (!getBool("hide_menu_uaf"))
            return;
        findAndHookMethod(MainFragment, "r", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Dialog dialog = getObject(param.thisObject, Dialog.class, "b");
                LinearLayout layout = getObject(dialog, LinearLayout.class, "a");
                layout.getChildAt(0)
                        .setVisibility(GONE);
                layout.getChildAt(1)
                        .setVisibility(GONE);
            }
        });
    }

    /**
     * 隐藏底部分组
     */
    private void hideMainFragmentTab() {
        findAndHookMethod(MainFragment, "a", View.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                View[] views = getObject(param.thisObject, View[].class, "a");
                // 联系人
                hideView(views[2], "hide_tab_contact");
                // 动态
                hideView(views[3], "hide_tab_dynamic");
                // 看点
                hideView(views[6], "hide_tab_readinjoy");
            }
        });

        // 隐藏底部消息数量
        findAndHookMethod(MainFragment, "a", int.class, BusinessInfoCheckUpdate$RedTypeInfo, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                int i = (int) param.args[0];
                if ((i == 33 && getBool("hide_tab_num_contacts")) ||
                    (i == 34 && (getBool("hide_tab_num_dynamic")))) {
                    param.setResult(null);
                }
            }
        });

        // 隐藏消息列表底部消息数量
        findAndHookMethod(MainFragment, "a", int.class, int.class, Object.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                int i = (int) param.args[0];
                if (i == 32 && getBool("hide_tab_num_conversation")) {
                    param.setResult(null);
                }
            }
        });
    }

    /**
     * 隐藏分割线
     */
    private void hideDivider() {
        if(!getBool("hide_conversation_divider"))
            return;
        findAndHookMethod(RecentEfficientItemBuilder, "a", View.class, RecentBaseData, Context.class, Drawable.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                View args0 = (View) param.args[0];
                Object tag = args0.getTag();
                if(tag.getClass().getName().contains("RecentEfficientItemBuilder$RecentEfficientItemBuilderHolder")){
                    View view = getObject(tag, View.class, "f");
                    view.setBackground(null);
                }
            }
        });
    }

    /**
     * 隐藏消息列表右上角快捷入口内容
     */
    @SuppressWarnings ("unchecked")
    private void hidePopupMenu() {
        if (HookUtil.isMoreThan765()) {
            Method method = findMethodIfExists(RecentOptPopBar, View.class, "a");
            hookMethod(method, replaceNull("hide_popup_cmGame"));
        }

        // 隐藏消息列表右上角快捷入口
        findAndHookMethod(Conversation, "D", hideView(ImageView.class, "a", "hide_conversation_popupMenu"));

        // 隐藏AR足球
        if (MainEntryAni != null) {
            Method[] methods = MainEntryAni.getDeclaredMethods();
            for (Method method : methods) {
                Class<?>[] clazz = method.getParameterTypes();
                if (method.getName()
                            .equals("b") && method.getReturnType() == void.class &&
                    clazz.length == 1 && clazz[0].getClass() != MainEntryAni) {
                    hookMethod(method, replaceNull("hide_conversation_popupWorldCup"));
                    break;
                }
            }
        }

        // 隐藏Item
        findAndHookMethod(PopupMenuDialog, "a", Activity.class, List.class, PopupMenuDialog$OnClickActionListener, PopupMenuDialog$OnDismissListener, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                List list = (List) param.args[1];
                ArrayList needRemove = new ArrayList();
                for (Object item : list) {
                    String title = getObject(PopupMenuDialog$MenuItem, String.class, "a", item);
                    for (String key : popouItem.keySet()) {
                        String value = popouItem.get(key);
                        if (title.contains(value) && getBool(key)) {
                            needRemove.add(item);
                        }
                    }
                }
                if (needRemove.size() == list.size()) {
                    needRemove.remove(0);
                }
                list.removeAll(needRemove);
            }
        });
    }

    /**
     * 隐藏消息列表部分内容
     */
    private void hideConversationContent() {
        // 隐藏好友小视频
        findAndHookMethod(Conversation, "g", boolean.class, replaceNull("hide_conversation_video"));

        // 隐藏搜索框
        if (getBool("hide_conversation_search")) {
            findAndHookConstructor(LocalSearchBar, TListView, View.class, View.class, BaseActivity, View.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    View view = (View) param.args[4];
                    if (view == null)
                        return;
                    view.setVisibility(GONE);
                    ViewGroup listView = (ViewGroup) param.args[0];
                    Activity activity = (Activity) param.args[3];
                    int paddingTop = (int) (activity.getResources()
                                                    .getDisplayMetrics().density * 43 + 0.5f);
                    listView.setPadding(0, -paddingTop, 0, 0);
                }
            });
        }

        // 隐藏全民闯关入口
        if (!isMoreThan765()) {
            findAndHookMethod(ConversationNowController, "a", String.class, replaceNull("hide_conversation_nowController"));
        }
        findAndHookMethod(BannerManager, "e", View.class, replaceNull("hide_conversation_headAd"));
    }

    /**
     * 隐藏联系人界面部分内容
     */
    private void hideContactsContent() {
        // 隐藏坦白说
        findAndHookMethod(CardController, "a", int.class, CommonCardEntry, int.class, replaceNull("hide_contacts_slidCards"));
        findAndHookMethod(CardController, "a", int.class, CommonCardEntry, replaceNull("hide_contacts_slidCards"));

        findAndHookMethod(Contacts, "o", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                View newFriendEntry = getObject(param.thisObject, View.class, "a");
                View createTroopEntry = getObject(param.thisObject, View.class, "b");
                View searchBox = ((LinearLayout) (newFriendEntry.getParent())).getChildAt(0);
                // 隐藏搜索框
                hideView(searchBox, "hide_contacts_search");
                // TODO 联系人消息角标
                // 隐藏新朋友
                hideView(newFriendEntry, "hide_contacts_newFriend");
                // 隐藏创建群聊
                hideView(createTroopEntry, "hide_contacts_createTroop");
            }
        });
        hideContactsConstant();

        // 隐藏不常用联系人
        findAndHookMethod(FriendFragment, "i", hideView(View.class, "b", "hide_contacts_unusualContacts"));
        findAndHookMethod(FriendFragment, "j", hideView(View.class, "b", "hide_contacts_unusualContacts"));
    }

    /**
     * 隐藏联系人分组
     */
    private void hideContactsConstant() {
        findAndHookMethod(SimpleSlidingIndicator, "a", int.class, View.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                int i = (int) param.args[0];
                View v = (View) param.args[1];
                if ((i == 2 && getBool("hide_contacts_tab_device")) ||
                    (i == 3 && getBool("hide_contacts_tab_phone")) ||
                    (i == 4 && getBool("hide_contacts_tab_pub_account"))) {
                    v.setVisibility(GONE);
                }
            }
        });

        findAndHookMethod(SimpleSlidingIndicator, "a", int.class, boolean.class, boolean.class, new XC_MethodHook() {

            private MethodHookParam param;
            private int i;
            private int b;

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                this.i = (int) param.args[0];
                this.b = getObject(param.thisObject, int.class, "b");
                this.param = param;
                boolean isHideDevice = getBool("hide_contacts_tab_device");
                boolean isHidePhone = getBool("hide_contacts_tab_phone");
                boolean isHidePubAccount = getBool("hide_contacts_tab_pub_account");
                if (isHideDevice && !isHidePhone && !isHidePubAccount) {
                    f(2);
                }
                if (isHideDevice && isHidePhone && !isHidePubAccount) {
                    f(2, 3);
                }
                if (isHideDevice && !isHidePhone && isHidePubAccount) {
                    param.args[0] = (b == 1 && i == 2) ? 3 : (b == 3 && i == 4) ? 0 : param.args[0];
                }
                if (isHideDevice && isHidePhone && isHidePubAccount) {
                    if (i != 2 && i != 3 && i != 4) {
                        return;
                    }
                    m(1, 0);
                }
                if (!isHideDevice && isHidePhone && !isHidePubAccount) {
                    f(3);
                }
                if (!isHideDevice && isHidePhone && isHidePubAccount) {
                    f(3, 4);
                }
                if (!isHideDevice && !isHidePhone && isHidePubAccount) {
                    f(4);
                }
            }

            private void f(int pos) {
                if (i != pos) {
                    return;
                }
                m(pos - 1, pos != 4 ? pos + 1 : 0);
            }

            private void f(int pos1, int pos2) {
                if (i != pos1 && i != pos2) {
                    return;
                }
                m(pos1 - 1, pos2 != 4 ? pos2 + 1 : 0);
            }

            private void m(int left, int right) {
                param.args[0] = b == left ? right : b == right ? left : param.args[0];
            }

        });
    }

    /**
     * 隐藏动态界面部分内容
     */
    private void hideLebaContent() {
        // 隐藏大家都在搜
        if (isMoreThan735()) {
            findAndHookMethod(Leba, "a", List.class, replaceNull("hide_leba_hotWordSearch"));
        }

        // 隐藏搜索框
        Method method = findMethodIfExists(Leba, void.class, "b");
        hookMethod(method, hideView(LinearLayout.class, "a", "hide_leba_search"));

        findAndHookMethod(Leba, "w", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                final Object obj = param.thisObject;

                // 隐藏搜索框
                hideView(obj, LinearLayout.class, "a", "hide_leba_search");
                // 隐藏空间入口
                hideView(obj, View.class, "c", "hide_leba_qzone");
                // 隐藏附近的人入口
                hideView(obj, View.class, "d", "hide_leba_near");
                // 隐藏兴趣部落入口
                hideView(obj, View.class, "f", "hide_leba_tribal");

                // 隐藏空间头像提醒
                if (!isMoreThan758()) {
                    hideView(obj, ImageView.class, "b", "hide_leba_qzoneRemind");
                }

                if (getBool("hide_leba_qzoneRemind")) {
                    Field field = findField(Leba, TextView.class, "b");
                    field.set(param.thisObject, null);
                }

                // 隐藏附近的人提醒
                View nearSub = getObject(param.thisObject, ImageView.class, "d");
                hideView(nearSub, "hide_leba_nearRemind");

                // 隐藏兴趣部落提醒
                View tribalSub = getObject(param.thisObject, URLImageView, "a");
                hideView(tribalSub, "hide_leba_tribalRemind");
                if (getBool("hide_leba_tribalRemind")) {
                    Field field = findField(Leba, TextView.class, "d");
                    field.set(param.thisObject, null);
                }
            }
        });

        // 隐藏空间提醒
        if (isMoreThan758()) {
            findAndHookMethod(Leba, "w", setFieldNullAfterMethod(Leba, LebaQZoneFacePlayHelper, "a", "hide_leba_qzoneRemind"));
        }
    }
}
