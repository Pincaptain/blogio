package com.gjorovski.blogio.blog.mapper;

import com.gjorovski.blogio.blog.model.Post;
import com.gjorovski.blogio.blog.model.dto.PostDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostFromDto(PostDto postDto, @MappingTarget Post post);

    Post postFromPostDto(PostDto postDto);

    PostDto postDtoFromPost(Post post);
}
