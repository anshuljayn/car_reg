package runners;

        import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = {"@car", "not @ignore"})

public class BrowserTest1 extends BaseRunner {}