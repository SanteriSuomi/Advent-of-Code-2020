import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day7input.txt");
        Day7 d = new Day7();
        System.out.println(d.compute(input));
    }

    public int compute(List<String> s) {
        Map<String, Bag> bagMap = new HashMap<>();
        for (String line : s) {
            addBags(line, bagMap);
        }
        int totalBagsWithGold = 0;
        Pattern pattern = Pattern.compile("shiny gold bag");
        Matcher matcher = null;
        for (Entry<String, Bag> bag : bagMap.entrySet()) {
            Deque<String> bagStack = new ArrayDeque<>();
            bagStack.addAll(bag.getValue().getBags().keySet());
            while (!bagStack.isEmpty()) {
                String currBag = bagStack.removeLast();
                matcher = pattern.matcher(currBag);
                if (matcher.results().count() > 0) {
                    totalBagsWithGold++;
                    break;
                }
                Bag currBagBags = bagMap.get(currBag);
                if (currBagBags != null) {
                    bagStack.addAll(currBagBags.getBags().keySet());
                }
            }
        }
        int totalBagsInside = 0;
        Bag shinyGold = bagMap.get("shiny gold bag");
        int currMultiplier = 1;
        for (Entry<String, Integer> bag : shinyGold.getBags().entrySet()) {
            int bagsInsideBag = 0;
            Deque<BagPack> bagStack = new ArrayDeque<>();
            bagStack.add(new BagPack(bag.getKey(), bag.getValue()));
            while (!bagStack.isEmpty()) {
                BagPack currBag = bagStack.removeLast();
                Bag currBagBags = bagMap.get(currBag.bag);
                if (currBagBags != null) {
                    for (Entry<String, Integer> b : currBagBags.getBags().entrySet()) {
                        bagStack.add(new BagPack(b.getKey(), b.getValue()));
                        bagsInsideBag += (b.getValue() * currMultiplier);
                    }
                }
                if (!bagStack.isEmpty()) {
                    currMultiplier = bagStack.peekLast().val;
                }
            }
            totalBagsInside += bag.getValue() + (bag.getValue() * bagsInsideBag);
        }
        System.out.println(totalBagsInside);
        return totalBagsWithGold;
    }

    class BagPack {
        public String bag;
        public int val;
        public BagPack(String bag, int val) {
            this.bag = bag;
            this.val = val;
        }
    }

    private void addBags(String line, Map<String, Bag> bagMap) {
        String currBag = null;
        Bag currBagBags = new Bag();
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (; i < line.length() - 1; i++) {
            if (isEndOfFirst(line, i)) {
                deleteLastChar(str);
                currBag = str.toString();
                str.setLength(0);
                break;
            }
            str.append(line.charAt(i));
        }
        i += 11;
        for (; i < line.length() - 1; i++) {
            if (line.charAt(i) == ',' && line.charAt(i + 1) == ' ' && Character.isDigit(line.charAt(i + 2))) {
                i += 4;
                deleteLastChar(str);
                currBagBags.getBags().put(str.toString(), getNumber(i, line));
                str.setLength(0);
            }
            str.append(line.charAt(i));
        }
        deleteLastChar(str);
        currBagBags.getBags().put(str.toString(), getNumber(i, line));
        bagMap.put(currBag, currBagBags);
    }

    private int getNumber(int i, String line) {
        for (int j = i; j > 0; j--) {
            if (Character.isDigit(line.charAt(j))) {
                return Character.getNumericValue(line.charAt(j));
            }
        }
        return 0;
    }

    private boolean isEndOfFirst(String line, int i) {
        return line.charAt(i) == ' ' 
        && line.charAt(i + 1) == 'c' 
        && line.charAt(i + 2) == 'o' 
        && line.charAt(i + 3) == 'n' 
        && line.charAt(i + 4) == 't'
        && line.charAt(i + 5) == 'a'
        && line.charAt(i + 6) == 'i'
        && line.charAt(i + 7) == 'n';
    }

    private void deleteLastChar(StringBuilder str) {
        if (str.charAt(str.length() - 1) == 's') {
            str.deleteCharAt(str.length() - 1);
        }
    } 

    class Bag {
        private Map<String, Integer> bags;
        public Map<String, Integer> getBags() {
            return bags;
        }

        public Bag() {
            bags = new HashMap<>();
        }
    }
}