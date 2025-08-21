package utils;

import lombok.experimental.UtilityClass;
import org.awaitility.Awaitility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@UtilityClass
public class WaitUtils {
    private static final Path DEFAULT_DOWNLOAD_DIR =
            Paths.get("src", "test", "resources");
    private static final long DEFAULT_TIMEOUT_SECONDS = 60;
    private static final long DEFAULT_POLL_INTERVAL_SECONDS = 2;

    public static void waitForFileDownloaded(String fileName) {
        waitForFileDownloaded(DEFAULT_DOWNLOAD_DIR, fileName,
                DEFAULT_TIMEOUT_SECONDS, DEFAULT_POLL_INTERVAL_SECONDS);
    }

    public static void waitForFileDownloaded(Path directory, String fileName,
                                             long timeoutSeconds, long pollIntervalSecs) {
        Awaitility.await()
                .atMost(timeoutSeconds, TimeUnit.SECONDS)
                .pollInterval(pollIntervalSecs, TimeUnit.SECONDS)
                .until(() -> FileUtils.isFileDownloaded(directory, fileName));
    }

    public static void waitUntil(Supplier<Boolean> condition,
                                 long timeoutSeconds,
                                 long pollIntervalSecs) {
        Awaitility.await()
                .atMost(timeoutSeconds, TimeUnit.SECONDS)
                .pollInterval(pollIntervalSecs, TimeUnit.SECONDS)
                .until(condition::get);
    }

    public static void waitUntilElementPresent(WebDriver driver,
                                               By locator,
                                               long timeoutSeconds,
                                               long pollIntervalSec) {
        waitUntil(() -> !driver.findElements(locator).isEmpty(),
                timeoutSeconds,
                pollIntervalSec);
    }
}
