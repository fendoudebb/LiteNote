package z.note.lite.service.api;

import z.note.lite.web.http.request.Identity;
import z.note.lite.web.http.response.Credentials;

public interface LoginService {

    Credentials login(Identity identity);

    default void logout() {
    }

}
