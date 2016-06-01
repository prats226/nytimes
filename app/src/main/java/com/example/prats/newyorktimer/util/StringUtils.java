package com.example.prats.newyorktimer.util;

import java.util.List;

/**
 * Created by prats on 1/6/16.
 */
public class StringUtils {
    public interface Iterator<T> {
        String getProperty(T object);
    }

    public static String join(String[] strings, String separator, String quote) {
        StringBuilder returnStr = new StringBuilder();
        if (strings != null && strings.length > 0) {
            for (int i = 0; i < strings.length; i++) {
                if (i > 0) {
                    returnStr.append(separator);
                }
                returnStr.append(quote);
                returnStr.append(strings[i]);
                returnStr.append(quote);
            }
        }
        return returnStr.toString();
    }

    public static <T> String join(T[] array, Iterator<T> iterator, String separator, String quote, int limit) {
        StringBuilder returnStr = new StringBuilder();
        if (array != null && array.length > 0) {
            if (limit > array.length || limit == 0) limit = array.length;
            for (int i = 0; i < limit; i++) {
                if (i > 0) {
                    returnStr.append(separator);
                }
                returnStr.append(quote);
                returnStr.append(iterator.getProperty(array[i]));
                returnStr.append(quote);
            }
        }
        return returnStr.toString();
    }

    public static <T> String join(T[] array, Iterator<T> iterator, String separator, String quote) {
        return join(array, iterator, separator, quote, 0);
    }

    public static <T> String join(List<T> list, Iterator<T> iterator, String separator, String quote) {
        StringBuilder returnStr = new StringBuilder();
        if (list != null && list.size() > 0) {
            boolean first = true;
            for (T item : list) {
                String string;
                if (iterator == null) string = "" + item;
                else string = iterator.getProperty(item);
                if (first) {
                    first = false;
                } else {
                    returnStr.append(separator);
                }
                returnStr.append(quote);
                returnStr.append(string);
                returnStr.append(quote);
            }
        }
        return returnStr.toString();
    }

    public static String join(String[] strings) {
        return join(strings, ",", "\"");
    }

    public static <T> String join(T[] array, Iterator<T> iterator) {
        return join(array, iterator, ",", "\"");
    }

    public static <T> String join(List<T> list) {
        return join(list, null, ",", "\"");
    }

    public static <T> String join(List<T> list, Iterator<T> iterator) {
        return join(list, iterator, ",", "\"");
    }

    public static <T> String join(List<T> list, String separator, String quote) {
        return join(list, null, separator, quote);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Checks equality of two strings.
     *
     * @param string1
     * @param string2
     * @return false if string1 in null. Otherwise, return result of string1.equals(string2)
     */
    public static boolean areEqual(String string1, String string2) {
        return string1 != null && string1.equals(string2);
    }

    /**
     * Checks equality of two strings.
     *
     * @param string1
     * @param string2
     * @return If both are null returns true. Otherwise, return result of
     * areEqual(string1, string2)
     */
    public static boolean areEqual2(String string1, String string2) {
        if (string1 == null && string2 == null) return true;
        return areEqual(string1, string2);
    }

    /**
     * To compare two strings. Handles null values. null value are consider greater than non-null.
     *
     * @param string1
     * @param string2
     * @return 0 if strings are equal. -1 if string1 smaller thatn string2.
     * 1 if string1 is greater than string2.
     */
    public static int compareTo(String string1, String string2) {
        if (string1 == null && string2 == null) return 0;
        else if (string1 == null) return 1;
        else if (string2 == null) return -1;
        return string1.compareTo(string2);
    }

    /**
     * To get substring and handle list index out of range error and return full string in such case.
     *
     * @param string
     * @param start  Similar to the input of substring
     * @param end    Similar to the input of substring
     * @return substring, if list index out of range it returns full string
     */
    public static String getSubstring(String string, int start, int end) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        try {
            String subStr = "";
            subStr = subStr.substring(start, end);
            return subStr;
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            return string;
        }
    }

    /**
     * To get substring and handle list index out of range error and return full string in such case.
     *
     * @param string
     * @param start  Similar to the input of substring
     * @return substring, if list index out of range it returns full string
     */
    public static String getSubstring(String string, int start) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        try {
            String subStr = "";
            subStr = subStr.substring(start);
            return subStr;
        } catch (java.lang.StringIndexOutOfBoundsException e) {
            return string;
        }
    }

}
