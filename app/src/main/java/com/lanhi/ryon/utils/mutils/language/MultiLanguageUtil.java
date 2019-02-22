package com.lanhi.ryon.utils.mutils.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import com.lanhi.ryon.utils.constant.SPConstants;
import com.lanhi.ryon.utils.mutils.SPUtils;
import com.lanhi.vgo.driver.App;
import com.lanhi.vgo.driver.R;
import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

/**
 * 多语言切换的帮助类
 * http://blog.csdn.net/finddreams
 */
public class MultiLanguageUtil {

    private static final String TAG = "MultiLanguageUtil";
    private static MultiLanguageUtil instance;


    public static void init() {
        if (instance == null) {
            synchronized (MultiLanguageUtil.class) {
                if (instance == null) {
                    instance = new MultiLanguageUtil();
                }
            }
        }
    }

    public static MultiLanguageUtil getInstance() {
        if (instance == null) {
            throw new IllegalStateException("You must be init MultiLanguageUtil first");
        }
        return instance;
    }

    /**
     * 设置语言
     */
    public void setConfiguration() {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = App.getInstance().getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(targetLocale);
        } else {
            configuration.locale = targetLocale;
        }
        Resources resources = App.getInstance().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);//语言更换生效的代码!
    }

    /**
     * 如果不是英文、简体中文、繁体中文，默认返回简体中文
     *
     * @return
     */
    private Locale getLanguageLocale() {
        int languageType = SPUtils.getInstance(SPConstants.LANGUAGE.NAME).getInt(SPConstants.LANGUAGE.LANGUAGETYPE, 0);
        if (languageType == LanguageType.LANGUAGE_FOLLOW_SYSTEM) {
            Locale sysLocale= getSysLocale();
            return sysLocale;
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return new Locale("en", "US");
        } else if (languageType == LanguageType.LANGUAGE_CHINESE) {
            return new Locale("zh", "CN");
        } else if (languageType == LanguageType.LANGUAGE_ES) {
            return new Locale("es", "US");
        }
        getSystemLanguage(getSysLocale());
        Log.e(TAG, "getLanguageLocale" + languageType + languageType);
        return Locale.SIMPLIFIED_CHINESE;
    }

    private String getSystemLanguage(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();

    }

    //以上获取方式需要特殊处理一下
    public Locale getSysLocale() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale;
    }

    /**
     * 更新语言
     *
     * @param languageType
     */
    public void updateLanguage(int languageType) {
        SPUtils.getInstance(SPConstants.LANGUAGE.NAME).put(SPConstants.LANGUAGE.LANGUAGETYPE, languageType);
        MultiLanguageUtil.getInstance().setConfiguration();
        EventBus.getDefault().post(new OnChangeLanguageEvent(languageType));
    }

    public String getLanguageName() {
        int languageType = SPUtils.getInstance(SPConstants.LANGUAGE.NAME).getInt(SPConstants.LANGUAGE.LANGUAGETYPE,LanguageType.LANGUAGE_FOLLOW_SYSTEM);
        if (languageType == LanguageType.LANGUAGE_CHINESE) {
            return App.getInstance().getResources().getString(R.string.language_zh);
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return App.getInstance().getResources().getString(R.string.language_en_rUs);
        } else if (languageType == LanguageType.LANGUAGE_ES) {
            return App.getInstance().getResources().getString(R.string.language_es_rUs);
        }
        return App.getInstance().getResources().getString(R.string.language_zh);
    }

    /**
     * 获取到用户保存的语言类型
     * @return
     */
    public int getLanguageType() {
        int languageType = SPUtils.getInstance(SPConstants.LANGUAGE.NAME).getInt(SPConstants.LANGUAGE.LANGUAGETYPE, LanguageType.LANGUAGE_FOLLOW_SYSTEM);
         if (languageType == LanguageType.LANGUAGE_CHINESE) {
            return LanguageType.LANGUAGE_CHINESE;
        } else if (languageType == LanguageType.LANGUAGE_EN) {
            return LanguageType.LANGUAGE_EN;
        } else if (languageType == LanguageType.LANGUAGE_ES) {
           return LanguageType.LANGUAGE_ES;
        }
        Log.e(TAG, "getLanguageType" + languageType);
        return languageType;
    }

    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            MultiLanguageUtil.getInstance().setConfiguration();
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale=getInstance().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
}
