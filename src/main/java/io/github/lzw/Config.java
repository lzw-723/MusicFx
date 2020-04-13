/*
 * @Author: lzw-723
 * @Date: 2020-02-19 16:29:34
 * @LastEditors: lzw-723
 * @LastEditTime: 2020-04-13 12:20:27
 * @Description: 配置信息
 * @FilePath: \MusicFx\src\main\java\io\github\lzw\Config.java
 */
package io.github.lzw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.lzw.service.SongOService.Type;
import io.github.lzw.util.FileUtil;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    private final static Config config = new Config();
    private Properties properties = new Properties();
    private SimpleStringProperty dir = new SimpleStringProperty();
    private SimpleFloatProperty volume = new SimpleFloatProperty();
    private SimpleObjectProperty<Type> type = new SimpleObjectProperty<>(Type.Netease);
    private SimpleBooleanProperty hotkeyAble = new SimpleBooleanProperty(true);

    public String getDir() {
        return dir.get();
    }

    public SimpleStringProperty dirProperty() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir.set(dir);
    }

    public float getVolume() {
        return volume.get();
    }

    public void setVolume(float volume) {
        this.volume.set(volume);
    }

    public SimpleFloatProperty volumeProperty() {
        return volume;
    }

    public Type getType() {
        return type.get();
    }

    public void setType(Type type) {
        this.type.set(type);
    }

    public SimpleObjectProperty<Type> typeProperty() {
        return type;
    }

    public boolean getHotkeyAble() {
        return hotkeyAble.get();
    }

    public void setHotkeyAble(boolean able) {
        hotkeyAble.set(able);
    }

    public SimpleBooleanProperty hotkeyAbleProperty() {
        return hotkeyAble;
    }

    private Config() {
        if (config != null) {
            throw new RuntimeException("单例异常");
        } else {
            try {
                init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() throws IOException {

        properties.load(new FileInputStream(getConfigFile()));
        setDir(properties.getProperty("dir", "请选择目录"));
        setVolume(Float.valueOf(properties.getProperty("volume", "0.3")));
        setType(Type.stringOf(properties.getProperty("type", "网易")));
        setHotkeyAble(Boolean.valueOf(properties.getProperty("hotkeyAble", "true")));
        dir.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                properties.setProperty("dir", newValue);
                properties.setProperty("volume", String.valueOf(getVolume()));
                properties.setProperty("type", getType().getName());
                properties.setProperty("hotkeyAble", String.valueOf(getHotkeyAble()));
                save();
            }
        });
        volume.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                properties.setProperty("dir", getDir());
                properties.setProperty("volume", String.valueOf(arg2.floatValue()));
                properties.setProperty("type", getType().getName());
                properties.setProperty("hotkeyAble", String.valueOf(getHotkeyAble()));
                save();
            }

        });
        type.addListener(new ChangeListener<Type>() {

            @Override
            public void changed(ObservableValue<? extends Type> arg0, Type arg1, Type arg2) {
                properties.setProperty("dir", getDir());
                properties.setProperty("volume", String.valueOf(getVolume()));
                properties.setProperty("type", arg2.getName());
                properties.setProperty("hotkeyAble", String.valueOf(getHotkeyAble()));
                save();
            }
        });

        hotkeyAble.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
                properties.setProperty("dir", getDir());
                properties.setProperty("volume", String.valueOf(getVolume()));
                properties.setProperty("type", getType().getName());
                properties.setProperty("hotkeyAble", String.valueOf(arg2));
                save();
            }
        });
    }

    private File getConfigFile() {
        File file = FileUtil.getFile("settings.properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("配置文件新建失败，{}", e.getMessage());
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * @description: 保存配置信息
     */
    private void save() {
        try {
            properties.store(new FileOutputStream(getConfigFile()), "");
        } catch (IOException e) {
            logger.error("配置文件保存失败，{}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        return config;
    }

}
