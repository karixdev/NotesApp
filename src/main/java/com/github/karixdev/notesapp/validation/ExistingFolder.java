package com.github.karixdev.notesapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistingFolderValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistingFolder {
    String message() default "Folder with provided id does not exist";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
