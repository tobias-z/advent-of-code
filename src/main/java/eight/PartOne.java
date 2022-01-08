package eight;

import utils.TaskReader;

import java.util.List;
import java.util.stream.Collectors;

public class PartOne {
    public static void main(String[] args) {
        List<String[]> outputList = getOutputValues();
        int answer = 0;
        for (String[] outputValues : outputList)
            for (String output : outputValues)
                for (int display : new int[] {2, 4, 3, 7})
                    if (isNumber(output, display))
                        answer++;

        System.out.println(answer);
    }

    private static boolean isNumber(String outputValues, int display) {
        return outputValues.length() == display;
    }

    private static List<String[]> getOutputValues() {
        return TaskReader.readFile()
                .stream()
                .map(line -> line
                        .split(" \\| ")[1]
                        .split(" ")
                )
                .collect(Collectors.toList());
    }
}
