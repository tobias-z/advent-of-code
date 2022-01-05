package four;

import org.javatuples.Pair;
import utils.TaskReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PartOne {
    public static void main(String[] args) {
        List<String> input = TaskReader.readFile();
        List<Integer> commands = getCommands(input);
        input.remove(0);
        input.remove(0);
        List<Board> boardList = createBoard(input);
        Pair<Board, Integer> winningPair = getWinnerAndWinningNumber(commands, boardList);
        Board winner = winningPair.getValue0();
        int winningNumber = winningPair.getValue1();
        int sum = getSumOfUnmarkedNumbers(winner);
        System.out.println(sum * winningNumber);
    }
    
    private static int getSumOfUnmarkedNumbers(Board board) {
        int sum = 0;
        for (List<BoardItem> boardItem : board.boardItems) {
            for (BoardItem item : boardItem) {
                if (!item.isTouched) sum+= item.value;
            }
        }
        return sum;
    }

    private static Pair<Board, Integer> getWinnerAndWinningNumber(List<Integer> commands, List<Board> boardList) {
        for (Integer command : commands) {
            for (Board board : boardList) {
                board.checkNumber(command);
                if (board.isFinished()) {
                    return new Pair<>(board, command);
                }
            }
        }
        return null;
    }

    private static List<Board> createBoard(List<String> inputList) {
        List<Board> boardList = new ArrayList<>();
        String[] boardStrings = new String[5];
        int count = 0;
        for (String item : inputList) {
            if (count >= 5) {
                count = 0;
                boardList.add(new Board(boardStrings));
                boardStrings = new String[5];
                continue;
            }
            boardStrings[count] = item;
            count++;
        }
        boardList.add(new Board(boardStrings));
        return boardList;
    }

    private static List<Integer> getCommands(List<String> input) {
        return Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    static class Board {
        private final List<List<BoardItem>> boardItems = new ArrayList<>();

        public Board(String[] boardRows) {
            for (String col : boardRows) {
                boardItems.add(Arrays.stream(col.split(" "))
                        .filter(item -> !item.isEmpty())
                        .map(s -> new BoardItem(Integer.parseInt(s)))
                        .collect(Collectors.toList()));
            }
        }

        private void checkNumber(int number) {
            for (List<BoardItem> items : boardItems) {
                for (BoardItem item : items) {
                    if (number == item.value)
                        item.touch();
                }
            }
        }

        public boolean isFinished() {
            for (int i = 0; i < boardItems.size(); i++) {
                List<BoardItem> boardItem = boardItems.get(i);
                if (checkVertical(i)) return true;
                if (checkHorizontal(boardItem)) return true;
            }
            return false;
        }

        private boolean checkHorizontal(List<BoardItem> boardItem) {
            int count = 0;
            for (BoardItem item : boardItem) {
                if (item.isTouched) count++;
            }
            return count == 5;
        }

        private boolean checkVertical(int i) {
            int count = 0;
            for (List<BoardItem> boardItem : boardItems) {
                if (boardItem.get(i).isTouched) count++;
            }
            return count == 5;
        }

    }

    static class BoardItem {
        private final int value;
        private boolean isTouched;

        public BoardItem(int value) {
            this.value = value;
            this.isTouched = false;
        }

        public void touch() {
            this.isTouched = true;
        }
    }
}
