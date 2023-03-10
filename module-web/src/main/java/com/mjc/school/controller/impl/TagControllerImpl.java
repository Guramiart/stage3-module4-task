package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "api/tags",
        consumes = {"application/JSON"},
        produces = {"application/JSON", "application/XML" }
)
public class TagControllerImpl implements TagController {

    private final TagService tagService;

    @Autowired
    public TagControllerImpl(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    @GetMapping(produces = "application/com.mjc.school-v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TagDtoResponse> readAll() {
        return tagService.readAll();
    }

    @Override
    @GetMapping(value = "/{id:\\d+}", produces = "application/com.mjc.school-v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public TagDtoResponse readById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TagDtoResponse create(@RequestBody TagDtoRequest createRequest) {
        return tagService.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public TagDtoResponse update(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean deleteById(@PathVariable Long id) {
        return tagService.deleteById(id);
    }

    @Override
    @GetMapping(value = "/tag", produces = "application/com.mjc.school-v1+json")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse readByNewsId(@RequestParam("news-id") Long newsId) {
        return tagService.readByNewsId(newsId);
    }
}
