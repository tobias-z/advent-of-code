package eight;

import org.javatuples.Pair;
import utils.Console;
import utils.TaskReader;

import java.util.*;
import java.util.stream.Collectors;

public class PartTwo {
    public static void main(String[] args) {
        List<Pair<String[], String[]>> signalAndOutputPair = getSignalAndOutputPair();
        int answer = 0;

        for (Pair<String[], String[]> signalAndOutput : signalAndOutputPair) {
            String[] signal = signalAndOutput.getValue0();
            String[] outputValues = signalAndOutput.getValue1();
            Display display = new Display(signal);

            StringBuilder values = new StringBuilder();
            for (String outputValue : outputValues) {
                values.append(display.getNum(outputValue));
            }
            int parsed = Integer.parseInt(values.toString());
            Console.log(values, "parsed", parsed);
            answer += parsed;
        }

        System.out.println(answer);

    }


    private static List<Pair<String[], String[]>> getSignalAndOutputPair() {
        return TaskReader.readFile(true)
                .stream()
                .map(line -> line
                        .split(" \\| ")
                )
                .map(line -> new Pair<>(
                        line[0].split(" "),
                        line[1].split(" ")
                ))
                .collect(Collectors.toList());
    }

    static class Display {
        final Map<Integer, List<String>> possibilities = new HashMap<>();
        final Map<Character, Set<Position>> positionMap = new HashMap<>();
        final Map<Position, Set<Character>> positionSetMap = new HashMap<>();
        final Map<String, Integer> correctNumberMap = new HashMap<>();

        Display(String[] arr) {
            for (int i = 2; i < 8; i++)
                possibilities.put(i, new ArrayList<>());
            for (char c : "abcdefg".toCharArray())
                positionMap.put(c, new HashSet<>());
            for (Position p : Position.values())
                positionSetMap.put(p, new HashSet<>());

            calculatePossibilities(arr);
            createPositions();
        }

        void calculatePossibilities(String[] arr) {
            for (String signal : arr)
                possibilities.get(signal.length()).add(signal);

        }

        void createPositions() {
            String twos = possibilities.get(2).get(0);
            correctNumberMap.put(twos, 2);
            for (char c : twos.toCharArray()) {
                addPosition(c, Position.TOP_RIGHT, Position.BOTTOM_RIGHT);
            }

            String threes = possibilities.get(3).get(0);
            correctNumberMap.put(threes, 3);
            for (char c : threes.toCharArray()) {
                if (twos.contains(String.valueOf(c)))
                    addPosition(c, Position.TOP_RIGHT, Position.BOTTOM_RIGHT);
                else addPosition(c, Position.TOP);
            }

            String fours = possibilities.get(4).get(0);
            correctNumberMap.put(fours, 4);
            for (char c : fours.toCharArray()) {
                if (twos.contains(String.valueOf(c))) {
                    addPosition(c, Position.TOP_RIGHT, Position.BOTTOM_RIGHT);
                    continue;
                }
                addPosition(c, Position.TOP_LEFT, Position.MIDDLE);
            }

            correctNumberMap.put(possibilities.get(7).get(0), 7);

            int topRightPositions = 0;
            for (String s : possibilities.get(5)) {
                Set<Character> topLeft = positionSetMap.get(Position.TOP_LEFT);
                Set<Character> middle = positionSetMap.get(Position.MIDDLE);
                List<Boolean> booleanList = new ArrayList<>();
                Set<Character> usedCharSet = new HashSet<>();
                for (char c : s.toCharArray()) {
                    if (topLeft.contains(c) && !usedCharSet.contains(c)) {
                        booleanList.add(true);
                        usedCharSet.add(c);
                    }
                    if (middle.contains(c) && !usedCharSet.contains(c)) {
                        booleanList.add(true);
                        usedCharSet.add(c);
                    }

                }

                Iterator<Character> iterator = positionSetMap.get(Position.TOP_RIGHT).iterator();
                Character character = iterator.next();
                if (s.contains(String.valueOf(character))) {
                    topRightPositions++;
                    Set<Character> charValue = new HashSet<>() {
                        {
                            add(character);
                        }
                    };
                    if (topRightPositions == 2 && booleanList.size() != 2) {
                        // is 2
                        correctNumberMap.put(s, 2);
                        if (iterator.hasNext()) {
                            positionSetMap.replace(Position.TOP_RIGHT, charValue);
                            positionSetMap.replace(Position.BOTTOM_RIGHT, new HashSet<>() {
                                {
                                    add(iterator.next());
                                }
                            });
                        }
                    } else if (booleanList.size() == 2) {
                        positionSetMap.replace(Position.TOP_RIGHT, new HashSet<>() {
                            {
                                add(iterator.next());
                            }
                        });
                        positionSetMap.replace(Position.BOTTOM_RIGHT, charValue);
                    }
                }

                if (booleanList.size() == 2)
                    correctNumberMap.put(s, 5);

            }

            for (String s : possibilities.get(5)) {
                if (!correctNumberMap.containsKey(s))
                    correctNumberMap.put(s, 3);
            }

            char sixNext = positionSetMap.get(Position.TOP_RIGHT).iterator().next();

            String searchForG = "";
            for (String s : possibilities.get(6)) {
                if (!s.contains(String.valueOf(sixNext))) {
                    searchForG = s;
                    correctNumberMap.put(s, 6);
                }
            }

            List<Pair<Character, String>> chars = new ArrayList<>();
            for (String s : possibilities.get(6)) {
                for (char c : searchForG.toCharArray()) {
                    if (!s.contains(String.valueOf(c))) {
                        chars.add(new Pair<>(c, s));
                    }
                }
            }

            for (Pair<Character, String> aChar : chars) {
                char c = aChar.getValue0();
                String val = aChar.getValue1();
                if (positionMap.get(c).size() > 1) {
                    correctNumberMap.put(val, 0);
                } else correctNumberMap.put(val, 9);
            }
        }

        void addPosition(char c, Position... positions) {
            positionMap.get(c).addAll(List.of(positions));
            for (Position position : positions)
                positionSetMap.get(position).add(c);
        }

        int getNum(String key) {
            OUTER:
            for (Map.Entry<String, Integer> stringIntegerEntry : correctNumberMap.entrySet()) {
                String str = stringIntegerEntry.getKey();
                Integer num = stringIntegerEntry.getValue();
                if (key.length() != str.length()) continue;

                for (char numChar : str.toCharArray()) {
                    if (!key.contains(String.valueOf(numChar))) {
                        continue OUTER;
                    }
                }

                return num;
            }
            throw new RuntimeException("No such key was found: " + key);
        }

        enum Position {
            TOP,
            TOP_RIGHT,
            TOP_LEFT,
            MIDDLE,
            BOTTOM_RIGHT,
            BOTTOM_LEFT,
            BOTTOM
        }
    }
}
