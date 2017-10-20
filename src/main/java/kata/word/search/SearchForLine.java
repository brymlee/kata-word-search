package kata.word.search;

import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;

import static com.google.common.base.Preconditions.*;

@FunctionalInterface
public interface SearchForLine{
	public File file();

	public default String fileAsString(){
		checkState(file().exists(), "Input file must exist.");
		try{
			final InputStream inputStream = new FileInputStream(file());
			final byte[] bytes = new byte[inputStream.available()];
			inputStream.read(bytes);
			inputStream.close();
			return new String(bytes);
		}catch(final IOException ioException){
			throw new RuntimeException(ioException);
		}
	}

	public default String firstLine(){
		return fileAsString().split("\n")[0];
	}

	public default String[] firstLineSplit(){
		return firstLine().split(",");
	}

	public default String get(final Integer wordIndex){
		return firstLineSplit()[wordIndex];	
	}

	public default Integer wordCount(){
		return firstLineSplit().length;
	}
}
