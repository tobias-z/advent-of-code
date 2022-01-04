package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskReader {
    public static List<String> readFile(boolean isTest) {
        return getFile(isTest);
    }

    public static List<String> readFile() {
        return getFile(false);
    }

    private static List<String> getFile(boolean isTest) {
        try {
            List<String> result = new ArrayList<>();
            File file = new File(isTest ? "inputTest" : "input");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                result.add(scanner.nextLine());
            }
            scanner.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
