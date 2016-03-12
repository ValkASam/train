package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Valk on 08.01.16.
 */
public class RegexTest {
    public static void main(String[] args) {
        {
            String fileName = "asdf.txt";
            System.out.println((Pattern.compile(".*.txt").matcher(fileName).matches()));
            System.out.println((Pattern.matches(".*.txt", fileName)));
            System.out.println(fileName.matches(".*.txt"));
            String str = "2+3-1*4".replaceAll("[^\\+\\-\\*]", "");
            System.out.println(str);
        }
        //
        System.out.println("=================");
        //
        {
            String str = "12 text var2 14 8v 1";
            str = str.replaceAll("\\s", "  ");
            Matcher matcher = Pattern.compile("(^\\d+\\s|\\s\\d+\\s|\\s\\d+$)").matcher(str);
            String buf = "";
            while (matcher.find()) {
                buf = buf + matcher.group();
            }
            buf = (buf.replaceAll("  ", " ")).trim();
            System.out.println(buf);
        }
        System.out.println("=================");
        //
        {
            //использование двух групп
            String str = "Иванов Иван Иванович# 31 12 1987 Петров Петр Петрович# 30 11 1988 ";
            Matcher matcher = Pattern.compile("(\\D+)#(\\s\\d+\\s\\d+\\s\\d+)\\s").matcher(str);
            String buf = "";
            matcher.find();
            buf = buf + matcher.group(1);
            buf = buf + "* "+ matcher.group(2);
            matcher.find();
            buf = buf + "|";
            buf = buf + matcher.group(1);
            buf = buf + "* "+ matcher.group(2);
            System.out.println(buf);
        }
        {
            //использование двух групп.
            //Особенность: пытается охватить как можно больший кусок текста.
            // Если \\D+ заменить на .+ (не числовые символы на любые), то для первой группы подойдет
            //и Иванов Иван Иванович# 31 12 1987
            //и Иванов Иван Иванович# 31 12 1987 Петров Петр Петрович
            //будет выбран второй вариант
            String str = "Иванов Иван Иванович# 31 12 1987 Петров Петр Петрович# 30 11 1988 ";
            Matcher matcher = Pattern.compile("(.+)#(\\s\\d+\\s\\d+\\s\\d+)\\s").matcher(str);
            String buf = "";
            matcher.find();
            buf = buf + matcher.group(1);
            buf = buf + "* "+ matcher.group(2);
            buf = buf + "|";
            System.out.println(buf);
        }
        {
            //демо стремления охватить максимальный кусок текста на примере одной группы.
            String str = "Иванов Иван Иванович# 31 12 1987 Петров Петр Петрович# 30 11 1988 ";
            Matcher matcher = Pattern.compile("(.+)#").matcher(str);
            String buf = "";
            matcher.find();
            buf = buf + matcher.group();
            buf = buf + "|";
            System.out.println(buf);
        }
        {
            //использование двух групп.
            //(\s{1}|\s{0}) дает возможность выбрать длинный или короткий кусок. Выбирает длинный
            String str = "Иванов Иван Иванович# 31 12 1987 Петров Петр Петрович# 30 11 1988"; //тут убрал пробел в конце
            Matcher matcher = Pattern.compile("(.+)#(\\s\\d+\\s\\d+\\s\\d+)(\\s{1}|\\s{0})").matcher(str);
            String buf = "";
            matcher.find();
            buf = buf + matcher.group(1);
            buf = buf + "* "+ matcher.group(2);
            buf = buf + "|";
            System.out.println(buf);
        }

    }
}
