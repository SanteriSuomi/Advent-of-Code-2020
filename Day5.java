import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day5input.txt");
        Day5 d = new Day5();
        System.out.println(d.compute(input));
    }

    public int compute(List<String> s) {
        List<Integer> seatIds = new ArrayList<>();
        int highestSeatId = 0;
        int rowIndex;
        int columnIndex;
        int seatId;
        for (String code : s) { // Calculate seat IDs
            int back = 0;
            int front = 127;
            int mid = getMid(back, front);
            rowIndex = getIndex(code, 0, 7, back, front, mid, 'F', 'B');
            back = 0;
            front = 7;
            mid = getMid(back, front);
            columnIndex = getIndex(code, 7, code.length(), back, front, mid, 'L', 'R');
            seatId = rowIndex * 8 + columnIndex;
            seatIds.add(seatId);
            if (seatId > highestSeatId) {
                highestSeatId = seatId;
            }
        }
        Collections.sort(seatIds);
        for (int i = 1; i < seatIds.size() - 1; i++) { // Calculate missing seat ID
            int currSeatId = seatIds.get(i);
            if (seatIds.get(i - 1) != (currSeatId - 1) 
             || seatIds.get(i + 1) != (currSeatId + 1)) {
                System.out.println("Missing Seat ID: " + (currSeatId + 1));
                break;
            }
        }
        return highestSeatId;
    }

    private int getMid(int back, int front) {
        return (back + front) / 2;
    }

    private int getIndex(String str, int ind, int len, int front, int back, int mid, char low, char high) {
        for (; ind < len; ind++) {
            if (str.charAt(ind) == low) {
                back = mid;
                mid = getMid(back, front);
            } else if (str.charAt(ind) == high) {
                front = mid + 1;
                mid = getMid(back, front);
            }
        }
        return mid;
    }
}