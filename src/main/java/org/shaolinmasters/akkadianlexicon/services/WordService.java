package org.shaolinmasters.akkadianlexicon.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.shaolinmasters.akkadianlexicon.dtos.NotVerbDTO;
import org.shaolinmasters.akkadianlexicon.dtos.VerbDTO;
import org.shaolinmasters.akkadianlexicon.models.*;
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

  public void saveVerb(VerbDTO verb) {

    Verb word = new Verb();
    VocabularyForm vocabularyForm = new VocabularyForm();

    vocabularyForm.setNominative(verb.getNominative());
    vocabularyForm.setSumerianForm(verb.getSumerianForm());
    vocabularyForm.setMeaning(verb.getMeaning());

    word.setVocabularyForm(vocabularyForm);
    word.setVerbalStem(verb.getVerbalStem());
    word.setVowelClass(verb.getVowelClass());

    wordRepository.save(word);
  }

  public void saveNoun(NotVerbDTO notVerb) {
    Noun word = new Noun();

    VocabularyForm vocabularyForm = new VocabularyForm();
    vocabularyForm.setMeaning(notVerb.getMeaning());
    vocabularyForm.setNominative(notVerb.getNominative());
    vocabularyForm.setSumerianForm(notVerb.getSumerianForm());

    word.setVocabularyForm(vocabularyForm);

    wordRepository.save(word);

  }

  public void saveAdjective(NotVerbDTO notVerb) {
    Adjective word = new Adjective();

    VocabularyForm vocabularyForm = new VocabularyForm();
    vocabularyForm.setMeaning(notVerb.getMeaning());
    vocabularyForm.setNominative(notVerb.getNominative());
    vocabularyForm.setSumerianForm(notVerb.getSumerianForm());

    word.setVocabularyForm(vocabularyForm);

    wordRepository.save(word);
  }

  public void savePronoun(NotVerbDTO notVerb) {
    Pronoun word = new Pronoun();

    VocabularyForm vocabularyForm = new VocabularyForm();
    vocabularyForm.setMeaning(notVerb.getMeaning());
    vocabularyForm.setNominative(notVerb.getNominative());
    vocabularyForm.setSumerianForm(notVerb.getSumerianForm());

    word.setVocabularyForm(vocabularyForm);

    wordRepository.save(word);
  }

  public void saveAdverb(NotVerbDTO notVerb) {
    Adverb word = new Adverb();

    VocabularyForm vocabularyForm = new VocabularyForm();
    vocabularyForm.setMeaning(notVerb.getMeaning());
    vocabularyForm.setNominative(notVerb.getNominative());
    vocabularyForm.setSumerianForm(notVerb.getSumerianForm());

    word.setVocabularyForm(vocabularyForm);

    wordRepository.save(word);
  }
}
