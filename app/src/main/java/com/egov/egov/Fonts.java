package com.egov.egov;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by tonyhaddad on 7/19/15.
 */
public class Fonts {
    private static Hashtable<String, Typeface> sTypeFaces = new Hashtable<String, Typeface>(2);
    private Context context;

    public Fonts(Context context) {
        this.context = context;
    }

    public static Typeface getTypeFace(Context context, String fileName) {
        Typeface tempTypeface = sTypeFaces.get(fileName);

        if (tempTypeface == null) {
            String fontPath = null;

            if (fileName == "light") {
                fontPath = "fonts/Lato-Light.ttf";
            } else if (fileName == "reg") {

                fontPath = "fonts/Lato-Regular.ttf";
            }
            tempTypeface = Typeface.createFromAsset(context.getAssets(), fontPath);
            sTypeFaces.put(fileName, tempTypeface);
        }

        return tempTypeface;

    }
}
