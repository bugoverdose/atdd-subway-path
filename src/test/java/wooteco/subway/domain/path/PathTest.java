package wooteco.subway.domain.path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import wooteco.subway.domain.section.Section;
import wooteco.subway.domain.station.Station;

@SuppressWarnings("NonAsciiCharacters")
class PathTest {

    private final Station STATION1 = new Station(1L, "역1");
    private final Station STATION2 = new Station(2L, "역2");
    private final Station STATION3 = new Station(3L, "역3");
    private final Station STATION4 = new Station(4L, "역4");
    private final Station STATION5 = new Station(5L, "역5");
    private final Station STATION6 = new Station(6L, "역6");
    private final Navigator<Station, Section> NAVIGATOR = new NavigatorJgraphtAdapter(List.of(
            new Section(1L, STATION1, STATION2, 1),
            new Section(1L, STATION2, STATION3, 100),
            new Section(2L, STATION2, STATION4, 2),
            new Section(3L, STATION2, STATION5, 100),
            new Section(1L, STATION3, STATION6, 6),
            new Section(2L, STATION4, STATION5, 3),
            new Section(3L, STATION5, STATION3, 5)));

    @Test
    void toStations_메서드는_시작점부터_목적지까지의_최단경로에_해당하는_지하철역들을_순서대로_제공() {
        Path path = getPathOf(STATION2, STATION3);

        List<Station> actual = path.toStations();
        List<Station> expected = List.of(STATION2, STATION4, STATION5, STATION3);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getDistance_메서드는_최단경로의_거리를_반환() {
        Path path = getPathOf(STATION2, STATION3);

        int actual = path.getDistance();
        int expected = 10;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getPassingLineIds_메서드는_최단거리의_구간들이_속해있는_모든_노선들의_id를_반환() {
        Path path = getPathOf(STATION2, STATION3);

        List<Long> actual = path.getPassingLineIds();
        List<Long> expected = List.of(2L, 3L);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 노선에_구간으로_등록되지_않은_역에_대한_경로를_조회하려는_경우_예외_발생() {
        Station nonRegisteredStation = new Station(999L, "등록되지 않은 역");
        Navigator<Station, Section> navigator = new NavigatorJgraphtAdapter(List.of(
                new Section(STATION1, STATION2, 10),
                new Section(STATION2, STATION3, 100),
                new Section(STATION3, STATION4, 20)));

        assertThatThrownBy(() -> getPathOf(STATION1, nonRegisteredStation, navigator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출발점과_도착점이_동일한_경우_예외_발생() {
        Navigator<Station, Section> navigator = new NavigatorJgraphtAdapter(List.of(
                new Section(STATION1, STATION2, 10)));

        assertThatThrownBy(() -> getPathOf(STATION1, STATION1, navigator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 도달할_수_없는_경로를_조회하려는_경우_예외_발생() {
        Navigator<Station, Section> navigator = new NavigatorJgraphtAdapter(List.of(
                new Section(STATION1, STATION2, 10),
                new Section(STATION3, STATION4, 20)));

        assertThatThrownBy(() -> getPathOf(STATION1, STATION3, navigator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private Path getPathOf(Station start, Station target, Navigator<Station, Section> navigator) {
        return new Path(start, target, navigator);
    }

    private Path getPathOf(Station start, Station target) {
        return getPathOf(start, target, NAVIGATOR);
    }
}
