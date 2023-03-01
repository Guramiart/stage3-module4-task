package com.mjc.school.repository.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
@EntityListeners(AuditingEntityListener.class)
public class AuthorModel implements BaseEntity<Long>{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdatedDate;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<NewsModel> news;

    private static class AuthorBuilder {
        private final Long id;
        private final String name;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdatedDate;
        private List<NewsModel> news;

        public AuthorBuilder(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public AuthorBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public AuthorBuilder lastUpdateDate(LocalDateTime lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
            return this;
        }

        public AuthorBuilder news(List<NewsModel> news) {
            this.news = news;
            return this;
        }

        public AuthorModel build() {
            return new AuthorModel(this);
        }

    }

    public AuthorModel() {}

    public AuthorModel(AuthorBuilder authorBuilder) {
        id = authorBuilder.id;
        name = authorBuilder.name;
        createdDate = authorBuilder.createdDate;
        lastUpdatedDate = authorBuilder.lastUpdatedDate;
        news = authorBuilder.news;
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

    public List<NewsModel> getNews() {
        return Collections.unmodifiableList(news);
    }

    public void setNewsModelList(List<NewsModel> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorModel that = (AuthorModel) o;
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
        return String.format("%s[id=%d, name=%s, createDate=%s, updateDate=%s]",
                getClass().getSimpleName(), id, name, createdDate, lastUpdatedDate);
    }
}
