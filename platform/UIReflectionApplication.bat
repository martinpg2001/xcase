CALL SetClassPath.bat
java -Djsse.enableSNIExtension=false -classpath %CLASSPATH% xy.reflect.ui.ReflectionUI com.xcase.rest.generator.swagger.objects.CommonWebProxy