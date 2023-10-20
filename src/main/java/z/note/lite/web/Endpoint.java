package z.note.lite.web;

public interface Endpoint {

    String ERROR = "/error";

    interface Api {

        String CONTEXT = "/api";

        String PATTERN = CONTEXT + "/**";

        String FILTER_PATTERN = CONTEXT + "/*";

        String LOGIN = CONTEXT + "/login";

        String LOGOUT = CONTEXT + "/logout";

        String CAPTCHA = CONTEXT + "/captcha";

        String POSTS = CONTEXT + "/posts";

        String POST = CONTEXT + "/post/{postId}";

        String POST_STATUS = CONTEXT + "/post/status";

        String POST_COMMENT_STATUS = CONTEXT + "/post/comment/status/{postId}";

        String UPLOAD = CONTEXT + "/upload";

        String USERS = CONTEXT + "/users";

        String USER = CONTEXT + "/user";

        String CLEAN_CACHE = CONTEXT + "/cache/i18n";

    }

    interface Admin {

        String CONTEXT = "/admin";

        String PATTERN = CONTEXT + "/**";

        String LOGIN = CONTEXT + "/login";

        String INDEX = CONTEXT + "/index";

    }

    interface Portal {

        String PATTERN = Root.PATTERN;

        String INDEX = "/";

        String MESSAGES = "/messages.html";

        String SEARCH = "/search.html";

        String TOPICS = "/topics.html";

        String DATA = "/data.html";

        String POST = "/p/{postId}.html";

        String POST_RANDOM = "/post/random";

        String TOPIC = "/topic/{topic}.html";

        String SITEMAP = "/sitemap.xml";

    }

    interface Mobile {

        String CONTEXT = "/m";

        String INDEX = CONTEXT + "/index";

        String POST = CONTEXT + "/p/{postId}";

        String SEARCH = CONTEXT + "/search/{keywords}";

    }

    interface Root {

        String PATTERN = "/**";

        String FILTER_PATTERN = "/*";

    }

}