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
public interface WordResults{
	public Map<String, Object> parameters();

	public default WordResult wordResult(final String word){
		final List<List<List<List<Map.Entry<Integer, Integer>>>>> coordinates = (List<List<List<List<Map.Entry<Integer, Integer>>>>>) parameters().get("coordinates"); 
		checkState(notNull().apply(coordinates), "coordinates must be specified");
		final List<String> words = (List<String>) parameters().get("words");
		checkState(notNull().apply(words), "words must be specified");
		final Integer wordIndex = range(0, words.size())
			.filter((IntPredicate) index -> {
				return word.equals(words.get(index));
			}).findFirst()
			.getAsInt();
		final Map<String, Object> parameters = ImmutableMap.<String, Object>of("word", words.get(wordIndex)
										      ,"coordinates", coordinates.get(wordIndex));
		final WordResult wordResult = () -> parameters; 
		return wordResult;
	}

}
