import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day6 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day6input.txt");
        Day6 d = new Day6();
        System.out.println(d.compute(input));
    }

    public int compute(List<String> s) {
        Map<Character, Integer> answers = new HashMap<>();
        int currPeople = 0;
        int totalAnswers = 0;
        for (int i = 0; i < s.size(); i++) {
            String curr = s.get(i);
            if (curr.isEmpty()) {
                totalAnswers += getValidAnswers(answers, currPeople);
                answers.clear();
                currPeople = 0;
                continue;
            }
            currPeople++;
            for (int j = 0; j < curr.length(); j++) {
                update(curr.charAt(j), answers);
            }
        }
        totalAnswers += getValidAnswers(answers, currPeople);
        return totalAnswers;
    }

    private int getValidAnswers(Map<Character, Integer> questions, int currPeople) {
        int validAnswers = 0;
        for (Entry<Character, Integer> entry : questions.entrySet()) {
            if (entry.getValue() == currPeople) {
                validAnswers++;
            }
        }
        return validAnswers;
    }

    private void update(char c, Map<Character, Integer> m) {
        if (m.containsKey(c)) {
            m.put(c, m.get(c) + 1);
        } else {
            m.put(c, 1);
        }
    }
}