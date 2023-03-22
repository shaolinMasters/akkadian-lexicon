package org.shaolinmasters.akkadianlexicon.services;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.Word;
import org.shaolinmasters.akkadianlexicon.repositories.WordRepositoryI;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WordService {

  private final WordRepositoryI wordRepository;


  public Word loadWordByNominative(String word) {


    Optional<Word> optionalWord = wordRepository.getWordByNominative(word);

    if (optionalWord.isEmpty()) {
      throw new ResourceNotFoundException("Resource not found: " + word);

    }

    return optionalWord.get();
  }
}
