package com.github.karixdev.notesapp.validation;

import com.github.karixdev.notesapp.folder.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UniqueFolderNameValidator implements ConstraintValidator<UniqueFolderName, String> {

    private final FolderRepository folderRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return folderRepository
                .findFolderByName(s)
                .isEmpty();
    }
}
