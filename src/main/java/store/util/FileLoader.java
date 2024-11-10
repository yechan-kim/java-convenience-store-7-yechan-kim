package store.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import store.exception.ErrorMessage;
import store.exception.StoreException;

public class FileLoader {

    public List<String> load(String filePath) {
        try (Stream<String> linesStream = Files.lines(Paths.get(filePath))) {
            return linesStream
                    .skip(1)
                    .filter(line -> !line.trim().isEmpty())
                    .toList();
        } catch (IOException e) {
            throw StoreException.from(ErrorMessage.FILE_LOAD_ERROR);
        }
    }
}
