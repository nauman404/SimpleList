package com.simplelist.utils;

/**
 * StaticMethod is for static methods
 * @author by Nauman Ashraf on 9/11/2016.
 */
public class StaticMethod {

    private static final String TAG = StaticMethod.class.getCanonicalName();

    /**
     * @param s
     * @return
     */
    public static String capitalize(String s) {
        if(s == null){
            return null;
        }
        if(s.length() == 1){
            return s.toUpperCase();
        }
        if(s.length() > 1){
            return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
        }
        return "";
    }
}
