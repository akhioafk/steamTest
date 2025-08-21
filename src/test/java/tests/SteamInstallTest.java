package tests;


import org.testng.annotations.Test;
import pages.InstallPage;
import utils.FileUtils;
import utils.WaitUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SteamInstallTest extends BaseTest {
    private final InstallPage installPage = new InstallPage();
    private final String steamSetupFileName = "SteamSetup.exe";
    private final Path downloadDir = Paths.get("src/test/resources");

    @Test
    public void testSteamInstallerDownload() {
        homePage.clickInstallSteam();
        installPage.clickDownload();
        WaitUtils.waitForFileDownloaded(steamSetupFileName);
        assertTrue(
                FileUtils.isFileDownloaded(
                        downloadDir,
                        steamSetupFileName
                )
        );
        FileUtils.deleteDownloadedFile(steamSetupFileName, downloadDir);
    }
}
