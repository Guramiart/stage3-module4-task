package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "api/news",
        consumes = {"application/JSON"},
        produces = {"application/JSON", "application/XML"}
)
public class NewsControllerImpl implements NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsControllerImpl(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public NewsDtoResponse readById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        return newsService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public NewsDtoResponse update(@PathVariable Long id, NewsDtoRequest updateRequest) {
        return newsService.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean deleteById(@PathVariable Long id) {
        return newsService.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<NewsDtoResponse> readBySearchParams(NewsQueryParams newsQueryParams) {
        return null;
    }
}
