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

	private static final String EXAMPLE_FILE_NAME = "resources/exampleWordSearch.txt";
	private static final File EXAMPLE_FILE = new File(EXAMPLE_FILE_NAME);

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
	public void findWordDown_bones(){
		final ImmutableMap<String, Object> findBonesDownParameters = ImmutableMap.<String, Object>of("file", EXAMPLE_FILE, "wordToFind", "bones");
		final FindWord findBonesDown = () -> findBonesDownParameters; 
		final ImmutableList<Map.Entry<Integer, Integer>> expectedBonesDownCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(0, 6)
				       ,entry(0, 7)
				       ,entry(0, 8)
				       ,entry(0, 9)
				       ,entry(0, 10));
		final List<Map.Entry<Integer, Integer>> actualBonesDownCoordinates = findBonesDown
			.coordinates(ContiguousCoordinates.Direction.DOWN)
			.get(0);
		final Integer expectedBonesDownCoordinatesSize = expectedBonesDownCoordinates.size();
		checkState(expectedBonesDownCoordinatesSize.equals(actualBonesDownCoordinates.size()), "Count of found word's coordinates must be equal from actual to expected.");
		final Long matchedDownCoordinateCount = range(0, expectedBonesDownCoordinates.size())
			.filter(whereCoordinatesEqual(actualBonesDownCoordinates, expectedBonesDownCoordinates))
			.count();
		assertEquals(expectedBonesDownCoordinatesSize, Integer.valueOf(matchedDownCoordinateCount.intValue()));
	}

	@Test
	public void findWordUp_khan(){
		final ImmutableMap<String, Object> findKhanUpParameters = ImmutableMap.<String, Object>of("file", EXAMPLE_FILE, "wordToFind", "khan");
		final FindWord findKhanUp = () -> findKhanUpParameters; 
		final ImmutableList<Map.Entry<Integer, Integer>> expectedKhanUpCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(5, 9)
				       ,entry(5, 8)
				       ,entry(5, 7)
				       ,entry(5, 6));
		final List<Map.Entry<Integer, Integer>> actualKhanUpCoordinates = findKhanUp
			.coordinates(ContiguousCoordinates.Direction.UP)
			.get(0);
		final Integer expectedKhanUpCoordinatesSize = expectedKhanUpCoordinates.size();
		checkState(expectedKhanUpCoordinatesSize.equals(actualKhanUpCoordinates.size()), "Count of found word's coordinates must be equal from actual to expected.");
		final Long matchedUpCoordinateCount = range(0, expectedKhanUpCoordinates.size())
			.filter(whereCoordinatesEqual(actualKhanUpCoordinates, expectedKhanUpCoordinates))
			.count();
		assertEquals(expectedKhanUpCoordinatesSize, Integer.valueOf(matchedUpCoordinateCount.intValue()));
	}

	@Test
	public void findWordLeft_kirk(){
		final ImmutableMap<String, Object> findParameters = ImmutableMap.<String, Object>of("file", EXAMPLE_FILE, "wordToFind", "kirk");
		final FindWord findWord = () -> findParameters; 
		final ImmutableList<Map.Entry<Integer, Integer>> expectedCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(4, 7)
				       ,entry(3, 7)
				       ,entry(2, 7)
				       ,entry(1, 7));
		final List<Map.Entry<Integer, Integer>> actualCoordinates = findWord
			.coordinates(ContiguousCoordinates.Direction.LEFT)
			.get(0);
		final Integer expectedCoordinatesSize = expectedCoordinates.size();
		checkState(expectedCoordinatesSize.equals(actualCoordinates.size()), "Count of found word's coordinates must be equal from actual to expected.");
		final Long matchedUpCoordinateCount = range(0, expectedCoordinates.size())
			.filter(whereCoordinatesEqual(actualCoordinates, expectedCoordinates))
			.count();
		assertEquals(expectedCoordinatesSize, Integer.valueOf(matchedUpCoordinateCount.intValue()));
	}

	@Test
	public void findWordRight_scotty(){
		final ImmutableMap<String, Object> findParameters = ImmutableMap.<String, Object>of("file", EXAMPLE_FILE, "wordToFind", "scotty");
		final FindWord findWord = () -> findParameters; 
		final ImmutableList<Map.Entry<Integer, Integer>> expectedCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(0, 5)
			        ,entry(1, 5)
			        ,entry(2, 5)
			        ,entry(3, 5)
			        ,entry(4, 5)
			        ,entry(5, 5));
		final List<Map.Entry<Integer, Integer>> actualCoordinates = findWord
			.coordinates(ContiguousCoordinates.Direction.RIGHT)
			.get(0);
		final Integer expectedCoordinatesSize = expectedCoordinates.size();
		checkState(expectedCoordinatesSize.equals(actualCoordinates.size()), "Count of found word's coordinates must be equal from actual to expected.");
		final Long matchedUpCoordinateCount = range(0, expectedCoordinates.size())
			.filter(whereCoordinatesEqual(actualCoordinates, expectedCoordinates))
			.count();
		assertEquals(expectedCoordinatesSize, Integer.valueOf(matchedUpCoordinateCount.intValue()));
	}

	private static IntPredicate whereCoordinatesEqual(final List<Map.Entry<Integer, Integer>> actualCoordinates
						         ,final List<Map.Entry<Integer, Integer>> expectedCoordinates){
		return coordinateIndex -> expectedCoordinates
			.get(coordinateIndex)
				.getKey()
			.equals(actualCoordinates
				.get(coordinateIndex)
				.getKey()) 
			&& expectedCoordinates
				.get(coordinateIndex)
				.getValue()
			.equals(actualCoordinates
				.get(coordinateIndex)
				.getValue());
	}
}
