package me.zpp0196.qqsimple.hook.comm;

import java.util.HashMap;

/**
 * Created by zpp0196 on 2018/6/17 0017.
 */

public class Maps {

    public static HashMap<String, String[]> panelItem;
    public static HashMap<String, String> sidebarItem;
    public static HashMap<String, String> popouItem;
    public static HashMap<String, String> settingItem;
    public static HashMap<String, String> qzoneMenuItem;
    public static HashMap<String, String> qzoneNavItem;

    public static void init() {
        if (panelItem == null) {
            panelItem = new HashMap<>();
            panelItem.put("hide_chat_toolbar_voice", new String[]{"a", "b", "c", "d", "e", "f"});
            panelItem.put("hide_chat_toolbar_pic", new String[]{"g", "h", "i"});
            panelItem.put("hide_chat_toolbar_camera", new String[]{"j", "k"});
            panelItem.put("hide_chat_toolbar_redPacket", new String[]{"q"});
            panelItem.put("hide_chat_toolbar_poke", new String[]{"y"});
            panelItem.put("hide_chat_toolbar_gif", new String[]{"z", "C", "D"});
        }
        if (sidebarItem == null) {
            sidebarItem = new HashMap<>();
            sidebarItem.put("hide_sidebar_vip", "会员");
            sidebarItem.put("hide_sidebar_pocket", "钱包");
            sidebarItem.put("hide_sidebar_myFavorites", "收藏");
            sidebarItem.put("hide_sidebar_myFiles", "文件");
            sidebarItem.put("hide_sidebar_myPhotos", "相册");
            sidebarItem.put("hide_sidebar_dressUp", "装扮");
            sidebarItem.put("hide_sidebar_myCards", "名片");
            sidebarItem.put("hide_sidebar_myVideos", "视频");
            sidebarItem.put("hide_sidebar_kingCard", "流量");
        }
        if (popouItem == null) {
            popouItem = new HashMap<>();
            popouItem.put("hide_popup_multiChat", "建群");
            popouItem.put("hide_popup_add", "加好友");
            popouItem.put("hide_popup_sweep", "扫一扫");
            popouItem.put("hide_popup_face2face", "快传");
            popouItem.put("hide_popup_pay", "付款");
            popouItem.put("hide_popup_shoot", "拍摄");
            popouItem.put("hide_popup_videoDance", "舞室");
        }
        if (settingItem == null) {
            settingItem = new HashMap<>();
            settingItem.put("hide_setting_newXman", "达人");
            settingItem.put("hide_setting_msgNotify", "通知");
            settingItem.put("hide_setting_msgHistory", "记录");
            settingItem.put("hide_setting_qqClean", "清理");
            settingItem.put("hide_setting_deviceSecurity", "安全");
            settingItem.put("hide_setting_privacy", "隐私");
            settingItem.put("hide_setting_assist", "辅助");
            settingItem.put("hide_setting_about", "关于");
        }
        if (qzoneMenuItem == null) {
            qzoneMenuItem = new HashMap<>();
            qzoneMenuItem.put("hide_qzone_plus_mood", "说说");
            qzoneMenuItem.put("hide_qzone_plus_album", "相册");
            qzoneMenuItem.put("hide_qzone_plus_shoot", "拍摄");
            qzoneMenuItem.put("hide_qzone_plus_signIn", "签到");
            qzoneMenuItem.put("hide_qzone_plus_redPacket", "小红包");
            qzoneMenuItem.put("hide_qzone_plus_live", "直播");
        }
        if (qzoneNavItem == null) {
            qzoneNavItem = new HashMap<>();
            qzoneNavItem.put("hide_qzone_nav_album", "相册");
            qzoneNavItem.put("hide_qzone_nav_mood", "说说");
            qzoneNavItem.put("hide_qzone_nav_dress", "个性化");
            qzoneNavItem.put("hide_qzone_nav_game", "小游戏");
            qzoneNavItem.put("hide_qzone_nav_video", "小视频");
            qzoneNavItem.put("hide_qzone_nav_news", "消息");
        }
    }
}
