package kata.word.search;

import java.util.List;
import java.io.File;
import java.util.Map;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.IntFunction;

import static com.google.common.base.Preconditions.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.*;
import static com.google.common.base.Predicates.*;

@FunctionalInterface
public interface PuzzleLines{
	public File file();

	public static <T, U> Map.Entry entry(final T t
				            ,final U u){
		return ImmutableMap.<T, U>of(t, u)
			.entrySet()
			.iterator()
			.next();
	}

	public default String fileAsString(){
		return ((SearchForLine) () -> file())
			.fileAsString();
	}

	public default List<String> lineSplit(){
		final String[] totalLineSplit = fileAsString().split("\n");
		checkState(totalLineSplit.length > 2, "Puzzle grid must have more than 1 line.");
		return range(1, totalLineSplit.length)
			.mapToObj(lineIndex -> totalLineSplit[lineIndex])
			.collect(toList());
	}

	public default ImmutableList.Builder<Map.Entry<Integer, Map.Entry<Integer, Integer>>> coordinatesOfWord(final Integer lineIndex
													       ,final Map<String, Integer> wordLetterMap
													       ,final String wordToFind){
		final List<String> lineSplit = lineSplit();
		final String line = lineSplit.get(lineIndex);
		final Function<Map.Entry<Integer, Map.Entry<Integer, Integer>>, ImmutableList.Builder<Map.Entry<Integer, Map.Entry<Integer, Integer>>>> toListBuilder = entry -> 
			new ImmutableList.Builder<Map.Entry<Integer, Map.Entry<Integer, Integer>>>().add(entry);
		final IntFunction<Map.Entry<Integer, Map.Entry<Integer, Integer>>> toEntry = letterIndex -> {
			final String letter = letterSplit(line).get(letterIndex); 
			final Integer wordLetterIndex = wordLetterMap.get(letter);
			final Integer xCoordinate = notNull().apply(wordLetterIndex) 
				? letterIndex
				: -1;
			final Integer yCoordinate = notNull().apply(wordLetterIndex)
				? lineIndex
				: -1;
			final Map.Entry<Integer, Map.Entry<Integer, Integer>> entry = entry(notNull().apply(wordLetterIndex) 
				? wordLetterIndex 
				: -1, entry(xCoordinate, yCoordinate));
			return entry;
		};
		return range(0, letterSplit(line).size())
			.mapToObj(toEntry)
			.map(toListBuilder)
			.reduce((i, j) -> new ImmutableList.Builder<Map.Entry<Integer, Map.Entry<Integer, Integer>>>()
				.addAll(i.build())
				.addAll(j.build()))
			.get();
	}

	public default List<String> letterSplit(final String line){
		final String[] letterSplit = line.split(",");
		checkState(letterSplit.length > 0, "A line must have at least one letter.");
		return range(0, letterSplit.length)
			.mapToObj(letterIndex -> letterSplit[letterIndex])
			.collect(toList());
	}

	public default List<String> get(final Integer lineIndex){
		return letterSplit(lineSplit().get(lineIndex));
	}
}
