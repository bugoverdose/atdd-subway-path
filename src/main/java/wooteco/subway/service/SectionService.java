package wooteco.subway.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.subway.domain.section.Section;
import wooteco.subway.domain.section.Sections;
import wooteco.subway.domain.section.SectionUpdates;
import wooteco.subway.domain.section.SectionsManager;
import wooteco.subway.domain.station.Station;
import wooteco.subway.dto.request.CreateSectionRequest;
import wooteco.subway.repository.StationRepository;
import wooteco.subway.repository.SectionRepository;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final StationRepository stationRepository;

    public SectionService(SectionRepository sectionRepository, StationRepository stationRepository) {
        this.sectionRepository = sectionRepository;
        this.stationRepository = stationRepository;
    }

    @Transactional
    public void save(Long lineId, CreateSectionRequest request) {
        SectionsManager sectionsManager = new SectionsManager(sectionRepository.findAllSectionsByLineId(lineId));
        Station upStation = stationRepository.findExistingStation(request.getUpStationId());
        Station downStation = stationRepository.findExistingStation(request.getDownStationId());
        Section newSection = new Section(upStation, downStation, request.getDistance());
        Sections updatedSections = sectionsManager.save(newSection);

        updateSectionChanges(sectionsManager, updatedSections, lineId);
    }

    @Transactional
    public void delete(Long lineId, Long stationId) {
        SectionsManager sectionsManager = new SectionsManager(sectionRepository.findAllSectionsByLineId(lineId));
        Sections updatedSections = sectionsManager.delete(stationRepository.findExistingStation(stationId));

        updateSectionChanges(sectionsManager, updatedSections, lineId);
    }

    private void updateSectionChanges(SectionsManager oldSectionsManager, Sections updatedSections, Long lineId) {
        SectionUpdates compareResult = oldSectionsManager.compareDifference(updatedSections);
        sectionRepository.deleteSections(lineId, compareResult.getOldSections());
        sectionRepository.saveSections(lineId, compareResult.getNewSections());
    }
}
