package org.shaolinmasters.akkadianlexicon.services;

import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.repositories.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceService {

  @Autowired
  private SourceRepository sourceRepository;

  public List<Source> listAll(String keyword) {
    if (keyword != null) {
      return sourceRepository.search(keyword);
    }
    return sourceRepository.findAll();
  }
}
