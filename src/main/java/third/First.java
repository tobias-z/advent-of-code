package third;


import org.javatuples.Pair;
import utils.TaskReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class First {

    private static Pair<String, String> getGammaAndEpsilon(List<List<String>> input) {
        StringBuilder gammaBuilder = new StringBuilder();
        StringBuilder epsilonBuilder = new StringBuilder();
        for (int j = 0; j < input.get(0).size(); j++) {
            int ones = 0;
            int zeros = 0;
            for (List<String> strings : input) {
                String binaryChar = strings.get(j);
                if (binaryChar.equals("1")) {
                    ones++;
                } else if (binaryChar.equals("0")) {
                    zeros++;
                }
            }
            if (ones > zeros) {
                gammaBuilder.append(1);
                epsilonBuilder.append(0);
            } else if (ones < zeros) {
                gammaBuilder.append(0);
                epsilonBuilder.append(1);
            }
        }
        return new Pair<>(gammaBuilder.toString(), epsilonBuilder.toString());
    }

    public static void main(String[] args) {
        List<List<String>> input = TaskReader.readFile()
                .stream()
                .map(item ->
                        Arrays.stream(item.split("")).collect(Collectors.toList())
                )
                .collect(Collectors.toList());
        Pair<String, String> gammaAndEpsilonPair = getGammaAndEpsilon(input);
        int gammaDecimal = Integer.parseInt(gammaAndEpsilonPair.getValue0(), 2);
        int epsilonDecimal = Integer.parseInt(gammaAndEpsilonPair.getValue1(), 2);
        System.out.println(gammaDecimal * epsilonDecimal);
    }

}
