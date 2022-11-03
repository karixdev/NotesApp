package com.github.karixdev.notesapp.validation;

import com.github.karixdev.notesapp.folder.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ExistingFolderValidator implements ConstraintValidator<ExistingFolder, Long> {

    private final FolderRepository folderRepository;

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        return folderRepository.findById(aLong)
                .isPresent();
    }
}
