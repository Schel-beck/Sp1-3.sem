package app.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditsResponseDTO {

    private int id;
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
}
