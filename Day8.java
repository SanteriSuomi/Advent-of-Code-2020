import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day8input.txt");
        Day8 d = new Day8();
        System.out.println(d.compute1(input));
        System.out.println(d.compute2(input));    }

    private Map<String, Command> commands;
    
    public Day8() {
        commands = new HashMap<>();
        commands.put("acc", Command.ACCUMULATE);
        commands.put("jmp", Command.JUMP);
        commands.put("nop", Command.NO_OPERATION);
    }

    public int compute1(List<String> s) {
        StringBuilder str = new StringBuilder();
        Set<String> usedCommands = new HashSet<>();
        int accumulator = 0;
        int index = 0;
        while (true) {
            String unparsedCommand = s.get(index);
            String operationSignature = getOperationSignature(unparsedCommand, index);
            if (usedCommands.contains(operationSignature)) return accumulator;
            usedCommands.add(operationSignature);
            Command currCommand = readCommand(unparsedCommand, str);
            if (currCommand == Command.NO_OPERATION) {
                index++;
                continue;
            } 
            if (currCommand == Command.JUMP) {
                index += readValue(unparsedCommand, str);
                continue;
            }
            if (currCommand == Command.ACCUMULATE) {
                accumulator += readValue(unparsedCommand, str);
            }
            index++;
        }
    }

    public int compute2(List<String> s) {
        StringBuilder str = new StringBuilder();
        Set<String> usedCommands = new HashSet<>();
        Set<String> usedCommandsCopy = new HashSet<>();
        int accumulator = 0;
        int accumulatorCopy = 0;
        int index = 0;
        int indexCopy = 0;
        boolean testing = false; // Are we testing currently?
        boolean canTest = false; // Can we test?
        while (true) {
            if (index >= s.size()) {
                return accumulator;
            }
            String unparsedCommand = s.get(index);
            Command currCommand = readCommand(unparsedCommand, str);
            if ((currCommand == Command.JUMP 
              || currCommand == Command.NO_OPERATION) 
              && !testing && canTest) {
                // Start "test" path, store copies of values to backtrack later if not valid "test" path
                accumulatorCopy = accumulator;
                indexCopy = index;
                testing = true;
                unparsedCommand = flipCommand(currCommand, unparsedCommand);
                currCommand = readCommand(unparsedCommand, str);
                usedCommandsCopy.addAll(usedCommands);
            }
            canTest = true;
            String operationSignature = getOperationSignature(unparsedCommand, index);
            if (usedCommands.contains(operationSignature)) { 
                // This path was not valid, reset back to where we came from
                accumulator = accumulatorCopy; 
                index = indexCopy;
                testing = false;
                canTest = false;
                usedCommands.clear();
                usedCommands.addAll(usedCommandsCopy);
                continue;
            }
            usedCommands.add(operationSignature);
            if (currCommand == Command.NO_OPERATION) {
                index++;
                continue;
            } 
            if (currCommand == Command.JUMP) {
                index += readValue(unparsedCommand, str);
                continue;
            }
            if (currCommand == Command.ACCUMULATE) {
                accumulator += readValue(unparsedCommand, str);
            }
            index++;
        }
    }

    private String flipCommand(Command command, String unparsedCommand) {
        if (command == Command.JUMP) {
            return unparsedCommand.replace("jmp", "nop");
        } else {
            return unparsedCommand.replace("nop", "jump");
        }
    }

    private String getOperationSignature(String unparsed, int index) {
        return unparsed + "-" + index;
    }

    private Command readCommand(String s, StringBuilder str) {
        for (int i = 0; i < 3; i++) {
            str.append(s.charAt(i));
        }
        String result = str.toString();
        str.setLength(0);
        return commands.get(result);
    }

    private int readValue(String s, StringBuilder str) {
        boolean negative = (s.charAt(4) == '-');
        for (int i = 5; i < s.length(); i++) {
            str.append(s.charAt(i));
        }
        String result = str.toString();
        str.setLength(0);
        if (negative) {
            return -Integer.parseInt(result);
        }
        return Integer.parseInt(result);
    }

    enum Command {
        ACCUMULATE,
        JUMP,
        NO_OPERATION
    }
}