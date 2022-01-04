package utils;

import java.util.List;

public class Console {
    public static <T> void log(List<T> list) {
        for (T t : list) {
            System.out.println(t);
        }
    }

    public static void log(Object... args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            builder.append(arg);
            if (i != args.length - 1) {
                builder.append(", ");
            }
        }
        System.out.println(builder);
    }
}
