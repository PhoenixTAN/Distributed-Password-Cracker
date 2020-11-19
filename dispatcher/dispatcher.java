/**
 * @Author: Jingzhou Xue
 */
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
public class Dispatcher {

    /**
     * 输入是worker数量，worker最大数量是5，最小是1，输入参数不对请抛出异常
     * 例如有5个worker = 5
     * 输出是：
     *      "aaaaa" ~ "eeeee"
     *      "eeeee" ~ "jjjjj"
     *      "jjjjj" ~ "ooooo"
     *      "ooooo" ~ "ttttt"
     *      "ttttt" ~ "zzzzz"
     * 返回的数据结构自选
     */
    public ArrayList<Job> getJobList(int numOfWorkers) {
        ArrayList<Job> jobList = new ArrayList<Job>();
        int total = (int) Math.pow(52, 5);
        int workload = total / numOfWorkers;
        String start = "aaaaa";
        for (int i = 0; i < numOfWorkers - 1; i++) {
            Job p = getJobFrom(start, workload);
            jobList.add(p);
            start = increment(start, workload);
        }
        jobList.add(new Job(start, "ZZZZZ"));
        return jobList;
    }
    private Job getJobFrom(String start, int workload) {
        String end = increment(start, workload);
        return new Job(start, end);
    }

    public String increment(String s, int step) {
        String allChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] chars = new char[5];
        if (s.length() != 5) return "";
        int position = 0;
        for (int i = 0; i < s.length(); i++) {
            int charIdx = 5 - 1 - i;
            // System.out.println(allChars.indexOf(s.charAt(charIdx)));
            position += Math.pow(BASE, i) * (allChars.indexOf(s.charAt(charIdx)));
            // System.out.println("position: " + position);
        }
        position += step;
        for (int i = 0; i < s.length(); i++) {
            int charIdx = 5 - 1 - i;
            // System.out.println("number: " + position % BASE);
            chars[charIdx] = allChars.charAt(position % BASE);

            position /= BASE;
            // System.out.println(chars);
            // System.out.println("position: " + position);

        }
        System.out.println(chars);
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        int numOfWorkers = 10;
    }

}