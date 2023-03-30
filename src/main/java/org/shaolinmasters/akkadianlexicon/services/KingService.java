package org.shaolinmasters.akkadianlexicon.services;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.dtos.KingDTO;
import org.shaolinmasters.akkadianlexicon.exceptions.ResourceNotFoundException;
import org.shaolinmasters.akkadianlexicon.models.King;
import org.shaolinmasters.akkadianlexicon.models.Source;
import org.shaolinmasters.akkadianlexicon.repositories.KingRepositoryI;
import org.shaolinmasters.akkadianlexicon.utils.YearAttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KingService {

  private final KingRepositoryI kingRepository;

  private SourceService sourceService;

  private final YearAttributeConverter yearAttributeConverter;

  @Autowired
  public void setSourceService(@Lazy SourceService sourceService) {
    this.sourceService = sourceService;
  }

  public List<King> findAllKingsOrderByRegnalYearFromAscNameAsc() {
    return kingRepository.findAllOrderByRegnalYearFromAscNameAsc();
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

  @Transactional
  public void saveKing(KingDTO kingDTO) {
    if (!(kingDTO.getSourceId() == 0)) {
      Source source = sourceService.findSourceById(kingDTO.getSourceId());
      King king =
        new King(
          kingDTO.getKingName(),
          yearAttributeConverter.convertToEntityAttribute(Short.parseShort(kingDTO.getRegnalYearFrom())),
          yearAttributeConverter.convertToEntityAttribute(Short.parseShort(kingDTO.getRegnalYearTo())),
          source);
      source.setKing(king);
      kingRepository.save(king);
    } else {
      King king =
        new King(
          kingDTO.getKingName(),
          yearAttributeConverter.convertToEntityAttribute(Short.parseShort(kingDTO.getRegnalYearFrom())),
          yearAttributeConverter.convertToEntityAttribute(Short.parseShort(kingDTO.getRegnalYearTo())),
          null);
      kingRepository.save(king);
    }


  }

  @Transactional
  public void deleteKingById(Long id) {
    kingRepository.deleteById(id);
  }
}
