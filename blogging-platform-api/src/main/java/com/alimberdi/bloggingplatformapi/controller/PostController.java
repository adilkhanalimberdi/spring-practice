package com.alimberdi.bloggingplatformapi.controller;

import com.alimberdi.bloggingplatformapi.dto.PostCreateRequest;
import com.alimberdi.bloggingplatformapi.dto.PostUpdateRequest;
import com.alimberdi.bloggingplatformapi.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity
                .ok(postService.getAll());
    }

    @GetMapping(params = "term")
    public ResponseEntity<?> getPostsByFilter(
            @RequestParam("term")
            @NotBlank(message = "Search term cannot be blank")
            String term
    ) {
        return ResponseEntity
                .ok(postService.getAllByFilter(term));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable @Min(0) Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody @Valid PostCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable @Min(0) Long id,
            @RequestBody @Valid PostUpdateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable @Min(0) Long id) {
        postService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
