package org.shaolinmasters.akkadianlexicon.services;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.shaolinmasters.akkadianlexicon.repositories.WebContentRepositoryI;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebContentService {

  private final WebContentRepositoryI webContentRepository;

  public WebContent findByTitle(String title) {
    Optional<WebContent> result = webContentRepository.findByTitle(title);
    if (result.isPresent()) {
      return result.get();
    }
    throw new ResourceNotFoundException("Not found content with title: " + title);
  }
}
