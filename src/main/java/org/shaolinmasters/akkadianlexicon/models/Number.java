package org.shaolinmasters.akkadianlexicon.models;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public class Number extends Word {
  // equals, hashcode
}
