package ru.galaxy773.multiplatform.api.gamer.friend;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FriendAction {

    ADD("ADD_FRIEND"),
    REMOVE("REMOVE_FRIEND");

    private final String messageKey;
}
