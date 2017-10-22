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
public interface WordResult{

	public Map<String, Object> parameters();

	public static List<Map.Entry<Integer, Integer>> join(final List<List<List<Map.Entry<Integer, Integer>>>> segmentedCoordinates){
		final List<List<Map.Entry<Integer, Integer>>> duplicateWordCoordinates = segmentedCoordinates
			.stream()
			.map((Function<List<List<Map.Entry<Integer, Integer>>>, ImmutableList.Builder<List<Map.Entry<Integer, Integer>>>>) entries -> {
				return ImmutableList.<List<Map.Entry<Integer, Integer>>>builder()
					.addAll(entries);
			}).reduce((i, j) -> {
				return ImmutableList.<List<Map.Entry<Integer, Integer>>>builder()
					.addAll(i.build())
					.addAll(j.build());
			}).get()
			.build();
		final List<Map.Entry<Integer, Integer>> wordCoordinates = duplicateWordCoordinates
			.stream()
			.map((Function<List<Map.Entry<Integer, Integer>>, ImmutableList.Builder<Map.Entry<Integer, Integer>>>) entries -> {
				return ImmutableList.<Map.Entry<Integer, Integer>>builder()
					.addAll(entries);
			}).reduce((i , j) -> {
				return ImmutableList.<Map.Entry<Integer, Integer>>builder()
					.addAll(i.build())
					.addAll(j.build());
			}).get()
			.build();
		return wordCoordinates;
	}

	public default String word(){
		final String word = (String) parameters().get("word");
		checkState(notNull().apply(word), "word must be specified");
		return word;
	}

	public default Map.Entry<Integer, Integer> coordinate(final Integer coordinateIndex){
		final List<List<List<Map.Entry<Integer, Integer>>>> segmentedCoordinates = (List<List<List<Map.Entry<Integer, Integer>>>>) parameters().get("coordinates");
		checkState(notNull().apply(segmentedCoordinates), "coordinates must be specified");
		return join(segmentedCoordinates).get(coordinateIndex);
	}
}
