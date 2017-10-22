package kata.word.search;

import org.junit.Test;
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
import java.util.function.Predicate;
import java.util.function.IntFunction;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.*;
import static com.google.common.base.Preconditions.*;
import static com.google.common.base.Predicates.*;
import static java.util.stream.IntStream.*;
import static kata.word.search.PuzzleLines.*;
import static kata.word.search.ContiguousCoordinates.Direction.*;

@FunctionalInterface
public interface FindWord{
	public ImmutableMap<String, Object> findWordDownParameters();

	public default List<List<Map.Entry<Integer, Integer>>> coordinates(final ContiguousCoordinates.Direction direction){
		final File file = (File) findWordDownParameters().get("file");
		checkState(notNull().apply(file), "file must not be null");
		final String wordToFind = ((String) findWordDownParameters().get("wordToFind")).toUpperCase();
		checkState(notNull().apply(wordToFind), "wordToFind must not be null");
		final PuzzleLines puzzleLines = () -> file;
		char[] characters = wordToFind.toCharArray();
		final List<String> wordLetterSplit = range(0, characters.length)
			.mapToObj(index -> new Character(characters[index]))
			.map(character -> String.valueOf(character))
			.collect(toList());
		final List<Map.Entry<String, Integer>> wordLetterSequence = range(0, wordToFind.length())
			.mapToObj((IntFunction<Map.Entry<String, Integer>>) wordLetterIndex -> entry(wordLetterSplit.get(wordLetterIndex), wordLetterIndex))
			.collect(toList());
		final List<String> lineSplit = puzzleLines.lineSplit();
		final List<Map.Entry<Integer, Map.Entry<Integer, Integer>>> letterCoordinates = range(0, lineSplit.size())
			.mapToObj(lineIndex -> puzzleLines.coordinatesOfWord(lineIndex, wordLetterSequence, wordToFind))
			.reduce((i, j) -> {
				return ImmutableList.<Map.Entry<Integer, Map.Entry<Integer, Integer>>>builder()
					.addAll(i.build())
					.addAll(j.build());
			}).get()
			.build();
		final ImmutableMap<String, Object> contiguousCoordinatesDownParameters = ImmutableMap.<String, Object>of("direction", direction
															,"coordinates", letterCoordinates
															,"wordToFind", wordToFind);
		final List<List<Map.Entry<Integer, Integer>>> filteredCoordinates = ((ContiguousCoordinates) () -> contiguousCoordinatesDownParameters).coordinates(); 
		return filteredCoordinates;
	}

	public default List<List<Map.Entry<Integer, Integer>>> coordinates(){
		return coordinates(DOWN);
	}
}
