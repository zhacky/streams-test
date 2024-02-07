package streams;

import java.util.*;
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

    public static boolean noWordsStartWith(List<String> words, String start) {
        return test26(words, start);
    }

    public static List<String> findFirstThreeWords(List<String> words) {
        return test27(words);
    }

    public static List<String> getWordsSkippingFirstFew(List<String> words, int count) {
        return test28(words, count);
    }

    public static double getAverageLength(List<String> words) {
        return test29(words);
    }

    public static List<String> replaceOccurrencesWith(List<String> words, String orig, String replacement) {
        return test30(words, orig, replacement);
    }

    public static boolean allWordsUppercase(List<String> words) {
        return test31(words);
    }

    public static String concatenateDistinctLetters(List<String> words) {
        return test32(words);
    }

    public static Set<String> convertListOfWordsToSet(List<String> words) {
        return test33(words);
    }

    public static List<String> printLengthsAndUppercaseForm(List<String> words) {
        return test34(words);
    }

    public static String findTheLongestWord(List<String> words) {
        return test35(words);
    }

    public static String findTheShortestWord(List<String> words) {
        return test36(words);
    }


    public static List<String> reverseEachWord(List<String> words) {
        return test37(words);
    }

    public static boolean allWordsEndWith(List<String> words, String end) {
        return test38(words, end);
    }

    public static List<String> removeWordsContainingTheLetter(List<String> words, String letter) {
        return test39(words, letter);
    }

    public static List<String> convertTheFirstLetterToUppercase(List<String> words) {
        return test40(words);
    }

    public static int findIndexOfFirstWordStartingWith(List<String> words, String start) {
        return test41(words, start);
    }

    public static boolean hasWordContainingDigit(List<String> words) {
        return test42(words);
    }

    public static List<String> replaceVowelsInEachWordWith(List<String> words, String replacement) {
        return test43(words, replacement);
    }

    public static List<String> findDistinctLettersFromWords(List<String> words) {
        return test44(words);
    }

    public static boolean allWordsHaveAtLeastLengthOf(List<String> words, int length) {
        return test45(words, length);
    }

    public static boolean hasAnyWordExactlyCharacters(List<String> words, int length) {
        return test46(words, length);
    }

    public static String getTheSecondWord(List<String> words) {
        return test47(words);
    }

    public static List<String> reverseAndCollectToSet(List<String> words) {
        return test48(words);
    }

    public static int findIndexOfLastWordStartingWith(List<String> words, String start) {
        return test49(words, start);
    }

    public static List<String> removeWordsContaining(List<String> words, String letter) {
        return test50(words, letter);
    }

    public static boolean hasNoWordMoreThan(List<String> words, int count) {
        return test51(words, count);
    }
    // endregion

    /// ----------------------------------------------
    ///                Tests start here!!!
    /// ----------------------------------------------
    private static List<String> test00(String[] phrase) {
        return null;
    }

    public static ConcurrentMap<Object, Long> test01(List<String> states) {
        return null;
    }

    private static Map<String, Integer> test02(List<String> states) {
        return null;
    }

    private static List<String> test03(List<String> words) {
        return null;
    }

    private static List<String> test04(List<String> words) {
        return null;
    }

    private static List<String> test05(List<String> words) {
        return null;
    }

    private static List<String> test06(List<String> words) {
        return null;
    }

    private static void test07(List<String> words) {
        return;
    }

    private static String test08(List<String> words) {
        return null;
    }

    private static Long test09(List<String> words) {
        return null;
    }

    private static boolean test10(List<String> words, String start) {
        return false;
    }

    private static String test11(List<String> words, String start) {
        return null;
    }

    private static String test12(List<String> words, String start) {
        return null;
    }

    private static List<String> test13(List<String> words, int length) {
        return null;
    }


    private static List<Integer> test14(List<String> words) {
        return null;
    }

    private static long test15(List<String> words) {
        return -1;
    }

    private static String test16(List<String> words) {
        return null;
    }

    private static String test17(List<String> words) {
        return null;
    }

    private static boolean test18(List<String> words, int count) {
        return false;
    }


    private static int test19(List<String> words) {
        return -1;
    }

    private static int test20(List<String> words) {
        return -1;
    }

    private static List<String> test21(List<String> words, String letter) {
        return null;
    }

    private static long test22(List<String> words, String start) {
        return -1;
    }

    private static Map<Integer, List<String>> test23(List<String> words) {
        return null;
    }

    private static int test24(List<String> words) {
        return -1;
    }


    private static int test25(List<String> words) {
        return -1;
    }


    private static boolean test26(List<String> words, String start) {
        return false;
    }


    private static List<String> test27(List<String> words) {
        return null;
    }



    private static List<String> test28(List<String> words, int count) {
        return null;
    }


    private static double test29(List<String> words) {
        return -1;
    }


    private static List<String> test30(List<String> words, String orig, String replacement) {
        return null;
    }

    private static boolean test31(List<String> words) {
        return false;
    }

    private static String test32(List<String> words) {
        return null;
    }

    private static Set<String> test33(List<String> words) {
        return null;
    }

    private static List<String> test34(List<String> words) {
        return null;
    }

    private static String test35(List<String> words) {
        return null;
    }
    private static String test36(List<String> words) {
        return null;
    }

    private static List<String> test37(List<String> words) {
        return null;
    }

    private static boolean test38(List<String> words, String end) {
        return true;
    }

    private static List<String> test39(List<String> words, String letter) {
        return null;
    }

    private static List<String> test40(List<String> words) {
        return null;
    }

    private static int test41(List<String> words, String start) {
        return -1;
    }

    private static boolean test42(List<String> words) {
        return false;
    }

    private static List<String> test43(List<String> words, String replacement) {
        return null;
    }

    private static List<String> test44(List<String> words) {
        return null;
    }

    private static boolean test45(List<String> words, int length) {
        return false;
    }

    private static boolean test46(List<String> words, int length) {
        return false;
    }

    private static String test47(List<String> words) {
        return null;
    }

    private static List<String> test48(List<String> words) {
        return null;
    }

    private static int test49(List<String> words, String start) {
        return -1;
    }

    private static List<String> test50(List<String> words, String letter) {
        return null;
    }

    private static boolean test51(List<String> words, int count) {
        return false;
    }
}
