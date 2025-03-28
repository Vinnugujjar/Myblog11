package com.myblog11.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="posts",uniqueConstraints = {@UniqueConstraint(columnNames={"title"})})
@Data
public class Post {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="description", nullable = false)
    private String description;


    @Column(name="content",nullable = false)
    private String content;
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
