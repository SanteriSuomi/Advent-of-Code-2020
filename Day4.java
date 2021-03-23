import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day4 {
    public static void main(String[] args) {
        List<String> input = ReadInput.asString("day4input.txt");
        Day4 d = new Day4();
        System.out.println(d.compute(input));
    }

    private int requiredAmount;
    private Map<String, KeywordType> requiredKeywords;
    private Set<String> permittedEyeColors;
    private Set<Character> permittedHairColorChars;

    enum KeywordType {
        BIRTH_YEAR, ISSUE_YEAR, EXPIRATION_YEAR, HEIGHT, HAIR_COLOR, EYE_COLOR, PASSPORT_ID
    }

    public Day4() {
        requiredKeywords = new HashMap<>();
        requiredKeywords.put("byr", KeywordType.BIRTH_YEAR);
        requiredKeywords.put("iyr", KeywordType.ISSUE_YEAR);
        requiredKeywords.put("eyr", KeywordType.EXPIRATION_YEAR);
        requiredKeywords.put("hgt", KeywordType.HEIGHT);
        requiredKeywords.put("hcl", KeywordType.HAIR_COLOR);
        requiredKeywords.put("ecl", KeywordType.EYE_COLOR);
        requiredKeywords.put("pid", KeywordType.PASSPORT_ID);
        requiredAmount = 7;
        permittedEyeColors = new HashSet<>();
        permittedEyeColors.add("amb");
        permittedEyeColors.add("blu");
        permittedEyeColors.add("brn");
        permittedEyeColors.add("gry");
        permittedEyeColors.add("grn");
        permittedEyeColors.add("hzl");
        permittedEyeColors.add("oth");
        permittedHairColorChars = new HashSet<>();
        permittedHairColorChars.add('a');
        permittedHairColorChars.add('b');
        permittedHairColorChars.add('c');
        permittedHairColorChars.add('d');
        permittedHairColorChars.add('e');
        permittedHairColorChars.add('f');
    }

    public int compute(List<String> s) {
        StringBuilder str = new StringBuilder();
        int total = 0;
        int currRequired = 0;
        for (String batch : s) {
            if (batch.isEmpty()) {
                if (currRequired >= requiredAmount) {
                    total++;
                }
                currRequired = 0;
                continue;
            }
            str.setLength(0);
            str.append(batch.charAt(0));
            str.append(batch.charAt(1));
            str.append(batch.charAt(2));
            int end = batch.length() - 1;
            String currKeyword = str.toString();
            if (isValidKeyword(currKeyword, batch, 4, end)) {
                currRequired++;
            }
            for (int i = 3; i <= end; i++) {
                str.deleteCharAt(0);
                str.append(batch.charAt(i));
                currKeyword = str.toString();
                if (isValidKeyword(currKeyword, batch, i + 2, end)) {
                    currRequired++;
                }
            }
        }
        if (currRequired >= requiredAmount) {
            total++;
        }
        return total;
    }

    private boolean isValidKeyword(String keyword, String batch, int index, int end) {
        KeywordType kt = requiredKeywords.get(keyword);
        if (kt != null) {
            if (kt == KeywordType.BIRTH_YEAR) {
                return isYearValid(batch, index, end, 1920, 2002);
            } else if (kt == KeywordType.ISSUE_YEAR) {
                return isYearValid(batch, index, end, 2010, 2020);
            } else if (kt == KeywordType.EXPIRATION_YEAR) {
                return isYearValid(batch, index, end, 2020, 2030);
            } else if (kt == KeywordType.HEIGHT) {
                return isHeightValid(batch, index, end);
            } else if (kt == KeywordType.HAIR_COLOR) {
                return isHairColorValid(batch, index, end);
            } else if (kt == KeywordType.EYE_COLOR) {
                return isEyeColorValid(batch, index, end);
            } else if (kt == KeywordType.PASSPORT_ID) {
                return isPassportIdValid(batch, index, end);
            }
        }
        return false;
    }

    private String getKeywordValue(String batch, int index, int end) {
        StringBuilder str = new StringBuilder();
        for (int i = index; i <= end; i++) {
            if (batch.charAt(i) == ' ')
                break;
            str.append(batch.charAt(i));
        }
        return str.toString();
    }

    private boolean isYearValid(String batch, int index, int end, int yearMin, int yearMax) {
        String res = getKeywordValue(batch, index, end);
        if (res.isEmpty()) {
            return false;
        }
        int year = Integer.parseInt(res);
        return year >= yearMin && year <= yearMax;
    }

    private boolean isHeightValid(String batch, int index, int end) {
        String res = getKeywordValue(batch, index, end);
        if (res.isEmpty()) {
            return false;
        }
        int height;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < res.length(); i++) {
            if (res.charAt(i) == 'c') {
                height = Integer.parseInt(str.toString());
                if (height >= 150 && height <= 193) {
                    return true;
                }
            } else if (res.charAt(i) == 'i') {
                height = Integer.parseInt(str.toString());
                if (height >= 59 && height <= 76) {
                    return true;
                }
            }
            str.append(res.charAt(i));
        }
        return false;
    }

    private boolean isHairColorValid(String batch, int index, int end) {
        String res = getKeywordValue(batch, index, end);
        if (res.isEmpty()) {
            return false;
        }
        if (res.charAt(0) != '#')
            return false;
        for (int i = 1; i < res.length(); i++) {
            if (Character.isDigit(res.charAt(i))) {
                int digit = Character.getNumericValue(res.charAt(i));
                if (digit < 0 || digit > 9) {
                    return false;
                }
            } else if (Character.isLetter(res.charAt(i)) && !permittedHairColorChars.contains(res.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isEyeColorValid(String batch, int index, int end) {
        String res = getKeywordValue(batch, index, end);
        if (res.isEmpty()) {
            return false;
        }
        return permittedEyeColors.contains(res);
    }

    private boolean isPassportIdValid(String batch, int index, int end) {
        String res = getKeywordValue(batch, index, end);
        if (res.isEmpty()) {
            return false;
        }
        int digits = 0;
        for (int i = 0; i < res.length(); i++) {
            if (Character.isDigit(res.charAt(i))) {
                digits++;
            }
        }
        return digits == 9;
    }
}