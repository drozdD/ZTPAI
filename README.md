# Dokumentacja Projektu ZTPAI

Projekt to prosta aplikacja backendowa REST API stworzona w technologii Spring Boot i języku Java. Umożliwia zarzadzanie bazą produktów oraz realizuje bezpieczne logowanie i autoryzację za pomocą tokenów JWT (JSON Web Tokens).

## Wykorzystane technologie

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* H2 Database (baza danych w pamięci)
* JJWT (biblioteka do obsługi tokenów JWT)

## Architektura projektu

Projekt jest podzielony na standardowe warstwy:

* model - encje bazodanowe (User, Product)
* repository - interfejsy do komunikacji z bazą danych
* service - warstwa logiki biznesowej
* dto - klasy transferu danych (Request/Response) oraz mapper
* controller - punkty wejścia API (kontrolery)
* security - filtry i konfiguracja bezpieczeństwa aplikacji

## Autoryzacja żądań

Aplikacja korzysta z bezstanowej sesji. Aby uzyskać dostęp do chronionych ścieżek, należy do nagłówka zapytania dodać klucz i wartość:

* Klucz: Authorization
* Wartość: Bearer <TOWJ_TOKEN_JWT>

## Endpointy API

### Logowanie i rejestracja (ścieżka /api/auth)

* POST /api/auth/register - Rejestracja nowego użytkownika (publiczny)
* POST /api/auth/login - Logowanie użytkownika (publiczny), zwraca token JWT

### Produkty (ścieżka /api/products)

* GET /api/products - Pobranie listy produktów (dostępne dla USER oraz ADMIN)
* GET /api/products/{id} - Pobranie szczegółów produktu o danym ID (dostępne dla USER oraz ADMIN)
* POST /api/products - Dodanie nowego produktu (dostępne tylko dla ADMIN)
* DELETE /api/products/{id} - Usunięcie produktu o danym ID (dostępne tylko dla ADMIN)

## Instrukcja uruchomienia

Przy pierwszym uruchomieniu aplikacja automatycznie zakłada konto administratora o poniższych danych:

* Login: admin
* Hasło: admin123

Aby uruchomić aplikację lokalnie, wykonaj w terminalu w głównym folderze projektu polecenie:

.\mvnw.cmd spring-boot:run

## Testy

Projekt posiada zestaw testów jednostkowych i integracyjnych (wykorzystujących JUnit oraz Mockito) weryfikujących poprawność działania logiki biznesowej (np. poprawność walidacji tworzenia produktów).

Aby uruchomić testy, wykonaj w głównym folderze projektu polecenie:

.\mvnw.cmd test
