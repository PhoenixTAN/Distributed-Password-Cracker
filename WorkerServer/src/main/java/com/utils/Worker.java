package com.utils;


import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Jingzhou Xue
 */
public class Worker {

    private final int BASE = 52;
    private final String ALL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final int LENGTH_OF_PASSWORD = 5;

    /**
     * @description:
     *      Brute force to get the password.
     *      If the password is not in range [start, end], return null.
     *      For example, start is "aaaaa", end is "ddddd" and md5 is "ab56b4d92b40713acc5af89985d4b786".
     *      Then calculate the md5 (denoted as res) of "aaaaa", "aaaab", "aaaac", ... , "aaaba", ... , "ddddd".
     *      If one of the res is the same as md5, that string should be returned.
     * @param{String} start: the start string.
     * @param{String} end: the end string.
     * @param{String} md5: the md5 of the correct password.
     * @return{String} the correct password or null.
     */
    public String bruteForcingPassword(String start, String end, String md5) {

        md5 = md5.toUpperCase();

        try {
            while(!start.equals(end)) {
                if (encrypt(start).equals(md5)) {
                    return start;
                }
                start = increment(start, 1);
            }

            // edge case
            if (encrypt(start).equals(md5)) {
                return start;
            }

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            System.out.println(noSuchAlgorithmException);
        }

        return "";
    }

    /**
     * @description: get the next string.
     *      For example, if s is "aaaaa" and step is 1, we return "aaaab".
     *      If s is "ccccc" and step is 2, we return "cccce".
     *      If s is "zzzzz" and step is 1, we return "Aaaaa".
     * @param{String}: s
     * @param{int}: step
     * @return the next string.
     */
    private String increment(String s, int step) {

        if (s.length() != LENGTH_OF_PASSWORD) {
            return "";
        }

        char[] chars = new char[LENGTH_OF_PASSWORD];

        int position = 0;

        for (int i = 0; i < s.length(); i++) {
            int charIdx = LENGTH_OF_PASSWORD - 1 - i;
            position += Math.pow(BASE, i) * (ALL_CHARS.indexOf(s.charAt(charIdx)));
        }

        position += step;

        for (int i = 0; i < s.length(); i++) {
            int charIdx = LENGTH_OF_PASSWORD - 1 - i;
            chars[charIdx] = ALL_CHARS.charAt(position % BASE);
            position /= BASE;
        }

        return String.valueOf(chars);
    }

    /**
     * get md5 of a string
     */
    private String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest);
        return myHash;
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        String start = null;
        String end = null;
        String md5 = null;
        String expectedPassword = null;
        String password = null;

        // test case 1
        start = "aaaaa";
        end = "abddd";
        md5 = "ab56b4d92b40713acc5af89985d4b786";
        expectedPassword = "abcde";

        password = worker.bruteForcingPassword(start, end, md5);
        System.out.println(password + " " + (expectedPassword.equals(password)));

        // test case 2
        start = "aaaaa";
        end = "ddddd";
        md5 = "8f7aeea0fc2138f306aa14308099dd25";
        expectedPassword = "dbcde";

        password = worker.bruteForcingPassword(start, end, md5);
        System.out.println(password + " " + (expectedPassword.equals(password)));

        // test case 3
        start = "aaaaa";
        end = "ccccc";
        md5 = "67c762276bced09ee4df0ed537d164ea";
        expectedPassword = "ccccc";

        password = worker.bruteForcingPassword(start, end, md5);
        System.out.println(password + " " + (expectedPassword.equals(password)));

        // test case 4
        // this may takes 2 minutes
        start = "aaaaa";
        end = "ZZZZZ";
        md5 = "37e464916dcb6dfc3994ca4549e97272";
        expectedPassword = "AbCdE";

        password = worker.bruteForcingPassword(start, end, md5);
        System.out.println(password + " " + (expectedPassword.equals(password)));

        // test case 5
        // this may take 5 minutes
        start = "aaaaa";
        end = "ZZZZZ";
        md5 = "3c255c428815c9a56bcef2f707a9014b";
        expectedPassword = "Zzzzz";

        password = worker.bruteForcingPassword(start, end, md5);
        System.out.println(password + " " + (expectedPassword.equals(password)));
    }
}
