package com.alimberdi.bloggingplatformapi.service;

import com.alimberdi.bloggingplatformapi.dto.PostCreateRequest;
import com.alimberdi.bloggingplatformapi.dto.PostUpdateRequest;
import com.alimberdi.bloggingplatformapi.entity.Post;
import com.alimberdi.bloggingplatformapi.exception.ResourceNotFoundException;
import com.alimberdi.bloggingplatformapi.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public Post getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    public List<Post> getAll() {
        return repository.findAll();
    }

    public List<Post> getAllByFilter(String term) {
        return repository.searchByTerm(term);
    }

    public Post create(PostCreateRequest request) {
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .category(request.category())
                .tags(request.tags())
                // createdAt and updatedAt fields
                // will be automatically created by JpaAudit
                .build();

        return repository.save(post);
    }

    @Transactional
    public Post update(Long id, PostUpdateRequest request) {
        Post post = getById(id);
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setCategory(request.category());
        post.setTags(request.tags());
        // updatedAt field will be updated by JpaAudit

        return repository.save(post);
    }

    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Post with id " + id + " not found");
        }
        repository.deleteById(id);
    }

}
