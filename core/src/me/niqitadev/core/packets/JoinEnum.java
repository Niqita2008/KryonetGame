package me.niqitadev.core.packets;

public enum JoinEnum {

    JOINED(null), USER_WITH_THIS_NICK_ALREADY_CONNECTED_TO_THE_SERVER ("user with this nick already connected to the server");

    public final String message;

    JoinEnum(String message) {
        this.message = message;
    }
}
