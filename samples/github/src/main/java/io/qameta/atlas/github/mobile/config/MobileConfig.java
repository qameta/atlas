package io.qameta.atlas.github.mobile.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Converter;

import java.io.File;
import java.lang.reflect.Method;


/**
 * Read props from mobile.properties.
 */
@Sources("classpath:mobile.properties")
public interface MobileConfig extends Config {

    @DefaultValue("http://127.0.0.1:4723/wd/hub")
    @Key("appium.url")
    String url();

    @Key("appium.capability.platformName")
    String platformName();

    @Key("appium.capability.newCommandTimeout")
    Integer newCommandTimeout();

    @Key("android.capability.deviceName")
    String deviceName();

    @Key("android.capability.platformVersion")
    String platformVersion();

    @Key("android.capability.appPackage")
    String appPackage();

    @Key("android.capability.appActivity")
    String appActivity();

    @Key("android.capability.unicodeKeyboard")
    Boolean unicodeKeyboard();

    @Key("android.capability.resetKeyboard")
    Boolean resetKeyboard();

    @Key("android.capability.automationName")
    String automationName();

    @ConverterClass(FileConverter.class)
    @Key("android.capability.apk")
    File apkFile();

    @Key("ios.capability.deviceName")
    String deviceIOSName();

    @Key("ios.capability.platformVersion")
    String platformIOSVersion();

    @ConverterClass(FileConverter.class)
    @Key("ios.capability.app")
    File appFile();

    /**
     * Converter to {@link File}.
     */
    class FileConverter implements Converter<File> {
        @Override
        public File convert(Method method, String name) {
            return new File("src/test/resources/mobile-applications/".concat(name)).getAbsoluteFile();
        }
    }
}


