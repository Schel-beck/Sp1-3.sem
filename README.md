**Sp-1 3.sem**

Et program som henter og håndterer danske film fra de sidste 5 år. Programmet henter skuespiller, director, genre og information om hver film.
Programmet har disse funktionaliteter
The database should contain all Danish movies (orginates from Denmark) from the TMDb API that has been released in the last 5 years. So just recent Danish movies. It should be around 1661 movies in total (give and take a few hundred).
We would like to be able to see a list of all movies pulled from the database.
Each movie has a list of actors and a director. We would like to be able to see a list of all actors and directors as well that have been part of those movies. You need to figure out how to fetch and store the actors and directors in the database. Also, what kind of relationship should there be between the entities?
Each movie has a list of genres. We would like to be able to see a list of all genres as well. Also be able to list all movies within a particular genre. You need to figure out how to fetch and store the genres in the database. Also, what kind of relationship should there be between the entities?
In case you want to add a new movie to the database, you should be able to do that as well. You should also be able to update and delete movies from the database. Not necessarily all fields, but at least the title and the release date.
We would like to be able to search for a movie by title. The search should be case insensitive and should return all movies that contain the search string in the title.
We would like to be able to get the total average rating of all movies in the database, the top-10 lowest and highest rated movies, and the top-10 most popular movies.
We would like to be able to see a list of all movies that a particular actor has been part of.
We would like to be able to see a list of all movies that a particular director has directed.

Måden dataen kommer over i databasen er via et stort while loop i main, som fortsætter indtil alle pages er loopet igennem.
Der bliver brugt DAO metoder til at få sende entiteters data ind i postgres. Disse DAO metoder er testet.
