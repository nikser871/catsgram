package com.catsgram.catsgram.controller;

import ch.qos.logback.classic.Level;
import com.catsgram.catsgram.exception.PostNotFoundException;
import com.catsgram.catsgram.model.Post;
import com.catsgram.catsgram.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/posts")
    public List<Post> findAll(@RequestParam(defaultValue = "asc") String sort,
                              @RequestParam(defaultValue = "2") String size,
                              @RequestParam(defaultValue = "1") String page) {
        return postService.findAll(Integer.parseInt(size), sort, Integer.parseInt(page), null);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {

        return postService.create(post);
    }

    @GetMapping(value = "/posts/{postId}")
    public Optional<Post> findById(@PathVariable int postId) {
        Optional<Post> result = postService.getPosts().stream()
                .filter(x -> x.getId() == postId)
                .findFirst();
        if (result.isEmpty()) {
            throw new PostNotFoundException("Поста с данным ID не существует");
        }
        return result;
    }

//    @GetMapping(value = "/posts/search")
//    public List<Post> searchPosts(@RequestParam String author,
//                                  @RequestParam
//                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
//        return ...
//    }

}
