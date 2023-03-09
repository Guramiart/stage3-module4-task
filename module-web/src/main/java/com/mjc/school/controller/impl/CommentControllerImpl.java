package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "api/comments",
        consumes = {"application/JSON"},
        produces = {"application/JSON", "application/XML"}
)
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CommentDtoResponse> readAll() {
        return commentService.readAll();
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public CommentDtoResponse readById(@PathVariable Long id) {
        return commentService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        return commentService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.OK)
    public CommentDtoResponse update(@PathVariable Long id, CommentDtoRequest updateRequest) {
        return commentService.update(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public boolean deleteById(@PathVariable Long id) {
        return commentService.deleteById(id);
    }

    @Override
    @GetMapping(value = "/comment")
    public CommentDtoResponse readByNewsId(@RequestParam("news-id") Long newsId) {
        return commentService.readByNewsId(newsId);
    }
}
