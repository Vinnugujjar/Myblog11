package com.myblog11.service;

import com.myblog11.payload.CommentDto;

public interface CommentService {
    CommentDto saveComment(CommentDto dto , long postId);

    void deleteByCommnetId(long id);

    CommentDto UpdateByCommnet(CommentDto commentdto, long id);
}
