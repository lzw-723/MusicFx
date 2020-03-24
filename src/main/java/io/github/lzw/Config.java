package io.github.lzw;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.*;
import java.util.Properties;

public class Config {
    private final static Config config = new Config();
    private Properties properties = new Properties();
    private SimpleStringProperty dir = new SimpleStringProperty();

    public String getDir() {
        return dir.get();
    }

    public SimpleStringProperty dirProperty() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir.set(dir);
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
        File file = new File(new File(".").getPath() + "settings.p");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        properties.load(new FileInputStream(file));
        setDir(properties.getProperty("dir", "C://Music"));
        dir.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                properties.setProperty("dir", newValue);
                try {
                    properties.store(new FileOutputStream(file), "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(newValue);
            }
        });
    }

    public static Config getInstance() {
        return config;
    }
}
