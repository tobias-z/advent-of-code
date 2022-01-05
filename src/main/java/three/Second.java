package three;


import org.javatuples.Pair;
import utils.TaskReader;

import java.util.ArrayList;
import java.util.List;

enum Method {
    OXYGEN,
    CO2
}

public class Second {
    public static void main(String[] args) {
        List<String> input = TaskReader.readFile();
        Pair<String, String> oxygenAndCo2 = getOxygenAndCo2(input);
        int oxygen = Integer.parseInt(oxygenAndCo2.getValue0(), 2);
        int co2 = Integer.parseInt(oxygenAndCo2.getValue1(), 2);
        System.out.println(oxygen * co2);
    }

    private static Pair<String, String> getOxygenAndCo2(List<String> input) {
        String oxygenList = getValue(input, 0, Method.OXYGEN);
        String co2List = getValue(input, 0, Method.CO2);
        return new Pair<>(oxygenList, co2List);
    }

    private static String getValue(List<String> input, int index, Method method) {
        if (input.size() == 1) return input.get(0);
        List<String> oneList = new ArrayList<>();
        List<String> zeroList = new ArrayList<>();
        int ones = 0;
        int zeros = 0;
        for (String strings : input) {
            char[] chars = strings.toCharArray();
            char binaryChar = chars[index];
            if (binaryChar == '1') {
                oneList.add(strings);
                ones++;
            } else if (binaryChar == '0') {
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

}
