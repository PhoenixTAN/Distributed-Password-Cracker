package com.utils; /**
 * @Author: Jingzhou Xue
 */

import com.bean.Job;

import java.util.ArrayList;

public class Dispatcher {

    private final int BASE = 52;
    private final String ALL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final int LENGTH_OF_PASSWORD = 5;

    private int numOfWorkers;

    /**
     * Constructor
     */
    public Dispatcher(int _numOfWorkers) {
        this.numOfWorkers = _numOfWorkers;
    }

    /**
     * @description
     *      Input is the number of workers.
     *      If there are 5 workers,
     *      The output should be:
     *          "aaaaa" ~ "eeeee"
     *          "eeeee" ~ "jjjjj"
     *          "jjjjj" ~ "ooooo"
     *          "ooooo" ~ "ttttt"
     *          "ttttt" ~ "zzzzz"
     * @return a list of Job
     */
    public ArrayList<Job> getJobList() {
        ArrayList<Job> jobList = new ArrayList<Job>();
        int total = (int) Math.pow(52, LENGTH_OF_PASSWORD);
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
        
        char[] chars = new char[LENGTH_OF_PASSWORD];

        if (s.length() != LENGTH_OF_PASSWORD) {
            return "";
        }

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

    public static void main(String[] args) {
        int numOfWorkers = 0;
        int MAX_WORKERS = 52;
        Dispatcher dispatcher = null;
        ArrayList<Job> jobList = null;

        for ( int i = 1; i <= MAX_WORKERS; i++ ) {
            numOfWorkers = i;
            dispatcher = new Dispatcher(numOfWorkers);
            jobList = dispatcher.getJobList();
            System.out.println("Number of workers: " + numOfWorkers);
            for (Job job: jobList) {
                System.out.println(job);
            }
        }

    }

}