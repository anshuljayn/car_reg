package pageobjects;

import automation.library.selenium.core.Element;
import automation.library.selenium.exec.BasePO;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeCarCheckPage extends BasePO {

    private static final By vehicalIdentity = By.xpath("//div/h4[text()[normalize-space() = \"Vehicle Identity\"]]/../following-sibling::div/dl");
    private static final By vnf = By.xpath("//h5//span[text()=\"Vehicle Not Found\"]");
    private static final By tryAgain = By.linkText("Try Again"); // xpath -> //a[text()="Try Again"]

    public Map<String, String> getVehicleIdentity() {

        Map<String, String> vehicleSpecs = new HashMap<>();
        List<Element> vehicleIdentityList = findElements(vehicalIdentity);

        vehicleIdentityList.forEach(e -> {
            vehicleSpecs.put(e.findElement(By.tagName("dt")).getText().trim()
                    ,e.findElement(By.tagName("dd")).getText().trim());
        });

        return vehicleSpecs;
    }

    public List<Element> checkErrorMessage(){
        return findElements(vnf);
    }

    public void clickTryAgain(){
        findElement(tryAgain).click();
    }

}
