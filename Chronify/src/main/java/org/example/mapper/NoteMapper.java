package org.example.mapper;

import org.example.pojo.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM notes WHERE user_id = #{userId} AND deleted_at IS NULL ORDER BY created_at DESC")
    List<Note> findByUserId(Long userId);

    @Insert("INSERT INTO notes(user_id, title, content) VALUES(#{userId}, #{title}, #{content})")
    void insert(Note note);

    @Update("UPDATE notes SET title = #{title}, content = #{content} " +
            "WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    int update(Note note);

    @Update("UPDATE notes SET deleted_at = NOW() WHERE id = #{id} AND user_id = #{userId}")
    int delete(@Param("id") Long id, @Param("userId") Long userId);

    @Select("SELECT * FROM notes WHERE id = #{id} AND user_id = #{userId} AND deleted_at IS NULL")
    Note findById(@Param("id") Long id, @Param("userId") Long userId);
}