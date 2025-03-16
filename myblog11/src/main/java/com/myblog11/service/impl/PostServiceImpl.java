package com.myblog11.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.myblog11.entity.Post;
import com.myblog11.exception.ResourceNotFound;
import com.myblog11.payload.PostDto;
import com.myblog11.payload.PostResponse;
import com.myblog11.repository.PostRepository;
import com.myblog11.service.PostService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper
) {
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatePost = postRepo.save(post);
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());


        return dto;
    }

    @Override
    public void deletePostById(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFound("Post is not found with id:"+postId));
        postRepo.deleteById((long) postId);
    }

    @Override
    public PostDto getPostById(long postId) {
        Post post = postRepo.findById((long) postId)
                .orElseThrow(() -> new ResourceNotFound("Post not found with id: " + postId));

        return  mapToDto(post);
    }

    PostDto mapToDto(Post post) {
        PostDto dto = modelMapper.map(post, PostDto.class);
        //  PostDto dto = new PostDto();
      //  dto.setId(post.getId());
      //  dto.setContent(post.getContent());
     //   dto.setTitle(post.getTitle());
      //  dto.setDescription(post.getDescription());
        return dto;
    }

    // http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=description&sortBy=title
    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(all.getNumber());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setPageSize(all.getSize());
        postResponse.setLast(all.isLast());
        postResponse.setTotalElements((int)all.getTotalElements());


        return postResponse;
    }
}
