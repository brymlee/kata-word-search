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
}
