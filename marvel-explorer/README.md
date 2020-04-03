# Yapily Marvel code test
## Installation
### Production
```
mvn clean install -DskipTests -PproductionMode
```
### Debug
```
mvn clean install -DskipTests
```
## Usage
### Production
https://developer.marvel.com/ for get the API access keys.

Windows:

```
java -jar marvel-explorer-app\target\marvel-explorer-app-1.0-FINAL.jar --marvel.api.publickey="xxx" --marvel.api.privatekey="xxx" --vaadin.productionMode=true
```
Linux:

```
java -jar marvel-explorer-app/target/marvel-explorer-app-1.0-FINAL.jar --marvel.api.publickey="xxx" --marvel.api.privatekey="xxx" --vaadin.productionMode=true
```
### Mocked
Windows:

```
java -jar marvel-explorer-app-mocked\target\marvel-explorer-app-mocked-1.0-FINAL.jar --vaadin.productionMode=true
```
Linux:

```
java -jar marvel-explorer-app-mocked/target/marvel-explorer-app-mocked-1.0-FINAL.jar --vaadin.productionMode=true
```