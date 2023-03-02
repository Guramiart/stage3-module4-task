package com.mjc.school.repository.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;

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

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @OneToOne(mappedBy = "comment")
    private NewsModel news;

    private static class CommentBuilder {

        private final Long id;
        private final String comment;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdatedDate;
        private NewsModel news;

        public CommentBuilder(Long id, String comment) {
            this.id = id;
            this.comment = comment;
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
        comment = commentBuilder.comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
                && Objects.equals(comment, that.comment)
                && Objects.equals(createdDate, that.createdDate)
                && Objects.equals(lastUpdatedDate, that.lastUpdatedDate)
                && Objects.equals(news, that.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comment, createdDate, lastUpdatedDate, news);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, comment=%s, createdDate=%s, lastUpdatedDate=%s, newsId=%d]",
                getClass().getSimpleName(), id, comment, createdDate, lastUpdatedDate, news.getId());
    }
}
