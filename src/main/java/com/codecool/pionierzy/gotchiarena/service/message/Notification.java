package com.codecool.pionierzy.gotchiarena.service.message;

public class Notification {
    private String msg;

    private Long userRoomId;

    public Notification() {
    }

    public Notification(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getUserRoomId() {
        return userRoomId;
    }

    public void setUserRoomId(Long userRoomId) {
        this.userRoomId = userRoomId;
    }
}
