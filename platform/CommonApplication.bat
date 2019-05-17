CALL SetClassPath.bat
REM java -Djsse.enableSNIExtension=false -classpath %CLASSPATH% com.xcase.common.CommonApplication -h -D C:/temp/Headers.txt -d "dummy data" -F q=sausage -H "Accept:application/json" -H "Content-Type:application/json" -o C:/temp/Output.txt -u martin:password -X GET https://www.google.com

java -Djsse.enableSNIExtension=false -classpath %CLASSPATH% com.xcase.common.CommonApplication -D C:/temp/Headers.txt -d "dummy data" -F q=sausage -H "Accept: application/json" -H "Content-Type: application/json" -o C:/temp/Output.txt -u martin:password -X GET https://www.google.com

java -Djsse.enableSNIExtension=false -classpath %CLASSPATH% com.xcase.common.CommonApplication -F file=@C:/temp/TestApplication.txt -X POST http://localhost:8080/examples/jsp/upload/Action_file_upload.jsp