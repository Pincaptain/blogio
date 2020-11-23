package com.gjorovski.blogio.blog.controller;

import com.gjorovski.blogio.blog.exception.NotFoundException;
import com.gjorovski.blogio.blog.model.Post;
import com.gjorovski.blogio.blog.model.dto.PostDto;
import com.gjorovski.blogio.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(path = "")
public class BlogController {
    @SuppressWarnings("unused")
    private static final String TAG = "BlogController";

    private final PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "posts")
    public String posts(@RequestParam(name = "page") Optional<Integer> page,
                        @RequestParam(name = "size") Optional<Integer> size,
                        @RequestParam(name = "sort") Optional<String> sort,
                        @RequestParam(name = "desc") Optional<Boolean> desc,
                        @RequestParam(name = "filter") Optional<String> filter, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);
        String sortBy = sort.orElse("updatedAt");
        boolean isDesc = desc.orElse(true);
        String titleFilter = filter.orElse("");

        Page<Post> posts = postService.findPaginated(currentPage, pageSize, sortBy, isDesc, titleFilter);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("isDesc", isDesc);

        return "blog/posts";
    }

    @GetMapping(path = "posts/{id}")
    public String postsDetail(@PathVariable(name = "id") long id, Model model) throws NotFoundException {
        Post post = postService.findById(id);

        model.addAttribute("post", post);

        return "blog/posts_detail";
    }

    @GetMapping(path = "posts/create")
    public String postsCreate(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "blog/posts_create";
    }

    @PostMapping(path = "posts/create")
    public String postsCreate(@ModelAttribute @Valid PostDto postDto, Errors errors) {
        if (errors.hasErrors()) {
            return "blog/posts_create";
        }

        Post post = this.postService.createPost(postDto);

        return String.format("redirect:/posts/%d", post.getId());
    }

    @GetMapping(path = "posts/update/{id}")
    public String postsUpdate(@PathVariable(name = "id") long id, Model model) throws NotFoundException {
        PostDto postDto = postService.findDtoById(id);

        model.addAttribute("postDto", postDto);

        return "blog/posts_update";
    }

    @PostMapping(path = "posts/update/{id}")
    public String postsUpdate(@PathVariable(name = "id") long id, @ModelAttribute @Valid PostDto postDto, Errors errors)
            throws NotFoundException {
        if (errors.hasErrors()) {
            return "blog/posts_update";
        }

        Post post = postService.updatePost(id, postDto);

        return String.format("redirect:/posts/%d", post.getId());
    }

    @GetMapping(path = "posts/delete/{id}")
    public String postsDelete(@PathVariable(name = "id") long id, Model model) throws NotFoundException {
        Post post = postService.findById(id);

        model.addAttribute("post", post);

        return "blog/posts_delete";
    }

    @PostMapping(path = "posts/delete/{id}")
    public String postsDelete(@PathVariable(name = "id") long id) {
        postService.deleteById(id);

        return "redirect:/posts";
    }
}