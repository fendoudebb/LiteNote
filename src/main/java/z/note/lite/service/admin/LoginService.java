package z.note.lite.service.admin;

import z.note.lite.dto.request.Identity;
import z.note.lite.dto.response.Credentials;

public interface LoginService {

    Credentials login(Identity identity);

}
