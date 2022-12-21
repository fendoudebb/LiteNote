package dev.z.blog.dto.request;

import jakarta.validation.constraints.NotBlank;

public record Identity(
        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank
        String captcha

) {
}
