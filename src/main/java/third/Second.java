package third;


import org.javatuples.Pair;
import utils.Console;
import utils.TaskReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Second {
    public static void main(String[] args) {
        List<List<String>> input = TaskReader.readFile()
                .stream()
                .map(item ->
                        Arrays.stream(item.split("")).collect(Collectors.toList())
                )
                .collect(Collectors.toList());
        Pair<String, String> oxygenAndCo2 = getOxygenAndCo2(input);
        int oxygen = Integer.parseInt(oxygenAndCo2.getValue0(), 2);
        int co2 = Integer.parseInt(oxygenAndCo2.getValue1(), 2);
        System.out.println(oxygen * co2);
    }

    private static Pair<String, String> getOxygenAndCo2(List<List<String>> input) {
        List<String> oxygenList = getValue(input, 0, Method.OXYGEN);
        List<String> co2List = getValue(input, 0, Method.CO2);
        return new Pair<>(getString(oxygenList), getString(co2List));
    }

    private static String getString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s);
        }
        return builder.toString();
    }

    private static List<String> getValue(List<List<String>> input, int index, Method method) {
        if (input.size() == 1) return input.get(0);
        List<List<String>> oneList = new ArrayList<>();
        List<List<String>> zeroList = new ArrayList<>();
        int ones = 0;
        int zeros = 0;
        for (List<String> strings : input) {
            String binaryChar = strings.get(index);
            if (binaryChar.equals("1")) {
                oneList.add(strings);
                ones++;
            } else if (binaryChar.equals("0")) {
                zeroList.add(strings);
                zeros++;
            }
        }
        boolean isOxygen = method.equals(Method.OXYGEN);
        if (ones > zeros) {
            if (isOxygen)
                return getValue(oneList, ++index, method);
            return getValue(zeroList, ++index, method);
        } else if (ones < zeros) {
            if (isOxygen)
                return getValue(zeroList, ++index, method);
            return getValue(oneList, ++index, method);
        }
        if (isOxygen)
            return getValue(oneList, ++index, method);
        return getValue(zeroList, ++index, method);
    }

    enum Method {
        OXYGEN,
        CO2
    }


}
