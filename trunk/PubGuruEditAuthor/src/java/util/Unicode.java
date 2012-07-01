/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author HuyNVK
 */
public class Unicode {
    public static String changeToUnicode(String str) throws UnsupportedEncodingException {
        if (str != null) {
            str = new String(str.getBytes("8859_1"), "UTF-8");
        }
        return str;
    }
}
