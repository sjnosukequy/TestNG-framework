package org.QAHexagon.testng.filters;

import io.restassured.internal.print.RequestPrinter;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.internal.NoParameterValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.parsing.Parser;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.ProxySpecification;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class SafePrinter extends RequestPrinter {
    public static String safePrint(FilterableRequestSpecification requestSpec, String requestMethod,
            String completeRequestUri, LogDetail logDetail, Set<String> blacklistedHeaders, PrintStream stream,
            boolean shouldPrettyPrint) {
        StringBuilder builder = new StringBuilder();
        if (logDetail == LogDetail.ALL || logDetail == LogDetail.METHOD) {
            addSingle(builder, "Request method:", requestMethod);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.URI) {
            addSingle(builder, "Request URI:", completeRequestUri);
        }

        if (logDetail == LogDetail.ALL) {
            addProxy(requestSpec, builder);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.PARAMS) {
            addMapDetails(builder, "Request params:", requestSpec.getRequestParams());
            addMapDetails(builder, "Query params:", requestSpec.getQueryParams());
            addMapDetails(builder, "Form params:", requestSpec.getFormParams());
            addMapDetails(builder, "Path params:", requestSpec.getNamedPathParams());
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.HEADERS) {
            addHeaders(requestSpec, blacklistedHeaders, builder);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.COOKIES) {
            addCookies(requestSpec, builder);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.PARAMS) {
            addMultiParts(requestSpec, builder);
        }

        if (logDetail == LogDetail.ALL || logDetail == LogDetail.BODY) {
            addBody(requestSpec, builder, shouldPrettyPrint);
        }

        String logString = StringUtils.removeEnd(builder.toString(), SystemUtils.LINE_SEPARATOR);
        stream.println(logString);
        return logString;
    }

    public static String safePrint(FilterableRequestSpecification requestSpec, String requestMethod,
            String completeRequestUri, Set<LogDetail> logDetails, Set<String> blacklistedHeaders, PrintStream stream,
            boolean shouldPrettyPrint) {
        StringBuilder builder = new StringBuilder();
        if (logDetails.contains(LogDetail.ALL)) {
            return print(requestSpec, requestMethod, completeRequestUri, LogDetail.ALL, blacklistedHeaders, stream,
                    shouldPrettyPrint);
        } else {
            if (logDetails.contains(LogDetail.METHOD)) {
                addSingle(builder, "Request method:", requestMethod);
            }

            if (logDetails.contains(LogDetail.URI)) {
                addSingle(builder, "Request URI:", completeRequestUri);
            }

            if (logDetails.contains(LogDetail.PARAMS)) {
                addMapDetails(builder, "Request params:", requestSpec.getRequestParams());
                addMapDetails(builder, "Query params:", requestSpec.getQueryParams());
                addMapDetails(builder, "Form params:", requestSpec.getFormParams());
                addMapDetails(builder, "Path params:", requestSpec.getNamedPathParams());
            }

            if (logDetails.contains(LogDetail.HEADERS)) {
                addHeaders(requestSpec, blacklistedHeaders, builder);
            }

            if (logDetails.contains(LogDetail.COOKIES)) {
                addCookies(requestSpec, builder);
            }

            if (logDetails.contains(LogDetail.PARAMS)) {
                addMultiParts(requestSpec, builder);
            }

            if (logDetails.contains(LogDetail.BODY)) {
                addBody(requestSpec, builder, shouldPrettyPrint);
            }

            String logString = StringUtils.removeEnd(builder.toString(), SystemUtils.LINE_SEPARATOR);
            stream.println(logString);
            return logString;
        }
    }

    private static void addProxy(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        builder.append("Proxy:");
        ProxySpecification proxySpec = requestSpec.getProxySpecification();
        appendThreeTabs(builder);
        if (proxySpec == null) {
            builder.append("<none>");
        } else {
            builder.append(proxySpec.toString());
        }

        builder.append(SystemUtils.LINE_SEPARATOR);
    }

    private static void addBody(FilterableRequestSpecification requestSpec, StringBuilder builder,
            boolean shouldPrettyPrint) {
        builder.append("Body:");
        if (requestSpec.getBody() != null) {
            Object body;
            if (shouldPrettyPrint) {
                body = (new Prettifier()).getPrettifiedBodyIfPossible(requestSpec);
            } else {
                body = requestSpec.getBody();
            }

            builder.append(SystemUtils.LINE_SEPARATOR).append(body);
        } else {
            appendTab(appendTwoTabs(builder)).append("<none>");
        }

    }

    private static void addCookies(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        builder.append("Cookies:");
        Cookies cookies = requestSpec.getCookies();
        if (!cookies.exist()) {
            appendTwoTabs(builder).append("<none>").append(SystemUtils.LINE_SEPARATOR);
        }

        int i = 0;

        Cookie cookie;
        for (Iterator var4 = cookies.iterator(); var4.hasNext(); builder.append(cookie)
                .append(SystemUtils.LINE_SEPARATOR)) {
            cookie = (Cookie) var4.next();
            if (i++ == 0) {
                appendTwoTabs(builder);
            } else {
                appendFourTabs(builder);
            }
        }

    }

    private static void addHeaders(FilterableRequestSpecification requestSpec, Set<String> blacklistedHeaders,
            StringBuilder builder) {
        builder.append("Headers:");
        Headers headers = requestSpec.getHeaders();
        if (!headers.exist()) {
            appendTwoTabs(builder).append("<none>").append(SystemUtils.LINE_SEPARATOR);
        } else {
            int i = 0;

            Header processedHeader;
            for (Iterator var5 = headers.iterator(); var5.hasNext(); builder.append(processedHeader)
                    .append(SystemUtils.LINE_SEPARATOR)) {
                Header header = (Header) var5.next();
                if (i++ == 0) {
                    appendTwoTabs(builder);
                } else {
                    appendFourTabs(builder);
                }

                processedHeader = header;
                if (blacklistedHeaders.contains(header.getName())) {
                    processedHeader = new Header(header.getName(), "[ BLACKLISTED ]");
                }
            }
        }

    }

    private static void addMultiParts(FilterableRequestSpecification requestSpec, StringBuilder builder) {
        builder.append("Multiparts:");
        List<MultiPartSpecification> multiParts = requestSpec.getMultiPartParams();
        if (multiParts.isEmpty()) {
            appendTwoTabs(builder).append("<none>").append(SystemUtils.LINE_SEPARATOR);
        } else {
            for (int i = 0; i < multiParts.size(); ++i) {
                MultiPartSpecification multiPart = (MultiPartSpecification) multiParts.get(i);
                if (i == 0) {
                    appendTwoTabs(builder);
                } else {
                    appendFourTabs(builder.append(SystemUtils.LINE_SEPARATOR));
                }

                builder.append("------------");
                appendFourTabs(appendFourTabs(builder.append(SystemUtils.LINE_SEPARATOR))
                        .append("Content-Disposition: ").append(requestSpec.getContentType().replace("multipart/", ""))
                        .append("; name = ").append(multiPart.getControlName())
                        .append(multiPart.hasFileName() ? "; filename = " + multiPart.getFileName() : "")
                        .append(SystemUtils.LINE_SEPARATOR)).append("Content-Type: ").append(multiPart.getMimeType());
                Map<String, String> headers = multiPart.getHeaders();
                if (!headers.isEmpty()) {
                    Set<Map.Entry<String, String>> headerEntries = headers.entrySet();
                    Iterator var7 = headerEntries.iterator();

                    while (var7.hasNext()) {
                        Map.Entry<String, String> headerEntry = (Map.Entry) var7.next();
                        appendFourTabs(appendFourTabs(builder.append(SystemUtils.LINE_SEPARATOR))
                                .append((String) headerEntry.getKey()).append(": ")
                                .append((String) headerEntry.getValue()));
                    }
                }

                builder.append(SystemUtils.LINE_SEPARATOR);
                if (multiPart.getContent() instanceof InputStream) {
                    appendFourTabs(builder.append(SystemUtils.LINE_SEPARATOR)).append("<inputstream>");
                } else if (isBinaryContent(multiPart.getContent(), multiPart.getMimeType())) {
                    appendFourTabs(builder.append(SystemUtils.LINE_SEPARATOR)).append("<binary content>");
                } else {
                    Parser parser = Parser.fromContentType(multiPart.getMimeType());
                    String prettified = (new Prettifier()).prettify(multiPart.getContent(), parser);
                    String prettifiedIndented = StringUtils.replace(prettified, SystemUtils.LINE_SEPARATOR,
                            SystemUtils.LINE_SEPARATOR + "\t\t\t\t");
                    appendFourTabs(builder.append(SystemUtils.LINE_SEPARATOR)).append(prettifiedIndented);
                }
            }

            builder.append(SystemUtils.LINE_SEPARATOR);
        }

    }

    private static boolean isBinaryContent(Object content, String mimeType) {

        if (content == null) {
            return false;
        }

        // hard binary types
        if (content instanceof InputStream ||
                content instanceof byte[] ||
                content instanceof File) {
            return true;
        }

        // defensive: common binary mime types
        if (mimeType != null) {
            String mt = mimeType.toLowerCase();
            return mt.startsWith("image/")
                    || mt.startsWith("video/")
                    || mt.startsWith("audio/")
                    || mt.equals("application/octet-stream")
                    || mt.equals("application/pdf");
        }

        return false;
    }

    private static void addSingle(StringBuilder builder, String str, String requestPath) {
        appendTab(builder.append(str)).append(requestPath).append(SystemUtils.LINE_SEPARATOR);
    }

    private static void addMapDetails(StringBuilder builder, String title, Map<String, ?> map) {
        appendTab(builder.append(title));
        if (map.isEmpty()) {
            builder.append("<none>").append(SystemUtils.LINE_SEPARATOR);
        } else {
            int i = 0;

            for (Iterator var4 = map.entrySet().iterator(); var4.hasNext(); builder
                    .append(SystemUtils.LINE_SEPARATOR)) {
                Map.Entry<String, ?> entry = (Map.Entry) var4.next();
                if (i++ != 0) {
                    appendFourTabs(builder);
                }

                Object value = entry.getValue();
                builder.append((String) entry.getKey());
                if (!(value instanceof NoParameterValue)) {
                    builder.append("=").append(value);
                }
            }
        }

    }

    private static StringBuilder appendFourTabs(StringBuilder builder) {
        appendTwoTabs(appendTwoTabs(builder));
        return builder;
    }

    private static StringBuilder appendTwoTabs(StringBuilder builder) {
        appendTab(appendTab(builder));
        return builder;
    }

    private static StringBuilder appendThreeTabs(StringBuilder builder) {
        appendTwoTabs(appendTab(builder));
        return builder;
    }

    private static StringBuilder appendTab(StringBuilder builder) {
        return builder.append("\t");
    }
}
