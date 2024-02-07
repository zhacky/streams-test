package streams;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

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
    }

    @Test
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
    void filterWords_returnWordsThatStartWithA() {
        // given
        List<String> expected = List.of("apple", "apple");
        // when
        List<String> actual = UsingStreams.getWordsThatStartWithA(words);
        // then
        assertLinesMatch(expected, actual);
    }

    @Test
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
    void findWord_returnFirstWordStartingWith() {
        // given
        String expectedStartsWithO = "orange";
        // when
        String actualStartsWithO = UsingStreams.findFirstStartsWith(words, "o");
        String actualStartsWithZ = UsingStreams.findFirstStartsWith(words, "z");
        // then
        assertEquals(expectedStartsWithO, actualStartsWithO);
        assertEquals(null, actualStartsWithZ);
    }

    @Test
    void findWord_returnAnyWordStartsWith() {
        // given
        String expectedStartsWithK = "kiwi";
        // when
        String actualStartsWithK = UsingStreams.findAnyStartsWith(words, "k");
        String actualStartsWithZ = UsingStreams.findAnyStartsWith(words, "z");
        // then
        assertEquals(expectedStartsWithK, actualStartsWithK);
        assertEquals(null, actualStartsWithZ);
    }

    @Test
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
    void listLengths_returnListOfWordLengths() {
        // given
        List<Integer> expected = List.of(5, 6, 6, 5, 4, 4, 5);
        // when
        List<Integer> actual = UsingStreams.getListOfWordLengths(words);
        // then
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void lengths_countTheNumberOfDistinctLengths() {
        // given
        long expected = 3;
        // when
        long actual = UsingStreams.countDistinctLengths(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void hyphenSeparated_concatenateWordsWithHyphen() {
        // given
        String expected = "apple-banana-orange-grape-pear-kiwi-apple";
        // when
        String actual = UsingStreams.concatenateWithHyphen(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void upperConcatenated_convertWordsToUppercaseThenConcatenateThem() {
        // given
        String expected = "APPLEBANANAORANGEGRAPEPEARKIWIAPPLE";
        // when
        String actual = UsingStreams.uppercaseConcatenated(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
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
    void findMax_returnMaximumLengthOfAWord() {
        // given
        int expected = 6;
        // when
        int actual = UsingStreams.findMaxWordLength(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void findMin_returnMinimumLengthOfAWord() {
        // given
        int expected = 4;
        // when
        int actual = UsingStreams.findMinWordLength(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
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
    void findSum_calculateTotalLengthOfAllWords() {
        // given
        int expected = 35;
        // when
        int actual = UsingStreams.calculateTotalLengthofAllWords(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void findProduct_calculateTheProductOfTheLengthsOfAllWords() {
        // given
        int expected = 72000;
        // when
        int actual = UsingStreams.calculateProductOfWordLengths(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
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
    void findWords_returnFirstThreeWords() {
        // given
        List<String> expected = List.of("apple", "banana", "orange");
        // when
        List<String> actual = UsingStreams.findFirstThreeWords(words);
        // then
        assertLinesMatch(expected, actual);
    }

    @Test
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
    void averageLength_findAverageLengthOfWords() {
        // given
        double expected = 5.0;
        // when
        double actual = UsingStreams.getAverageLength(words);
        // then
        assertEquals(expected, actual, 0.001);
    }

    @Test
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
    void contatenateLetters_returnConcatenatedDistinctLettersFromAllWords() {
        // given
        String expected = "aplebnorgkiw";
        // when
        String actual = UsingStreams.concatenateDistinctLetters(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void convertWords_returnASetOfWords() {
        // given
        Set<String> expected = Set.of("apple", "banana", "orange", "grape", "pear", "kiwi");
        // when
        Set<String> actual = UsingStreams.convertListOfWordsToSet(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
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
    void longestWord_findTheLongestWord() {
        // given
        String expected = "banana";
        // when
        String actual = UsingStreams.findTheLongestWord(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void shortestWord_findTheShortestWord() {
        // given
        String expected = "pear";
        // when
        String actual = UsingStreams.findTheShortestWord(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void reverse_eachWord() {
        // given
        List<String> expected = List.of("elppa", "ananab", "egnaro", "eparg", "raep", "iwik", "elppa");
        // when
        List<String> actual = UsingStreams.reverseEachWord(words);
        // then
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    void checkWords_allWordsEndWith() {
        // given
        boolean expected = false;
        // when
        boolean actual = UsingStreams.allWordsEndWith(words, "e");
        // then
        assertEquals(expected, actual);
    }

    @Test
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
    void convertWord_convertTheFirstLetterOfEachWord() {
        // given
        List<String> expected1 = List.of("Apple", "Banana", "Orange", "Grape", "Pear", "Kiwi", "Apple");
        // when
        List<String> actual1 = UsingStreams.convertTheFirstLetterToUppercase(words);
        // then
        assertEquals(expected1, actual1);
    }

    @Test
    void findIndex_findWordIndexOfFirstWordStartingWith() {
        // given
        int expected1 = 3;
        // when
        int actual1 = UsingStreams.findIndexOfFirstWordStartingWith(words, "g");
        // then
        assertEquals(expected1, actual1);
    }

    @Test
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
    void findLetters_extractDistinctLettersFromWords() {
        // given
        List<String> expected = List.of("a", "p", "l", "e", "b", "n", "o", "r", "g", "k", "i", "w");
        // when
        List<String> actual = UsingStreams.findDistinctLettersFromWords(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
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
    void findWord_returnTheSecondWord() {
        // given
        String expected = "banana";
        // when
        String actual = UsingStreams.getTheSecondWord(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
    void collectToSet_inReverseOrder() {
        // given
        List<String> expected = List.of("pear", "orange", "kiwi", "grape",  "banana", "apple");
        // when
        List<String> actual = UsingStreams.reverseAndCollectToSet(words);
        // then
        assertEquals(expected, actual);
    }

    @Test
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