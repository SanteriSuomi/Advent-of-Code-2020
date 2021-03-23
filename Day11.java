import java.util.ArrayList;
import java.util.List;

public class Day11 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day11input.txt");
        char[][] seats = convertInput(input);
        Day11 d = new Day11();
        System.out.println(d.compute1(seats));
        seats = convertInput(input);
        System.out.println(d.compute2(seats));
    }

    private static char[][] convertInput(List<String> input) {
        char[][] seats = new char[input.size()][input.get(0).length()];
        for (int r = 0; r < seats.length; r++) {
            for (int c = 0; c < seats[0].length; c++) {
                seats[r][c] = input.get(r).charAt(c);
            }
        }
        return seats;
    }

    private char empty = 'L';
    private char occupied = '#';
    private char floor = '.';

    public int compute1(char[][] seats) {
        List<Change> changes = new ArrayList<>();
        int changedStateAmount;
        do {
            changedStateAmount = 0;
            changes.clear();
            for (int row = 0; row < seats.length; row++) {
                for (int col = 0; col < seats[0].length; col++) {
                    if (doesChangeAdjacent(seats, changes, row, col)) {
                        changedStateAmount++;
                    }
                }
            }
            applyChanges(seats, changes);
        } while (changedStateAmount > 0);
        return getOccupiedAmount(seats);
    }

    public int compute2(char[][] seats) {
        List<Change> changes = new ArrayList<>();
        int changedStateAmount;
        do {
            changedStateAmount = 0;
            changes.clear();
            for (int row = 0; row < seats.length; row++) {
                for (int col = 0; col < seats[0].length; col++) {
                    if (doesChangeDirection(seats, changes, row, col)) {
                        changedStateAmount++;
                    }
                }
            }
            applyChanges(seats, changes);
        } while (changedStateAmount > 0);
        return getOccupiedAmount(seats);
    }

    private boolean doesChangeAdjacent(char[][] seats, List<Change> changes, int row, int col) {
        int adj = getAdjacent(seats, row, col, occupied, 0, 1);
        if (seats[row][col] == empty && adj == 0) {
            changes.add(new Change(row, col, occupied));
            return true;
        } else if (seats[row][col] == occupied && adj >= 4) {
            changes.add(new Change(row, col, empty));
            return true;
        }
        return false;
    }

    private int getAdjacent(char[][] seats, int row, int col, char c, int depth, int maxDepth) {
        return adjacent(seats, row - 1, col, c, depth + 1, maxDepth) // Top
             + adjacent(seats, row + 1, col, c, depth + 1, maxDepth) // Bottom
             + adjacent(seats, row, col - 1, c, depth + 1, maxDepth) // Left
             + adjacent(seats, row, col + 1, c, depth + 1, maxDepth) // Right
             + adjacent(seats, row - 1, col - 1, c, depth + 1, maxDepth) // Top left
             + adjacent(seats, row - 1, col + 1, c, depth + 1, maxDepth) // Top right
             + adjacent(seats, row + 1, col - 1, c, depth + 1, maxDepth) // Bottom left
             + adjacent(seats, row + 1, col + 1, c, depth + 1, maxDepth); // Bottom right
    }

    private int adjacent(char[][] seats, int row, int col, char c, int depth, int maxDepth) {
        if (depth > maxDepth 
            || (row < 0 || row == seats.length) 
            || (col < 0 || col == seats[0].length)
            || seats[row][col] == floor) return 0;  
        if (seats[row][col] == c) return 1;
        return getAdjacent(seats, row, col, c, depth, maxDepth);
    }

    private boolean doesChangeDirection(char[][] seats, List<Change> changes, int row, int col) {
        int dir = getDirection(seats, row, col, occupied);
        if (seats[row][col] == empty && dir == 0) {
            changes.add(new Change(row, col, occupied));
            return true;
        } else if (seats[row][col] == occupied && dir >= 5) {
            changes.add(new Change(row, col, empty));
            return true;
        }
        return false;
    }

    private int getDirection(char[][] seats, int row, int col, char c) {
        return direction(seats, row - 1, col, c, -1, 0) // Top
             + direction(seats, row + 1, col, c, 1, 0) // Bottom
             + direction(seats, row, col - 1, c, 0, -1) // Left
             + direction(seats, row, col + 1, c, 0, 1) // Right
             + direction(seats, row - 1, col - 1, c, -1, -1) // Top left
             + direction(seats, row - 1, col + 1, c, -1, 1) // Top right
             + direction(seats, row + 1, col - 1, c, 1, -1) // Bottom left
             + direction(seats, row + 1, col + 1, c, 1, 1); // Bottom right
    }

    private int direction(char[][] seats, int row, int col, int c, int rowAdd, int colAdd) {
        if ((row < 0 || row == seats.length) 
            || (col < 0 || col == seats[0].length)) return 0;
        if (seats[row][col] != floor) {
            if (seats[row][col] == c) {
                return 1;
            }
            return 0;
        }
        return direction(seats, row + rowAdd, col + colAdd, c, rowAdd, colAdd);
    }

    private void applyChanges(char[][] seats, List<Change> changes) {
        for (Change change : changes) {
            seats[change.row][change.col] = change.ch;
        }
    }

    private int getOccupiedAmount(char[][] seats) {
        int amount = 0;
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[0].length; col++) {
                if (seats[row][col] == occupied) {
                    amount++;
                } 
            }
        }
        return amount;
    }

    class Change {
        public char ch;
        public int row;
        public int col;
        public Change(int row, int col, char ch) {
            this.row = row;
            this.col = col;
            this.ch = ch;
        }
    }
}