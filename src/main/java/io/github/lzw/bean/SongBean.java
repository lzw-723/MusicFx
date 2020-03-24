package io.github.lzw.bean;

import java.util.List;

/**
 * SongBean
 */
public class SongBean {

    private List<SongO> data;
    private int code;
    private String error;

    public List<SongO> getData() {
        return data;
    }

    public void setData(List<SongO> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}