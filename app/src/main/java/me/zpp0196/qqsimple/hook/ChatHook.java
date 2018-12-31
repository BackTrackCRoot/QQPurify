package me.zpp0196.qqsimple.hook;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Pattern;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import me.zpp0196.qqsimple.hook.base.BaseHook;

import static me.zpp0196.qqsimple.hook.comm.Classes.AIOPanelUtiles;
import static me.zpp0196.qqsimple.hook.comm.Classes.AbstractChatItemBuilder$ViewHolder;
import static me.zpp0196.qqsimple.hook.comm.Classes.AioAnimationConfigHelper;
import static me.zpp0196.qqsimple.hook.comm.Classes.BaseBubbleBuilder$ViewHolder;
import static me.zpp0196.qqsimple.hook.comm.Classes.BaseChatItemLayout;
import static me.zpp0196.qqsimple.hook.comm.Classes.BaseChatPie;
import static me.zpp0196.qqsimple.hook.comm.Classes.BubbleManager;
import static me.zpp0196.qqsimple.hook.comm.Classes.ChatMessage;
import static me.zpp0196.qqsimple.hook.comm.Classes.EmoticonMainPanel;
import static me.zpp0196.qqsimple.hook.comm.Classes.EmoticonManager;
import static me.zpp0196.qqsimple.hook.comm.Classes.FontManager;
import static me.zpp0196.qqsimple.hook.comm.Classes.GatherContactsTips;
import static me.zpp0196.qqsimple.hook.comm.Classes.GrayTipsItemBuilder;
import static me.zpp0196.qqsimple.hook.comm.Classes.MedalNewsItemBuilder;
import static me.zpp0196.qqsimple.hook.comm.Classes.MessageRecord;
import static me.zpp0196.qqsimple.hook.comm.Classes.OnLongClickAndTouchListener;
import static me.zpp0196.qqsimple.hook.comm.Classes.PanelIconLinearLayout;
import static me.zpp0196.qqsimple.hook.comm.Classes.QzoneFeedItemBuilder;
import static me.zpp0196.qqsimple.hook.comm.Classes.RichStatItemBuilder;
import static me.zpp0196.qqsimple.hook.comm.Classes.SougouInputGrayTips;
import static me.zpp0196.qqsimple.hook.comm.Classes.TextItemBuilder;
import static me.zpp0196.qqsimple.hook.comm.Classes.TextPreviewActivity;
import static me.zpp0196.qqsimple.hook.comm.Classes.VipSpecialCareGrayTips;
import static me.zpp0196.qqsimple.hook.comm.Maps.panelItem;
import static me.zpp0196.qqsimple.hook.util.HookUtil.isMoreThan732;
import static me.zpp0196.qqsimple.hook.util.HookUtil.isMoreThan760;

/**
 * Created by zpp0196 on 2018/3/11.
 */

class ChatHook extends BaseHook {

    @Override
    public void init() {
        hideEmotion();
        hidePanelLinearLayout();
        hideFontEffect();
        hideItemLayout();
        // 隐藏嗨爆字体按钮
        findAndHookMethod(BaseChatPie, "afterTextChanged",Editable.class, hideView(ImageButton.class, "a", "hide_chat_hiBoomFont"));
        // 隐藏好友新动态
        hideItemBuilder(QzoneFeedItemBuilder, "hide_chat_newFeed");
        // 隐藏好友新签名
        hideItemBuilder(RichStatItemBuilder, "hide_chat_newSignature");
        // 隐藏取消隐藏不常用联系人提示
        hideGrayTips(GatherContactsTips, "hide_chatTip_unusualContacts");
        // 隐藏设置特别消息提示音
        hideGrayTips(VipSpecialCareGrayTips, "hide_chatTip_specialCare");
        // 隐藏搜狗输入法广告
        hideGrayTips(SougouInputGrayTips, "hide_chatTip_sougouInput");
        // 隐藏会员相关广告
        hideGrayTipsItem("hide_chatTip_vipAd", ".+会员.+");
        if (isMoreThan732()) {
            // 隐藏好友获得了新徽章
            hideItemBuilder(MedalNewsItemBuilder, "hide_chat_newBadge");
        }
    }

