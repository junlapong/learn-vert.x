# Learn Vert.x

![](https://vertx.io/assets/logo-sm.png)

- [ทำความรู้จักกับ Vert.x Tool-kit for building reactive applications on the JVM](https://cyl3erpunkz.wordpress.com/2015/07/04/get-started-vertx/)
- [สร้าง Reactive RESTful Web Service ด้วย Vert.x](https://link.medium.com/B2As0rPAxX)

## Try

	mvn clean verify
	mvn exec:java

The command compiles the project and runs the tests, then it launches the application, so you can check by yourself. Open your browser to http://localhost:8080. You should see a Hello World message.

## Build

	mvn clean package -Dmaven.test.skip
	java -jar target/vertx-start-project-1.0-SNAPSHOT-fat.jar

