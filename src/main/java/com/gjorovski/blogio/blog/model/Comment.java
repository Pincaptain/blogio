package com.gjorovski.blogio.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "post", nullable = false)
    private Post post;
}
