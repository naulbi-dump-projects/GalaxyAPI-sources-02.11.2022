package ru.galaxy773.multiplatform.api.gamer.customization.tituls;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.galaxy773.multiplatform.api.gamer.customization.Rarity;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum TitulType {

    //todo: убрать NONE
    NONE(-1, TitulsCategory.BASE, new BoxesTitul("Не выбрано", Rarity.DEFAULT, "TITUL_BOXES_TITULS")),
    ANIMESHNIK(1, TitulsCategory.ANIME, new BuyableTitul("§6Анимешник", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    NARUTO(2, TitulsCategory.ANIME, new BuyableTitul("§6Наруто", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    TYAN(3, TitulsCategory.ANIME, new BuyableTitul("§dТян", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    NYA(4, TitulsCategory.ANIME, new BuyableTitul("§5Ня", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    CHAN(5, TitulsCategory.ANIME, new BuyableTitul("§dЧан", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    KUN(6, TitulsCategory.ANIME, new BuyableTitul("§9Кун", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    YURI(7, TitulsCategory.ANIME, new BuyableTitul("§5Юри", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    SENPAI(8, TitulsCategory.ANIME, new BuyableTitul("§9Сенпай", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    KAVAI(9, TitulsCategory.ANIME, new BuyableTitul("§6Кавай", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    DARLING(10, TitulsCategory.ANIME, new BuyableTitul("§dМилый", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    PANCU(11, TitulsCategory.ANIME, new BuyableTitul("§aПанцу", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    OTAKU(12, TitulsCategory.ANIME, new BuyableTitul("§2Отаку", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    CHIBI(13, TitulsCategory.ANIME, new BuyableTitul("§eЧиби", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    CUNDERE(14, TitulsCategory.ANIME, new BuyableTitul("§cЦундере", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    ZERO_TWO(15, TitulsCategory.ANIME, new BuyableTitul("§d§lНоль два", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    GUL(16, TitulsCategory.ANIME, new BuyableTitul("§4§lГуль", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    EVANGELION(17, TitulsCategory.ANIME, new BuyableTitul("§2§lЕвангелион", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    MOE(18, TitulsCategory.ANIME, new BuyableTitul("§b§lМоэ", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    EROGE(19, TitulsCategory.ANIME, new BoxesTitul("§3§l§nЭроге", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    AHEGAO(20, TitulsCategory.ANIME, new BoxesTitul("§b§l§nАхегао", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    HENTAI(21, TitulsCategory.ANIME, new BoxesTitul("§b§l§nХентай", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    LEAVER(22, TitulsCategory.BASE, new BuyableTitul("§eЛивер", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    CHEATER(23, TitulsCategory.BASE, new BuyableTitul("§cЧитер", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    COMBER(24, TitulsCategory.BASE, new BuyableTitul("§6Комбер", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    TEAMER(25, TitulsCategory.BASE, new BuyableTitul("§bТимер", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    SWORDER(26, TitulsCategory.BASE, new BuyableTitul("§6Мечник", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    ARCHER(27, TitulsCategory.BASE, new BuyableTitul("§2Лучник", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    CRYSTALLER(28, TitulsCategory.BASE, new BuyableTitul("§dКристальщик", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    STRAFER(29, TitulsCategory.BASE, new BuyableTitul("§bСтрайфер", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    NULLER(30, TitulsCategory.BASE, new BuyableTitul("§eНулёвый", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    NOOBIK(31, TitulsCategory.BASE, new BuyableTitul("§2Нубик", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    PRO(32, TitulsCategory.BASE, new BuyableTitul("§6Про", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    AGRO(33, TitulsCategory.BASE, new BuyableTitul("§bАгро", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    UNDEAD(34, TitulsCategory.BASE, new BuyableTitul("§aБессмертный", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    KING(35, TitulsCategory.BASE, new BuyableTitul("§6§lЦарь", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    LEGEND(36, TitulsCategory.BASE, new BuyableTitul("§9§lЛегенда", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    RICH(37, TitulsCategory.BASE, new BuyableTitul("§e§lБогатый", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    ANARCHIST(38, TitulsCategory.BASE, new BuyableTitul("§6§lАнархист", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    ZADROT(39, TitulsCategory.BASE, new BoxesTitul("§5§l§nЗадрот", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    EMPEROR(40, TitulsCategory.BASE, new BoxesTitul("§4§l§nИмперор", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    HACKER(41, TitulsCategory.BASE, new BoxesTitul("§3§l§nХакер", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    SNOW(42, TitulsCategory.NEW_YEAR, new BuyableTitul("§bСнег", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    XMAS_TREE(43, TitulsCategory.NEW_YEAR, new BuyableTitul("§aЁлка", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    GARLAND(44, TitulsCategory.NEW_YEAR, new BuyableTitul("§dГирлянда", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    DEER(45, TitulsCategory.NEW_YEAR, new BuyableTitul("§6Олень", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    SNOWDRIFT(46, TitulsCategory.NEW_YEAR, new BuyableTitul("§3Сугроб", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    PRESENT(47, TitulsCategory.NEW_YEAR, new BuyableTitul("§eПодарок", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    MITTEN(48, TitulsCategory.NEW_YEAR, new BuyableTitul("§9Варежка", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    SNOWBALL(49, TitulsCategory.NEW_YEAR, new BuyableTitul("§bПодарок", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    SNOWFALL(50, TitulsCategory.NEW_YEAR, new BuyableTitul("§3Снегопад", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    HOLIDAY(51, TitulsCategory.NEW_YEAR, new BuyableTitul("§9Праздник", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    WINTER(52, TitulsCategory.NEW_YEAR, new BuyableTitul("§bЗима", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    DREAM(53, TitulsCategory.NEW_YEAR, new BuyableTitul("§a§lМечта", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    FIRE(54, TitulsCategory.NEW_YEAR, new BuyableTitul("§6§lОгонёк", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    IN_SLEEP(55, TitulsCategory.NEW_YEAR, new BuyableTitul("§e§lВ спячке", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    SNIVEL(56, TitulsCategory.NEW_YEAR, new BuyableTitul("§d§lСопелька", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    SNOWMAN(57, TitulsCategory.NEW_YEAR, new BoxesTitul("§b§l§nСнеговик", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    SANTA_CLAUS(58, TitulsCategory.NEW_YEAR, new BoxesTitul("§c§l§nДед мороз", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    SANTA(59, TitulsCategory.NEW_YEAR, new BoxesTitul("§6§l§nСанта", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    GRINCH(60, TitulsCategory.NEW_YEAR, new BoxesTitul("§2§l§nГринч", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    STARS(61, TitulsCategory.SPECIAL, new BuyableTitul("§2***", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    SNOWS(62, TitulsCategory.SPECIAL, new BuyableTitul("§b❆ ❆ ❆", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    STARS_PLUS(63, TitulsCategory.SPECIAL, new BuyableTitul("§e✭✭✭", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    MUSIC(64, TitulsCategory.SPECIAL, new BuyableTitul("§6♫", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    CROSS(65, TitulsCategory.SPECIAL, new BuyableTitul("§a✟", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    ACCEPT(66, TitulsCategory.SPECIAL, new BuyableTitul("§a✔", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    DENY(67, TitulsCategory.SPECIAL, new BuyableTitul("§4✖", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    SMILE_1(68, TitulsCategory.SPECIAL, new BuyableTitul("§a( ͡° ͜ʖ ͡°)", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    SMILE_3(69, TitulsCategory.SPECIAL, new BuyableTitul("§dつ ◕_◕ ༽つ", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    SMILE_4(70, TitulsCategory.SPECIAL, new BuyableTitul("§6(◑‿◐)", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    SMILE_5(71, TitulsCategory.SPECIAL, new BuyableTitul("§e(〃＞＿＜;〃)", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    SMILE_6(72, TitulsCategory.SPECIAL, new BuyableTitul("§5(●´ω｀●)", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    SMILE_7(73, TitulsCategory.SPECIAL, new BuyableTitul("§6ʕ •ᴥ• ʔ", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    SMILE_8(74, TitulsCategory.SPECIAL, new BuyableTitul("§c(｡•́︿•̀｡)", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    SMILE_9(75, TitulsCategory.SPECIAL, new BuyableTitul("§b\\(★ω★)/", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    SMILE_10(76, TitulsCategory.SPECIAL, new BuyableTitul("§3٩(｡•́‿•̀｡)۶", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    SMILE_11(77, TitulsCategory.SPECIAL, new BoxesTitul("§e§l⑳⑳", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    SMILE_12(78, TitulsCategory.SPECIAL, new BoxesTitul("§6§lⅩⅠ век", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    SMILE_13(79, TitulsCategory.SPECIAL, new BoxesTitul("§d§kBLOCKED", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    SOKOL(80, TitulsCategory.MARVEL, new BuyableTitul("§6Сокол", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    ROSOMAHA(81, TitulsCategory.MARVEL, new BuyableTitul("§eРосомаха", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    SORVIGOLOVA(82, TitulsCategory.MARVEL, new BuyableTitul("§aСорвиголова", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    MURAVEY(83, TitulsCategory.MARVEL, new BuyableTitul("§cМуравей", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    BOEGOLOVKA(84, TitulsCategory.MARVEL, new BuyableTitul("§bБоеголовка", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    MAGNETTO(85, TitulsCategory.MARVEL, new BuyableTitul("§9Магнето", Rarity.DEFAULT, "TITUL_BUYABLE_COINS", 10, BuyableTitul.MoneyType.COINS)),
    RTUT(86, TitulsCategory.MARVEL, new BuyableTitul("§3Ртуть", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    MISTIC(87, TitulsCategory.MARVEL, new BuyableTitul("§5Мистик", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    GRUT(88, TitulsCategory.MARVEL, new BuyableTitul("§cГрут", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    PANTERA(89, TitulsCategory.MARVEL, new BuyableTitul("§eПантера", Rarity.RARE, "TITUL_BUYABLE_COINS", 15, BuyableTitul.MoneyType.COINS)),
    FURY(90, TitulsCategory.MARVEL, new BuyableTitul("§aФьюри", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    KARATEL(91, TitulsCategory.MARVEL, new BuyableTitul("§4Каратель", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    HULK(92, TitulsCategory.MARVEL, new BuyableTitul("§2Халк", Rarity.UNIGUE, "TITUL_BUYABLE_COINS", 25, BuyableTitul.MoneyType.COINS)),
    CAPITAN_AMERICA(93, TitulsCategory.MARVEL, new BuyableTitul("§b§lКап.Америка", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    SPIDER(94, TitulsCategory.MARVEL, new BuyableTitul("§4§lПаук", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    VISION(95, TitulsCategory.MARVEL, new BuyableTitul("§d§lВижен", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    STRANGE(96, TitulsCategory.MARVEL, new BuyableTitul("§6§lСтрэндж", Rarity.EPIC, "TITUL_BUYABLE_COINS", 50, BuyableTitul.MoneyType.COINS)),
    TOR(97, TitulsCategory.MARVEL, new BoxesTitul("§6§l§nТор", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    TANOS(98, TitulsCategory.MARVEL, new BoxesTitul("§d§l§nТанос", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    DEADPOOL(99, TitulsCategory.MARVEL, new BoxesTitul("§c§l§nДэдпул", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    MARVEL(100, TitulsCategory.MARVEL, new BoxesTitul("§4§l§nМарвел!", Rarity.LEGENDARY, "TITUL_BOXES_TITULS")),
    SOCIABLE(101, TitulsCategory.GAMING, new AchievementTitul("§6§lОбщительный", Rarity.EPIC, "TITUL_ACHIEVEMENT")),
    DRAGON_SLAYER(102, TitulsCategory.GAMING, new AchievementTitul("§9§lДракон", Rarity.EPIC, "TITUL_ACHIEVEMENT"));

    private final int id;
    private final TitulsCategory category;
    private final Titul titul;

    private static final TIntObjectMap<TitulType> TITULS = new TIntObjectHashMap<>();
    public static TitulType getTitul(int id) {
        return TITULS.get(id);
    }

    static {
        Arrays.stream(values()).forEach(titul -> TITULS.put(titul.getId(), titul));
    }
}
