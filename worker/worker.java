
/**
 * @Author: 
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
    public String bruteForcingPassword(String start, String end, String md5) {

    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        String password = worker.bruteForcingPassword();
        System.out.println(password);
    }

}
