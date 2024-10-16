package ru.galaxy773.multiplatform.api.gamer.customization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rarity {

    DEFAULT("\u00A77\u041E\u0431\u044B\u0447\u043D\u044B\u0439", "\u00A7e\u272D"),
    RARE("\u00A7a\u0420\u0435\u0434\u043A\u0438\u0439", "\u00A7e\u272D\u272D"),
    UNIGUE("\u00A79\u0423\u043D\u0438\u043A\u0430\u043B\u044C\u043D\u044B\u0439",  "\u00A7e\u272D\u272D\u272D"),
    EPIC("\u00A76\u042D\u043F\u0438\u0447\u0435\u0441\u043A\u0438\u0439", "\u00A7e\u272D\u272D\u272D\u272D"),
    LEGENDARY("\u00A75\u041B\u0435\u0433\u0435\u043D\u0434\u0430\u0440\u043D\u044B\u0439", "\u00A7e\u272D\u272D\u272D\u272D\u272D");

    private final String name;
    private final String stars;
}
