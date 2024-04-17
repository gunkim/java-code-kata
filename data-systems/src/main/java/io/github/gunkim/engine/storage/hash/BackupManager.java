package io.github.gunkim.engine.storage.hash;

import io.github.gunkim.engine.storage.exception.BackupFailedException;

import java.io.*;
import java.util.Optional;

public class BackupManager<T> {
    private final String backupPath;

    BackupManager(String backupPath) {
        this.backupPath = backupPath;
    }

    public void backup(T data) {
        try (var fileOut = new FileOutputStream(backupPath);
             var out = new ObjectOutputStream(fileOut)) {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            throw new BackupFailedException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Optional<T> restore() {
        try (var fileInputStream = new FileInputStream(backupPath);
             var objectInputStream = new ObjectInputStream(fileInputStream)) {

            return (Optional<T>) Optional.ofNullable(objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
