package streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class UsingStreams {

    // region Tested methods
    public static List<String> getUpperCase(String[] words) {
        return test00(words);
    }

    public static ConcurrentMap<Object, Long> getCounts(List<String> states) {
        return test01(states);
    }

    public static Map<String, Integer> getCountsBasic(List<String> states) {
        return test02(states);
    }

    public static List<String> getWordsThatStartWithA(List<String> words) {
        return test03(words);
    }

    public static List<String> getWordsWithoutDuplicates(List<String> words) {
        return test04(words);
    }

    public static List<String> getWordsSortedInNaturalOrder(List<String> words) {
        return test05(words);
    }

    public static List<String> printWordsThenReturnAsUppercase(List<String> words) {
        return test06(words);
    }

    public static void printEachWord(List<String> words) {
        test07(words);
    }

    public static String getCommaSeparatedString(List<String> words) {
        return test08(words);
    }

    public static Long getWordCount(List<String> words) {
        return test09(words);
    }

    public static boolean checkIfStartsWith(List<String> words, String start) {
        return test10(words, start);
    }

    public static String findFirstStartsWith(List<String> words, String start) {
        return test11(words, start);
    }

    public static String findAnyStartsWith(List<String> words, String start) {
        return test12(words, start);
    }

    public static List<String> findWordsWithLengthOf(List<String> words, int length) {
        return test13(words, length);
    }

    public static List<Integer> getListOfWordLengths(List<String> words) {
        return test14(words);
    }

    public static long countDistinctLengths(List<String> words) {
        return test15(words);
    }

    public static String concatenateWithHyphen(List<String> words) {
        return test16(words);
    }

    public static String uppercaseConcatenated(List<String> words) {
        return test17(words);
    }

    public static boolean hasWordsGreaterThan(List<String> words, int count) {
        return test18(words, count);
    }

    public static int findMaxWordLength(List<String> words) {
        return test19(words);
    }

    public static int findMinWordLength(List<String> words) {
        return test20(words);
    }

    public static List<String> findWordsContaining(List<String> words, String letter) {
        return test21(words, letter);
    }

    public static long countWordsStartingWith(List<String> words, String start) {
        return test22(words, start);
    }

    public static Map<Integer, List<String>> groupWordsByLength(List<String> words) {
        return test23(words);
    }

    public static int calculateTotalLengthofAllWords(List<String> words) {
        return test24(words);
    }

    public static int calculateProductOfWordLengths(List<String> words) {
        return test25(words);
    }
    // endregion

    /// ----------------------------------------------
    ///                Tests start here!!!
    /// ----------------------------------------------
    private static List<String> test00(String[] phrase) {
        List<String> uppercaseWords = null;
        uppercaseWords = Arrays.stream(phrase).map(String::toUpperCase)
                .peek(System.out::println)
                .toList();

        return uppercaseWords;
    }

    public static ConcurrentMap<Object, Long> test01(List<String> states) {
        return states.stream()
                .collect(Collectors.groupingByConcurrent(s -> s, Collectors.counting()));
    }

    private static Map<String, Integer> test02(List<String> states) {
        Map<String, Long> map = states.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        return map.entrySet()
                .stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Math.toIntExact(e.getValue())));
    }

    private static List<String> test03(List<String> words) {
        List<String> wordsStartingWithA = null;
        wordsStartingWithA = words.stream()
                .filter(s -> s.toUpperCase().startsWith("A"))
                .collect(Collectors.toList());

        return wordsStartingWithA;
    }

    private static List<String> test04(List<String> words) {
        return words.stream().distinct().toList();
    }

    private static List<String> test05(List<String> words) {
        return words.stream().sorted().toList();
    }

    private static List<String> test06(List<String> words) {
        return words.stream().map(w -> {
            System.out.println(w);
            return w.toUpperCase();
        }).toList();
    }

    private static void test07(List<String> words) {
        words.stream().forEach(System.out::print);
    }

    private static String test08(List<String> words) {
        return words.stream().collect(Collectors.joining(","));
    }

    private static Long test09(List<String> words) {
        return words.stream().count();
    }

    private static boolean test10(List<String> words, String start) {
        return words.stream().anyMatch(s -> s.startsWith(start));
    }

    private static String test11(List<String> words, String start) {
        return words.stream()
                .filter(s -> s.startsWith(start))
                .findFirst()
                .orElse(null);
    }

    private static String test12(List<String> words, String start) {
        return words.stream().filter(s -> s.startsWith(start)).findAny().orElse(null);
    }

    private static List<String> test13(List<String> words, int length) {
        return words.stream()
                .filter(s -> s.length() == length)
                .toList();
    }


    private static List<Integer> test14(List<String> words) {
        return words.stream()
                .map(String::length)
                .toList();
    }

    private static long test15(List<String> words) {
        return words.stream()
                .map(String::length)
                .distinct()
                .count();
    }

    private static String test16(List<String> words) {
        return words.stream()
                .collect(Collectors.joining("-"));
    }

    private static String test17(List<String> words) {
        return words.stream().map(String::toUpperCase).collect(Collectors.joining());
    }

    private static boolean test18(List<String> words, int count) {
        return words.stream()
                .allMatch(s -> s.length() > count);
    }


    private static int test19(List<String> words) {
        return words.stream()
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(0);
    }

    private static int test20(List<String> words) {
        return words.stream()
                .map(String::length)
                .min(Integer::compareTo)
                .orElse(0);
    }

    private static List<String> test21(List<String> words, String letter) {
        return words.stream()
                .filter(s -> s.contains(letter))
                .toList();
    }

    private static long test22(List<String> words, String start) {
        return words.stream().filter(s -> s.startsWith(start)).count();
    }

    private static Map<Integer, List<String>> test23(List<String> words) {
        return words.stream()
                .collect(Collectors.groupingBy(String::length));
    }

    private static int test24(List<String> words) {
        return words.stream()
                .map(String::length)
                .reduce(0, Integer::sum);
    }


    private static int test25(List<String> words) {
        return words.stream()
                .map(String::length)
                .reduce(1, (a, b) -> a * b);
    }
}
