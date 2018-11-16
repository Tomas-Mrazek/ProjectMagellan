package cz.jaktoviditoka.projectmagellan.nanoleaf.aurora.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Authorization {

    @JsonProperty("auth_token")
    private String authToken;

}
