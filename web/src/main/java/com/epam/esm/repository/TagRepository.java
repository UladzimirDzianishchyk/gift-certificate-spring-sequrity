package com.epam.esm.repository;

import com.epam.esm.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameIgnoreCase(String name);
    Page<Tag> findByNameContainingIgnoreCase(String name, Pageable pageable);
    @Query(value = "select id, name from (select sum(o.price) as price, t.id, t.name" +
            "         from orders as o" +
            "                  join gift_certificate_tag as gct on gct.gift_certificate_id=o.gift_certificate_id" +
            "                  join tag as t on gct.tag_id=t.id" +
            "         where o.user_id=:userId" +
            "         group by o.price, t.id" +
            "         order by price desc limit 1) as r",nativeQuery = true)
    Optional<Tag> findMostPopularEndExpensiveTagByUserId(@Param("userId") Long userId);
}
