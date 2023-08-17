package z.note.lite.constant.mvc;

public interface Endpoint {

    String ERROR = "/error";

    interface Api {

        String CONTEXT = "/api";

        String PATTERN = CONTEXT + "/**";

        String FILTER_PATTERN = CONTEXT + "/*";

        String LOGIN = CONTEXT + "/login";

        String CAPTCHA = CONTEXT + "/captcha";

        String USERS = CONTEXT + "/users";

        String USER = CONTEXT + "/user";

        String CLEAN_CACHE = CONTEXT + "/cache/i18n";

    }

    interface Admin {

        String CONTEXT = "/admin";

        String PATTERN = CONTEXT + "/**";

        String LOGIN = CONTEXT + "/login";

    }

    interface Portal {

        String PATTERN = Root.PATTERN;

        String INDEX = "/";

    }

    interface Root {

        String PATTERN = "/**";

        String FILTER_PATTERN = "/*";

    }

}
