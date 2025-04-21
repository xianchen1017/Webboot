package org.example.webboot.repository;

import org.example.webboot.dto.AuthorStatsDTO;
import org.example.webboot.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a, COUNT(ar) FROM Author a LEFT JOIN a.articles ar GROUP BY a")
    Page<Object[]> findAllWithArticleCount(Pageable pageable);

    @Query("SELECT new org.example.webboot.dto.AuthorStatsDTO(a.username, SIZE(a.articles)) " +
            "FROM Author a ORDER BY SIZE(a.articles) DESC")
    List<AuthorStatsDTO> findAuthorsWithArticleCount();

    Optional<Author> findById(Long id);  // 返回类型改为 Optional<Author>
}

