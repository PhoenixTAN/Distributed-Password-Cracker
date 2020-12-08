package com.bean;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Message implements Serializable {

    @JsonProperty("MD5Password")
    private String MD5Password;

    @JsonProperty("workerNum")
    private Integer workerNum;

    public Message() {

    }

    public Message(String MD5Password, Integer workerNum) {
        this.MD5Password = MD5Password;
        this.workerNum = workerNum;
    }

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
