package org.shaolinmasters.akkadianlexicon.services;

import org.shaolinmasters.akkadianlexicon.models.WebContents;
import org.shaolinmasters.akkadianlexicon.repositories.ContentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HomeService {

  private final ContentRepository contentRepository;

  public HomeService(ContentRepository contentRepository) {
    this.contentRepository = contentRepository;
  }

  public List<WebContents> findByTitle(Optional<String> title) {
    List<WebContents> result = new ArrayList<>();
    if (title.isPresent()) {
      result = contentRepository.findByTitle(title.get());
    }
    return result.isEmpty() ? List.of() : List.copyOf(result);
  }
}
