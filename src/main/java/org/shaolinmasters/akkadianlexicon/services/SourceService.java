package org.shaolinmasters.akkadianlexicon.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
    if(sourceDTO.getKingId() != null) {
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
}
