package me.ilias.backoffice.config;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(name = "page",description = "the page number based on zero index",example = "1",content = {@Content(schema = @Schema(type = "Integer",defaultValue = "0"))})
@Parameter(name = "size",description = "the page size, max size is 50",example = "1",content = {@Content(schema = @Schema(type = "Integer",defaultValue = "10"))})
public @interface PageablePrams {
}
