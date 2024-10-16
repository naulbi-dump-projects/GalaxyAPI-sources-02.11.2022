package ru.galaxy773.multiplatform.api.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@UtilityClass
public class StringUtil {

    private final TreeMap<Integer, String> ROMAN_MAP = new TreeMap<>();
    private final SimpleDateFormat CURRENT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public String getCurrentDate() {
        return CURRENT_DATE_FORMAT.format(new Date());
    }

    public String proccess(String cur) {
        StringBuilder n = new StringBuilder();
        StringBuilder now = new StringBuilder();
        for (int i = 0; i < cur.length();) {
            if (cur.charAt(i) == '§' && i + 1 < cur.length()) {
                now = new StringBuilder();
                while (i + 1 < cur.length() && cur.charAt(i) == '§') {
                    now.append(cur.charAt(i)).append(cur.charAt(i + 1));
                    i += 2;
                }
            }
            if (i >= cur.length()) {
                break;
            }

            if (cur.charAt(i) != ' ')
                n.append(now).append(cur.charAt(i));
            else
                n.append(cur.charAt(i));

            ++i;
        }
        return n.toString();
    }

    public List<String> getAnimation(String name) {
        //TODO: дописать
        return Collections.singletonList(name);
    }

    public List<String> getAnimationTitle(String title, String code1, String code2, int spaces) {
        List<String> toReturn = new ArrayList<>();

        while (spaces >= 0) {
            StringBuilder cur = new StringBuilder();
            for (int i = 0; i < title.length(); ++i) {
                cur.append(title.charAt(i));
                for (int j = 0; j < spaces; ++j)
                    cur.append(" ");

            }
            toReturn.add(cur.toString());
            --spaces;
        }
        for (int i = 0; i < title.length(); ++i)
            toReturn.add(code1 + "§l" + title.substring(0, i) + code2 + "§l" + title.charAt(i)
                    + code1 + "§l" + title.substring(i + 1));

        toReturn.add(title);

        return toReturn;
    }

    public String joinByIndex(int index, String separator, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = index; i < args.length; i++) {
            stringBuilder.append(args[i]);
            if (i == args.length - 1)
                continue;
            stringBuilder.append(separator);
        }
        return stringBuilder.toString().trim();
    }

    public String join(Iterable<String> pieces, String separator) {
        StringBuilder buffer = new StringBuilder();
        Iterator iter = pieces.iterator();

        while(iter.hasNext()) {
            buffer.append((String)iter.next());
            if (iter.hasNext()) {
                buffer.append(separator);
            }
        }

        return buffer.toString();
    }

    public String onPercentBar(double currentValue, double maxValue) {
        String currentColor = "\u00A7d";
        String totalColor = "\u00A78";
        double length = 25.0D;
        double progress = currentValue / maxValue * length;
        return currentColor + StringUtils.repeat("|", (int)progress) + totalColor + StringUtils.repeat("|", (int) (length - progress));
    }

    public String getLineCode(int line) {
        StringBuilder builder = new StringBuilder();
        for (char c : String.valueOf(line).toCharArray()) {
            builder.append("\u00A7");
            builder.append(c);
        }
        return builder.toString();
    }

    public double onPercent(int value, int max) {
        return Double.parseDouble(String.format("%.1f", (double)(value * 100.0f / max)));
    }

    public String onPercentString(int value, int max) {
        return onPercent(value, max) + "%";
    }

    public boolean isInteger(String number) {
        if(number.length() > 10) {
            return false;
        }
        return StringUtils.isNumeric(number);
    }

    public static String stringToCenter(String text, int length) {
        if (text != null && text.length() <= length) {
            return org.apache.commons.lang3.StringUtils.repeat(" ", (length - textLength(text)) / 2) + text;
        }
        return text;
    }

    private static int textLength(String text) {
        int count = 0;
        char[] charArray;
        char[] array = charArray = text.toCharArray();
        for (char symbol : charArray) {
            if (symbol == '\u00A7') {
                count += 2;
            }
        }

        return text.length() - count;
    }

    public String leftpad(String text, int length) {
        return String.format("%" + length + "." + length + "s", text);
    }

    public String rightpad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    private int getSizeCharArray(char[] array) {
        int i = 0;
        for (char arr : array) {
            if (arr == 0) {
                i++;
            }
        }
        return i;
    }

    public String getNumberFormat(int amount) {
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }

    public String getNumberFormat(double amount) {
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
    }


    public String getRomanNumber(int number) {
        int l = ROMAN_MAP.floorKey(number);
        if (number == l) {
            return ROMAN_MAP.get(number);
        }

        return ROMAN_MAP.get(l) + getRomanNumber(number-l);
    }


    public String getCompleteTime(int time) {
        long longVal = new BigDecimal(time).longValue();
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int min = remainder / 60;
        remainder = remainder - min * 60;
        int sec = remainder;
        return String.format("%02d:%02d", min, sec);
    }

    static {
        ROMAN_MAP.put(1000, "M");
        ROMAN_MAP.put(900, "CM");
        ROMAN_MAP.put(500, "D");
        ROMAN_MAP.put(400, "CD");
        ROMAN_MAP.put(100, "C");
        ROMAN_MAP.put(90, "XC");
        ROMAN_MAP.put(50, "L");
        ROMAN_MAP.put(40, "XL");
        ROMAN_MAP.put(10, "X");
        ROMAN_MAP.put(9, "IX");
        ROMAN_MAP.put(5, "V");
        ROMAN_MAP.put(4, "IV");
        ROMAN_MAP.put(1, "I");
    }
}
