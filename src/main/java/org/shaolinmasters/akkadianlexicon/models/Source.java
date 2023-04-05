package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Source {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, name = "catalogue_ref")
  private String catalogueRef;

  @Lob
  @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
  private String text;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "king_id")
  @Exclude
  private King king;

  @Column(nullable = false)
  private String bibliography;

  @ManyToMany
  // @JoinTable not required, only included for explicit definition of names
  @JoinTable(
      name = "word_source",
      joinColumns = @JoinColumn(name = "source_id"),
      inverseJoinColumns = @JoinColumn(name = "word_id"))
  @Exclude
  private List<Word> words;

  public Source(String title, String catalogueRef, String text, King king, String bibliography) {
    this.title = title;
    this.catalogueRef = catalogueRef;
    this.text = text;
    this.king = king;
    this.bibliography = bibliography;
  }


  @PrePersist
  private void encodeText() {
    text = URLEncoder.encode(text.replace("\n", "%0A"), StandardCharsets.UTF_8);
  }

  @PostLoad
  private void decodeText() {
    text = URLDecoder.decode(text, StandardCharsets.UTF_8).replace("%0A", "\n");
  }

  // hashcode
  // equals

}
