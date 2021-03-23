import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day17 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day17input.txt");
        Day17 d = new Day17();
        System.out.println(d.compute1(input));
    }

    public int compute1(List<String> input) {
        Map<String, Character> map = new HashMap<>();
        int cycles = 1;
        while (cycles <= 6) {
            for (int x = 0; x < input.size(); x++) {
                for (int y = 0; y < input.get(x).length(); y++) {
                    
                }
            }
            cycles++;
        }
    }

    private void compute(int x, int y, int z) {
        
    }
}