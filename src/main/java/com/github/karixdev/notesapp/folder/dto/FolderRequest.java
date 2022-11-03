package com.github.karixdev.notesapp.folder.dto;

import com.github.karixdev.notesapp.validation.UniqueFolderName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FolderRequest {
    @Size(min = 2, max = 255)
    @NotEmpty
    @UniqueFolderName
    String name;
}
