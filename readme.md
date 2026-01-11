# TODO LISTS

What you need to do after you clone the template

1. Create a `.env` file
```javascript
// .env file content
ACCESS_TOKEN=<Input your access token here>
REFRESH_TOKEN=<Input your refresh token here>
```

2. Set some system enviroment variable(`baseURI`,..etc) at `src/test/resources/config.properties`

3. Implement the [Rest Assured](https://github.com/rest-assured/rest-assured/wiki/Usage) default to use the`baseURI` in `src\test\resources\config.properties` as the default baseURI

```
Implement in the baseAPI class
src\test\java\org\QAHexagon\testng\api\baseAPI\baseAPI.java
```

4. Implement the login API at `src\test\java\org\QAHexagon\testng\api\authAPI\loginAPI.java`

5. Implement your API of choice in the `src\test\java\org\QAHexagon\testng\api` folder

6. Design your testcase (using testNG) in `src\test\java\org\QAHexagon\testng\tests` folder

# Allure Report

Generate report

```
allure generate target\allure-results
```

View Report
```
allure open reports\allure
```