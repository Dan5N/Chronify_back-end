package org.example.service.impl;

import org.example.mapper.NoteMapper;
import org.example.pojo.Note;
import org.example.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Override
    public List<Note> list(Long userId) {
        return noteMapper.findByUserId(userId);
    }

    @Override
    public Note getById(Long id, Long userId) {
        return noteMapper.findById(id, userId);
    }

    @Override
    public void add(Note note) {
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        noteMapper.insert(note);
    }

    @Override
    public void update(Note note) {
        note.setUpdatedAt(LocalDateTime.now());
        noteMapper.update(note);
    }

    @Override
    public void delete(Long id, Long userId) {
        noteMapper.delete(id, userId);
    }
}