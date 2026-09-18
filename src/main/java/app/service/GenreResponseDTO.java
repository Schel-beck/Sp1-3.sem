package app.service;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GenreResponseDTO {

    private List<GenreDTO> genres;
}