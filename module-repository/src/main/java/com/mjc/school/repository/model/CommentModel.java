package com.mjc.school.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class CommentModel implements BaseEntity<Long>{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "news_id")
    private NewsModel news;

    private static class CommentBuilder {

        private final Long id;
        private final String name;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdatedDate;
        private NewsModel news;

        public CommentBuilder(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public CommentBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public CommentBuilder lastUpdatedDate(LocalDateTime lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
            return this;
        }

        public CommentBuilder news(NewsModel news) {
            this.news = news;
            return this;
        }

        public CommentModel build() {
            return new CommentModel(this);
        }

    }

    public CommentModel(){}

    public CommentModel(CommentBuilder commentBuilder) {
        id = commentBuilder.id;
        name = commentBuilder.name;
        createdDate = commentBuilder.createdDate;
        lastUpdatedDate = commentBuilder.lastUpdatedDate;
        news = commentBuilder.news;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public NewsModel getNews() {
        return news;
    }

    public void setNews(NewsModel news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentModel that = (CommentModel) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(createdDate, that.createdDate)
                && Objects.equals(lastUpdatedDate, that.lastUpdatedDate)
                && Objects.equals(news, that.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdDate, lastUpdatedDate, news);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, comment=%s, createdDate=%s, lastUpdatedDate=%s, newsId=%d]",
                getClass().getSimpleName(), id, name, createdDate, lastUpdatedDate, news.getId());
    }
}
