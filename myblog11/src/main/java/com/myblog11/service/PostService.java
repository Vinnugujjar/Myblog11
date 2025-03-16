package com.myblog11.service;


import com.myblog11.payload.PostDto;
import com.myblog11.payload.PostResponse;

public interface PostService {



     PostDto createPost(PostDto postDto);

     void deletePostById(long postId);

     PostDto getPostById(long id);

    PostResponse getAllPost(int pageNo, int pageZize, String sortBy, String sortDir);


}

