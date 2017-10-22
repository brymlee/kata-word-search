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
import java.util.stream.Stream;

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
		})
		,LEFT(entry -> {
			return coordinateAddend -> {
				final Integer newXCoordinate = entry.getValue().getKey() - coordinateAddend;
				return entry(coordinateAddend, entry(newXCoordinate, entry.getValue().getValue()));
			};
		})
		,RIGHT(entry -> {
			return coordinateAddend -> {
				final Integer newXCoordinate = entry.getValue().getKey() + coordinateAddend;
				return entry(coordinateAddend, entry(newXCoordinate, entry.getValue().getValue()));
			};
		})
		,DOWN_RIGHT(entry -> {
			return coordinateAddend -> {
				final Integer newXCoordinate = entry.getValue().getKey() + coordinateAddend;
				final Integer newYCoordinate = entry.getValue().getValue() + coordinateAddend;
				return entry(coordinateAddend, entry(newXCoordinate, newYCoordinate));
			};
		})
		,UP_LEFT(entry -> {
			return coordinateAddend -> {
				final Integer newXCoordinate = entry.getValue().getKey() - coordinateAddend;
				final Integer newYCoordinate = entry.getValue().getValue() - coordinateAddend;
				return entry(coordinateAddend, entry(newXCoordinate, newYCoordinate));
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
		return (Predicate<Map.Entry<Integer, Map.Entry<Integer, Integer>>>) j -> 
			//&& i.getKey().equals(j.getKey())
			i.getValue().getKey().equals(j.getValue().getKey())
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
		final Predicate<Map.Entry<Integer, Map.Entry<Integer, Integer>>> whereCoordinatesArePositive = entry -> {
			final boolean isKeyPositive = 0 <= entry.getKey();
			final boolean isXPositive = 0 <= entry.getValue().getKey();
			final boolean isYPositive = 0 <= entry.getValue().getValue();
			return isKeyPositive && isXPositive && isYPositive;
		};
		final Predicate<Map.Entry<Integer, Map.Entry<Integer, Integer>>> whereCoordinatesAreStartingWordLetter = entry -> { 
			return Integer.valueOf(0).equals(entry.getKey());
		};
		final Predicate<Optional<List<Map.Entry<Integer, Map.Entry<Integer, Integer>>>>> isPresent = optionalEntries -> {
			return optionalEntries.isPresent();
		};
		final Function<Optional<List<Map.Entry<Integer, Map.Entry<Integer, Integer>>>>, List<Map.Entry<Integer, Map.Entry<Integer, Integer>>>> unwrapOptional = optionalEntries -> {
			return optionalEntries.get();
		};
		final Function<Map.Entry<Integer, Map.Entry<Integer, Integer>>, Optional<List<Map.Entry<Integer, Map.Entry<Integer, Integer>>>>> toExpectedCoordinates = entry -> {
			//System.out.println(entry.getKey() + ", " + entry.getValue().getKey() + ", " + entry.getValue().getValue());
			final List<Map.Entry<Integer, Map.Entry<Integer, Integer>>> expectedCoordinates = range(0, wordToFind.length())
				.mapToObj(toExpectedCoordinate(entry, direction))
				.collect(toList());
			final Boolean isAllCoordinatesPositive = expectedCoordinates
				.stream()
				.allMatch(whereCoordinatesArePositive);
			if(!isAllCoordinatesPositive){
				return Optional.empty();
			}
			//expectedCoordinates.stream().forEach(i -> System.out.println(i.getKey() + ", " + i.getValue().getKey() + ", " + i.getValue().getValue()));
			return Optional.ofNullable(expectedCoordinates);
		};
		//letterCoordinates.stream().forEach(i -> System.out.println(i.getKey() + ", " + i.getValue().getKey() + ", " + i.getValue().getValue()));
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
			.filter(isPresent)
			.map(unwrapOptional)
			.filter(whereActualCoordinatesContainExpectedCoordinates)
			.map(toProperCoordinates)
			.collect(toList());
	}
}
