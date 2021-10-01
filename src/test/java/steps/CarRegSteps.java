package steps;

import automation.library.common.Property;
import automation.library.cucumber.core.Constants;
import automation.library.cucumber.selenium.BaseSteps;
import automation.library.selenium.core.Element;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pageobjects.FreeCarCheckPage;
import pageobjects.Home;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarRegSteps extends BaseSteps {
    public static final String BASEPATH = System.getProperty("user.dir") + "/src/test/resources/";
    private List<String> carReg;
    private Map<String, Map<String, String>> carSpecs = new HashMap<>();

    @Given("there is input file {string} having car registrations")
    public void thereIsInputFileHavingCarRegistrations(String file) {
        File f = new File(BASEPATH + "/testdata/inputs/" + file);

        //pattern to extract the Reg
        Pattern pattern = Pattern.compile("(\\b[A-Za-z]{2}[0-9]{2}\\s*[A-Za-z]{3}\\b)");

        carReg = new ArrayList<>();

        Scanner myReader = null;
        try {
            myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Matcher matcher = pattern.matcher(data);
                while (matcher.find()) {
                    carReg.add(matcher.group(0).replaceAll("\\s","").toUpperCase());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert myReader != null;
        myReader.close();
        System.out.println(carReg);
    }

    @When("I perform Free Car Check on {string}")
    public void iCheckPerformOn(String app) {
        Home home = new Home();

        getDriver().manage().window().maximize();
        String url = Property.getProperty(Constants.ENVIRONMENTPATH+Property.getVariable("cukes.env")+".properties",app);
        getDriver().get(url);

        //validate page loaded
        Assert.assertEquals(home.getHeading(),"Free Car Check","Page not loaded");

        carSpecs = new HashMap<>();
        FreeCarCheckPage fc = new FreeCarCheckPage();
        SoftAssert sa = new SoftAssert();

        carReg.forEach( c->{
            //enter Reg
            home.enterRegistration(c);
            home.clickFCCButton();

            System.out.println(new Date());
            List<Element> eM = fc.checkErrorMessage();
            System.out.println(new Date());

            if(eM.size() ==0){
                carSpecs.put(c,fc.getVehicleIdentity());
                getDriver().navigate().back();
            }
            else{
                fc.clickTryAgain();
            }

            Assert.assertEquals(home.getHeading(),"Free Car Check","Page not loaded");
        });
        System.out.println(carSpecs);
    }

    @Then("data matches with car output file {string}")
    public void iDataMatchesWithCarOutputFile(String oFile) {
        File f = new File(BASEPATH + "/testdata/outputs/" + oFile);
        Scanner myReader = null;
        Map<String, Map<String,String>> expSpecs = new HashMap<>();
        try {
            myReader = new Scanner(f);
            String hLine = myReader.nextLine();
            String[] heading = hLine.split(",") ;


            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] specs = line.split(",") ;

                Map<String,String> specsList = new HashMap<>();

                for (int i=0;i < heading.length;i++){
                    specsList.put(heading[i],specs[i]);
                }

                expSpecs.put(specsList.get("REGISTRATION"),specsList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (myReader != null) {
            myReader.close();
        }

        carReg.forEach(r->{

            if(carSpecs.containsKey(r)){
                System.out.println("validating : " + r);
                Map<String, String> exp = expSpecs.get(r);
                Map<String, String> act = carSpecs.get(r);

                try{
                    sa().assertEquals(act.get("Make"),exp.get("MAKE"),"mismatch in MAKE for reg:"+r);
                    sa().assertEquals(act.get("Model"),exp.get("MODEL"),"mismatch in MODEL for reg:"+r);
                    sa().assertEquals(act.get("Colour"),exp.get("COLOR"),"mismatch in COLOR for reg:"+r);
                    sa().assertEquals(act.get("Year"),exp.get("YEAR"),"mismatch in YEAR for reg:"+r);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                sa().fail("Reg Not Found : " + r);
            }
        });

        sa().assertAll();
    }
}
