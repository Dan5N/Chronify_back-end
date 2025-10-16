package org.example.service;

import org.example.pojo.Note;
import java.util.List;

public interface NoteService {

    List<Note> list(Long userId);

    Note getById(Long id, Long userId);

    void add(Note note);

    void update(Note note);

    void delete(Long id, Long userId);
}