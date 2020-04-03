# Census Explorer

### Production
[http://census.us-east-1.elasticbeanstalk.com/](http://census.us-east-1.elasticbeanstalk.com/)

### Usage
1. Put the database in the census-explorer\census-explorer-spring\src\main\resources\us-census.db<br/>
2. Install
```
mvn clean install -DskipTests
```
3. Run
```
java -jar census-explorer-spring\target\census-explorer-spring-1.0-FINAL.jar
```
4. Open browser<br/>
[http://localhost:5000](http://localhost:5000)