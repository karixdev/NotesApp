package com.github.karixdev.notesapp.folder.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueFolderNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueFolderName {
    String message() default "Folder with this name already exists";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
