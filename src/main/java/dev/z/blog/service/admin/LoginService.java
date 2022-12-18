package dev.z.blog.service.admin;

import dev.z.blog.dto.request.Identity;
import dev.z.blog.dto.response.Credentials;

public interface LoginService {

    Credentials login(Identity identity);

}
