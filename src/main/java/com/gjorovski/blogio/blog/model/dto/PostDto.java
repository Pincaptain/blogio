package com.gjorovski.blogio.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PostDto {
    @NotBlank(message = "Title should not be empty or blank")
    @Size(min = 2, max = 64, message = "Title should be between 2 and 64 characters long")
    private String title;

    @NotBlank(message = "Content should not be empty or blank")
    private String content;
}
