package ru.galaxy773.multiplatform.api.gamer.customization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;

@RequiredArgsConstructor
public enum FastMessage {

    FAST_MESSAGE_1(1, Group.KING, "\u041C\u0430\u0436\u043E\u0440(\uFFE3\uFE3F\uFFE3)"),
    FAST_MESSAGE_2(2, Group.KING, "\u0422\u0430\u043D\u0446\u0443\u044E(\u1D54.\u1D54)"),
    FAST_MESSAGE_3(3, Group.KING, "\u0417\u0430\u0441\u044B\u043F\u0430\u044E(\uFFE3O\uFFE3)"),
    FAST_MESSAGE_4(4, Group.KING, "\u0423\u0431\u0438\u0442 o(>< )o"),
    FAST_MESSAGE_5(5, Group.KING, "\u0421\u043B\u0443\u0448\u0430\u044E \u043C\u0443\u0437\u044B\u043A\u0443(^_^\u266A)"),
    FAST_MESSAGE_6(6, Group.KING, "\u041F\u0440\u0438\u0432\u0435\u0442(^-^)"),
    FAST_MESSAGE_7(7, Group.KING, "\u041B\u0430\u0433\u0430\u0435\u0442(>\uFE4F<)"),
    FAST_MESSAGE_8(8, Group.KING, "\u041C\u0438\u043B\u043E(//\u03C9//)"),
    FAST_MESSAGE_9(9, Group.KING, "\u041A\u0443\u0448\u0430\u044E( \u02D8\u25BD\u02D8)\u3063\u2668"),
    FAST_MESSAGE_10(10, Group.KING, "\u0413\u043E\u0443(o\u02D8\u25E1\u02D8o)"),
    FAST_MESSAGE_11(11, Group.KING, "\u0413\u0440\u0443\u0449\u0443(\u2565_\u2565)"),
    FAST_MESSAGE_12(12, Group.KING, "\u0412\u0430\u0443,\u043A\u043B\u0435\u0432\u043E\u30FD(*\u2312\u25BD\u2312*)\uFF89"),
    FAST_MESSAGE_13(13, Group.KING, "\u0421\u0434\u0430\u044E\u0441\u044C(\uFF1E\uFE4F\uFF1C)"),
    FAST_MESSAGE_14(14, Group.KING, "\u041E\u0431\u043D\u0438\u043C\u0430\u044E(^\u03C9~)"),
    FAST_MESSAGE_15(15, Group.KING, "\u041B\u044E\u0431\u043B\u044E(\u2500\u203F\u203F\u2500)"),
    FAST_MESSAGE_16(16, Group.KING, "\u0427\u0435\u0435?(\u2312_\u2312;)"),
    FAST_MESSAGE_17(17, Group.KING, "\u041F\u043E\u0448\u0435\u043B \u0432\u043E\u043D\u30FD(\u2035\uFE4F\u2032)\u30CE"),
    FAST_MESSAGE_18(18, Group.KING, "\u042F\u0441\u043D\u043E(\uFF03\uFFE3\u03C9\uFFE3)"),
    FAST_MESSAGE_19(19, Group.KING, "\u041D\u0435 \u0442\u0443\u043F\u0438(\uFFE3\u30D8\uFFE3)");

    private final int id;
    @Getter
    private final Group group;
    @Getter
    private final String message;

    public int getID() {
        return this.id;
    }
}
