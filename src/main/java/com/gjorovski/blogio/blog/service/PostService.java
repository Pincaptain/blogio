package com.gjorovski.blogio.blog.service;

import com.gjorovski.blogio.blog.exception.NotFoundException;
import com.gjorovski.blogio.blog.mapper.PostMapper;
import com.gjorovski.blogio.blog.model.Post;
import com.gjorovski.blogio.blog.model.dto.PostDto;
import com.gjorovski.blogio.blog.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public Page<Post> findPaginated(int page, int size, String sortBy, boolean isDesc, String titleFilter) {
        Sort.Direction sortDirection = isDesc ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortDirection, sortBy));

        return postRepository.findByTitleContaining(titleFilter, pageable);
    }

    public Post findById(long id) throws NotFoundException {
        return postRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public PostDto findDtoById(long id) throws NotFoundException {
        return postMapper.postDtoFromPost(postRepository
                .findById(id)
                .orElseThrow(NotFoundException::new));
    }

    public Post createPost(PostDto postDto) {
        Post post = postMapper.postFromPostDto(postDto);

        return this.postRepository.save(post);
    }

    public Post updatePost(long id, PostDto postDto) throws NotFoundException {
        Post post = findById(id);

        postMapper.updatePostFromDto(postDto, post);
        postRepository.save(post);

        return post;
    }

    public void deleteById(long id) {
        postRepository.deleteById(id);
    }
}
