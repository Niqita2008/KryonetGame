package me.niqitadev.core.packets;

public enum JoinRequestEnum {

    JOINED(null), USER_WITH_THIS_NICK_ALREADY_CONNECTED_TO_THE_SERVER("User with this nick already connected to the server");

    public final String message;

    JoinRequestEnum(String message) {
        this.message = message;
    }
}
