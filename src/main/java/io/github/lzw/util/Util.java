package io.github.lzw.util;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Util
 */
public class Util {

    private static final Util UTIL = new Util();
    private SimpleBooleanProperty searching;
    
    public static Util get() {
        return UTIL;
    }

    private Util() {
        if (UTIL != null) {
            throw new RuntimeException();
        }
        searching = new SimpleBooleanProperty(false);
    }

    public boolean getSearching() {
        return searching.get();
    }

    public void setSearching(boolean searching) {
        this.searching.set(searching);
    }
}