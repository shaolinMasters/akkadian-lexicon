package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "web_contents")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class WebContents {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Lob
  @Column(nullable = false, columnDefinition = "varchar(20)")
  private String content;
  @Lob
  @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
  private String title;

  // hashcode
  // equals
}
