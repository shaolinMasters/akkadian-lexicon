package org.shaolinmasters.akkadianlexicon.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shaolinmasters.akkadianlexicon.dtos.SourceDTO;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.repositories.SourceRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SourceService {

  private final SourceRepositoryI sourceRepository;

  private KingService kingService;

  @Autowired
  public void setKingService(@Lazy KingService kingService) {
    this.kingService = kingService;
  }

  public List<Source> findSourcesByKingName(String kingName) {
    // remove whitespaces etc.
    King king = kingService.findByNameIgnoreCase(kingName);
    List<Source> result = sourceRepository.findAllByKingOrderByTitleAsc(king);
    return result.isEmpty() ? List.of() : result;
  }

  @Transactional
  public void saveSource(SourceDTO sourceDTO) {
    King king = null;
    if (sourceDTO.getKingId() != null) {
      king = kingService.findKingById(sourceDTO.getKingId());
    }
    Source source =
      new Source(
        sourceDTO.getTitle(),
        sourceDTO.getCatalogueRef(),
        sourceDTO.getText(),
        king,
        sourceDTO.getBibliography());
    sourceRepository.save(source);
  }

  public Source findSourceByTitle(String title) {
    Optional<Source> result = sourceRepository.findByTitle(title);
    if (result.isPresent()) {
      return result.get();
    }
    throw new ResourceNotFoundException("Not found source with title: " + title);
  }

  public Source findSourceById(Long id) {
    Optional<Source> result = sourceRepository.findById(id);
    if (result.isPresent()) {
      return result.get();
    }
    throw new ResourceNotFoundException("Not found source with id :" + id);
  }

  public List<Source> listAllSourcesByTitleAsc() {
    return sourceRepository.findAllOrderByTitleAsc();
  }

  @Transactional
  public void deleteSourceById(Long id) {
    sourceRepository.deleteById(id);
  }

  public List<Source> listAllSourcesWithoutKingByTitleAsc() {
    return sourceRepository.findAllByKingIdNullOrderByTitleAsc();
  }

  public Map<Character, List<Source>> getSourcesMapGroupedByFirstLetter() {
    List<Source> sourceList = listAllSourcesByTitleAsc();
    return sourceList.stream().collect(Collectors.groupingBy(source -> source.getTitle().charAt(0), TreeMap::new, Collectors.toList()));
  }


}
