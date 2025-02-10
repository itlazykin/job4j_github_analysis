![Build Status](https://github.com/itlazykin/job4j_github_analysis/actions/workflows/maven.yml/badge.svg)

# Spring Boot GitHub Data Analysis

Это Spring Boot приложение для анализа данных с GitHub. Оно регулярно собирает информацию о репозиториях и коммитах, используя GitHub API, и сохраняет эти данные в базе данных PostgreSQL.

## Технологии

- **Java 17**
- **Spring Boot**
- **PostgreSQL 16**
- **Spring Data JPA**
- **Liquibase** 
- **Maven 3.8**
- **JUnit** 

## Окружение

- **Java**: 17
- **PostgreSQL**: 16
- **Maven**: 3.8

## Функциональность

- Сбор данных: Приложение собирает информацию о репозиториях и коммитах с GitHub через API.
- Планировщик задач: Используется Spring Boot Scheduler для регулярного сбора данных.
- Асинхронность: Задачи выполняются асинхронно с помощью аннотации @Async.

## Установка и запуск

### 1. Клонировать репозиторий

```bash

git clone https://github.com/itlazykin/job4j_github_analysis
cd job4j_github_analysis
```
### 2. Запуск приложения
```bash

mvn spring-boot:run
```