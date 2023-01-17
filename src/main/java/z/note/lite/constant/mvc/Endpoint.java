package z.note.lite.constant.mvc;

public interface Endpoint {

    interface Api {

        String CONTEXT = "/api";

        String PATTERN = CONTEXT + "/**";

        String FILTER_PATTERN = CONTEXT + "/*";

        String LOGIN = CONTEXT + "/login";

        String CAPTCHA = CONTEXT + "/captcha";

        String USERS = CONTEXT + "/users";

        String USER = CONTEXT + "/user";

    }

    interface Admin {

        String CONTEXT = "/admin";

        String PATTERN = CONTEXT + "/**";

        String LOGIN = CONTEXT + "/login";

        String ERROR = CONTEXT + "/error";

    }

    interface Portal {

        String PATTERN = Root.PATTERN;

        String ERROR = "/error";

    }

    interface Root {
        String PATTERN = "/**";

        String FILTER_PATTERN = "/*";
    }

}
