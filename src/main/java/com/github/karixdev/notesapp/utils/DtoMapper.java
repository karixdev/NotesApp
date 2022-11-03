package com.github.karixdev.notesapp.utils;

public interface DtoMapper<T, E> {
    T toDto(E entity);
    E toEntity(T dto);
}
