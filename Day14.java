import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Code is a MASSIVE mess, would definitely had to be refactored, but couldn't just be bothered that day :D
 * It works, though!
 */
public class Day14 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day14input.txt");
        Day14 d = new Day14();
        System.out.println(d.compute1(input));
        System.out.println(d.compute2(input));
    }

    private Map<Integer, Long> memory1;
    private Map<Long, Integer> memory2;

    public Day14() {
        memory1 = new HashMap<>();
        memory2 = new HashMap<>();
    }

    public long compute1(List<String> input) {
        StringBuilder str = new StringBuilder();
        StringBuilder str1 = new StringBuilder();
        String mask = "";
        for (String s : input) {
            str.setLength(0);
            str1.setLength(0);
            if (s.charAt(1) == 'a') {
                for (int j = 7; j < s.length(); j++) {
                    str.append(s.charAt(j));
                }
                mask = str.toString();
                str.setLength(0);
            } else if (s.charAt(1) == 'e') {
                int memLoc = 0;
                int k = 0;
                while (!Character.isDigit(s.charAt(k))) k++;
                for (; k < s.length(); k++) {
                    if (s.charAt(k) == ']') break;
                    str.append(s.charAt(k));
                }
                memLoc = Integer.parseInt(str.toString());
                str.setLength(0);
                String memValBin = "";
                k += 3;
                while (!Character.isDigit(s.charAt(k))) k++;
                for (; k < s.length(); k++) {
                    str.append(s.charAt(k));
                }
                memValBin = Integer.toBinaryString(Integer.parseInt(str.toString()));
                int missingZeros = mask.length() - memValBin.length();
                for (int j = 0; j < missingZeros; j++) {
                    str1.append('X');
                }
                memValBin = str1.append(memValBin).toString();
                str1.setLength(0);
                str1.append(memValBin);
                for (int j = 0; j < str1.length(); j++) {
                    if (mask.charAt(j) == '1') {
                        str1.setCharAt(j, '1');
                    } else if (mask.charAt(j) == '0') {
                        str1.setCharAt(j, '0');
                    } else if (str1.charAt(j) == 'X') {
                        str1.setCharAt(j, '0');
                    }
                }
                long memVal = Long.parseLong(str1.toString(), 2);
                memory1.put(memLoc, memVal);
            }
        }
        long sum = 0;
        for (Map.Entry<Integer, Long> entry : memory1.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public long compute2(List<String> input) {
        StringBuilder str = new StringBuilder();
        StringBuilder str1 = new StringBuilder();
        List<Integer> floats = new ArrayList<>();
        Set<String> bins = new HashSet<>();
        String mask = "";
        for (String s : input) {
            str.setLength(0);
            str1.setLength(0);
            floats.clear();
            bins.clear();
            if (s.charAt(1) == 'a') {
                for (int j = 7; j < s.length(); j++) {
                    str.append(s.charAt(j));
                }
                mask = str.toString();
                str.setLength(0);
            } else if (s.charAt(1) == 'e') {
                int k = 0;
                while (!Character.isDigit(s.charAt(k))) k++;
                for (; k < s.length(); k++) {
                    if (s.charAt(k) == ']') break;
                    str.append(s.charAt(k));
                }
                String memLocBin = Integer.toBinaryString(Integer.parseInt(str.toString()));
                str.setLength(0);
                int missingZeros = mask.length() - memLocBin.length();
                for (int j = 0; j < missingZeros; j++) {
                    str1.append('0');
                }
                memLocBin = str1.append(memLocBin).toString();
                str1.setLength(0);
                str1.append(memLocBin);
                for (int j = 0; j < str1.length(); j++) {
                    if (mask.charAt(j) == '1') {
                        str1.setCharAt(j, '1');
                    } else if (mask.charAt(j) == 'X') {
                        floats.add(j);
                    }
                }
                int memValue = 0;
                while (!Character.isDigit(s.charAt(k))) k++;
                str.setLength(0);
                for (int i = k; i < s.length(); i++) {
                    str.append(s.charAt(i));
                }
                memValue = Integer.parseInt(str.toString());
                combinations(str1, floats, bins, 0);
                for (String bin : bins) {
                    long memIndex = Long.parseLong(bin, 2);
                    memory2.put(memIndex, memValue);
                }
            }
        }
        long sum = 0;
        for (Map.Entry<Long, Integer> entry : memory2.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    private void combinations(StringBuilder bin, List<Integer> floats, Set<String> bins, int i) {
        if (i >= floats.size()) {
            return;
        }
        bin.setCharAt(floats.get(i), '0');
        bins.add(bin.toString());
        combinations(bin, floats, bins, i + 1);
        bin.setCharAt(floats.get(i), '1');
        bins.add(bin.toString());
        combinations(bin, floats, bins, i + 1);
    }
}