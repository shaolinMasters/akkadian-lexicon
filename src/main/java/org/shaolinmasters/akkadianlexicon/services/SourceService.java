package org.shaolinmasters.akkadianlexicon.services;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.repositories.SourceRepositoryI;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SourceService {
  private final SourceRepositoryI sourceRepository;

    public Source findSourceByTitle (String title){
      Optional<Source> result = sourceRepository.findByTitle(title);
      if (result.isEmpty()) {
        throw new RuntimeException("The text with the title: " + title + " is not found in the database.");
      }
      return result.get();
    }

    public List<String> listAllSourcesByTitle() {
      return sourceRepository.findByOrderByTitleAsc();
    }

  }
