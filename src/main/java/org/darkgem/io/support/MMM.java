package org.darkgem.io.support;

import org.darkgem.annotation.Nullable;

/**
 * SQL JDBC 帮助
 */
public class MMM {
    static final String TRUE = "TRUE";
    static final String FALSE = "FALSE";
    static final String UNDEFINED = "UNDEFINED";

    /***
     * 是否拥有具体定义
     */
    public static boolean def(@Nullable String def) {
        if (def == null || UNDEFINED.equals(def)) {
            return false;
        }
        return true;
    }

    /**
     * db 2 java
     */
    public static boolean boolean4db2java(String bool) {
        return TRUE.equals(bool);
    }

    /**
     * java 2 db
     */
    public static Object boolean4java2db(Boolean bool) {
        if (bool == null) {
            return null;
        } else {
            if (bool) {
                return TRUE;
            } else {
                return FALSE;
            }
        }
    }

    /**
     * 获取占位符
     *
     * @param size 多少个?
     * @return 如果没有，则返回null
     */
    @Nullable
    static public String placeholder(int size) {
        StringBuilder sb = null;
        for (int i = 0; i < size; ++i) {
            if (sb == null) {
                sb = new StringBuilder();
                sb.append("?");
            } else {
                sb.append(",");
                sb.append("?");
            }
        }
        //如果没有，则返回空字符串
        if (sb == null) {
            return null;
        } else {
            return sb.toString();
        }
    }

    /**
     * 数组对象转换为字符串数组对象，调用{@link Object#toString()}
     *
     * @param arr 待处理的对象数组
     */
    public static String[] strings(Object[] arr) {
        String[] ret = new String[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            ret[i] = arr[i].toString();
        }
        return ret;
    }

    /**
     * 合并数组和参数
     *
     * @param origin 原始数组
     * @param args   不定参数
     * @return 合并完成的数组
     */
    public static Object[] array(Object[] origin, Object... args) {
        Object[] ret = new Object[origin.length + args.length];
        //内存拷贝
        System.arraycopy(origin, 0, ret, 0, origin.length);
        //依次添加
        for (int i = 0; i < args.length; ++i) {
            ret[origin.length + i] = args[i];
        }
        return ret;
    }
}