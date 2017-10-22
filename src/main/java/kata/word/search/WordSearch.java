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

@FunctionalInterface
public interface WordSearch{

	public File file();

	public default WordResults wordResults(){
		final SearchForLine searchForLine = () -> file();
		final Function<FindWord, List<List<List<Map.Entry<Integer, Integer>>>>> toAllCoordinates = findWord -> {
			final List<Direction> directions = Arrays.asList(Direction.values());
			return directions
				.stream()
				.map((Function<Direction, List<List<Map.Entry<Integer, Integer>>>>) direction -> {
					return findWord.coordinates(direction);
				}).collect(toList());
		};
		final List<String> words = Arrays.asList(searchForLine.firstLineSplit());
		final List<List<List<List<Map.Entry<Integer, Integer>>>>> coordinates = words
			.stream()
			.map((Function<String, FindWord>) word -> {
				final List<Direction> directions = Arrays.asList(Direction.values());
				final ImmutableMap<String, Object> parameters = ImmutableMap.<String, Object>of("file", file()
           ,"wordToFind", word);
				final FindWord findWord = () -> parameters;
				return findWord;
			}).map(toAllCoordinates)
			.collect(toList());
		final Map<String, Object> parameters = ImmutableMap.<String, Object>of("coordinates", coordinates
										      ,"words", words);
		final WordResults wordResults = () -> parameters;
		return wordResults;
	}
}
