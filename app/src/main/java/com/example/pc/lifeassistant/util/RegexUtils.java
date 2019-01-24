package com.example.pc.lifeassistant.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pc on 2019/1/23.
 */

public class RegexUtils {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private static Matcher matcher;


    //验证邮箱
    public static boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //验证长度
    public static boolean validatePassword(String password) {
        return password.length() > 6 && password.length() < 12;
    }

    //验证重复密码
    public static boolean validatePasswordAgain(String password, String password_again) {

        return password.equals(password_again);
    }

}
