package app.service;
import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Movie;
import app.persistence.ActorDAO;
import app.persistence.DirectorDAO;
import app.persistence.MovieDAO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiReader {

    private static final String API_KEY = System.getenv("api_key");


    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createUrlForMovie(int page) {
        return "https://api.themoviedb.org/3/discover/movie"
                + "?include_adult=false"
                + "&include_video=false"
                + "&language=en-US"
                + "&page=" + page
                + "&release_date.gte=2021-09-15"
                + "&release_date.lte=2026-09-15"
                + "&sort_by=popularity.desc"
                + "&with_origin_country=DK"
                + "&api_key=" + API_KEY;
    }
    public String createCreditsUrl(int movieId) {
        return "https://api.themoviedb.org/3/movie/"
                + movieId
                + "/credits"
                + "?language=en-US"
                + "&api_key=" + API_KEY;
    }
    public String createGenreUrl() {
        return "https://api.themoviedb.org/3/genre/movie/list?api_key="
                + API_KEY;
    }

    public String readAPI(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "GET request failed. Status code: " + response.statusCode()
                );
            }

            return response.body();

        } catch (Exception e) {
            throw new RuntimeException("Error calling API", e);
        }
    }

    public <T> T convertFromJson(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON", e);
        }
    }





}