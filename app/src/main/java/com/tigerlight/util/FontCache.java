package com.tigerlight.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontCache {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static Typeface get(String name, Context context) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
                //            	if (tf.isBold()) {
                //    				tf = Typeface.createFromAsset(context.getAssets(), name + "_bold.ttf");
                //    			} else {
                //    				tf = Typeface.createFromAsset(context.getAssets(), name + "_regular.ttf");
                //				}
            } catch (Exception e) {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}