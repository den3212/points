REST сервис для ведения базы точек.

Используются следующие библиотеки:
 Jersey - для создания REST сервиса, 
 EclipseLink - для работы с БД, 
 Bootstrap - верстка,
 jQuery - javascript,
 Freemarker - для работы с шаблонами страниц.

Для работы необходим PostgreSql (хост localhost, порт 5432, база данных Points).

Для сборки проекта использовался maven.

Для тестов сервис разворачивался на Jetty (maven-jetty-plugin).
