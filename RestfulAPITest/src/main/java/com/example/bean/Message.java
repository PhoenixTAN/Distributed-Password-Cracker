package com.example.bean;


import java.io.Serializable;

public class Message implements Serializable {
    private String MD5Password;
    private Integer workerNum;

    public String getMD5Password() {
        return MD5Password;
    }

    public void setMD5Password(String MD5Password) {
        this.MD5Password = MD5Password;
    }

    public Integer getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(Integer workerNum) {
        this.workerNum = workerNum;
    }

    @Override
    public String toString() {
        return "Message{" +
                "MD5Password='" + MD5Password + '\'' +
                ", workerNum=" + workerNum +
                '}';
    }
}
