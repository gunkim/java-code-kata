package io.github.gunkim.engine.storage.lsm;

import io.github.gunkim.engine.storage.exception.StorageWriteException;
import io.github.gunkim.engine.storage.serializer.JsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.SortedMap;

public class FileSystemAccess<T> {
    private final JsonSerializer jsonSerializer = new JsonSerializer();

    public void existsDirectory(File file) {
        var directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public boolean existsPath(Path path) {
        return Files.exists(path);
    }

    public Optional<T> get(File file, String key) throws IOException {
        try (var fileReader = Files.newBufferedReader(file.toPath())) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                var seperator = line.indexOf(",");

                String savedKey = line.substring(0, seperator);
                if (savedKey.equals(key)) {
                    return Optional.of(jsonSerializer.deserialize(line.substring(seperator + 1)));
                }
            }
        }
        return Optional.empty();
    }

    public void flush(File file, SortedMap<String, T> datas) {
        try (var fileWriter = new FileWriter(file, false)) {
            for (var entry : datas.entrySet()) {
                String key = entry.getKey();
                String value = jsonSerializer.serialize(entry.getValue());

                var content = "%s,%s\n".formatted(key, value);
                fileWriter.append(content);
            }
            fileWriter.flush();
        } catch (IOException e) {
            throw new StorageWriteException(e.getMessage());
        }
    }
}
