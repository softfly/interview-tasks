# serverside-test
[![Build Status](https://travis-ci.com/softfly/sainsbury-serverside-test.svg?branch=master)](https://travis-ci.com/softfly/sainsbury-serverside-test)
[![Coverage Status](https://coveralls.io/repos/github/softfly/sainsbury-serverside-test/badge.svg?branch=master)](https://coveralls.io/github/softfly/sainsbury-serverside-test?branch=master)

Requirements:<br/>
https://jsainsburyplc.github.io/serverside-test/

## Installation
```
mvn clean install -DskipTests
```
## Usage
Windows:

```
java -jar target\sainsbury-serverside-test-1.0.FINAL.jar
```
Linux:

```
java -jar target/sainsbury-serverside-test-1.0.FINAL.jar
```
## TODO
I skipped creating interfaces. In a real project, interfaces would be needed, for example, for transactional (spring proxy).
## Links
Reports:<br/>
https://softfly.github.io/sainsbury-serverside-test/<br/>
JavaDoc:<br/>
https://softfly.github.io/sainsbury-serverside-test/apidocs/index.html<br/>
Test coverage:<br/>
https://coveralls.io/github/softfly/sainsbury-serverside-test<br/>
CI:<br/>
https://travis-ci.com/softfly/sainsbury-serverside-test