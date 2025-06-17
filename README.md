# Automation In Testing
Maven + Selenium + Cucumber + Allure Reports UI tests written in Java.

## Precondition
### Tech stack
- Maven (latest)
- Java (8+)
### Allure report install
- https://docs.qameta.io/allure/#_get_started

## Run
### Maven parameters:
```shell
mvn clean test -Dbrowser=CHROME -Dheadless=false "-Dcucumber.filter.tags=@debug"
```

### Allure report:
- Running Allure reports (if installed, or plugin added to Jenkins) with command `allure:serve` at the end of the Maven execution command
- To generate allure report run ` allure serve ./ui/allure-results`
