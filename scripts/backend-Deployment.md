# Management server and worker servers deployment
1. Update apt-get.
``` 
$ sudo apt-get update
```
2. Install Java 1.8.
```
$ sudo apt-get install openjdk-8-jdk
```
3. Check java version.
```
$ java -version
openjdk version "1.8.0_275"
OpenJDK Runtime Environment (build 1.8.0_275-8u275-b01-0ubuntu1~18.04-b01)
OpenJDK 64-Bit Server VM (build 25.275-b01, mixed mode)
```
4. Run the service.

Management server.
```
$ nohup java -jar management-server-v1.jar -Dspring.config.location=application.properties
```

Worker server.
```
$ java -jar worker-v1.jar -Dspring.config.location=application.properties
```

`nohup` to make the program run in backend.

## How do we pack the spring boot project?

1. Add `<packaging>jar</packaging>` into pom.xml.
2. Run `mvn clean package`.
3. Run `3. java -jar management-server-v1.jar -Dspring.config.location=application.properties`.



