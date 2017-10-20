package kata.word.search;

import java.util.List;
import java.io.File;

import static com.google.common.base.Preconditions.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.*;

@FunctionalInterface
public interface PuzzleLines{
	public File file();

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
