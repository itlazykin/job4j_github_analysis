![Build Status](https://github.com/itlazykin/job4j_github_analysis/actions/workflows/maven.yml/badge.svg)

# Spring Boot GitHub Data Analysis

Это Spring Boot приложение для анализа данных с GitHub, включая извлечение коммитов из репозиториев и хранение их в базе
данных PostgreSQL. Приложение использует Liquibase для управления схемой базы данных и настроено для регулярного
выполнения задач с помощью планировщика.

## Технологии

- **Java 17**
- **Spring Boot** (с использованием Spring Data, Spring Scheduling, и Spring JPA)
- **PostgreSQL** — для хранения данных.
- **Liquibase** — для миграций базы данных.
- **JDBC** — для работы с базой данных.
- **RestTemplate** — для взаимодействия с GitHub API.

## Окружение

- **Java**: 17
- **PostgreSQL**: 16
- **Maven**: 3.8

## Функциональность

- Регулярное извлечение коммитов из репозиториев на GitHub.
- Хранение информации о репозиториях и коммитах в базе данных.
- Автоматическое применение изменений в схеме базы данных с использованием Liquibase.

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

#### Контакты для связи:
* Лазыкин Денис Андреевич;
* +7 926 888 23 28;
* @slimdenchi