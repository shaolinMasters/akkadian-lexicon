package org.shaolinmasters.akkadianlexicon.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.repositories.SourceRepositoryI;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SourceService {

  private final SourceRepositoryI sourceRepository;

  public List<Source> findSourcesByKingName(String name) {
    // remove whitespaces etc.
    List<Source> result = sourceRepository.findByKingNameIgnoreCaseOrderByTitleAsc(name);
    return result.isEmpty() ? List.of() : result;
  }

  public Source findSourceByTitle(String sourceTitle) {
    return sourceRepository.findByTitle(sourceTitle);
  }

  public List<Source> listAllSourcesByTitleAsc() {
    return sourceRepository.findByOrderByTitleAsc();
  }
}
