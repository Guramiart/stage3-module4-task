package com.mjc.school.repository.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tags")
public class TagModel implements BaseEntity<Long>{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<NewsModel> news;

    private static class TagBuilder {
        private final Long id;
        private final String name;
        private Set<NewsModel> news;

        public TagBuilder(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public TagBuilder news(Set<NewsModel> news) {
            this.news = news;
            return this;
        }

        public TagModel build() {
            return new TagModel(this);
        }
    }
    public TagModel() {}

    public TagModel(TagBuilder tagBuilder) {
        id = tagBuilder.id;
        name = tagBuilder.name;
        news = tagBuilder.news;
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

    public Set<NewsModel> getNews() {
        return news;
    }

    public void setNews(Set<NewsModel> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagModel tagModel = (TagModel) o;
        return Objects.equals(id, tagModel.id)
                && Objects.equals(name, tagModel.name)
                && Objects.equals(news, tagModel.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, news);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, name=%s]", getClass().getSimpleName(), id, name);
    }
}
