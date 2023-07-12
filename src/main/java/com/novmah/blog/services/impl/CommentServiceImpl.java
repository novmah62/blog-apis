package com.novmah.blog.services.impl;

import com.novmah.blog.entities.Comment;
import com.novmah.blog.entities.Post;
import com.novmah.blog.exceptions.ResourceNotFoundException;
import com.novmah.blog.payloads.CommentDto;
import com.novmah.blog.repositories.CommentRepo;
import com.novmah.blog.repositories.PostRepo;
import com.novmah.blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ModelMapper mapper;

    private final PostRepo postRepo;

    private final CommentRepo commentRepo;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setPost(post);
        commentRepo.save(comment);

        return mapper.map(comment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment", "comment id", commentId));

        commentRepo.delete(comment);
    }
}
