package seven;

import utils.TaskReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PartOne {
    public static void main(String[] args) {
        List<Integer> input = Arrays.stream(TaskReader.readFile().get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int i = getMedian(input);
        int fuel = 0;
        for (Integer n : input)
            fuel += Math.max(n, i) - Math.min(n, i);

        System.out.println(fuel);
    }

    private static int getMedian(List<Integer> list) {
        List<Integer> sorted = list
                .stream()
                .sorted()
                .collect(Collectors.toList());

        if (sorted.size() % 2 == 0)
            return (sorted.get(sorted.size() / 2) + sorted.get(sorted.size() / 2 - 1)) / 2;

        return sorted.get(sorted.size() / 2);
    }
}