    private void hideItemLayout() {
        // 隐藏头像挂件
        findAndHookMethod(BaseChatItemLayout, "setPendantImage", Drawable.class, replaceNull("hide_chat_avatarPendant"));

        // 隐藏个性气泡
        Method method = findMethodIfExists(BubbleManager, File.class, "a");
        hookMethod(method, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (getBool("hide_chat_bubble")) {
                    String oldDir = ((File) param.getResult()).getAbsolutePath();
                    File file = new File(oldDir.replace("bubble_info", "imei"));
                    param.setResult(file);
                }
            }
        });
    }

    private void hideEmotion() {
        if (isMoreThan732()) {
            // 隐藏推荐表情
            findAndHookMethod(EmoticonManager, "a", boolean.class, int.class, boolean.class, replaceObj(new ArrayList<>(), "hide_chat_hotExpression"));
        }
        // 隐藏表情联想
        findAndHookMethod(BaseChatPie, "a", Editable.class, replaceNull("hide_chat_associatedExpression"));
        // 隐藏表情商城
        findAndHookMethod(EmoticonMainPanel, "d", int.class, hideView(ImageView.class, "a", "hide_btn_more_emoticon"));
        // 隐藏表情掉落
        findAndHookMethod(AioAnimationConfigHelper, "a", replaceNull("hide_chat_dropExpression"));
    }

    /**
     * 隐藏聊天界面底部工具栏
     */
    private void hidePanelLinearLayout() {
        Method method = findMethodIfExists(PanelIconLinearLayout, void.class, "a");
        hookMethod(method, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                ArrayList arrayList = getObject(param.thisObject, ArrayList.class, "a");
                if (arrayList == null || arrayList.isEmpty())
                    return;
                for (String key : panelItem.keySet()) {
                    String[] values = panelItem.get(key);
                    for (String value : values) {
                        int[] i = getObject(AIOPanelUtiles, int[].class, value);
                        if (getBool(key))
                            arrayList.remove(i);
                    }
                }
            }
        });
    }

    /**
     * 隐藏字体特效
     */
    private void hideFontEffect() {
        findAndHookMethod(TextItemBuilder, "a", BaseBubbleBuilder$ViewHolder, ChatMessage, replaceNull("hide_chat_fontEffects"));
        findAndHookMethod(TextPreviewActivity, "a", int.class, replaceNull("hide_chat_fontEffects"));

        if (isMoreThan760()) {
            // 隐藏大字体特效
            Method method = findMethodIfExists(FontManager, boolean.class, "b", ChatMessage);
            hookMethod(method, replaceFalse("hide_chat_fontEffects"));
        }
    }

    private void hideItemBuilder(Class<?> ItemBuilder, String key) {
        findAndHookMethod(ItemBuilder, "a", MessageRecord, AbstractChatItemBuilder$ViewHolder, View.class, LinearLayout.class, OnLongClickAndTouchListener, replaceNull(key));
    }

    private void hideGrayTips(Class<?> Tips, String key) {
        findAndHookMethod(Tips, "a", Object[].class, replaceNull(key));
    }

    void hideGrayTipsItem(String key, String... regex) {
        if (!getBool(key))
            return;
        findAndHookMethod(GrayTipsItemBuilder, "a", MessageRecord, AbstractChatItemBuilder$ViewHolder, View.class, LinearLayout.class, OnLongClickAndTouchListener, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Field field = XposedHelpers.findFieldIfExists(MessageRecord, "msg");
                String msg = (String) field.get(param.args[0]);
                for (String str : regex) {
                    if (Pattern.matches(str, msg)) {
                        param.setResult(null);
                    }
                }
            }
        });
    }
}
