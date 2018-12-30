package me.zpp0196.qqsimple.hook.comm;

import me.zpp0196.qqsimple.hook.util.HookUtil;

import static me.zpp0196.qqsimple.Common.PACKAGE_NAME_QQ;
import static me.zpp0196.qqsimple.hook.util.HookUtil.isMoreThan763;

/**
 * Created by zpp0196 on 2018/4/27 0027.
 */

public class Classes {

    public static Class<?> R$drawable;
    public static Class<?> AbstractChatItemBuilder$ViewHolder;
    public static Class<?> AbstractGalleryScene;
    public static Class<?> AioAnimationConfigHelper;
    public static Class<?> AIOImageProviderService;
    public static Class<?> AIOPanelUtiles;
    public static Class<?> ApolloManager$CheckApolloInfoResult;
    public static Class<?> BannerManager;
    public static Class<?> BaseActivity;
    public static Class<?> BaseBubbleBuilder$ViewHolder;
    //public static Class<?> CardController;
    public static Class<?> BaseChatItemLayout;
    public static Class<?> BaseChatPie;
    public static Class<?> BaseTroopChatPie;
    public static Class<?> BubbleManager;
    public static Class<?> BusinessInfoCheckUpdate$RedTypeInfo;
    public static Class<?> Card;
    public static Class<?> ChatActivityUtils;
    public static Class<?> ChatMessage;
    //public static Class<?> CommonCardEntry;
    public static Class<?> Conversation;
    public static Class<?> ConversationNowController;
    public static Class<?> Contacts;
    public static Class<?> ContactUtils;
    public static Class<?> CoreService;
    public static Class<?> CoreService$KernelService;
    public static Class<?> CountDownProgressBar;
    public static Class<?> EmoticonMainPanel;
    public static Class<?> EmoticonManager;
    public static Class<?> FileManagerUtil;
    public static Class<?> FriendFragment;
    public static Class<?> FontManager;
    public static Class<?> FontSettingManager;
    public static Class<?> FrameHelperActivity;
    public static Class<?> GatherContactsTips;
    public static Class<?> GrayTipsItemBuilder;
    public static Class<?> HotChatFlashPicActivity;
    public static Class<?> ItemBuilderFactory;
    public static Class<?> Leba;
    public static Class<?> LebaQZoneFacePlayHelper;
    public static Class<?> LocalSearchBar;
    public static Class<?> MainEntryAni;
    public static Class<?> MainFragment;
    public static Class<?> MedalNewsItemBuilder;
    public static Class<?> MessageForDeliverGiftTips;
    public static Class<?> MessageForPic;
    public static Class<?> MessageInfo;
    public static Class<?> MessageRecord;
    public static Class<?> MessageRecordFactory;
    public static Class<?> OnLongClickAndTouchListener;
    public static Class<?> PanelIconLinearLayout;
    public static Class<?> PicItemBuilder;
    public static Class<?> PicItemBuilder$Holder;
    public static Class<?> PopupMenuDialog;
    public static Class<?> PopupMenuDialog$MenuItem;
    public static Class<?> PopupMenuDialog$OnClickActionListener;
    public static Class<?> PopupMenuDialog$OnDismissListener;
    public static Class<?> QQAppInterface;
    public static Class<?> QQMessageFacade;
    public static Class<?> QQSettingMe;
    public static Class<?> QQSettingSettingActivity;
    public static Class<?> QzoneFeedItemBuilder;
    public static Class<?> QzonePluginProxyActivity;
    public static Class<?> QZoneHelper;
    public static Class<?> RecentBaseData;
    public static Class<?> RecentEfficientItemBuilder;
    public static Class<?> RecentOptPopBar;
    public static Class<?> RichStatItemBuilder;
    public static Class<?> SimpleSlidingIndicator;
    public static Class<?> SougouInputGrayTips;
    public static Class<?> TextItemBuilder;
    public static Class<?> TextPreviewActivity;
    public static Class<?> TListView;
    public static Class<?> TroopAssistantActivity;
    public static Class<?> TroopEnterEffectController;
    public static Class<?> TroopGiftAnimationController;
    public static Class<?> UpgradeController;
    public static Class<?> UpgradeDetailWrapper;
    public static Class<?> URLImageView;
    public static Class<?> VipSpecialCareGrayTips;
    public static Class<?> XEditTextEx;
    public static Class<?> CTEntryMng;
    public static Class<?> CardViewController;

