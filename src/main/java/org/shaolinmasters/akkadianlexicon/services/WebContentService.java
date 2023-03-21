package org.shaolinmasters.akkadianlexicon.services;

import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.models.WebContent;
import org.shaolinmasters.akkadianlexicon.repositories.ContentRepositoryI;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebContentService {

  private final ContentRepositoryI contentRepository;

  public WebContent getWebContent(String title) {
    return contentRepository.getWebContent(title);
  }
}
