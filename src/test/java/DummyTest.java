import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.EnvFileReader;

public class DummyTest extends PlaywrightRunner{

    @Test
    public void emptyCartVerification(){
        page.navigate(EnvFileReader.getProperty("url"));
        homePage.clickCartLink();
        String expectedTittle = "Your Amazon Cart is empty";
        Assertions.assertEquals(expectedTittle,cartPage.getHeaderText());

    }
}
