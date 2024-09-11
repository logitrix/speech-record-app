Development: Wednesday, Septer 10, 2024

Spring Boot + Spring Web + Spring JPA on Java 17

## Tools
Java 17

Apache Maven 3.8.7

## Usage

```java
# After cloning the repo build the speech-record-app using maven
'mvn clean install'

# When successfully build, run the following command
'mvn spring-boot:run' or 'java -jar target/speech-record-app-1.jar'

# When successfully started, access application through 'http://localhost:8080/api/'
# Import the file App.postman_collection.json into Postman as Collection(v2) for the endpoints

# Following are the endpoints available
'Create/Update Author'
'View All Authors'
'Delete Author'
'Create/Update Speech'
'View All Speeches'
'Custom Seach Speeches'
'Generate Authors (For Sample Data)'
'Generate Speeches (For Sample Data)'

# Two main Entities in this Application
'Author' one author can have many speech
'Speech' one speech can only have one author

# Author
'firstname' -> required
'middlename' -> optional
'lastname' -> required
'profession' -> optional
'email' -> required and unique
'mobile' -> required and unique

# Speech
'subject' -> required and unique
'contents' -> 1000 characters
'author' -> required







