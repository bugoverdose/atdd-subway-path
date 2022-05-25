package wooteco.subway.dto.response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import wooteco.subway.domain.line.LineMap;
import wooteco.subway.domain.station.Station;

public class LineResponse {

    private final Long id;
    private final String name;
    private final String color;
    private final int extraFare;
    private final List<StationResponse> stations;

    public LineResponse(Long id,
                        String name,
                        String color,
                        int extraFare,
                        List<StationResponse> stations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.stations = stations;
    }

    public static LineResponse of(LineMap lineMap) {
        Long lineId = lineMap.getId();
        String lineName = lineMap.getName();
        String lineColor = lineMap.getColor();
        int extraFare = lineMap.getExtraFare();
        List<StationResponse> stations = toStationResponse(lineMap.getSortedStations());
        return new LineResponse(lineId, lineName, lineColor, extraFare, stations);
    }

    private static List<StationResponse> toStationResponse(List<Station> stations) {
        return stations.stream()
                .map(station -> new StationResponse(station.getId(), station.getName()))
                .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
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

    public List<StationResponse> getStations() {
        return stations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineResponse that = (LineResponse) o;
        return extraFare == that.extraFare
                && Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(color, that.color)
                && Objects.equals(stations, that.stations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, extraFare, stations);
    }

    @Override
    public String toString() {
        return "LineResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", extraFare=" + extraFare +
                ", stations=" + stations +
                '}';
    }
}
