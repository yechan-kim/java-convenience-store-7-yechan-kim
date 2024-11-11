package store.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FileLoaderTest {

    @Test
    void 파일_읽기를_실패_하면_예외_발생() {
        String path = "test";
        assertThrows(IllegalArgumentException.class, () -> FileLoader.load(path));
    }
}
