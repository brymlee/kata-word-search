package kata.word.search;

import org.junit.Test;
import org.junit.Ignore;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.IntFunction;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.*;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Predicates.*;
import static java.util.stream.IntStream.*;
import static kata.word.search.PuzzleLines.*;

public class WordSearchTest{

	protected static final String EXAMPLE_FILE_NAME = "resources/exampleWordSearch.txt";
	protected static final File EXAMPLE_FILE = new File(EXAMPLE_FILE_NAME);

	@Test
	public void isHelloHello_sanityTest(){
		final String HELLO = "Hello";
		assertEquals(HELLO, HELLO);
	}

	@Test
	public void isExampleFileExistant() throws IOException{
		assertTrue(EXAMPLE_FILE.exists());
	}

	@Test
	public void parseSearchForLine_areWordEntriesConsistant(){
		final SearchForLine actualSearchForLine = () -> EXAMPLE_FILE; 
		final ImmutableList<String> expectedSearchForLine = ImmutableList.<String>of("BONES"
											    ,"KHAN"
											    ,"KIRK"
											    ,"SCOTTY"
											    ,"SPOCK"
											    ,"SULU"
											    ,"UHURA");
		final Integer expectedWordCount = expectedSearchForLine.size();
		checkState(expectedWordCount.equals(actualSearchForLine.wordCount()), "expectedWordCount must be equal to actual word count from file");
		final Long matchedWordsCount = range(0, expectedWordCount)
			.filter(wordIndex -> actualSearchForLine.get(wordIndex).equals(expectedSearchForLine.get(wordIndex)))
			.count();
		assertEquals(expectedWordCount, Integer.valueOf(matchedWordsCount.intValue()));
	}

	@Test
	public void parsePuzzleLines_areLettersForFirstLineConsistant(){
		final PuzzleLines actualPuzzleLines = () -> EXAMPLE_FILE;
		final ImmutableList<String> expectedFirstPuzzleLine = ImmutableList.<String>of("U"
											      ,"M"
											      ,"K"
											      ,"H"
											      ,"U"
											      ,"L"
											      ,"K"
											      ,"I"
											      ,"N"
											      ,"V"
											      ,"J"
											      ,"O"
											      ,"C"
											      ,"W"
											      ,"E");
		final Integer expectedFirstPuzzleLineLetterCount = expectedFirstPuzzleLine.size();
		checkState(expectedFirstPuzzleLineLetterCount.equals(actualPuzzleLines.get(0).size()), "Compared puzzle lines must have same letter count.");
		final Long matchedLetterCount = range(0, expectedFirstPuzzleLineLetterCount)
			.filter(letterIndex -> actualPuzzleLines.get(0).get(letterIndex).equals(expectedFirstPuzzleLine.get(letterIndex)))
			.count();
		assertEquals(expectedFirstPuzzleLineLetterCount, Integer.valueOf(matchedLetterCount.intValue()));
	}

	@Test
	public void doGetWordSearchResults_exampleFile(){
		final WordSearch wordSearch = () -> EXAMPLE_FILE;
		final WordResults wordResults = wordSearch.wordResults();

		final String BONES = "BONES";
		final WordResult bones = wordResults.wordResult(BONES);
		assertEquals(BONES, bones.word());
		final List<Map.Entry<Integer, Integer>> expectedBonesCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(0, 6)
															        ,entry(0, 7)
															        ,entry(0, 8)
															        ,entry(0, 9)
															        ,entry(0, 10));
		assertTrue(isFindWordCorrect(expectedBonesCoordinates, BONES, bones));

		final String KHAN = "KHAN";
		final WordResult khan = wordResults.wordResult(KHAN);
		assertEquals(KHAN, khan.word());
		final List<Map.Entry<Integer, Integer>> expectedKhanCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(5, 9)
															       ,entry(5, 8)
															       ,entry(5, 7)
															       ,entry(5, 6));
		assertTrue(isFindWordCorrect(expectedKhanCoordinates, KHAN, khan));

		final String KIRK = "KIRK";
		final WordResult kirk = wordResults.wordResult(KIRK);
		assertEquals(KIRK, kirk.word());
		final List<Map.Entry<Integer, Integer>> expectedKirkCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(4, 7)
															       ,entry(3, 7)
															       ,entry(2, 7)
															       ,entry(1, 7));
		assertTrue(isFindWordCorrect(expectedKirkCoordinates, KIRK, kirk));

		final String SCOTTY = "SCOTTY";
		final WordResult scotty = wordResults.wordResult(SCOTTY);
		assertEquals(SCOTTY, scotty.word());
		final List<Map.Entry<Integer, Integer>> expectedScottyCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(0, 5)
															         ,entry(1, 5)
			     ,entry(2, 5)
			     ,entry(3, 5)
															         ,entry(4, 5)
															         ,entry(5, 5));
		assertTrue(isFindWordCorrect(expectedScottyCoordinates, SCOTTY, scotty));

		final String SPOCK = "SPOCK";
		final WordResult spock = wordResults.wordResult(SPOCK);
		assertEquals(SPOCK, spock.word());
		final List<Map.Entry<Integer, Integer>> expectedSpockCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(2, 1)
															         ,entry(3, 2)
			     ,entry(4, 3)
			     ,entry(5, 4)
															         ,entry(6, 5));
		assertTrue(isFindWordCorrect(expectedSpockCoordinates, SPOCK, spock));

		final String SULU = "SULU";
		final WordResult sulu = wordResults.wordResult(SULU);
		assertEquals(SULU, sulu.word());
		final List<Map.Entry<Integer, Integer>> expectedSuluCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(3, 3)
															         ,entry(2, 2)
			     ,entry(1, 1) 
															         ,entry(0, 0));
		assertTrue(isFindWordCorrect(expectedSuluCoordinates, SULU, sulu));

		final String UHURA = "UHURA";
		final WordResult uhura = wordResults.wordResult(UHURA);
		assertEquals(UHURA, uhura.word());
		final List<Map.Entry<Integer, Integer>> expectedUhuraCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(4, 0)
															         ,entry(3, 1)
			     ,entry(2, 2) 
			     ,entry(1, 3) 
															         ,entry(0, 4));
		assertTrue(isFindWordCorrect(expectedUhuraCoordinates, UHURA, uhura));
	}

	private static Boolean isProperEntriesEqual(final Map.Entry<Integer, Integer> i
						   ,final Map.Entry<Integer, Integer> j){ 
		return i.getKey().equals(j.getKey()) && i.getValue().equals(j.getValue());
	}

	private static Boolean isFindWordCorrect(final List<Map.Entry<Integer, Integer>> expectedEntries
						,final String word
						,final WordResult wordResult){
		final Boolean isFindWordCorrect = expectedEntries			
			.stream()
			.allMatch((Predicate<Map.Entry<Integer, Integer>>) expectedEntry -> {
				return range(0, word.length())
					.mapToObj((IntFunction<Map.Entry<Integer, Integer>>) actualIndex -> {
						return wordResult.coordinate(actualIndex);

					}).anyMatch((Predicate<Map.Entry<Integer, Integer>>) actualEntry -> {
					
						return isProperEntriesEqual(expectedEntry
									   ,actualEntry);
					});
			});
		return isFindWordCorrect;
	}

}
