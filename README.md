# Anime API application

This repository contains a simple REST API application that provides anime information and allows to post critics.

## Table of Contents

- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)

## Introduction

This is a basic REST API application built using [Spring Boot](https://spring.io/projects/spring-boot) framework and [Maven](https://maven.apache.org). The application allows users to get information about sunset and sunrise time information for a coordinates by making HTTP requests to predefined endpoints.

## Technologies Used

- [Spring Boot](https://spring.io/projects/spring-boot): Web framework for building the REST API.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Data access framework for interacting with the database.
- [MySQL](https://www.mysql.com): Database for local and global use.
- [Jikan API](https://docs.api.jikan.moe/): External API for anime and manga information.

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 21
- Maven
- MySQL server
- MySQL driver
- JPA

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/infxrnx/AnimeCritics
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    java -jar target/SunsetSunriseAPI-0.0.1-SNAPSHOT.jar
    ```

The application will start on `http://localhost:8080`.

## Usage

### Endpoints

- **Get information about anime by title:**

  ```http
  GET /api/anime/TITLE
  ```

  Example:
  ```http
  GET /api/anime/naruto
  ```
- **Get critics by title:**

  ```http
  GET /api/getCritics?title=TITLE
  ```

  Example:
  ```http
  GET /api/getCritics?title=bleach
  ```
- **Post critics about specific anime:**

  ```http
  POST /api/postCritics?title=TITLE&text=TEXT
  ```

  Example:
  ```http
  POST /api/postCritics?title=naruto&text=nice
  ```
