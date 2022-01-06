package five;

import org.javatuples.Pair;
import utils.TaskReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PartTwo {
    private static final Map<Integer, int[]> coveredLinesMap = new HashMap<>();

    public static void main(String[] args) {
        List<Pair<Coordinate, Coordinate>> coordinatePairs = getCoordinatePairs();
        buildCoveredLinesMap(coordinatePairs);
        int count = 0;
        for (Pair<Coordinate, Coordinate> coordinatePair : coordinatePairs) {
            Coordinate one = coordinatePair.getValue0();
            Coordinate two = coordinatePair.getValue1();
            count = getDiagonalCount(count, one, two);
            if (one.x == two.x) {
                count = followVertical(one.x, getHighestAndLowest(one.y, two.y), count);
            } else if (one.y == two.y) {
                count = followHorizontal(one.y, getHighestAndLowest(one.x, two.x), count);
            }

        }
        printCoveredLines();
        System.out.println(count);
    }

    private static int getDiagonalCount(int count, Coordinate one, Coordinate two) {
        try {
            int slope = (two.x - one.x) / (two.y - one.y);
            if (slope == 1 || slope == -1)
                if (one.y < two.y) {
                    count = followDiagonal(one, two, count);
                } else {
                    count = followDiagonal(two, one, count);
                }
        } catch (ArithmeticException e) {
            // ignore
        }
        return count;
    }

    private static int followDiagonal(Coordinate from, Coordinate to, int count) {
        int x = from.x;
        boolean isLess = from.x < to.x;
        for (int i = from.y; i <= to.y; i++) {
            coveredLinesMap.get(i)[x] += 1;
            if (coveredLinesMap.get(i)[x] == 2) count++;
            if (isLess) x++;
            else x--;
            if (x == -1) break;
        }
        return count;
    }

    private static void buildCoveredLinesMap(List<Pair<Coordinate, Coordinate>> coordinatePairs) {
        int xMax = 0;
        int yMax = 0;
        for (Pair<Coordinate, Coordinate> coordinatePair : coordinatePairs) {
            Coordinate one = coordinatePair.getValue0();
            xMax = Math.max(xMax, one.x);
            yMax = Math.max(yMax, one.y);
        }
        for (int i = 0; i < yMax + 1; i++) {
            int[] line = new int[xMax + 1];
            for (int j = 0; j <= xMax; j++) {
                line[j] = 0;
            }
            coveredLinesMap.put(i, line);
        }
    }

    private static Pair<Integer, Integer> getHighestAndLowest(int n1, int n2) {
        if (n1 > n2) return new Pair<>(n1, n2);
        return new Pair<>(n2, n1);
    }

    private static int followHorizontal(int y, Pair<Integer, Integer> items, int count) {
        int highest = items.getValue0();
        int lowest = items.getValue1();
        for (int i = lowest; i <= highest; i++) {
            coveredLinesMap.get(y)[i] += 1;
            if (coveredLinesMap.get(y)[i] == 2) count++;
        }
        return count;
    }

    private static int followVertical(int x, Pair<Integer, Integer> items, int count) {
        int highest = items.getValue0();
        int lowest = items.getValue1();
        for (int i = lowest; i <= highest; i++) {
            coveredLinesMap.get(i)[x] += 1;
            if (coveredLinesMap.get(i)[x] == 2) count++;
        }
        return count;
    }

    private static List<Pair<Coordinate, Coordinate>> getCoordinatePairs() {
        List<String> input = TaskReader.readFile();
        return input
                .stream()
                .map(s -> {
                    String[] pairStrings = s.split(" -> ");
                    return new Pair<>(getCoordinate(pairStrings[0]), getCoordinate(pairStrings[1]));
                })
                .collect(Collectors.toList());
    }

    private static Coordinate getCoordinate(String pairString) {
        String[] coordinateString = pairString.split(",");
        return new Coordinate(
                Integer.parseInt(coordinateString[0]),
                Integer.parseInt(coordinateString[1])
        );
    }

    private static void printCoveredLines() {
        StringBuilder builder = new StringBuilder();
        coveredLinesMap.forEach((cwlIdx, row) -> {
            for (int item : row) {
                if (item == 0) builder.append(".");
                else builder.append(item);
            }
            builder.append("\n");
        });
        System.out.println(builder);
    }

    record Coordinate(int x, int y) {
    }
}
