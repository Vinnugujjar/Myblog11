package com.myblog11.service.impl;

import com.myblog11.entity.Comment;
import com.myblog11.entity.Post;
import com.myblog11.exception.ResourceNotFound;
import com.myblog11.payload.CommentDto;
import com.myblog11.repository.CommentRepository;
import com.myblog11.repository.PostRepository;
import com.myblog11.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo,ModelMapper modelMapper) {

        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto saveComment(CommentDto dto, long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFound("Post not found with id:" + postId));
      //  Comment comment = new Comment();
      //  comment.setId(dto.getId());
     //   comment.setName(dto.getName());
//comment.setBody(dto.getBody());
       // comment.setEmail(dto.getEmail());
       // comment.setPost(post);
        Comment comment = mapToCommet(dto);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(savedComment.getId());
        commentDto.setName(savedComment.getName());
        commentDto.setBody(savedComment.getBody());
        commentDto.setEmail(savedComment.getEmail());

        return commentDto;
    }

    @Override
    public void deleteByCommnetId(long id) {
        commentRepo.deleteById(id);

    }

    @Override
    public CommentDto UpdateByCommnet(CommentDto commentdto, long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(()->  new ResourceNotFound("Comment not found with id:"+ id));
        comment.setName(commentdto.getName());
        comment.setEmail(commentdto.getEmail());
        comment.setBody(commentdto.getBody());
        Comment savedComment = commentRepo.save(comment);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(savedComment.getId());
        commentDto.setName(savedComment.getName());
        commentDto.setBody(savedComment.getBody());
        commentDto.setEmail(savedComment.getEmail());

        return commentDto;
    }
    public Comment mapToCommet(CommentDto dto) {
        Comment comment = modelMapper.map(dto, Comment.class);
       // Comment comment = new Comment();
      //  comment.setId(dto.getId());
      //  comment.setName(dto.getName());
     //   comment.setBody(dto.getBody());
     //   comment.setEmail(dto.getEmail());
        return comment;
    }





}
