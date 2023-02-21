package cnu.swabe.v2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StyleRequestDTO {
    private String hatColor;
    private String topColor;
    private String pantsColor;
    private String shoesColor;
    @JsonProperty(value = "isMale")
    private boolean male;
    private boolean isOrderByLikeNum;
}
