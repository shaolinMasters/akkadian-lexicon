package org.shaolinmasters.akkadianlexicon.repositories;

import java.util.List;
import java.util.Optional;
import org.shaolinmasters.akkadianlexicon.models.User;
import org.shaolinmasters.akkadianlexicon.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepositoryI extends JpaRepository<User, Long> {
  @Query("""
    select u from User u inner join u.authorities authorities
    where u.id <> ?1 and authorities.role = ?2
    order by u.name""")
  List<User> findByIdNotAndAuthorities_RoleOrderByNameAsc(Long id, Role role);



  @Query("select u from User u where u.name = ?1")
  User findByName(String name);



  @Query("select u from User u where upper(u.email) = upper(?1)")
  Optional<User> findByEmailIgnoreCase(String email);

  @Query("select u from User u order by u.email")
  List<User> findByEmail();

}
