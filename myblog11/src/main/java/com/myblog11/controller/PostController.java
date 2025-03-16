package com.myblog11.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import com.myblog11.entity.Post;
import com.myblog11.payload.PostDto;
import com.myblog11.payload.PostResponse;
import com.myblog11.repository.PostRepository;
import com.myblog11.service.PostService;
import javafx.scene.canvas.GraphicsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// http:localhost:8080/api/posts
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(
            @Valid  @RequestBody PostDto postDto,
            BindingResult result

    )
    {
        if(result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>("Post is created", HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable long postId) {

        postService.deletePostById(postId);

        return new ResponseEntity<String>("Post is deleted :"+ postId,HttpStatus.OK);


    }
    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostByID(@PathVariable long id) {
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<PostDto>(dto,HttpStatus.OK);

    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=description&sortBy=title&sortDir=desc
    @GetMapping
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)  int   pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir ){
        PostResponse postResponse =postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
        return postResponse;


    }

}

