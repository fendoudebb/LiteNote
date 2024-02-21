package z.note.lite.service.api;

import z.note.lite.request.Identity;
import z.note.lite.response.Credentials;

public interface LoginService {

    Credentials login(Identity identity);

    default void logout() {
    }

}
