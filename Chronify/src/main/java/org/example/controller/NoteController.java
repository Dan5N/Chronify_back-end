package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Note;
import org.example.service.NoteService;
import org.example.util.CurrentUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public Result list() {
        log.info("Query note list");

        Long userId = CurrentUserUtil.getCurrentUserId();

        List<Note> notes = noteService.list(userId);
        return Result.success(notes);
    }

    @PostMapping
    public Result add(@RequestBody Note note) {
        log.info("Add note: {}", note);

        Long userId = CurrentUserUtil.getCurrentUserId();
        note.setUserId(userId);

        noteService.add(note);
        return Result.success("Add successful");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("Query note details, ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();

        Note note = noteService.getById(id, userId);
        if (note == null) {
            return Result.error("Note not found");
        }
        return Result.success(note);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Note note) {
        log.info("Update note, ID: {}, data: {}", id, note);

        Long userId = CurrentUserUtil.getCurrentUserId();
        note.setId(id);
        note.setUserId(userId);

        noteService.update(note);
        return Result.success("Update successful");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        log.info("Delete note, ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();

        noteService.delete(id, userId);
        return Result.success("Delete successful");
    }
}