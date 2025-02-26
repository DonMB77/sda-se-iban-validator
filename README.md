# Project Name: sda-se-iban-validator
 IBAN validator service as per code challenge by SDA SE.

This service is able to receive both local file paths and URL's to firstly scan for PDF files and then scan those files for any IBAN's.
It will then check those IBAN's against blacklisted IBAN's stored within a database and respond with a suitable response on whether blacklisted IBAN's where present.

# Installation And Setup Guide:

# Dependencies and Libraries:
This project uses Maven as it's build tool to build the application and manage its dependencies. Here I would like to go into further detail why certain dependencies and libraries have been used.

This is a starter enabling for quick and easy implementation of web but also RESTful application using Spring MVC. It is a very convenient library to quickly set up a fully functioning application. It is here used to provide the general structure of the project.

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

There can be no good service or application without test. This dependency is the Spring Boot Starter implementing testing using JUnit Jupiter, Hamcrest and Mockito.

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

This service uses an H2 Database as an in-memory database. This means that all data stored is lost upon restart of the application. I've decided since that this is sufficient for the purpose of this project. It also simplified the setup and installation process. 

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