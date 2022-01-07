package seven;

import utils.TaskReader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PartOne {
    public static void main(String[] args) {
        List<Integer> input = Arrays.stream(TaskReader.readFile().get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int max = Collections.max(input);
        int min = Collections.min(input);

        int answer = 0;
        for (int i = min; i < max; i++) {
            int fuel = 0;
            for (Integer n : input) {
                int max2 = Math.max(n, i);
                int min2 = Math.min(n, i);
                fuel += max2 - min2;
            }
            if (answer == 0) answer = fuel;
            if (fuel < answer) answer = fuel;
        }

        System.out.println(answer);
    }
}
