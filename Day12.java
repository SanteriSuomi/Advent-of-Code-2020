import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day12input.txt");
        Day12 d = new Day12();
        System.out.println(d.compute1(input));
    }

    enum Facing {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    private Map<Integer, Facing> faceMap;
    private Map<Character, Facing> charMap;
    private int face = 1;
    private int x = 0;
    private int y = 0;

    public Day12() {
        faceMap = new HashMap<>();
        faceMap.put(0, Facing.NORTH);
        faceMap.put(1, Facing.EAST);
        faceMap.put(2, Facing.SOUTH);
        faceMap.put(3, Facing.WEST);
        charMap = new HashMap<>();
        charMap.put('N', Facing.NORTH);
        charMap.put('E', Facing.EAST);
        charMap.put('S', Facing.SOUTH);
        charMap.put('W', Facing.WEST);
    }

    public int compute1(List<String> input) {
        StringBuilder str = new StringBuilder();
        for (String line : input) {
            apply(line, str);
        }
        return (Math.abs(x) + Math.abs(y));
    }

    private void apply(String s, StringBuilder str) {
        char dir = s.charAt(0);
        int amount = value(s, str);
        if (dir == 'F') {
            move(faceMap.get(face), amount);
        } else if (dir == 'N' || dir == 'S' || dir == 'E' || dir == 'W') {
            move(charMap.get(dir), amount);
        } else {
            turn(dir, amount);
        }
    }

    private void move(Facing f, int amount) {
        switch (f) {
            case NORTH:
            y += amount;
            break;
            case SOUTH:
            y -= amount;
            break;
            case EAST:
            x += amount;
            break;
            case WEST:
            x -= amount;
            break;
        }
    }

    private void turn(char dir, int amount) {
        amount /= 90;
        if (dir == 'R') {
            face += amount;
            face = face % 4;
        } else {
            face -= amount;
            face = face % 4;
            if (face < 0) face += 4;
        }
    }

    private int value(String s, StringBuilder str) {
        for (int i = 1; i < s.length(); i++) {
            str.append(s.charAt(i));
        }
        int res = Integer.parseInt(str.toString());
        str.setLength(0);
        return res;
    }
}