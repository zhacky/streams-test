package streams;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

class UsingStreamsTest {
    // region Fields
    List<String> states;
    String[] phrase;
    List<String> words;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    // endregion

    // region Before And After Methods
    @BeforeEach
    void initialize() {
        // set upstreams
        System.setOut(new PrintStream(outContent));

        // given
        states = List.of("California", "Alabama", "Texas", "California", "Nevada", "Texas", "Louisiana", "Texas");
        phrase = new String[]{"the", "quick", "brown", "fox"};
        words = Arrays.asList("apple", "banana", "orange", "grape", "pear", "kiwi", "apple");
    }

    @AfterEach
    void disposeResources() {
        // restore streams
        System.setOut(originalOut);
    }
    // endregion

    @Test
    @DisplayName("Test00: Just print each word ...")
    void creatingStreams_JustPrint() {
        // given
        List<String> expected = List.of("THE", "QUICK", "BROWN", "FOX");
        // when
        List<String> actual = UsingStreams.getUpperCase(phrase);
        // then
        String printedWords = outContent.toString();
        assertTrue(printedWords.contains("THE"));
        assertTrue(printedWords.contains("QUICK"));
        assertTrue(printedWords.contains("BROWN"));
        assertTrue(printedWords.contains("FOX"));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test01: Return the count of each state ...")
    void getCounts_returnCountOfEachState() {
        // given
        Long expectedTexas = 3L;
        // when
        ConcurrentMap<Object, Long> counts = UsingStreams.getCounts(states);
        counts.forEach((state, count) -> System.out.printf("%s : %d%n", state, count));
        Long actual = counts.get("Texas");
        // then
        String printed = outContent.toString();
        assertTrue(printed.contains("Texas : 3"));
        assertEquals(expectedTexas, actual);
    }

    @Test
    @DisplayName("Test02: Just print each word ...")
    void getCounts_returnCountOfEachStateNonConcurrent() {
        // given
        int expectedTexas = 3;
        // when
        Map<String, Integer> counts = UsingStreams.getCountsBasic(states);
        Integer actual = counts.get("Texas");
        // then
        assertEquals(expectedTexas, actual);
    }

    @Test
    @DisplayName("Test03: Return words that start with A ...")
    void filterWords_returnWordsThatStartWithA() {
        // given
        List<String> expected = List.of("apple", "apple");
        // when
        List<String> actual = UsingStreams.getWordsThatStartWithA(words);
        // then
        assertLinesMatch(expected, actual);
    }

    @Test
    @DisplayName("Test04: Return words with duplicates ...")
    void removeDuplicateWords_returnWordsWithoutDuplicates() {
        // given
        List<String> expected = List.of("apple", "banana", "orange", "grape", "pear", "kiwi");
        // when
        List<String> actual = UsingStreams.getWordsWithoutDuplicates(words);
        // then
        assertLinesMatch(expected, actual);
    }

    @Test
    void sortedWords_returnWordsSortedInNaturalOrder() {
        // given
        List<String> expected = Arrays.asList("apple", "apple", "banana", "grape", "kiwi", "orange", "pear");
        // when
        List<String> actual = UsingStreams.getWordsSortedInNaturalOrder(words);
        // then
        assertLinesMatch(expected, actual);
    }

    @Test
    void printBeforeUppercase_printWordsAndReturnThemAsUppercase() {
        // given
        List<String> expected = List.of("APPLE", "BANANA", "ORANGE", "GRAPE", "PEAR", "KIWI", "APPLE");
        // when
        List<String> actual = UsingStreams.printWordsThenReturnAsUppercase(words);
        // then
        String printedWords = outContent.toString();
        StringBuilder sb = new StringBuilder();
        for (String word : words) sb.append(word).append("\n");
        assertTrue(printedWords.contentEquals(sb));
        assertLinesMatch(expected, actual);
    }

    @Test
    void printEachWord_eachWordMustBePrinted() {
        // when
        UsingStreams.printEachWord(words);
        String printedWords = outContent.toString();
        // then
        assertTrue(printedWords.contains("apple"));
        assertTrue(printedWords.contains("banana"));
        assertTrue(printedWords.contains("orange"));
        assertTrue(printedWords.contains("grape"));
        assertTrue(printedWords.contains("pear"));
        assertTrue(printedWords.contains("kiwi"));
    }

    @Test
    void collectWords_returnSingleCommaSeparatedString() {
        // given
        String expected = "apple,banana,orange,grape,pear,kiwi,apple";
        // when
        String actual = UsingStreams.getCommaSeparatedString(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void countWords_returnTheNumberOfWords() {
        // given
        Long expected = 7L;
        // when
        Long actual = UsingStreams.getWordCount(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test10: Return true if any word starts with ...")
    void checkWords_returnTrueIfAnyWordStartsWith() {
        // given
        boolean expected1 = true;
        boolean expected2 = false;
        // when
        boolean actual1 = UsingStreams.checkIfStartsWith(words, "g");
        boolean actual2 = UsingStreams.checkIfStartsWith(words, "z");
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test11: Return the first word that starts with ...")
    void findWord_returnFirstWordStartingWith() {
        // given
        String expectedStartsWithO = "orange";
        // when
        String actualStartsWithO = UsingStreams.findFirstStartsWith(words, "o");
        String actualStartsWithZ = UsingStreams.findFirstStartsWith(words, "z");
        // then
        assertEquals(expectedStartsWithO, actualStartsWithO);
        assertNull(actualStartsWithZ);
    }

    @Test
    @DisplayName("Test12: Return any word that starts with ...")
    void findWord_returnAnyWordStartsWith() {
        // given
        String expectedStartsWithK = "kiwi";
        // when
        String actualStartsWithK = UsingStreams.findAnyStartsWith(words, "k");
        String actualStartsWithZ = UsingStreams.findAnyStartsWith(words, "z");
        // then
        assertEquals(expectedStartsWithK, actualStartsWithK);
        assertNull(actualStartsWithZ);
    }

    @Test
    @DisplayName("Test13: Return words of given length ...")
    void findWords_returnWordsOfGivenLength() {
        // given
        int length1 = 5;
        List<String> expected1 = List.of("apple", "grape", "apple");
        int length2 = 10;
        // when
        List<String> actual1 = UsingStreams.findWordsWithLengthOf(words, length1);
        List<String> actual2 = UsingStreams.findWordsWithLengthOf(words, length2);
        // then
        assertLinesMatch(expected1, actual1);
        assertLinesMatch(Collections.emptyList(), actual2);
    }

    @Test
    @DisplayName("Test14: Return a list of the lengths of the words ...")
    void listLengths_returnListOfWordLengths() {
        // given
        List<Integer> expected = List.of(5, 6, 6, 5, 4, 4, 5);
        // when
        List<Integer> actual = UsingStreams.getListOfWordLengths(words);
        // then
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("Test15: Count the number of each length distinct ...")
    void lengths_countTheNumberOfDistinctLengths() {
        // given
        long expected = 3;
        // when
        long actual = UsingStreams.countDistinctLengths(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test16: Concatenate the words with hyphen ...")
    void hyphenSeparated_concatenateWordsWithHyphen() {
        // given
        String expected = "apple-banana-orange-grape-pear-kiwi-apple";
        // when
        String actual = UsingStreams.concatenateWithHyphen(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test17: Convert words to uppercase then concatenate them ...")
    void upperConcatenated_convertWordsToUppercaseThenConcatenateThem() {
        // given
        String expected = "APPLEBANANAORANGEGRAPEPEARKIWIAPPLE";
        // when
        String actual = UsingStreams.uppercaseConcatenated(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test18: Return true if all the words have lengths greater than ...")
    void checkWords_returnTrueIfAllWordsHaveLengthGreaterThan() {
        // given
        boolean expected1 = true;
        boolean expected2 = false;
        // when
        boolean actual1 = UsingStreams.hasWordsGreaterThan(words, 3);
        boolean actual2 = UsingStreams.hasWordsGreaterThan(words, 100);
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test19: Return maximum length of a word ...")
    void findMax_returnMaximumLengthOfAWord() {
        // given
        int expected = 6;
        // when
        int actual = UsingStreams.findMaxWordLength(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test20: Return minimum length of a word ...")
    void findMin_returnMinimumLengthOfAWord() {
        // given
        int expected = 4;
        // when
        int actual = UsingStreams.findMinWordLength(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test21: Return words that contain ...")
    void findWords_returnWordsThatContain() {
        // given
        List<String> expected1 = List.of("apple", "grape", "pear", "apple");
        List<String> expected2 = List.of("kiwi");
        // when
        List<String> actual1 = UsingStreams.findWordsContaining(words, "p");
        List<String> actual2 = UsingStreams.findWordsContaining(words, "k");
        List<String> actual3 = UsingStreams.findWordsContaining(words, "z");
        // then
        assertLinesMatch(expected1, actual1);
        assertLinesMatch(expected2, actual2);
        assertLinesMatch(Collections.emptyList(), actual3);
    }

    @Test
    @DisplayName("Test22: Return the number of words that start with ...")
    void countWords_returnCountOfWordsStartingWith() {
        // given
        long expected1 = 1;
        long expected2 = 0;
        // when
        long actual1 = UsingStreams.countWordsStartingWith(words, "b");
        long actual2 = UsingStreams.countWordsStartingWith(words, "z");
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test23: Return words grouped by their lengths ...")
    void groupWords_returnWordGroupsByLength() {
        // given
        Map<Integer, List<String>> expected = Map.of(4, List.of("pear", "kiwi"), 5, List.of("apple", "grape", "apple"), 6, List.of("banana", "orange"));
        // when
        Map<Integer, List<String>> actual = UsingStreams.groupWordsByLength(words);
        // then
        for (Map.Entry entry : expected.entrySet()) {
            assertLinesMatch((List<String>) entry.getValue(), actual.get(entry.getKey()));
        }
    }

    @Test
    @DisplayName("Test24: Calculate the total length of all words ...")
    void findSum_calculateTotalLengthOfAllWords() {
        // given
        int expected = 35;
        // when
        int actual = UsingStreams.calculateTotalLengthofAllWords(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test25: Calculate the total product of lengths of all words ...")
    void findProduct_calculateTheProductOfTheLengthsOfAllWords() {
        // given
        int expected = 72000;
        // when
        int actual = UsingStreams.calculateProductOfWordLengths(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test26: Return true if no words start with ...")
    void checkWords_ifNoWordsStartWith() {
        // given
        boolean expected1 = true;
        boolean expected2 = false;
        // when
        boolean actual1 = UsingStreams.noWordsStartWith(words, "z");
        boolean actual2 = UsingStreams.noWordsStartWith(words, "a");
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test27: Return the first 3 words ...")
    void findWords_returnFirstThreeWords() {
        // given
        List<String> expected = List.of("apple", "banana", "orange");
        // when
        List<String> actual = UsingStreams.findFirstThreeWords(words);
        // then
        assertLinesMatch(expected, actual);
    }

    @Test
    @DisplayName("Test28: Return the words after skipping the first few ...")
    void findWords_wordsAfterSkippingFirstFew() {
        // given
        List<String> expected1 = List.of("orange", "grape", "pear", "kiwi", "apple");
        List<String> expected2 = List.of("banana", "orange", "grape", "pear", "kiwi", "apple");
        // when
        List<String> actual1 = UsingStreams.getWordsSkippingFirstFew(words, 2);
        List<String> actual2 = UsingStreams.getWordsSkippingFirstFew(words, 1);
        // then
        assertLinesMatch(expected1, actual1);
        assertLinesMatch(expected2, actual2);
    }

    @Test
    @DisplayName("Test29: Return the average length of the words ...")
    void averageLength_findAverageLengthOfWords() {
        // given
        double expected = 5.0;
        // when
        double actual = UsingStreams.getAverageLength(words);
        // then
        assertEquals(expected, actual, 0.001);
    }

    @Test
    @DisplayName("Test30: Replace all occurrences of a letter with ...")
    void replace_allOccurrencesWith() {
        // given
        List<String> expected1 = List.of("Xpple", "bXnXnX", "orXnge", "grXpe", "peXr", "kiwi", "Xpple");
        List<String> expected2 = List.of("apple", "banana", "orange", "grape", "pear", "iwi", "apple");

        // when
        List<String> actual1 = UsingStreams.replaceOccurrencesWith(words, "a", "X");
        List<String> actual2 = UsingStreams.replaceOccurrencesWith(words, "k", "");
        // then
        assertLinesMatch(expected1, actual1);
        assertLinesMatch(expected2, actual2);
    }

    @Test
    @DisplayName("Test31: Return true if all words are in uppercase ...")
    void checkWords_ifAllWordsUppercase() {
        // given
        List<String> modified = words.stream().map(String::toUpperCase).toList();
        boolean expected = true;
        // when
        boolean actual = UsingStreams.allWordsUppercase(modified);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test32: Return all letters from all words distinct and concatenate them ...")
    void contatenateLetters_returnConcatenatedDistinctLettersFromAllWords() {
        // given
        String expected = "aplebnorgkiw";
        // when
        String actual = UsingStreams.concatenateDistinctLetters(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test33: Return a set from the list of words ...")
    void convertWords_returnASetOfWords() {
        // given
        Set<String> expected = Set.of("apple", "banana", "orange", "grape", "pear", "kiwi");
        // when
        Set<String> actual = UsingStreams.convertListOfWordsToSet(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test34: Print the lengths and the uppercase form of each word ...")
    void printLengths_wordLengthsAndTheirUppercaseForm() {
        // given
        // when
        List<String> actual = UsingStreams.printLengthsAndUppercaseForm(words);
        // then
        String printedWords = outContent.toString();
        assertTrue(printedWords.contains("Length: 4, Uppercase: PEAR"));
        assertLinesMatch(words, actual);
    }

    @Test
    @DisplayName("Test35: Return the longest word ...")
    void longestWord_findTheLongestWord() {
        // given
        String expected = "banana";
        // when
        String actual = UsingStreams.findTheLongestWord(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test36: Return the shortest word ...")
    void shortestWord_findTheShortestWord() {
        // given
        String expected = "pear";
        // when
        String actual = UsingStreams.findTheShortestWord(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test37: Return all words after reversing each one ...")
    void reverse_eachWord() {
        // given
        List<String> expected = List.of("elppa", "ananab", "egnaro", "eparg", "raep", "iwik", "elppa");
        // when
        List<String> actual = UsingStreams.reverseEachWord(words);
        // then
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    @DisplayName("Test38: Return true if all words end with ...")
    void checkWords_allWordsEndWith() {
        // given
        boolean expected = false;
        // when
        boolean actual = UsingStreams.allWordsEndWith(words, "e");
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test39: Return all words with the word containing the letter ...")
    void removeWords_containingTheLetter() {
        // given
        List<String> expected1 = List.of("apple", "banana", "orange", "grape", "pear", "apple");
        List<String> expected2 = List.of("apple", "banana", "grape", "pear", "kiwi", "apple");
        // when
        List<String> actual1 = UsingStreams.removeWordsContainingTheLetter(words, "k");
        List<String> actual2 = UsingStreams.removeWordsContainingTheLetter(words, "o");
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test40: Return all words with the first letter in uppercase ...")
    void convertWord_convertTheFirstLetterOfEachWord() {
        // given
        List<String> expected1 = List.of("Apple", "Banana", "Orange", "Grape", "Pear", "Kiwi", "Apple");
        // when
        List<String> actual1 = UsingStreams.convertTheFirstLetterToUppercase(words);
        // then
        assertEquals(expected1, actual1);
    }

    @Test
    @DisplayName("Test41: Return the index of the word that starts with ...")
    void findIndex_findWordIndexOfFirstWordStartingWith() {
        // given
        int expected1 = 3;
        // when
        int actual1 = UsingStreams.findIndexOfFirstWordStartingWith(words, "g");
        // then
        assertEquals(expected1, actual1);
    }

    @Test
    @DisplayName("Test42: Return true if the list contains a word with a digit...")
    void checkWords_ifAnyWordContainsDigit() {
        // given
        boolean expected1 = false;
        var modified = List.of("apple", "banana", "orange", "grape", "pear", "kiwi", "apple1");
        boolean expected2 = true;
        // when
        boolean actual1 = UsingStreams.hasWordContainingDigit(words);
        boolean actual2 = UsingStreams.hasWordContainingDigit(modified);
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test43: Replace vowels in each word with ...")
    void replaceVowels_inEachWordReplaceWith() {
        // given
        var expected = List.of("XpplX", "bXnXnX", "XrXngX", "grXpX", "pXXr", "kXwX", "XpplX");
        String replacement = "X";
        // when
        List<String> actual = UsingStreams.replaceVowelsInEachWordWith(words, replacement);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test44: Return a list of distinct letters from all the words ...")
    void findLetters_extractDistinctLettersFromWords() {
        // given
        List<String> expected = List.of("a", "p", "l", "e", "b", "n", "o", "r", "g", "k", "i", "w");
        // when
        List<String> actual = UsingStreams.findDistinctLettersFromWords(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test45: Return true if all the words have at least a character length of ...")
    void checkWords_ifAllWordsHaveAtLeastCharacterLengthOf() {
        // given
        boolean expected1 = true;
        boolean expected2 = false;
        // when
        boolean actual1 = UsingStreams.allWordsHaveAtLeastLengthOf(words, 3);
        boolean actual2 = UsingStreams.allWordsHaveAtLeastLengthOf(words, 100);
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test46: Return true if any word has exactly the length of ...")
    void checkWords_ifAnyWordHasExactlyCharacters() {
        // given
        boolean expected1 = true;
        boolean expected2 = false;
        // when
        boolean actual1 = UsingStreams.hasAnyWordExactlyCharacters(words, 4);
        boolean actual2 = UsingStreams.hasAnyWordExactlyCharacters(words, 10);
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test47: Return the second word from the list ...")
    void findWord_returnTheSecondWord() {
        // given
        String expected = "banana";
        // when
        String actual = UsingStreams.getTheSecondWord(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test48: Reverse the list and collect to a set ...")
    void collectToSet_inReverseOrder() {
        // given
        List<String> expected = List.of("pear", "orange", "kiwi", "grape",  "banana", "apple");
        // when
        List<String> actual = UsingStreams.reverseAndCollectToSet(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test49: Return the index of the last word that starts with ...")
    void findIndex_returnIndexOfLastWordStartingWith() {
        // given
        int expected1 = 6;
        int expected2 = 1;
        // when
        int actual1 = UsingStreams.findIndexOfLastWordStartingWith(words, "a");
        int actual2 = UsingStreams.findIndexOfLastWordStartingWith(words, "b");
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    @DisplayName("Test50: Remove the words except the words containing the letter ...")
    void removeWords_exceptWordsContainingTheLetter() {
        // given
        List<String> expected1 = List.of("apple", "banana", "orange", "grape", "pear", "apple");
        List<String> expected2 = List.of("banana", "orange", "kiwi");
        // when
        List<String> actual1 = UsingStreams.removeWordsContaining(words, "k");
        List<String> actual2 = UsingStreams.removeWordsContaining(words, "p");
        // then
        assertLinesMatch(expected1, actual1);
        assertLinesMatch(expected2, actual2);
    }

    @Test
    @DisplayName("Test51: Return true if list has no word more than ...")
    void checkWords_noMoreThanCharacters() {
        // given
        boolean expected1 = true;
        boolean expected2 = false;
        // when
        boolean actual1 = UsingStreams.hasNoWordMoreThan(words, 6);
        boolean actual2 = UsingStreams.hasNoWordMoreThan(words, 5);
        // then
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}