package utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@UtilityClass
public class FileUtils {
    public static boolean isFileDownloaded(Path downloadPath, String fileName) {
        File file = downloadPath.resolve(fileName).toFile();
        return file.exists() && file.length() > 0;
    }

    public static void deleteDownloadedFile(String fileName, Path directory) {
        try {
            Files.deleteIfExists(directory.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't delete file: " + fileName, e);
        }
    }
}