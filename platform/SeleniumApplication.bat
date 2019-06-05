CALL SetClassPath.bat
java -Dwebdriver.gecko.driver=C:\xcase\platform\lib\geckodriver.exe -Djsse.enableSNIExtension=false -classpath %CLASSPATH% com.xcase.selenium.SeleniumApplication