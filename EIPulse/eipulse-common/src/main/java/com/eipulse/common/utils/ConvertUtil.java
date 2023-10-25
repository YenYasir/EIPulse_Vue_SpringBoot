package com.eipulse.common.utils;

/**
 * 类型转换类
 * @author liuyj
 *
 */
public class ConvertUtil {

    /**
     * 数组转化成字符串，用"[]"分开
     * @param values String[]
     * @return String
     */
    public static String toString(String[] values) {
        String value = "";
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                value += "[" + values[i] + "]";
            }
        }
        return value;
    }

    /**
     * 数字数组转化成字符串，用"[]"分开
     * @param values String[]
     * @return String
     */
    public static String toString(long[] values) {
        String value = "";
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                value += "[" + values[i] + "]";
            }
        }
        return value;
    }

    /**
     * 整数数组转化成字符串，用"[]"分开
     * @param values String[]
     * @return String
     */
    public static String toString(int[] values) {
        String value = "";
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                value += "[" + values[i] + "]";
            }
        }
        return value;
    }

    /**
     * []型字符串转换成数字数组
     * @param parameter String
     * @return String
     */
    public static long[] toLongArray(String parameter) {
        long[] value = new long[0];
        if (parameter == null || parameter.length() < 2) return value;

        try {
            String[] strArray = parameter.substring(1, parameter.length() - 1).split("\\]\\[");
            if (strArray == null) {
                return value;
            }
            value = new long[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                try {
                    value[i] = Long.parseLong(strArray[i]);
                }
                catch (NumberFormatException ex1) {
                    value[i] = 0;
                }
            }
            return value;
        }
        catch (Exception ex) {
            return value;
        }
    }

    /**
     * []型字符串转化成整数数组
     * @param parameter String
     * @return String
     */
    public static int[] toIntArray(String parameter) {
        int[] value = new int[0];
        if (parameter == null || parameter.length() < 2) return value;

        try {
            String[] strArray = parameter.substring(1, parameter.length() - 1).split("\\]\\[");
            if (strArray == null) {
                return value;
            }
            value = new int[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                try {
                    value[i] = Integer.parseInt(strArray[i]);
                }
                catch (NumberFormatException ex1) {
                    value[i] = 0;
                }
            }
            return value;
        }
        catch (Exception ex) {
            return value;
        }
    }

    /**
     * []型字符串转化成字符串数组
     * @param parameter String
     * @return String
     */
    public static String[] toStringArray(String parameter) {
        String[] value = new String[0];
        if (parameter == null || parameter.length() < 2) return value;
        if (parameter.indexOf("[") < 0) {
            String[] ret = { parameter };
            return ret;
        }

        try {
            String[] strArray = parameter.substring(1, parameter.length() - 1).split("\\]\\[");
            return strArray;
        }
        catch (Exception ex) {
            return value;
        }
    }

    /**
     * 将字符串数据，转换成数据库sql或hql的in 函数 如 strArr =["a","b","c"]，转成 “('a','b','c')”
     * @param values
     * @return
     */
    public static String toDbString(String[] values) {
        String text = "(";
        for (int i = 0; i < values.length; i++) {
            if (i == 0)
                text += "'" + values[i] + "'";
            else
                text += ",'" + values[i] + "'";
        }
        text += ")";
        return text;
    }

    /**
     * 将Long数组数据，转换成数据库sql或hql的in 函数 如 longArr =[1,2,3]，转成 “(1,2,3)”
     * @param values
     * @return
     */
    public static String toDbString(Long[] values) {
        String text = "(";
        for (int i = 0; i < values.length; i++) {
            if (i == 0)
                text += values[i];
            else
                text += "," + values[i];
        }
        text += ")";
        return text;
    }

    /**
     * 文本替换函数，不适合大规模的文本替换，例如长度达到几个Mb的文本
     * @param source 即将被替换的文本
     * @param find 查找的字符串
     * @param replacewith 替换的字符串
     * @return 替换过的文本
     */
    public static String replace(String source, String find, String replacewith)
    {
        if (source == null || find == null)
        {
            return source;
        }
        int index = source.indexOf(find);
        if (index == -1)
        {
            return source;
        }
        int nOldLength = find.length();
        if (nOldLength == 0)
        {
            return source;
        }
        int indexStart = index + nOldLength;
        StringBuffer strDest = new StringBuffer(0);
        strDest.append(source.substring(0, index) + replacewith);
        while ( (index = source.indexOf(find, indexStart)) != -1)
        {
            strDest.append(source.substring(indexStart, index) + replacewith);
            indexStart = index + nOldLength;
        }
        strDest.append(source.substring(indexStart));
        return strDest.toString();
    }

}

