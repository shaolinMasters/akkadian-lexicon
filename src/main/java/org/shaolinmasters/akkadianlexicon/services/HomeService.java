package org.shaolinmasters.akkadianlexicon.services;

import org.shaolinmasters.akkadianlexicon.models.WebContents;
import org.shaolinmasters.akkadianlexicon.repositories.ContentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class HomeService {

  private final ContentRepository contentRepository;

  public HomeService(ContentRepository contentRepository) {
    this.contentRepository = contentRepository;
  }

  private final List<WebContents> aContent = new ArrayList<>();

  public WebContents findByTitle(String title) {
    return this.aContent.stream().filter(c -> Objects.equals(c.getTitle(), title)).findFirst().orElse(null);
  }
}
