package dev.z.blog.dto.request;

import lombok.Data;

public record Identity(
        String username,
        String password,
        String captcha

) {
}
