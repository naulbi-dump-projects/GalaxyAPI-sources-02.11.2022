package ru.galaxy773.multiplatform.api.gamer.customization;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import java.util.Arrays;

@RequiredArgsConstructor
public enum JoinMessage {

    NONE(-1, Group.DEFAULT, ""),
    JOIN_MESSAGE_1(1, Group.WARRIOR, "§a➛§7 %s §6зашел на сервер"),
    JOIN_MESSAGE_2(2, Group.WARRIOR, "§a➛§7 %s §6влетел на сервер"),
    JOIN_MESSAGE_3(3, Group.WARRIOR, "§a➛§7 %s §6влетел на сверхзвуковой скорости"),
    JOIN_MESSAGE_4(4, Group.WARRIOR, "§a➛§7 %s §6присоединился к вечеринке"),
    JOIN_MESSAGE_5(5, Group.WARRIOR, "§a➛§7 %s §6залез через окно и крикнул: §cгде мои штаны?"),
    JOIN_MESSAGE_6(6, Group.WARRIOR, "§a➛§7 %s §6залетел на сервер и сказал: §cдайте мне крышки!"),
    JOIN_MESSAGE_7(7, Group.WARRIOR, "§a➛§7 %s §6зашел на сервер, запирайте свои дома"),
    JOIN_MESSAGE_8(8, Group.WARRIOR, "§a➛§7 %s, §6добро пожаловать, мы тебя ждали!"),
    JOIN_MESSAGE_9(9, Group.WARRIOR, "§a➛§7 %s §6упал с луны прямо на наш дедик, о нет!"),
    JOIN_MESSAGE_10(10, Group.WARRIOR, "§a➛§7 %s §6решил нас посетить"),
    JOIN_MESSAGE_11(11, Group.WARRIOR, "§a➛§7 %s§6: §cя енотик полоскун, полоскаю свой пи..."),
    JOIN_MESSAGE_12(12, Group.WARRIOR, "§a➛§7 %s §6причалил на своем корабле"),
    JOIN_MESSAGE_13(13, Group.WARRIOR, "§a➛§7 %s §6подключился. Надеемся, что у него нет коронавируса..."),
    JOIN_MESSAGE_14(14, Group.WARRIOR, "§a➛§7 %s §6всполошил весь округ!"),
    JOIN_MESSAGE_15(15, Group.WARRIOR, "§a➛§7 %s §6присоединился к нашим рядам"),
    JOIN_MESSAGE_16(16, Group.WARRIOR, "§a➛§7 %s §6прилетел к нам на крыльях!"),
    JOIN_MESSAGE_17(17, Group.WARRIOR, "§a➛§7 %s §6был рассекречен!"),
    JOIN_MESSAGE_18(18, Group.WARRIOR, "§a➛§7 %s §6нашел время на нас, давайте его поприветствуем!"),
    JOIN_MESSAGE_19(19, Group.WARRIOR, "§a➛§7 %s §6пришел со школы");

    private final int id;
    @Getter
    private final Group group;
    @Getter
    private final String message;

    public int getID() {
        return this.id;
    }

    private static final TIntObjectMap<JoinMessage> JOIN_MESSAGES = new TIntObjectHashMap<>();
    public static JoinMessage getJoinMessage(int id) {
        return JOIN_MESSAGES.get(id);
    }

    static {
        Arrays.stream(values()).forEach(joinMessage -> JOIN_MESSAGES.put(joinMessage.getID(), joinMessage));
    }
}
