package io.github.gunkim.engine.storage.lsm;

import io.github.gunkim.engine.storage.exception.StorageReadException;
import io.github.gunkim.engine.storage.exception.StorageWriteException;
import io.github.gunkim.engine.storage.serializer.JsonSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class FileSystemAccess<T> {
    private static final String KEY_VALUE_SEPARATOR = ",";

    private final JsonSerializer jsonSerializer = new JsonSerializer();

    public Optional<T> get(File file, String key) {
        return executeWithIoExceptionHandled(() -> readFromFile(file, key));
    }

    public Stream<Path> getDirectoryInFilePaths(Path path) {
        if (!existsPath(path)) {
            return Stream.of();
        }
        return executeWithIoExceptionHandled(() -> Files.list(path));
    }

    public void flush(String newFileSavePath, MemTable<T> memTable) {
        var newFile = new File(newFileSavePath);
        prepareDirectory(newFile);

        try (var writer = new FileWriter(newFile)) {
            for (var entry : memTable.entrySet()) {
                String key = entry.getKey();
                String value = jsonSerializer.serialize(entry.getValue());
                writer.append(key).append(KEY_VALUE_SEPARATOR).append(value);
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new StorageWriteException(e.getMessage(), e);
        }
    }

    private void prepareDirectory(File file) {
        var directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private <T> T executeWithIoExceptionHandled(IOOperation<T> operation) {
        try {
            return operation.run();
        } catch (IOException e) {
            throw new StorageReadException(e.getMessage());
        }
    }

    private Optional<T> readFromFile(File file, String key) throws IOException {
        try (var fileReader = Files.newBufferedReader(file.toPath())) {
            return findKeyInReader(key, fileReader);
        }
    }

    private Optional<T> findKeyInReader(String key, BufferedReader fileReader) throws IOException {
        String line;
        while ((line = fileReader.readLine()) != null) {
            var seperator = line.indexOf(KEY_VALUE_SEPARATOR);
            String savedKey = line.substring(0, seperator);

            if (savedKey.equals(key)) {
                return Optional.of(jsonSerializer.deserialize(line.substring(seperator + 1)));
            }
        }
        return Optional.empty();
    }

    private boolean existsPath(Path path) {
        return Files.exists(path);
    }
}
