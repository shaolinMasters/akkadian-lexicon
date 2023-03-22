package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "web_content")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class WebContent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Lob
  @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
  private String content;

  // hashcode
  // equals
}
