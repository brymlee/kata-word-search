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

public class WordSearchView{
	protected static final String EXAMPLE_FILE_NAME = "resources/exampleWordSearch.txt";
	protected static final String EXAMPLE_FILE_2_NAME = "resources/example2WordSearch.txt";
	protected static final String EXAMPLE_FILE_3_NAME = "resources/example3WordSearch.txt";
	protected static final File EXAMPLE_FILE = new File(EXAMPLE_FILE_NAME);
	protected static final File EXAMPLE_FILE_2 = new File(EXAMPLE_FILE_2_NAME);
	protected static final File EXAMPLE_FILE_3 = new File(EXAMPLE_FILE_3_NAME);
	protected static final File EXAMPLE_VIEW_FILE = new File("resources/exampleView.txt");
	protected static final File TUPLE_VIEW_FILE = new File("resources/tupleView.txt");

	private static File file(final String fileId){
		switch(fileId){
			case "A" : 
				return EXAMPLE_FILE;
			case "B" :
				return EXAMPLE_FILE_2;
			case "C" :
				return EXAMPLE_FILE_3;
		}
		throw new RuntimeException("File ID not specified");
	}

	public static void main(final String[] arguments){
		try{
			final String fileId = arguments[0].trim();
			final File file = file(fileId);
			final WordSearch wordSearch = () -> file;
			final WordResults wordResults = wordSearch.wordResults();
			final SearchForLine searchForLine = () -> file;
			final List<String> words = Arrays.asList(searchForLine.firstLineSplit());
			final String exampleViewString = ((SearchForLine) () -> EXAMPLE_VIEW_FILE)
				.fileAsString();
			final String tupleViewString = ((SearchForLine) () -> TUPLE_VIEW_FILE)
				.fileAsString();
			final String viewString = range(0, words.size())
				.mapToObj((IntFunction<String>) wordIndex -> {
					final String word = words.get(wordIndex);
					final WordResult wordResult = wordResults.wordResult(word);
					final String coordinates = range(0, word.length())
						.mapToObj((IntFunction<String>) coordinateIndex -> {
							final Map.Entry<Integer, Integer> entry = wordResult.coordinate(coordinateIndex);
							final String coordinateTuple = tupleViewString
								.replaceAll("\\{x}", entry.getKey().toString())
								.replaceAll("\\{y}", entry.getValue().toString())
								.replaceAll("\n", "");
							return coordinateTuple.toString().concat(",");	
						}).reduce((i, j) -> i.concat(j))
						.get();
					return exampleViewString
						.replaceAll("\\{word}", word)
						.replaceAll("\\{coordinates}", coordinates.substring(0, coordinates.length() - 1));
				}).reduce((i, j) -> {
					return i.concat(j);
				}).get();
			System.out.println(viewString);
		}catch(final NumberFormatException numberFormatException){
			throw new RuntimeException(numberFormatException);
		}
	}

}
