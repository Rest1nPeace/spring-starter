package com.dmdev.spring.database.repository;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.SimpleTimeZone;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllBy(String firstname, String lastname);

    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",
    nativeQuery = true)
    List<User> findByAllByUsername(SimpleTimeZone username);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update User u set u.role = :role where u.id in (:ids)")
    int updateRole(Role role, Long... ids);


    Optional<User> findTopByOrderByIdDesc();

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<User> findTop3ByBirthDateBefore(LocalDate localDate, Sort sort);

    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(value = "select u from User u",
    countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);
}
