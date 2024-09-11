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
'Create/Update Author -> POST http://localhost:8080/api/author'
'View All Authors -> GET http://localhost:8080/api/author'
'Delete Author -> DELETE http://localhost:8080/api/author/delete/{uuid}'
'Create/Update Speech -> POST http://localhost:8080/api/speech'
'View All Speeches -> GET http://localhost:8080/api/speech'
'Custom Seach Speeches -> GET http://localhost:8080/api/speech/custom-search'

# Following are the endpoints/feature added for APPROVING,ARCHIVING, moving back to draft a speech.
# APPROVED and ARCHIVED speeches cannot be updated. Only draft can be updated.
'Approve Speech -> POST http://localhost:8080/api/speech/approve'
'Archived Speech -> POST http://localhost:8080/api/speech/archive'
'Move to Draft Speech -> POST http://localhost:8080/api/speech/draft'

# Following are the endpoints for generating sample for Authors and Speech
'Generate Authors -> POST http://localhost:8080/api/author/generate-data/{int}'
'Generate Speeches -> POST http://localhost:8080/api/speech/generate-data/{int}'

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
'status' -> updated through workflow







