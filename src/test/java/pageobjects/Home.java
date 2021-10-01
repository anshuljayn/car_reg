package pageobjects;

import automation.library.selenium.exec.BasePO;
import org.openqa.selenium.By;

public class Home extends BasePO {

    private static final By heading = By.tagName("h1");
    private static final By enterReg= By.id("vrm-input");
    private static final By freeCarCheck = By.xpath("//button[text()=\"Free Car Check\"]");

    public void enterRegistration(String reg){
        findElement(enterReg).clear().sendKeys(reg);
    }

    public void clickFCCButton(){
        findElement(freeCarCheck).clickJS();
    }

    public String getHeading(){
        return findElement(heading).getText().trim();
    }

}
