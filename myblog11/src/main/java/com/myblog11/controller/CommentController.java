package com.myblog11.controller;

import com.myblog11.payload.CommentDto;
import com.myblog11.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // http://localhost:8080/api/comments/{postId}
    @PostMapping("{postId}")
    public ResponseEntity<CommentDto> saveComment(@RequestBody CommentDto commentDto, @PathVariable long postId) {
        CommentDto dto = commentService.saveComment(commentDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteByCommnetId(@PathVariable long id) {
        commentService.deleteByCommnetId(id);
        return new ResponseEntity<String>("Commnet is deleted with id:" + id, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<CommentDto> UpdateByCommnet(@RequestBody CommentDto commentdto, @PathVariable long id) {
        CommentDto dto = commentService.UpdateByCommnet(commentdto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
