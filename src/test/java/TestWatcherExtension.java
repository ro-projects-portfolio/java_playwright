import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * This JUnit Jupiter extension implements TestWatcher and BeforeEachCallback interfaces
 * to perform actions before and after test methods are executed. It is used for managing test-specific
 * resources such as video recording and trace files, as well as deleting them after successful test execution.
 */
public class TestWatcherExtension implements TestWatcher, BeforeEachCallback {

    private List<Path> listOfVideoFiles = new ArrayList<>();

    /**
     * Initializes the list of video files before each test execution by scanning the "videos/" directory.
     *
     * @param extensionContext The context of the test extension.
     * @throws Exception If an error occurs during setup.
     */
    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        listOfVideoFiles = Files.walk(Paths.get("videos/")).filter(Files::isRegularFile).collect(Collectors.toList());

    }

    /**
     * Cleans up resources and artifacts after a successful test execution, including deleting trace files and
     * videos that were not present before the test.
     *
     * @param context The context of the test extension.
     */
    @Override
    public void testSuccessful(ExtensionContext context) {
        deleteTraces(context);
        deleteVideos();
    }

    /**
     * Deletes the trace file associated with the test context.
     *
     * @param context The context of the test extension.
     */
    private void deleteTraces(ExtensionContext context) {
        Path tracePath = Paths.get("traces/", context.getDisplayName().replace("()", "") + ".zip");
        try {
            Files.deleteIfExists(tracePath);
        } catch (IOException e) {
            System.out.println("Failed to delete trace file with name " + tracePath.getFileName());
        }
    }

    /**
     * Deletes videos that were not present before the test execution.
     */
    private void deleteVideos() {
        try {
            List<Path> newVideos = Files.walk(Paths.get("videos/")).filter(Files::isRegularFile)
                    .filter(el -> !listOfVideoFiles.contains(el)).collect(Collectors.toList());
            for(Path path : newVideos) {
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            System.out.println("Failed to delete videos. Error message: " + e.getMessage());
        }
    }




}
