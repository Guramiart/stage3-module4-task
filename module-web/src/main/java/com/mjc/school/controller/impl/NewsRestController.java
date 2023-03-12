package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "api/news",
        produces = {"application/JSON", "application/XML"}
)
public class NewsRestController implements NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    @GetMapping(produces = "application/com.mjc.school-v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public List<NewsDtoResponse> readAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size
    ) {
        return newsService.readNewsPage(PageRequest.of(page, size));
    }

    @Override
    @GetMapping(value = "/{id:\\d+}", produces = "application/com.mjc.school-v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public NewsDtoResponse readById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public NewsDtoResponse create(@RequestBody NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public NewsDtoResponse update(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public NewsDtoResponse patch(@PathVariable Long id, @RequestBody NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping(value = "/news", produces = "application/com.mjc.school-v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public List<NewsDtoResponse> readBySearchParams(
            @RequestParam(value = "tag-id", required = false) List<Integer> tagIds,
            @RequestParam(value = "tag-name", required = false) List<String> tagNames,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content) {
        NewsQueryParams newsQueryParams = new NewsQueryParams(tagIds, tagNames, author, title, content);
        return newsService.readBySearchParams(newsQueryParams);
    }
}
