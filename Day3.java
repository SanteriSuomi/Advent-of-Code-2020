import java.util.List;

public class Day3 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day3input.txt");
        char[][] map = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = input.get(i).charAt(j);
            }
        }
        Day3 d = new Day3();
        System.out.println(d.computePart1(map, map.length, map[0].length));
    }

    public long computePart1(char[][] input, int maxRow, int maxColumn) {
        return getTrees(input, 1, 1, maxRow, maxColumn) 
             * getTrees(input, 1, 3, maxRow, maxColumn)
             * getTrees(input, 1, 5, maxRow, maxColumn) 
             * getTrees(input, 1, 7, maxRow, maxColumn)
             * getTrees(input, 2, 1, maxRow, maxColumn);
    }

    private long getTrees(char[][] input, int rowIncrease, int columnIncrease, int maxRow, int maxColumn) {
        int trees = 0;
        int currRow = 0;
        int currColumn = 0;
        while (currRow <= maxRow) {
            currRow += rowIncrease;
            currColumn += columnIncrease;
            if (currRow >= maxRow) {
                return trees;
            }
            if (currColumn >= maxColumn) {
                int diff = currColumn - maxColumn;
                currColumn = diff;
            }
            if (input[currRow][currColumn] == '#') {
                trees++;
            }
        }
        return (long)trees;
    }
}