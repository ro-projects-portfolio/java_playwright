import annotations.PlaywrightPage;
import com.microsoft.playwright.*;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CartPage;
import pages.HomePage;
import utilities.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * This class serves as the base test class for Playwright-based test suites. It provides setup and teardown
 * methods for initializing Playwright, launching a browser, creating a browser context, and managing test-specific
 * resources like pages. Additionally, it supports test result monitoring using the TestWatcherExtension.
 *
 * @see TestWatcherExtension
 */
@ExtendWith(TestWatcherExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // for parallel execution
public class PlaywrightRunner {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    @PlaywrightPage
    protected HomePage homePage;
    @PlaywrightPage
    protected CartPage cartPage;
    protected static final Logger logger = Logger.getLogger(PlaywrightRunner.class);

    /**
     * Initializes Playwright at the class level, ensuring it's created only once for all test methods.
     */
    @BeforeAll
    public void init(){
        playwright = Playwright.create();
    }

    /**
     * Sets up the browser, browser context, and page for each test method.
     */
    @BeforeEach
    public void setUp(){

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        // new BrowserType.LaunchOptions().setHeadless(false) --> without this option, will be headless execution
        browserContext = browser.newContext(new Browser.NewContextOptions()
                .setPermissions(Arrays.asList("geolocation"))
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(Utils.getScreenWidth(), Utils.getScreenHeight())
                .setViewportSize(Utils.getScreenWidth(), Utils.getScreenHeight())
        );
        //browserContext.setDefaultTimeout(40000);
        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(false));
        // new Browser.NewContextOptions().setPermissions(Arrays.asList("geolocation"))--> this function will help ignore location pop-up
        // browser context create isolated session similar to Incognito mode. It helps to avoid some kind of issue (cookies)
        // As a recommendation, you should use the browser context for each of your tests, because in doing so you will have an isolated context for your test.

        page = browserContext.newPage();

        pageInitializer(this, page);
    }

    /**
     * Tears down resources after each test method, including stopping tracing, closing the browser context,
     * and closing the browser.
     *
     * @param testInfo Information about the executed test.
     */
    @AfterEach
    public void tearDown(TestInfo testInfo) {
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("traces/" + testInfo.getDisplayName().replace("()", "") + ".zip")));
        browserContext.close();
        browser.close();
    }

    /**
     * Initializes Playwright page objects annotated with {@code PlaywrightPage} for the current test class instance.
     *
     * @param object The current test class instance.
     * @param page   The Playwright page to be assigned to annotated fields.
     */
    private void pageInitializer(Object object, Page page) {
        Class<?> clazz = object.getClass().getSuperclass();
        for(Field field : clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(PlaywrightPage.class)) {
                Class<?>[] type = {Page.class};
                try {
                    field.set(this, field.getType().getConstructor(type).newInstance(page));
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    System.out.println("Could not call constructor for playwright page with title " + field.getName());
                }
            }
        }
    }
}



