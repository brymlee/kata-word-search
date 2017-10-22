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

public enum Direction{
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
	})
	,DOWN_LEFT(entry -> {
		return coordinateAddend -> {
			final Integer newXCoordinate = entry.getValue().getKey() - coordinateAddend;
			final Integer newYCoordinate = entry.getValue().getValue() + coordinateAddend;
			return entry(coordinateAddend, entry(newXCoordinate, newYCoordinate));
		};
	})
	,UP_RIGHT(entry -> {
		return coordinateAddend -> {
			final Integer newXCoordinate = entry.getValue().getKey() + coordinateAddend;
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
