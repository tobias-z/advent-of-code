package third;


import org.javatuples.Pair;
import utils.TaskReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

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

    private static int getDecimalFromBinary(String value) {
        int answer = 0;
        int count = 0;
        char[] binaries = value.toCharArray();
        for (int i = binaries.length - 1; i >= 0; --i) {
            char binary = binaries[i];
            if (binary == '1') {
                answer += Math.pow(2, count);
            }
            count++;
        }
        return answer;
    }

    public static void main(String[] args) {
        List<List<String>> input = TaskReader.readFile()
                .stream()
                .map(item ->
                        Arrays.stream(item.split("")).collect(Collectors.toList())
                )
                .collect(Collectors.toList());
        Pair<String, String> gammaAndEpsilonPair = getGammaAndEpsilon(input);
        int gammaDecimal = getDecimalFromBinary(gammaAndEpsilonPair.getValue0());
        int epsilonDecimal = getDecimalFromBinary(gammaAndEpsilonPair.getValue1());
        System.out.println(gammaDecimal * epsilonDecimal);
    }

}
