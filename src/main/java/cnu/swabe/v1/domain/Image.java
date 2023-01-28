package cnu.swabe.v1.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Image {
    private int imageNo;
    private int userNo;
    private String location;
    private String hatColor;
    private String topColor;
    private String pantsColor;
    private String shoesColor;

    public Image() {
    }

    public Image(int imageNo, int userNo, String location, String hatColor, String topColor, String pantsColor, String shoesColor) {
        this.imageNo = imageNo;
        this.userNo = userNo;
        this.location = location;
        this.hatColor = hatColor;
        this.topColor = topColor;
        this.pantsColor = pantsColor;
        this.shoesColor = shoesColor;
    }
}
