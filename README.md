## Car Registration Sample

A basic Java-Selenium-Cucumber implementation to extract the data from input file, get details from website and compare with output file. 

### Set up

- Clone the repo 
- Download and save the chromedriver at
  - `<project folder path>\lib\drivers\windows` (if OS is windows)
  - `<project folder path>\lib\drivers\mac` (if OS is MacOS)
  - `<project folder path>\lib\drivers\linux` (if OS is linux)
  
### Test Execution
- Select `Template -> TestNG` and enter below details as shown in screenshot below.

##### Intellij configuration - Edit Configurations VM Options
    Name                    :   CarTest
    Test Kind               :   Suite
    Suite                   :   src/test/resources/testsuites/browsertests.xml
    VM Options              :   -Dcukes.env=devtest
                                -Dcukes.techstack=LOCAL_CH
                                -Dorg.apache.logging.log4j.level=DEBUG
                                -Dcukes.selenium.defaultFindRetries=1
                                -DscreenshotOnFailure=true
    Shorten command line    :   classpath file

Note: to run the test in another browser update the run config (LOCAL_IE - Internet Explorer, LOCAL_FF - Firefox etc) and copy the respective driver in drivers folder.

Alternatively if you don't want to download the driver manually then use the below VM Options. This shall download the chromedriver during runtime. You need to know the chorme version, if not provided it will download latest chrome driver (Note if your chrome browser is not latest then provide the chome version)

    -Dcukes.env=devtest
    -Dcukes.techstack=LOCAL_CH
    -Dorg.apache.logging.log4j.level=DEBUG
    -Dcukes.selenium.defaultFindRetries=1
    -DscreenshotOnFailure=true
    -Dcukes.webdrivermanager=true
    -Dcukes.chromeDriver=94.0.4606.61

if -Dcukes.chromeDriver not provided then it will download the latest driver. 

Chrome Verions: 
![image](https://user-images.githubusercontent.com/43154620/136963069-4bfd40f4-6758-4e5b-844f-70749549b34c.png)

Note: It is recommended to download the compatible driver and copy in the driver folder as this is one time activity

Screenshot below for the lib directory. 

### Execution Summary Reports 

Reports can be found at /RunReports/ExtentHTML*

![image](https://user-images.githubusercontent.com/43154620/136964768-4feb4355-0fc4-45e7-b3be-93bbd6b50b8f.png)
