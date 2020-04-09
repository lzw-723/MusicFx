package io.github.lzw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import io.github.lzw.service.SongOService.Type;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Config {
    private final static Config config = new Config();
    private Properties properties = new Properties();
    private SimpleStringProperty dir = new SimpleStringProperty();
    private SimpleFloatProperty volume = new SimpleFloatProperty();
    private SimpleObjectProperty<Type> type = new SimpleObjectProperty<>(Type.Netease);

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

    private Config(){
        if (config != null) {
            throw new RuntimeException("单例异常");
        }else {
            try {
                init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void init() throws IOException {
        File file = new File(new File(".").getPath() + "settings.properties");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        properties.load(new FileInputStream(file));
        setDir(properties.getProperty("dir", "请选择目录"));
        setVolume(Float.valueOf(properties.getProperty("volume", "0.3")));
        setType(Type.stringOf(properties.getProperty("type", "网易")));
        dir.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                properties.setProperty("dir", newValue);
                properties.setProperty("volume", String.valueOf(getVolume()));
                properties.setProperty("type", getType().getName());
                try {
                    properties.store(new FileOutputStream(file), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        volume.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                // TODO Auto-generated method stub
                properties.setProperty("dir", getDir());
                properties.setProperty("volume", String.valueOf(arg2.floatValue()));
                properties.setProperty("type", getType().getName());
                try {
                    properties.store(new FileOutputStream(file), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
        });
        type.addListener(new ChangeListener<Type>() {

            @Override
            public void changed(ObservableValue<? extends Type> arg0, Type arg1, Type arg2) {
                // TODO Auto-generated method stub
                properties.setProperty("dir", getDir());
                properties.setProperty("volume", String.valueOf(getVolume()));
                properties.setProperty("type", arg2.getName());
                try {
                    properties.store(new FileOutputStream(file), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Config getInstance() {
        return config;
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
}
