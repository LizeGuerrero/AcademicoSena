package com.academico.academicosena.tools;

import com.academico.academicosena.util.PasswordUtils;

public class HashGenerator {
    public static void main(String[] args) {
        String pwd = "test1234";
        if (args != null && args.length > 0) pwd = args[0];
        String hash = PasswordUtils.hashPassword(pwd);
        System.out.println(hash);
    }
}
