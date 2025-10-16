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
        log.info("查询笔记列表");

        Long userId = CurrentUserUtil.getCurrentUserId();

        List<Note> notes = noteService.list(userId);
        return Result.success(notes);
    }

    @PostMapping
    public Result add(@RequestBody Note note) {
        log.info("添加笔记: {}", note);

        Long userId = CurrentUserUtil.getCurrentUserId();
        note.setUserId(userId);

        noteService.add(note);
        return Result.success("添加成功");
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("查询笔记详情，ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();

        Note note = noteService.getById(id, userId);
        if (note == null) {
            return Result.error("笔记不存在");
        }
        return Result.success(note);
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody Note note) {
        log.info("更新笔记，ID: {}, 数据: {}", id, note);

        Long userId = CurrentUserUtil.getCurrentUserId();
        note.setId(id);
        note.setUserId(userId);

        noteService.update(note);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        log.info("删除笔记，ID: {}", id);

        Long userId = CurrentUserUtil.getCurrentUserId();

        noteService.delete(id, userId);
        return Result.success("删除成功");
    }
}