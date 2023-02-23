package cnu.swabe.v2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StyleSearchRequestDTO {
    private String hatColor;
    private String topColor;
    private String pantsColor;
    private String shoesColor;
    private String gender;
    private String sort;
}
