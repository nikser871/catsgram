package com.catsgram.catsgram.controller;

import com.catsgram.catsgram.exception.IncorrectParameterException;
import com.catsgram.catsgram.model.Post;
import com.catsgram.catsgram.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/feed/friends")
public class PostFeedController {

    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public List<Post> createPosts(@RequestBody String posts) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(posts);
        List<String> emails = node.require().findValuesAsText("friends");
        String sort = node.get("sort").asText();
        int size = Integer.parseInt(node.get("size").asText());
        int page = Integer.parseInt(node.get("page").asText());
        if (sort == null) {
            throw new IncorrectParameterException("sort");
        } else if (page < 1) {
            throw new IncorrectParameterException("page");
        } else if (size < 1) {
            throw new IncorrectParameterException("size");
        }

        return postService.findAll(size, sort, page, emails);

    }


}
