CALL SetClassPath.bat
REM java -Djsse.enableSNIExtension=false -classpath %CLASSPATH% com.xcase.common.CommonApplication -h -D C:/temp/Headers.txt -d "dummy data" -F q=sausage -H "Accept:application/json" -H "Content-Type:application/json" -o C:/temp/Output.txt -u martin:password -X GET https://www.google.com

REM java -Djsse.enableSNIExtension=false -classpath %CLASSPATH% com.xcase.common.CommonApplication -D C:/temp/Headers.txt -d "dummy data" -F q=sausage -H "Accept:application/json" -H "Content-Type:application/json" -o C:/temp/Output.txt -u martin:password -X GET https://www.google.com

java -Djsse.enableSNIExtension=false -classpath %CLASSPATH% com.xcase.common.CommonApplication -D C:/temp/Headers.txt -d "dummy data" -F q=sausage -o C:/temp/Output.txt -X POST https://www.google.com