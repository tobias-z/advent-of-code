package six;

import utils.TaskReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PartOne {
    public static void main(String[] args) {
        long[] fishArr = getStartingFish();

        for (int i = 0; i < 256; i++) {
            long newFish = fishArr[0];
            fishArr[0] = 0;
            for (int j = 1; j < 9; j++) {
                fishArr[j - 1] += fishArr[j];
                fishArr[j] = 0;
            }
            fishArr[8] = newFish;
            fishArr[6] += newFish;
        }

        System.out.println(Arrays.stream(fishArr).sum());
    }

    private static long[] getStartingFish() {
        List<Integer> numbers = Arrays.stream(TaskReader.readFile().get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        long[] fishArr = new long[9];
        for (Integer n : numbers) {
            fishArr[n] += 1;
        }
        return fishArr;
    }
}
