package io.github.lzw.controller;

import io.github.lzw.bean.Song;

public interface ControllerImp {
    String getTitle();
    ControllerImp getController();
    void play(Song song);
}