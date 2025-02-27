# Project Name: sda-se-iban-validator
 IBAN validator service as per code challenge by SDA SE.

This service is able to receive both local file paths and URL's to firstly scan for PDF files and then scan those files for any IBAN's.
It will then check those IBAN's against blacklisted IBAN's stored within a database and respond with a suitable response on whether blacklisted IBAN's where present.

It is a RESTful-API service and uses generic HTTP methods to interact with all resources. Below the installation and setup guide there is a documentation including all HTTP methods possible, together with example requests and possible responses. Spring Boot was used mainly together with a handful of different libraries. This project was deliberately kept as focused on the task at hand as possible, and therefor lightweight. 
# Installation And Setup Guide:
The setup and installation processes has been deliberately kept as simple as possible. The application is build using Java JDK 21.

It is sufficient to just download this repository of GitHub and running it on any local system. The dependencies have been chosen in a away, that no further application or container technology is needed. Maven handles any dependencies and plugins.

# Usage

I personally used Postman for interacting with the service. But any number of tools can be used.
## HTTP Requests:

### POST local PDF to be scanned for IBAN's:
Endpoint:
``http://localhost:8080/api/v1/pdfImportLocal``

Example Request:\
``{
  "url": "C:/Users/exampleUser/exampleFolder/Testdata_Invoices.pdf"
}
``
Beware that the url has to be in a UNIX-like format!!

Response: 
- ``200 OK`` in case no blacklisted IBAN'S are found
- ``400 BAD REQUEST`` in case blacklisted IBAN's are found
### POST online PDF to be scanned for IBAN's:
Endpoint:
``http://localhost:8080/api/v1/pdfImportOnline``

Example Request:\
``{
  "url": "http://www.example.com/exampleInvoice.pdf"
}
``

Response:
- ``200 OK`` in case no blacklisted IBAN'S are found
- ``400 BAD REQUEST`` in case blacklisted IBAN's are found
### DELETE blacklisted Iban's from repository:
Endpoint:
``http://localhost:8080/api/v1/blacklistedIban/{id}``

Response:
- ``200 OK`` after successful deletion
- ``404 NOT FOUND`` in case resource was not found

### POST new blacklisted Iban's to repository:
Endpoint:
``http://localhost:8080/api/v1/blacklistedIban``

Example Request:\
``{
  "iban": "DE55 5555 5555 5555 5555 55"
}
``

Response:
- ``200 OK`` after successful deletion

### GET all saved blacklisted IBAN's:
Endpoint:
``http://localhost:8080/api/v1/blacklistedIban``

Example Request:\
``[
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "iban": "string"
  },
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "iban": "string"
  }
]
``

Response:
- ``200 OK`` after successful listing

# Dependencies and Libraries:
This project uses Maven as it's build tool to build the application and manage its dependencies. Here I would like to go into further detail why certain dependencies and libraries have been used.

This is a starter enabling for quick and easy implementation of web but also RESTful applications using Spring MVC. It is a very convenient library to quickly set up a fully functioning application. It is here used to provide the general structure of the project.

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

This is a starter for using Java Bean Validation with Hibernate Validation. Used for quick and easy validation in conjunction with hibernate.

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>

This project uses Spring Data JPA for easy implementation of fully ready JPA-based repositories.

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

I've debated with using Project Lombok for a bit. I then choose to still utilize it. It helps with implementing a chunk of ceremonial code. Considering the size and functionality of this project, there is virtually no downside to using Lombok.

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

There can be no good service or application without tests. This dependency is the Spring Boot Starter implementing testing using JUnit Jupiter, Hamcrest and Mockito.

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

This service uses an H2 Database as an in-memory database. This means that all data stored is lost upon restart of the application. I've decided that this is sufficient for the purpose of this project. It also simplified the setup and installation process. 

		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>

Apache PDFBox library is an open source Java tool for working with PDF documents in many different aspects. We here use it for processing the text out of imported PDF's.

		<dependency>
			<groupId>org.apache.pdfbox</groupId>
			<artifactId>pdfbox</artifactId>
			<version>2.0.4</version>
		</dependency>

This library helps to generate API documentation. This automatically generated documentation in JSON/YAML and HTML.

		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.8.4</version>
		</dependency>

# Possible Areas Of Improvement:

### Authentication:
This application does not use any kind of authentication. For any kind of actual production usage, this service should include at least some sort of basic authentication.

### Implementation of another database instead of H2:
H2 here is used as an in-memory database. It might be prudent to implement another database in the future, if the properties that come with an in-memory database are not suitable.

### Implementation of multiple Invoice design structures:
This service currently assumes that all incoming invoices are of the same general structure as given per the example invoices included with this challenge. It could be extended to accept other markups, such as as the IBAN keyword that is currently scanned for in any incoming invoice. If the IBAN keyword within the PDF is on another line, the service might struggle with being able to scan and extract it.

### Front-End:
This application is purely a RESTful-API service. There is no Front-End for clients to interact with. This could be added in the future, to make interaction with this service more convenient.