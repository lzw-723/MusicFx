package io.github.lzw.controller;

import io.github.lzw.bean.Song;

public interface ControllerImpl {
    String getTitle();
    ControllerImpl getController();
    void play(Song song);
}