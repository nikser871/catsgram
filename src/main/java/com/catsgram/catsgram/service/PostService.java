package com.catsgram.catsgram.service;

import com.catsgram.catsgram.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(int size, String sort, int from, List<String> emails) {
        List<Post> result;
        int tmp = size * from;

        if (tmp > posts.size()) {
            tmp = posts.size();
        }

        if(emails != null && sort.equals("asc")) {
            result = posts.stream()
                    .filter(x -> emails.contains(userService.getNickEmail().get(x.getAuthor())))
                    .sorted(Comparator.comparing(Post::getCreationDate).reversed())
                    .toList()
                    .subList(tmp - size, tmp);
            return result;
        } else if (emails != null){
            result = posts.stream()
                    .filter(x -> emails.contains(userService.getNickEmail().get(x.getAuthor())))
                    .sorted(Comparator.comparing(Post::getCreationDate))
                    .toList()
                    .subList(tmp - size, tmp);
            return result;
        }
        if (sort.equals("asc")) {

            result = posts.stream()
                    .sorted(Comparator.comparing(Post::getCreationDate).reversed())
                    .toList()
                    .subList(tmp - size, tmp);
        } else {
            result = posts.stream()
                    .sorted(Comparator.comparing(Post::getCreationDate))
                    .toList()
                    .subList(tmp - size, tmp);
        }
        return result;
    }

    public Post create(Post post) {
        posts.add(post);
        return post;
    }


    public List<Post> getPosts() {
        return posts;
    }
}
