package two;

import utils.Console;
import utils.TaskReader;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface Command {
    void apply(Both.Submarine submarine);
}

public class Both {
    private static List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();
        for (String task : TaskReader.readFile()) {
            String[] assignment = task.split(" ");
            String command = assignment[0];
            int amount = Integer.parseInt(assignment[1]);
            commands.add(createCommand(command, amount));
        }
        return commands;
    }

    private static Command createCommand(String command, int amount) {
        return submarine -> {
            switch (command) {
                case "forward" -> {
                    submarine.increaseDepth(amount);
                    submarine.addHorizontal(amount);
                }
                case "down" -> submarine.increaseAim(amount);
                case "up" -> submarine.decreaseAim(amount);
                default -> System.out.println("unknown command: " + command);
            }
        };
    }

    public static void main(String[] args) {
        List<Command> commands = getCommands();
        Submarine submarine = new Submarine();
        for (Command command : commands) {
            command.apply(submarine);
        }
        Console.log(submarine.depth * submarine.horizontal);
    }

    static class Submarine {
        private int horizontal = 0;
        private int depth = 0;
        private int aim = 0;

        public void addHorizontal(int h) {
            this.horizontal += h;
        }

        public void increaseDepth(int d) {
            this.depth += this.aim * d;
        }

        public void increaseAim(int a) {
            this.aim += a;
        }

        public void decreaseAim(int a) {
            this.aim -= a;
        }
    }
}
