CALL SetClassPath.bat
java -Dwebdriver.chrome.driver=.\lib\ChromeDriver.exe -Dwebdriver.gecko.driver=.\lib\geckodriver.exe -Djsse.enableSNIExtension=false -classpath %CLASSPATH% com.xcase.selenium.SeleniumApplication