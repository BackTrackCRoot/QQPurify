package me.zpp0196.qqsimple.hook.base;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import me.zpp0196.qqsimple.hook.comm.Ids;
import me.zpp0196.qqsimple.hook.util.HookUtil;
import me.zpp0196.qqsimple.hook.util.XPrefs;

import static de.robv.android.xposed.XposedHelpers.getStaticIntField;
import static me.zpp0196.qqsimple.hook.comm.Classes.R$drawable;

/**
 * Created by zpp0196 on 2018/3/18.
 */

public abstract class BaseHook {

    // 是否已经Hook过View
    private volatile boolean hasHookedDrawable = false;
    // 把要Hook的view的ID全部提前保存
    private Set<Integer> drawablesToHide = new HashSet<>();

    public abstract void init();

    protected void log(String msg) {
        HookUtil.log(getClass(), msg);
    }

    protected void log(String format, Object... args) {
        HookUtil.log(getClass(), format, args);
    }

    private int getDrawableIdInQQ(String name) {
        Integer id = Ids.getId(name);
        if (id != null && id != 0) {
            return id;
        }
        try {
            return getStaticIntField(R$drawable, name);
        } catch (Throwable e) {
            log("Can't find the drawable of name: %s!", name);
            return 0;
        }
    }

    protected void hideView(View view, String key) {
        if (view != null && getBool(key)) {
            view.setVisibility(View.GONE);
        }
    }

    protected void hideDrawable(String name) {
        hideDrawable(getDrawableIdInQQ(name));
    }

    private void hideDrawable(int id) {
        if (id == 0) {
            return;
        }
        if (!drawablesToHide.contains(id)) {
            drawablesToHide.add(id);
        }
        if (!hasHookedDrawable) {
            hasHookedDrawable = true;
            hookImageView();
        }
    }

    private void hookImageView() {
        findAndHookMethod(ImageView.class, "setImageResource", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                int id = Integer.parseInt(param.args[0].toString());
                if (drawablesToHide.contains(id)) {
                    param.setResult(null);
                }
            }
        });
    }

    protected <T> T getObject(Object obj, Class<?> type, String name) {
        return getObject(obj.getClass(), type, name, obj);
    }

    protected <T> T getObject(Class clazz, Class<?> type, String name) {
        return getObject(clazz, type, name, null);
    }

    @SuppressWarnings ("unchecked")
    protected <T> T getObject(Class clazz, Class<?> type, String name, Object obj) {
        try {
            Field field = findField(clazz, type, name);
            return field == null ? null : (T) field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    protected Field findField(Class<?> clazz, Class<?> type, String name) {
        if (clazz != null && type != null && !name.isEmpty()) {
            Class<?> clz = clazz;
            do {
                for (Field field : clz.getDeclaredFields()) {
                    if (field.getType() == type && field.getName()
                            .equals(name)) {
                        field.setAccessible(true);
                        return field;
                    }
                }
            } while ((clz = clz.getSuperclass()) != null);
            log("Can't find the field of type: %s and name: %s in class: %s!", type.getName(), name, clazz.getName());
        }
        return null;
    }

    protected void hookMethod(@Nullable Member hookMethod, XC_MethodHook callback) {
        if (hookMethod == null || callback == null) {
            return;
        }
        XposedBridge.hookMethod(hookMethod, callback);
    }

    protected Method findMethodIfExists(Class<?> clazz, Class<?> returnType, String methodName, Class<?>... parameterTypes) {
        if (clazz != null && returnType != null && !methodName.isEmpty()) {
            Class<?> clz = clazz;
            do {
                Method[] methods = XposedHelpers.findMethodsByExactParameters(clazz, returnType, parameterTypes);
                for (Method method : methods) {
                    if (method.getName()
                            .equals(methodName)) {
                        return method;
                    }
                }
            } while ((clz = clz.getSuperclass()) != null);
            log("Can't find the method of returnType: %s and methodName: %s(parameterTypes.length: %s) in class: %s!", returnType.getName(), methodName, parameterTypes.length, clazz.getName());
        }
        return null;
    }

    protected void findAndHookMethod(@Nullable Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        if (clazz == null || methodName.isEmpty()) {
            return;
        }
        for (Object obj : parameterTypesAndCallback) {
            if (obj == null) {
                return;
            }
        }
        try {
            XposedHelpers.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
        } catch (Throwable e) {
            HookUtil.log(e);
        }
    }

    protected void findAndHookConstructor(@Nullable Class<?> clazz, Object... parameterTypesAndCallback) {
        if (clazz == null) {
            return;
        }
        for (Object obj : parameterTypesAndCallback) {
            if (obj == null) {
                return;
            }
        }
        try {
            XposedHelpers.findAndHookConstructor(clazz, parameterTypesAndCallback);
        } catch (Throwable e) {
            HookUtil.log(e);
        }
    }

    protected XC_MethodHook replaceNull(String key) {
        return replaceObj(null, key);
    }

    protected XC_MethodHook replaceFalse(String key) {
        return replaceObj(false, key);
    }

    protected XC_MethodHook replaceObj(Object result, String key) {
        return getBool(key) ? XC_MethodReplacement.returnConstant(result) : null;
    }

    protected XC_MethodHook hideView(Class<?> type, String name, String key) {
        return getBool(key) ? new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                hideView(param.thisObject, type, name);
            }
        } : null;
    }

    protected void hideView(Object obj, Class<?> type, String name) {
        View view = getObject(obj, type, name);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    protected void hideView(Object obj, Class<?> type, String name, String key) {
        if (getBool(key)) {
            hideView(obj, type, name);
        }
    }

    protected XC_MethodHook setFieldNullAfterMethod(Class<?> clazz, Class<?> type, String name, String key) {
        return getBool(key) ? new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Field field = findField(clazz, type, name);
                if (field != null) {
                    field.set(param.thisObject, null);
                }
            }
        } : null;
    }

    protected XC_MethodHook setFieldBooleanBeforeMethod(String name,String key,boolean value) {
        return getBool(key) ? new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.setBooleanField(param,name,value);
            }
        } : null;
    }

    protected boolean getBool(String key) {
        return XPrefs.getBoolean(key);
    }
}
