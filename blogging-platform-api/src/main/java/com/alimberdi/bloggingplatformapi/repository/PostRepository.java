package com.alimberdi.bloggingplatformapi.repository;

import com.alimberdi.bloggingplatformapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("""
	SELECT p FROM Post p WHERE
		p.title ILIKE CONCAT('%', :term, '%') OR
		p.content ILIKE CONCAT('%', :term, '%') OR
		p.category ILIKE CONCAT('%', :term, '%')""")
	List<Post> searchByTerm(@Param("term") String term);

}
