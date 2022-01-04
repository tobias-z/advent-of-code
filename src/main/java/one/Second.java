package one;

import utils.TaskReader;

import java.util.List;
import java.util.stream.Collectors;

public class Second {
    public static void main(String[] args) {
        List<Integer> input = TaskReader.readFile()
                .stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int count = 0;
        int prev = getValue(input, 0);
        for (int i = 0; i < input.size(); i++) {
            boolean isAfterLastEntrance = !(i + 2 < input.size());
            if (isAfterLastEntrance) break;
            int curr = getValue(input, i);
            if (curr > prev) count++;
            prev = curr;
        }
        System.out.println(count);
    }

    private static int getValue(List<Integer> input, int i) {
        return input.get(i) + input.get(i + 1) + input.get(i + 2);
    }

}
