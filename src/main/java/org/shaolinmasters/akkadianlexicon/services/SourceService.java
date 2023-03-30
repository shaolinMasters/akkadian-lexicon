package org.shaolinmasters.akkadianlexicon.services;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.dtos.SourceDTO;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.repositories.SourceRepositoryI;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceService {

  private final SourceRepositoryI sourceRepository;

  private final KingService kingService;

  public List<Source> findSourcesByKingName(String kingName) {
    // remove whitespaces etc.
    King king = kingService.findByNameIgnoreCase(kingName);
    List<Source> result = sourceRepository.findAllByKingOrderByTitleAsc(king);
    return result.isEmpty() ? List.of() : result;
  }
  @Transactional
  public void saveSource(SourceDTO sourceDTO) {
    King king = null;
    if(sourceDTO.getKingId() != null){
      king = kingService.findKingById(sourceDTO.getKingId());
    }
    Source source = new Source(sourceDTO.getTitle(), sourceDTO.getCatalogueRef(), sourceDTO.getText(), king, sourceDTO.getBibliography());
    sourceRepository.save(source);
  }

  public Source findSourceByTitle(String title) {
    Optional<Source> result = sourceRepository.findByTitle(title);
    if (result.isPresent()) {
      return result.get();
    }
    throw new ResourceNotFoundException("Not found source with title: " + title);
  }

  public List<Source> listAllSourcesByTitleAsc() {
    return sourceRepository.findByOrderByTitleAsc();
  }

  @Transactional
  public void deleteSourceById(Long id) {
    sourceRepository.deleteById(id);
  }

}
