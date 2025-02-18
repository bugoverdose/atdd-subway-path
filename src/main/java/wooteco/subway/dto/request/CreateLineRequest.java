package wooteco.subway.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateLineRequest {

    @NotBlank(message = "노선의 이름이 입력되지 않았습니다.")
    private String name;

    @NotBlank(message = "노선의 색상이 입력되지 않았습니다.")
    private String color;

    @Min(value = 0, message = "추가 요금은 0원 이상이어야합니다.")
    private int extraFare;

    @NotNull(message = "노선의 상행역 정보가 입력되지 않았습니다.")
    private Long upStationId;

    @NotNull(message = "노선의 하행역 정보가 입력되지 않았습니다.")
    private Long downStationId;

    @Min(value = 1, message = "구간 간 거리는 최소 1이어야합니다.")
    private int distance;

    public CreateLineRequest() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getExtraFare() {
        return extraFare;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setExtraFare(int extraFare) {
        this.extraFare = extraFare;
    }

    public void setUpStationId(Long upStationId) {
        this.upStationId = upStationId;
    }

    public void setDownStationId(Long downStationId) {
        this.downStationId = downStationId;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "CreateLineRequest{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", extraFare=" + extraFare +
                ", upStationId=" + upStationId +
                ", downStationId=" + downStationId +
                ", distance=" + distance +
                '}';
    }
}
