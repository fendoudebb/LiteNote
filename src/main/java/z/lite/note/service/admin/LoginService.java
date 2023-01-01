package z.lite.note.service.admin;

import z.lite.note.dto.request.Identity;
import z.lite.note.dto.response.Credentials;

public interface LoginService {

    Credentials login(Identity identity);

}
