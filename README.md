# SurveyWebService
Web service for fillinha survey for personal health.
	The survey consists of the following questions:
	How was your sleep last night (range: 0(bad) - 10(good))
	How good is your skin condition (range: 0(bad) - 10(good))

Implemented with following technologies: Spring boot maven, Sprig data jpa(hibernate), Swagger 3 open api ,junit 5 and java 11.
for runnig you should have installed maven 3.65 and java 11. Or run through your preferred ide without maven installed.
for using the api and api documentation  and specification visit : http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/survey-controller
For simplicity embeeded H2 in memmory data base was used. For configuring any other sql database jsut change the:
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver 
int application.properties file.
