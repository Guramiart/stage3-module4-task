package com.mjc.school.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "news")
@EntityListeners(AuditingEntityListener.class)
public class NewsModel implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AuthorModel author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "news_tags",
            joinColumns = { @JoinColumn(name = "news_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private List<TagModel> tags;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CommentModel> comments;

    private static class NewsBuilder {
        private final Long id;
        private final String title;
        private final String content;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdatedDate;
        private AuthorModel author;
        private List<TagModel> tags;
        private List<CommentModel> comments;

        public NewsBuilder(Long id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }

        public NewsBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public NewsBuilder lastUpdatedDate(LocalDateTime lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
            return this;
        }

        public NewsBuilder author(AuthorModel author) {
            this.author = author;
            return this;
        }

        public NewsBuilder tags(List<TagModel> tags) {
            this.tags = tags;
            return this;
        }

        public NewsBuilder comments(List<CommentModel> comments) {
            this.comments = comments;
            return this;
        }

        public NewsModel build() {
            return new NewsModel(this);
        }
    }

    public NewsModel() {}

    public NewsModel(NewsBuilder newsBuilder) {
        id = newsBuilder.id;
        title = newsBuilder.title;
        content = newsBuilder.content;
        createdDate = newsBuilder.createdDate;
        lastUpdatedDate = newsBuilder.lastUpdatedDate;
        author = newsBuilder.author;
        tags = newsBuilder.tags;
        comments = newsBuilder.comments;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public AuthorModel getAuthor() {
        return author;
    }

    public void setAuthor(AuthorModel author) {
        this.author = author;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public void removeTag(TagModel tag) {
        tags.remove(tag);
        tag.getNews().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsModel newsModel = (NewsModel) o;
        return Objects.equals(id, newsModel.id)
                && Objects.equals(title, newsModel.title)
                && Objects.equals(content, newsModel.content)
                && Objects.equals(createdDate, newsModel.createdDate)
                && Objects.equals(lastUpdatedDate, newsModel.lastUpdatedDate)
                && Objects.equals(author, newsModel.author)
                && Objects.equals(tags, newsModel.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createdDate, lastUpdatedDate, author, tags);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%d, title=%s, content=%s, createDate=%s, updateDate=%s, authorId=%d]",
                getClass().getSimpleName(), id, title, content, createdDate, lastUpdatedDate, author.getId());
    }
}
