package org.shaolinmasters.akkadianlexicon.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.shaolinmasters.akkadianlexicon.repositories.WordRepositoryI;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordService {

  private final WordRepositoryI wordRepository;

  public List<Word> findWordsByNominative(String word) {
    List<Word> result =
        wordRepository.findAllByNominativeContainsIgnoreCaseOrderByNominativeAsc(word);
    return result.isEmpty() ? List.of() : result;
  }
}
