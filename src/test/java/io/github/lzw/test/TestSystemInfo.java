/*
 * @Author: lzw-723
 * @Date: 2021-02-10 18:41:58
 * @LastEditTime: 2021-02-10 18:52:24
 */
package io.github.lzw.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.github.lzw.SystemInfo;

public class TestSystemInfo {
    @Test
    public void testUserHome() {
        assertEquals(SystemInfo.userHome(), System.getProperty("user.home"));
    }

    @Test
    public void testJavaVersion() {
        assertEquals(SystemInfo.javaVersion(), System.getProperty("java.version"));
    }

    @Test
    public void testJavafxVersion() {
        assertEquals(SystemInfo.javafxVersion(), System.getProperty("javafx.version"));
    }
}
