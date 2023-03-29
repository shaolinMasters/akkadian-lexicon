package org.shaolinmasters.akkadianlexicon.services;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
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

  public King findKingById(Long id) {
    Optional<King> result = kingRepository.findById(id);
    if (result.isPresent()) {
      return result.get();
    }
    throw new ResourceNotFoundException("Not found king with id :" + id);
  }

  public King findByNameIgnoreCase(String name) {
    Optional<King> result = kingRepository.findByNameIgnoreCase(name);
    if (result.isPresent()) {
      return result.get();
    }
    throw new ResourceNotFoundException("Not found king with name: " + name);
  }
}
