
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
/**
 * @Author: Jingzhou Xue
 */
public class Worker {

    /**
     * @description: 
     *      Brute force to get the password. 
     *      If the password is not in range [start, end], return null.
     *      For example, start is "aaaaa", end is "ddddd" and md5 is "nvoahufbqwlbuousdhfbla".
     *      Then calculate the md5 (denoted as res) of "aaaaa", "aaaab", "aaaac", ... , "aaaba", ... , "ddddd". 
     *      If one of the res is the same as md5, that string should be returned.
     * @param{String} start: the start string.
     * @param{String} end: the end string.
     * @param{String} md5: the md5 of the correct password.
     * @return{String} the correct password or null.
     */
    private final int BASE = 26;

    public String bruteForcingPassword(String start, String end, String md5) {
        try {
            while(!start.equals(end)) {
                if (encrypt(start).equals(md5)) {
                    return start;
                }
                start = increment(start, 1);
            }
        } catch (NoSuchAlgorithmException nsae) {

        }
        return "";
    }
    // TODO: function increment is the same in dispatcher and worker. get rid of one.
    public String increment(String s, int step) {
        // TODO: wrap around zzzzz to AAAAA
        // check remaining distance from s to zzzzz and update step if step is larger
        // start from AAAAA and use new step
        char baseChar = 'a';
        if (Character.isUpperCase(s.charAt(0))) {
            baseChar = 'A';
        }
        char[] chars = new char[5];
        if (s.length() != 5) return "";
        int position = 0;
        for (int i = 0; i < s.length(); i++) {
            int charIdx = 5 - 1 - i;
            position += Math.pow(BASE, i) * (s.charAt(charIdx) - baseChar);
        }
        position += step;
        for (int i = 0; i < s.length(); i++) {
            int charIdx = 5 - 1 - i;
            // System.out.println("number: " + position % BASE);
            chars[charIdx] = (char) (baseChar + position % BASE);
            position /= BASE;
            // System.out.println(chars);
            // System.out.println("position: " + position);

        }
        return String.valueOf(chars);
    }

    private String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest);
        return myHash;
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        // TODO: get start, end, and md5
        String start, end, md5 = "";
        String password = worker.bruteForcingPassword(start, end, md5);
        System.out.println(password);
    }

}
