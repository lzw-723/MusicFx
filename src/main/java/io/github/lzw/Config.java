package io.github.lzw;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.*;
import java.util.Properties;

public class Config {
    private final static Config config = new Config();
    private Properties properties = new Properties();
    private SimpleStringProperty dir = new SimpleStringProperty();
    private SimpleFloatProperty volume = new SimpleFloatProperty();

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
        setDir(properties.getProperty("dir", "C://Music"));
        setVolume(0.3f);
        dir.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                properties.setProperty("dir", newValue);
                properties.setProperty("volume", String.valueOf(getVolume()));
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
}
