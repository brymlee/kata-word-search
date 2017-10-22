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
import static kata.word.search.WordSearchTest.*;

public class FindWordTest{
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
			.coordinates(Direction.DOWN)
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
			.coordinates(Direction.UP)
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
			.coordinates(Direction.LEFT)
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
			.coordinates(Direction.RIGHT)
			.get(0);
		final Integer expectedCoordinatesSize = expectedCoordinates.size();
		checkState(expectedCoordinatesSize.equals(actualCoordinates.size()), "Count of found word's coordinates must be equal from actual to expected.");
		final Long matchedUpCoordinateCount = range(0, expectedCoordinates.size())
			.filter(whereCoordinatesEqual(actualCoordinates, expectedCoordinates))
			.count();
		assertEquals(expectedCoordinatesSize, Integer.valueOf(matchedUpCoordinateCount.intValue()));
	}

	@Test
	public void findWordDownRight_spock(){
		final ImmutableMap<String, Object> findParameters = ImmutableMap.<String, Object>of("file", EXAMPLE_FILE, "wordToFind", "spock");
		final FindWord findWord = () -> findParameters; 
		final ImmutableList<Map.Entry<Integer, Integer>> expectedCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(2, 1)
			        ,entry(3, 2)
			        ,entry(4, 3)
			        ,entry(5, 4)
			        ,entry(6, 5));
		final List<Map.Entry<Integer, Integer>> actualCoordinates = findWord
			.coordinates(Direction.DOWN_RIGHT)
			.get(0);
		final Integer expectedCoordinatesSize = expectedCoordinates.size();
		checkState(expectedCoordinatesSize.equals(actualCoordinates.size()), "Count of found word's coordinates must be equal from actual to expected.");
		final Long matchedUpCoordinateCount = range(0, expectedCoordinates.size())
			.filter(whereCoordinatesEqual(actualCoordinates, expectedCoordinates))
			.count();
		assertEquals(expectedCoordinatesSize, Integer.valueOf(matchedUpCoordinateCount.intValue()));
	}

	@Test
	public void findWordUpLeft_sulu(){
		final ImmutableMap<String, Object> findParameters = ImmutableMap.<String, Object>of("file", EXAMPLE_FILE, "wordToFind", "sulu");
		final FindWord findWord = () -> findParameters; 
		final ImmutableList<Map.Entry<Integer, Integer>> expectedCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(3, 3)
			        ,entry(2, 2)
			        ,entry(1, 1)
			        ,entry(0, 0));
		final List<Map.Entry<Integer, Integer>> actualCoordinates = findWord
			.coordinates(Direction.UP_LEFT)
			.get(0);
		final Integer expectedCoordinatesSize = expectedCoordinates.size();
		checkState(expectedCoordinatesSize.equals(actualCoordinates.size()), "Count of found word's coordinates must be equal from actual to expected.");
		final Long matchedUpCoordinateCount = range(0, expectedCoordinates.size())
			.filter(whereCoordinatesEqual(actualCoordinates, expectedCoordinates))
			.count();
		assertEquals(expectedCoordinatesSize, Integer.valueOf(matchedUpCoordinateCount.intValue()));
	}

	@Test
	public void findWordDownLeft_uhura(){
		final ImmutableMap<String, Object> findParameters = ImmutableMap.<String, Object>of("file", EXAMPLE_FILE, "wordToFind", "uhura");
		final FindWord findWord = () -> findParameters; 
		final ImmutableList<Map.Entry<Integer, Integer>> expectedCoordinates = ImmutableList.<Map.Entry<Integer, Integer>>of(entry(4, 0)
			        ,entry(3, 1)
			        ,entry(2, 2)
				,entry(1, 3)
			        ,entry(0, 4));
		final List<Map.Entry<Integer, Integer>> actualCoordinates = findWord
			.coordinates(Direction.DOWN_LEFT)
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
