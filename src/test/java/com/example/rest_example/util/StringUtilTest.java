package com.example.rest_example.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    private StringUtil stringUtil = new StringUtil();

    @Test
    void trim_OK() {
        String value = "  java";
        String trim = stringUtil.trim(value);
        assertEquals(value.trim(), trim);
    }

    @Test
    void trim_OK_1() {
        String value = "  java  ";
        String trim = stringUtil.trim(value);
        assertEquals(value.trim(), trim);
    }

    @Test
    void trim_OK_2() {
        String value = "java";
        String trim = stringUtil.trim(value);
        assertEquals(value.trim(), trim);
    }

    @Test
    void trim_OK_POXOS() {
        String value = "poxos";
        String trim = stringUtil.trim(value);
        assertEquals("petros", trim);
    }

    @Test
    void trim_OK_NullPointer() {
        assertThrows(NullPointerException.class, () ->stringUtil.trim(null));
    }
}