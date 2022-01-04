package one;

import utils.TaskReader;

import java.util.List;
import java.util.stream.Collectors;

public class First {
    public static void main(String[] args) {
        List<Integer> input = TaskReader.readFile()
                .stream().map(Integer::parseInt).collect(Collectors.toList());

        int count = 0;
        int prev = input.get(0);
        for (int i = 1; i < input.size(); i++) {
            int curr = input.get(i);
            if (curr > prev) count++;
            prev = curr;
        }

        System.out.println(count);
    }
}
