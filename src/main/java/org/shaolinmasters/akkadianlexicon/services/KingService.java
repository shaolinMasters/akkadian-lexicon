package org.shaolinmasters.akkadianlexicon.services;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.repositories.KingRepositoryI;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KingService {

  private final KingRepositoryI kingRepository;

  public King findByName (String name) {
    Optional<King> result = kingRepository.findByName(name);
    if(result.isPresent()){
      return result.get();
    }
    throw new RuntimeException("Not found king with name: " + name);
  }
}
