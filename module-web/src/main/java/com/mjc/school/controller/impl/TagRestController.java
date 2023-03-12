package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.validator.constraint.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "api/tags",
        produces = {"application/JSON", "application/XML" }
)
public class TagRestController implements TagController {

    private final TagService tagService;

    @Autowired
    public TagRestController(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    @GetMapping(produces = "application/com.mjc.school-v1+json")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TagDtoResponse> readAll(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "5", required = false) int size
    ) {
        return tagService.readTagsPage(PageRequest.of(page, size));
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
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public TagDtoResponse update(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public TagDtoResponse patch(@PathVariable Long id, @RequestBody TagDtoRequest updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping(value = "/tag", produces = "application/com.mjc.school-v1+json")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDtoResponse> readByNewsId(@RequestParam("news-id") Long newsId) {
        return tagService.readByNewsId(newsId);
    }
}
