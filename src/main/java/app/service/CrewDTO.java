
package app.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class CrewDTO {

    private int id;

    @JsonProperty("original_name")
    private String originalName;

    private String job;

    private String department;

    @JsonProperty("credit_id")
    private String creditId;


}

