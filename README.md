# SP-1 – 3. semester

## Beskrivelse

Dette projekt er et program, der henter og håndterer information om danske film fra [The Movie Database (TMDb)](https://www.themoviedb.org/).

Programmet henter danske film, der er udgivet inden for de seneste 5 år, og gemmer informationerne i en PostgreSQL-database.

For hver film gemmes blandt andet:

* Titel
* Udgivelsesdato
* Rating
* Popularitet
* Skuespillere
* Instruktør
* Genrer

Formålet med projektet er at arbejde med REST API'er, databaser, relationer mellem entiteter samt CRUD- og søgefunktionalitet.

---

## Funktionalitet

Programmet understøtter følgende funktioner:

### Film

* Hente danske film fra TMDb API'et.
* Gemme filmene i PostgreSQL-databasen.
* Se en liste over alle film i databasen.
* Tilføje nye film.
* Opdatere film.
* Slette film.
* Søge efter film ud fra titel.

  * Søgningen er case-insensitive.
  * Søgningen finder alle film, hvor titlen indeholder den angivne søgestreng.

### Skuespillere og instruktører

Hver film har en liste af skuespillere og en instruktør.

Programmet kan derfor:

* Se en liste over alle skuespillere.
* Se en liste over alle instruktører.
* Se alle film, som en bestemt skuespiller har medvirket i.
* Se alle film, som en bestemt instruktør har instrueret.

Skuespillere og instruktører bliver hentet fra TMDb og gemt i databasen sammen med deres relationer til filmene.

### Genrer

Hver film kan have flere genrer.

Programmet kan derfor:

* Se en liste over alle genrer.
* Se alle film inden for en bestemt genre.

Genrer bliver hentet fra TMDb og gemt i databasen med en relation til de film, de tilhører.

### Statistik

Programmet kan beregne og vise:

* Den gennemsnitlige rating for alle film i databasen.
* De 10 højest ratede film.
* De 10 lavest ratede film.
* De 10 mest populære film.

---

## Data fra TMDb

Programmet bruger TMDb API'et som ekstern datakilde.

Der hentes kun film, som:

* Har Danmark (`DK`) som oprindelsesland.
* Er udgivet inden for de seneste 5 år.
* Ikke er markeret som voksenindhold.

Datoen for de seneste 5 år beregnes dynamisk med `LocalDate`, så programmet altid arbejder med et rullende 5-års interval.

Datamængden forventes at være omkring 1.661 film, men det præcise antal kan variere afhængigt af dataene i TMDb.

---

## Import af data

Dataene bliver hentet fra TMDb API'et gennem et stort `while`-loop i programmets `main`-metode.

Programmet fortsætter med at hente sider fra API'et, indtil alle relevante pages er gennemgået.

For hver film bliver relevante oplysninger hentet og gemt i databasen. Dette inkluderer også filmens:

* Skuespillere
* Instruktør
* Genrer

Dataene bliver sendt til PostgreSQL gennem DAO-metoder.

DAO-metoderne er testet for at sikre, at data bliver korrekt gemt og hentet fra databasen.

---

## Database og relationer

Projektet bruger PostgreSQL som database.

Filmens relationer til de øvrige entiteter håndteres i databasen, så den samme skuespiller, instruktør eller genre kan genbruges på tværs af flere film.

De centrale entiteter er:

```text
Movie
Actor
Director
Genre
```

En film kan have flere skuespillere og flere genrer, mens en skuespiller kan medvirke i flere film.

Dette giver blandt andet følgende relationer:

```text
Movie  <--->  Actor
Movie  <--->  Genre
Movie  --->   Director
```

Relationerne gør det muligt at lave opslag som:

* Alle film med en bestemt skuespiller.
* Alle film instrueret af en bestemt instruktør.
* Alle film inden for en bestemt genre.

---

## Teknologier

Projektet benytter blandt andet:

* **Java**
* **PostgreSQL**
* **TMDb API**
* **REST API**
* **DAO (Data Access Object)**
* **LocalDate**
* **JUnit** til test af DAO-metoder

---

## Projektets formål

Projektet kombinerer arbejdet med en ekstern REST API og en relationel database.

Vi arbejder blandt andet med:

* API-integration
* JSON-data
* Databasehåndtering
* DAO-pattern
* Relationer mellem entiteter
* CRUD-operationer
* Søgefunktionalitet
* Statistik og SQL-queries
* Unit tests

Projektet giver dermed mulighed for at arbejde med hele flowet fra ekstern API til database og videre til behandling og præsentation af data.