    private static ClassLoader qqClassLoader;

    public static void initClass(ClassLoader classLoader) {
        qqClassLoader = classLoader;
        if (R$drawable == null)
            R$drawable = findClassInQQ(".R$drawable");
        if (AbstractChatItemBuilder$ViewHolder == null)
            AbstractChatItemBuilder$ViewHolder = findClassInQQ(".activity.aio.AbstractChatItemBuilder$ViewHolder");
        if (AbstractGalleryScene == null)
            AbstractGalleryScene = findClassInQQ("com.tencent.common.galleryactivity.AbstractGalleryScene");
        if (AioAnimationConfigHelper == null)
            AioAnimationConfigHelper = findClassInQQ(".activity.aio.anim.AioAnimationConfigHelper");
        if (AIOImageProviderService == null)
            AIOImageProviderService = findClassInQQ(".activity.aio.photo.AIOImageProviderService");
        if (AIOPanelUtiles == null)
            AIOPanelUtiles = findClassInQQ(".activity.aio.panel.AIOPanelUtiles");
        if (ApolloManager$CheckApolloInfoResult == null)
            ApolloManager$CheckApolloInfoResult = findClassInQQ(".apollo.ApolloManager$CheckApolloInfoResult");
        if (BannerManager == null)
            BannerManager = findClassInQQ(".activity.recent.BannerManager");
        if (BaseActivity == null)
            BaseActivity = findClassInQQ(".app.BaseActivity");
        if (BaseBubbleBuilder$ViewHolder == null)
            BaseBubbleBuilder$ViewHolder = findClassInQQ(".activity.aio.BaseBubbleBuilder$ViewHolder");
//        if (CardController == null)
//            CardController = findClassInQQ(".activity.contacts.base.CardController");
        if (BaseChatItemLayout == null)
            BaseChatItemLayout = findClassInQQ(".activity.aio.BaseChatItemLayout");
        if (BaseChatPie == null)
            BaseChatPie = findClassInQQ(".activity.BaseChatPie");
        if (BaseTroopChatPie == null)
            BaseTroopChatPie = findClassInQQ(".activity.aio.rebuild.BaseTroopChatPie");
        if (BubbleManager == null)
            BubbleManager = findClassInQQ(".bubble.BubbleManager");
        if (BusinessInfoCheckUpdate$RedTypeInfo == null)
            BusinessInfoCheckUpdate$RedTypeInfo = findClassInQQ("com.tencent.pb.getbusiinfo.BusinessInfoCheckUpdate$RedTypeInfo");
        if (Card == null)
            Card = findClassInQQ(".data.Card");
        if (ChatActivityUtils == null)
            ChatActivityUtils = findClassInQQ(".activity.ChatActivityUtils");
        if (ChatMessage == null)
            ChatMessage = findClassInQQ(".data.ChatMessage");
//        if (CommonCardEntry  == null)
//            CommonCardEntry = findClassInQQ(".activity.contacts.base.CommonCardEntry");
        if(CTEntryMng == null)
            CTEntryMng = findClassInQQ(".activity.contacts.topentry.CTEntryMng");
        if(CardViewController == null)
            CardViewController = findClassInQQ(".activity.contacts.base.CardViewController");
        if (Conversation == null)
            Conversation = findClassInQQ(".activity.Conversation");
        if (ConversationNowController == null)
            ConversationNowController = findClassInQQ(".now.enter.ConversationNowController");
        if (Contacts == null)
            Contacts = findClassInQQ(".activity.Contacts");
        if (ContactUtils == null)
            ContactUtils = findClassInQQ(".utils.ContactUtils");
        if (CoreService == null)
            CoreService = findClassInQQ(".app.CoreService");
        if (CoreService$KernelService == null)
            CoreService$KernelService = findClassInQQ(".app.CoreService$KernelService");
        if (CountDownProgressBar == null)
            CountDownProgressBar = findClassInQQ("com.tencent.widget.CountDownProgressBar");
        if (EmoticonMainPanel == null)
            EmoticonMainPanel = findClassInQQ(".emoticonview.EmoticonMainPanel");
        if (EmoticonManager == null)
            EmoticonManager = findClassInQQ(".model.EmoticonManager");
        if (FileManagerUtil == null)
            FileManagerUtil = findClassInQQ(".filemanager.util.FileManagerUtil");
        if (FriendFragment == null)
            FriendFragment = findClassInQQ(".activity.contacts.fragment.FriendFragment");
        if (FontManager == null)
            FontManager = findClassInQQ("com.etrump.mixlayout.FontManager");
        if (FontSettingManager == null)
            FontSettingManager = findClassInQQ(".app.FontSettingManager");
        if (FrameHelperActivity == null)
            FrameHelperActivity = findClassInQQ(".app.FrameHelperActivity");
        if (GatherContactsTips == null)
            GatherContactsTips = findClassInQQ(".activity.aio.tips.GatherContactsTips");
        if (GrayTipsItemBuilder == null)
            GrayTipsItemBuilder = findClassInQQ(".activity.aio.item.GrayTipsItemBuilder");
        if (HotChatFlashPicActivity == null)
            HotChatFlashPicActivity = findClassInQQ(".dating.HotChatFlashPicActivity");
        if (ItemBuilderFactory == null)
            ItemBuilderFactory = findClassInQQ(".activity.aio.item.ItemBuilderFactory");
        if (Leba == null)
            Leba = findClassInQQ(".activity.Leba");
        if (LebaQZoneFacePlayHelper == null)
            LebaQZoneFacePlayHelper = findClassInQQ(".activity.LebaQZoneFacePlayHelper");
        if (LocalSearchBar == null)
            LocalSearchBar = findClassInQQ(".activity.recent.LocalSearchBar");
        if (MainEntryAni == null && isMoreThan763())
            MainEntryAni = findClassInQQ(".ar.config.MainEntryAni");
        if (MainFragment == null)
            MainFragment = findClassInQQ(".activity.MainFragment");
        if (MedalNewsItemBuilder == null)
            MedalNewsItemBuilder = findClassInQQ(".activity.aio.item.MedalNewsItemBuilder");
        if (MessageForDeliverGiftTips == null)
            MessageForDeliverGiftTips = findClassInQQ(".data.MessageForDeliverGiftTips");
        if (MessageForPic == null)
            MessageForPic = findClassInQQ(".data.MessageForPic");
        if (MessageInfo == null)
            MessageInfo = findClassInQQ(".troop.data.MessageInfo");
        if (MessageRecord == null)
            MessageRecord = findClassInQQ(".data.MessageRecord");
        if (MessageRecordFactory == null)
            MessageRecordFactory = findClassInQQ(".service.message.MessageRecordFactory");
        if (OnLongClickAndTouchListener == null)
            OnLongClickAndTouchListener = findClassInQQ(".activity.aio.OnLongClickAndTouchListener");
        if (PanelIconLinearLayout == null)
            PanelIconLinearLayout = findClassInQQ(".activity.aio.panel.PanelIconLinearLayout");
        if (PicItemBuilder == null)
            PicItemBuilder = findClassInQQ(".activity.aio.item.PicItemBuilder");
        if (PicItemBuilder$Holder == null)
            PicItemBuilder$Holder = findClassInQQ(".activity.aio.item.PicItemBuilder$Holder");
        if (PopupMenuDialog == null)
            PopupMenuDialog = findClassInQQ("com.tencent.widget.PopupMenuDialog");
        if (PopupMenuDialog$MenuItem == null)
            PopupMenuDialog$MenuItem = findClassInQQ("com.tencent.widget.PopupMenuDialog$MenuItem");
        if (PopupMenuDialog$OnClickActionListener == null)
            PopupMenuDialog$OnClickActionListener = findClassInQQ("com.tencent.widget.PopupMenuDialog$OnClickActionListener");
        if (PopupMenuDialog$OnDismissListener == null)
            PopupMenuDialog$OnDismissListener = findClassInQQ("com.tencent.widget.PopupMenuDialog$OnDismissListener");
        if (QQAppInterface == null)
            QQAppInterface = findClassInQQ(".app.QQAppInterface");
        if (QQMessageFacade == null)
            QQMessageFacade = findClassInQQ(".app.message.QQMessageFacade");
        if (QQSettingMe == null)
            QQSettingMe = findClassInQQ(".activity.QQSettingMe");
        if (QQSettingSettingActivity == null)
            QQSettingSettingActivity = findClassInQQ(".activity.QQSettingSettingActivity");
        if (QzoneFeedItemBuilder == null)
            QzoneFeedItemBuilder = findClassInQQ(".activity.aio.item.QzoneFeedItemBuilder");
        if (QzonePluginProxyActivity == null)
            QzonePluginProxyActivity = findClassInQQ("cooperation.qzone.QzonePluginProxyActivity");
        if (QZoneHelper == null)
            QZoneHelper = findClassInQQ("cooperation.qzone.QZoneHelper");
        if (RecentBaseData == null)
            RecentBaseData = findClassInQQ(".activity.recent.RecentBaseData");
        if (RecentEfficientItemBuilder == null)
            RecentEfficientItemBuilder = findClassInQQ(".activity.recent.RecentEfficientItemBuilder");
        if (RecentOptPopBar == null)
            RecentOptPopBar = findClassInQQ(".activity.recent.RecentOptPopBar");
        if (RichStatItemBuilder == null)
            RichStatItemBuilder = findClassInQQ(".activity.aio.item.RichStatItemBuilder");
        if (SimpleSlidingIndicator == null)
            SimpleSlidingIndicator = findClassInQQ(".activity.contacts.view.SimpleSlidingIndicator");
        if (SougouInputGrayTips == null)
            SougouInputGrayTips = findClassInQQ(".activity.aio.tips.SougouInputGrayTips");
        if (TextItemBuilder == null)
            TextItemBuilder = findClassInQQ(".activity.aio.item.TextItemBuilder");
        if (TextPreviewActivity == null)
            TextPreviewActivity = findClassInQQ(".activity.TextPreviewActivity");
        if (TListView == null)
            TListView = findClassInQQ("com.tencent.widget.ListView");
        if (TroopAssistantActivity == null)
            TroopAssistantActivity = findClassInQQ(".activity.TroopAssistantActivity");
        if (TroopEnterEffectController == null)
            TroopEnterEffectController = findClassInQQ(".troop.enterEffect.TroopEnterEffectController");
        if (TroopGiftAnimationController == null)
            TroopGiftAnimationController = findClassInQQ(".troopgift.TroopGiftAnimationController");
        if (UpgradeController == null)
            UpgradeController = findClassInQQ(".app.upgrade.UpgradeController");
        if (UpgradeDetailWrapper == null)
            UpgradeDetailWrapper = findClassInQQ(".app.upgrade.UpgradeDetailWrapper");
        if (URLImageView == null)
            URLImageView = findClassInQQ("com.tencent.image.URLImageView");
        if (VipSpecialCareGrayTips == null)
            VipSpecialCareGrayTips = findClassInQQ(".activity.aio.tips.VipSpecialCareGrayTips");
        if (XEditTextEx == null)
            XEditTextEx = findClassInQQ("com.tencent.widget.XEditTextEx");
    }

    private static Class<?> findClassInQQ(String className) {
        if (qqClassLoader == null || className.isEmpty()) {
            return null;
        }
        if (className.startsWith(".")) {
            className = PACKAGE_NAME_QQ + className;
        }
        try {
            return qqClassLoader.loadClass(className);
        } catch (Throwable e) {
            if (!className.contains("com.tencent.mobileqq.R$")) {
                HookUtil.log("Classes", String.format("Can't find the Class of name: %s!", className));
            }
            return null;
        }
    }
}