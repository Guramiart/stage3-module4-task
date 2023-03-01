package com.mjc.school.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "last_updated_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorModel author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "news_tags",
            joinColumns = { @JoinColumn(name = "news_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<TagModel> tags;

    private static class NewsBuilder {
        private final Long id;
        private final String title;
        private final String content;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdatedDate;
        private AuthorModel author;
        private Set<TagModel> tags;

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

        public NewsBuilder tags(Set<TagModel> tags) {
            this.tags = tags;
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

    public void setLastUpdateDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public AuthorModel getAuthor() {
        return author;
    }

    public void setAuthorModel(AuthorModel author) {
        this.author = author;
    }

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
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
