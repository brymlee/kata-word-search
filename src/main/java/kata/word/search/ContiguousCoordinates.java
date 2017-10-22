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

public interface ContiguousCoordinates{

	public static enum Direction{
		DOWN(entry -> {
			return coordinateAddend -> {
				final Integer newYCoordinate = entry.getValue().getValue() + coordinateAddend;
				return entry(coordinateAddend, entry(entry.getValue().getKey(), newYCoordinate));
			};
		}) 
		,UP(entry -> {
			return coordinateAddend -> {
				final Integer newYCoordinate = entry.getValue().getValue() - coordinateAddend;
				return entry(coordinateAddend, entry(entry.getValue().getKey(), newYCoordinate));
			};
		}); 

		private Function<Map.Entry<Integer, Map.Entry<Integer, Integer>>, IntFunction<Map.Entry<Integer, Map.Entry<Integer, Integer>>>> function;

		private Direction(final Function<Map.Entry<Integer, Map.Entry<Integer, Integer>>, IntFunction<Map.Entry<Integer, Map.Entry<Integer, Integer>>>> function){
			this.function = function;
		}

		public Function<Map.Entry<Integer, Map.Entry<Integer, Integer>>, IntFunction<Map.Entry<Integer, Map.Entry<Integer, Integer>>>> function(){
			return this.function;
		}
	}

	public static IntFunction<Map.Entry<Integer, Map.Entry<Integer, Integer>>> toExpectedCoordinate(final Map.Entry<Integer, Map.Entry<Integer, Integer>> entry
												       ,final Direction direction){
		return direction
			.function()
			.apply(entry);
	}

	public static Predicate<Map.Entry<Integer, Map.Entry<Integer, Integer>>> whereCoordinatesEqual(final Map.Entry<Integer, Map.Entry<Integer, Integer>> i){
		return (Predicate<Map.Entry<Integer, Map.Entry<Integer, Integer>>>) j -> i.getKey().equals(j.getKey())
			&& i.getValue().getKey().equals(j.getValue().getKey())
			&& i.getValue().getValue().equals(j.getValue().getValue());
	}

	public ImmutableMap<String, Object> contiguousCoordinatesParameters();

	public default List<List<Map.Entry<Integer, Integer>>> coordinates(){
		final Direction direction = (Direction) contiguousCoordinatesParameters().get("direction");
		checkState(notNull().apply(direction), "direction must be specified");
		final List<Map.Entry<Integer, Map.Entry<Integer, Integer>>> letterCoordinates = (List<Map.Entry<Integer, Map.Entry<Integer, Integer>>>) contiguousCoordinatesParameters().get("coordinates");
		checkState(notNull().apply(letterCoordinates), "letterCoordinates must be specified");
		final String wordToFind = (String) contiguousCoordinatesParameters().get("wordToFind");
		checkState(notNull().apply(wordToFind), "wordToFind must be specified");
		final Predicate<Map.Entry<Integer, Map.Entry<Integer, Integer>>> whereCoordinatesHaveWordLetters = entry -> !Integer.valueOf(-1).equals(entry.getKey()); 
		final Predicate<Map.Entry<Integer, Map.Entry<Integer, Integer>>> whereCoordinatesAreStartingWordLetter = entry -> { 
			return Integer.valueOf(0).equals(entry.getKey());
		};
		final Function<Map.Entry<Integer, Map.Entry<Integer, Integer>>, List<Map.Entry<Integer, Map.Entry<Integer, Integer>>>> toExpectedCoordinates = entry -> {
			final List<Map.Entry<Integer, Map.Entry<Integer, Integer>>> expectedCoordinates = range(0, wordToFind.length())
				.mapToObj(toExpectedCoordinate(entry, direction))
				.collect(toList());
			return expectedCoordinates;
		};
		final Predicate<List<Map.Entry<Integer, Map.Entry<Integer, Integer>>>> whereActualCoordinatesContainExpectedCoordinates = expectedCoordinates -> {
			return expectedCoordinates	
				.stream()
				.allMatch(expectedCoordinate -> {
					return letterCoordinates
						.stream()
						.anyMatch(whereCoordinatesEqual(expectedCoordinate));
				});
		};
		final Function<List<Map.Entry<Integer, Map.Entry<Integer, Integer>>>, List<Map.Entry<Integer, Integer>>> toProperCoordinates = coordinates -> {
			final Function<Map.Entry<Integer, Map.Entry<Integer, Integer>>, Map.Entry<Integer, Integer>> toEntry = coordinate -> {
				return entry(coordinate.getValue().getKey(), coordinate.getValue().getValue());
			};
			return coordinates
				.stream()
				.map(toEntry)
				.collect(toList());
		};
		return letterCoordinates
			.stream()
			.filter(whereCoordinatesHaveWordLetters)
			.filter(whereCoordinatesAreStartingWordLetter)
			.map(toExpectedCoordinates)
			.filter(whereActualCoordinatesContainExpectedCoordinates)
			.map(toProperCoordinates)
			.collect(toList());
	}
}
