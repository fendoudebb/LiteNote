package z.note.lite.config.event;

import is.tagomor.woothee.Classifier;
import is.tagomor.woothee.DataSet;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.RequestHandledEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import z.note.lite.entity.Ip;
import z.note.lite.infra.RequestUtils;
import z.note.lite.service.api.IpMgmtService;
import z.note.lite.service.portal.InvalidRequestService;
import z.note.lite.service.portal.PageViewService;
import z.note.lite.service.portal.PostService;
import z.note.lite.service.portal.SearchService;
import z.note.lite.controller.Endpoint;
import z.note.lite.entity.RecordInvalidRequest;
import z.note.lite.entity.RecordPageView;
import z.note.lite.entity.RecordSearch;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class RequestHandledEvents {

    //	@EventListener
    public void requestHandledEvent(RequestHandledEvent event) {
        log.info("RequestHandledEvent#{}", event);
    }

    @Resource
    private ResourceUrlProvider resourceUrlProvider;

    @Resource
    private PathMatcher pathMatcher;

    @Resource
    private PostService postService;

    @Resource
    private SearchService searchService;

    @Resource
    private PageViewService pageViewService;

    @Resource
    private InvalidRequestService invalidRequestService;

    @Resource
    IpMgmtService ipMgmtService;

    @EventListener
    public void servletRequestHandledEvent(ServletRequestHandledEvent event) {
        String requestUrl = event.getRequestUrl();
        if (Objects.equals(requestUrl, Endpoint.ERROR)) {
            return;
        }
        String staticResourceUrl = resourceUrlProvider.getForLookupPath(event.getRequestUrl());
        if (Objects.equals(requestUrl, staticResourceUrl)) {
            log.debug("static resource url: {}", requestUrl);
            return;
        }

        boolean match = pathMatcher.match(Endpoint.Api.PATTERN, requestUrl);
        System.out.println(requestUrl + ", match api: " + match);

        if (pathMatcher.match(Endpoint.Api.PATTERN, requestUrl) || pathMatcher.match(Endpoint.Admin.PATTERN, requestUrl)) {
            // TODO: Audit Log
        } else {
            recordPageView(event);
        }

    }

    private void recordPageView(ServletRequestHandledEvent event) {
        String requestUrl = event.getRequestUrl();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String referer = request.getHeader(HttpHeaders.REFERER);
        String ua = request.getHeader(HttpHeaders.USER_AGENT);
        String acceptLanguage = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        String reqParam = request.getQueryString();
        short reqMethod;
        switch (request.getMethod()) {
            case "GET" -> reqMethod = 0;
            case "POST" -> reqMethod = 1;
            case "PUT" -> reqMethod = 2;
            case "DELETE" -> reqMethod = 3;
            case "OPTIONS" -> reqMethod = 4;
            default -> reqMethod = 5;
        }

        Ip ip = ipMgmtService.findByIp(RequestUtils.getIp());
        Map<String, String> parse = Classifier.parse(ua);
        String uaName = parse.get(DataSet.DATASET_KEY_NAME);
        String uaCategory = parse.get(DataSet.DATASET_KEY_CATEGORY);
        String uaVersion = parse.get(DataSet.DATASET_KEY_VERSION);
        String uaVendor = parse.get(DataSet.DATASET_KEY_VENDOR);
        String uaOs = parse.get(DataSet.DATASET_KEY_OS);
        String usOsVersion = parse.get(DataSet.DATASET_KEY_OS_VERSION);

        int channel = 0;
        if (requestUrl.startsWith(Endpoint.Mobile.CONTEXT)) {
            channel = 1;
        }
        RecordPageView pv = RecordPageView.builder()
                .url(requestUrl)
                .reqMethod(reqMethod)
                .reqParam(reqParam)
                .ua(ua)
                .uaName(Objects.equals(DataSet.VALUE_UNKNOWN, uaName) ? null : uaName)
                .uaCategory(Objects.equals(DataSet.VALUE_UNKNOWN, uaCategory) ? null : uaCategory)
                .uaVersion(Objects.equals(DataSet.VALUE_UNKNOWN, uaVersion) ? null : uaVersion)
                .uaVendor(Objects.equals(DataSet.VALUE_UNKNOWN, uaVendor) ? null : uaVendor)
                .uaOs(Objects.equals(DataSet.VALUE_UNKNOWN, uaOs) ? null : uaOs)
                .uaOsVersion(Objects.equals(DataSet.VALUE_UNKNOWN, usOsVersion) ? null : usOsVersion)
                .referer(referer)
                .costTime(event.getProcessingTimeMillis())
                .ipId(ip.getId())
                .channel(channel)
                .build();
        if (event.getStatusCode() == HttpStatus.OK.value()) {
            if (pathMatcher.match(Endpoint.Portal.POST, requestUrl)) {
                Map<String, String> pathVariables = pathMatcher.extractUriTemplateVariables(Endpoint.Portal.POST, requestUrl);
                Integer postId = Integer.parseInt(pathVariables.get("postId"));
                pv.setPostId(postId);
                postService.increasePv(postId);
            }
            String keywords = null;
            if (pathMatcher.match(Endpoint.Portal.SEARCH, requestUrl)) {
                keywords = request.getParameter("keywords");
            } else if (pathMatcher.match(Endpoint.Mobile.SEARCH, requestUrl)) {
                Map<String, String> pathVariables = pathMatcher.extractUriTemplateVariables(Endpoint.Mobile.SEARCH, requestUrl);
                keywords = URLDecoder.decode(pathVariables.get("keywords"), StandardCharsets.UTF_8);
            }
            if (StringUtils.hasText(keywords)) {
                RecordSearch search = RecordSearch.builder()
                        .keywords(keywords)
                        .took(event.getProcessingTimeMillis())
                        .referer(referer)
                        .ua(ua)
                        .uaName(uaName)
                        .uaOs(uaOs)
                        .ipId(ip.getId())
                        .channel(channel)
                        .build();
                searchService.insert(search);
            }
            pageViewService.insert(pv);
        } else {
            RecordInvalidRequest invalidRequest = new RecordInvalidRequest();
            BeanUtils.copyProperties(pv, invalidRequest);
            invalidRequestService.insert(invalidRequest);
        }

//        String s = pathMatcher.extractPathWithinPattern(Endpoint.Portal.POST, request.getRequestURI());
//        Map<String, String> stringStringMap = pathMatcher.extractUriTemplateVariables(Endpoint.Portal.POST, request.getRequestURI());
        Map<String, Object> map = (Map<String, Object>) attributes.getAttribute(View.PATH_VARIABLES, RequestAttributes.SCOPE_REQUEST);
        Map<String, Object> map2 = (Map<String, Object>) attributes.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    }
}
