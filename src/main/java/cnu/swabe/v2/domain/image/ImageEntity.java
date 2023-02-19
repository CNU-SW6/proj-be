package cnu.swabe.v2.domain.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageEntity {
    private int no;
    private int userNo;
    private String location;
    private String hatColor;
    private String topColor;
    private String pantsColor;
    private String shoesColor;

    public ImageEntity() {
    }

    public ImageEntity(int userNo, String location, String hatColor, String topColor, String pantsColor, String shoesColor) {
        this.userNo = userNo;
        this.location = location;
        this.hatColor = hatColor;
        this.topColor = topColor;
        this.pantsColor = pantsColor;
        this.shoesColor = shoesColor;
    }
}
