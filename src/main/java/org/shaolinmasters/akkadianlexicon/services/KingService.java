package org.shaolinmasters.akkadianlexicon.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.repositories.KingRepositoryI;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KingService {

  private final KingRepositoryI kingRepository;

  public List<King> findAllKings() {
    List<King> kings = kingRepository.findAllOrderByRegnalYearFromAscNameAsc();
    return kings.isEmpty() ? List.of() : kings;
  }

}
