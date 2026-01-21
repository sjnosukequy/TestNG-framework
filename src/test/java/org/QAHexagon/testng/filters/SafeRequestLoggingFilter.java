package org.QAHexagon.testng.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.UrlDecoder;
import org.QAHexagon.testng.filters.SafePrinter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.Validate;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class SafeRequestLoggingFilter implements Filter {
    private static final boolean SHOW_URL_ENCODED_URI = true;
    private final LogDetail logDetail;
    private final PrintStream stream;
    private final boolean shouldPrettyPrint;
    private final boolean showUrlEncodedUri;
    private final Set<String> blacklistedHeaders;
    private final Set<LogDetail> logDetailSet;

   public SafeRequestLoggingFilter() {
      this(LogDetail.ALL, System.out);
   }

   public SafeRequestLoggingFilter(LogDetail logDetail) {
      this(logDetail, System.out);
   }

   public SafeRequestLoggingFilter(PrintStream printStream) {
      this(LogDetail.ALL, printStream);
   }

   public SafeRequestLoggingFilter(LogDetail logDetail, PrintStream stream) {
      this(logDetail, true, stream);
   }

   public SafeRequestLoggingFilter(LogDetail logDetail, boolean shouldPrettyPrint, PrintStream stream) {
      this(logDetail, shouldPrettyPrint, stream, true);
   }

   public SafeRequestLoggingFilter(LogDetail logDetail, boolean shouldPrettyPrint, PrintStream stream, boolean showUrlEncodedUri) {
      this(logDetail, shouldPrettyPrint, stream, showUrlEncodedUri, new TreeSet(String.CASE_INSENSITIVE_ORDER));
   }

   public SafeRequestLoggingFilter(LogDetail logDetail, boolean shouldPrettyPrint, PrintStream stream, boolean showUrlEncodedUri, Set<String> blacklistedHeaders) {
      this.logDetailSet = new HashSet();
      Validate.notNull(stream, "Print stream cannot be null", new Object[0]);
      Validate.notNull(blacklistedHeaders, "Blacklisted headers cannot be null", new Object[0]);
      Validate.notNull(logDetail, "Log details cannot be null", new Object[0]);
      if (logDetail == LogDetail.STATUS) {
         throw new IllegalArgumentException(String.format("%s is not a valid %s for a request.", LogDetail.STATUS, LogDetail.class.getSimpleName()));
      } else {
         this.stream = stream;
         this.logDetail = logDetail;
         TreeSet<String> caseInsensitiveBlacklistedHeaders = new TreeSet(String.CASE_INSENSITIVE_ORDER);
         caseInsensitiveBlacklistedHeaders.addAll(blacklistedHeaders);
         this.blacklistedHeaders = caseInsensitiveBlacklistedHeaders;
         this.shouldPrettyPrint = shouldPrettyPrint;
         this.showUrlEncodedUri = showUrlEncodedUri;
      }
   }

    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
            FilterContext ctx) {
        String uri = requestSpec.getURI();
        if (!this.showUrlEncodedUri) {
            uri = UrlDecoder.urlDecode(uri,
                    Charset.forName(requestSpec.getConfig().getEncoderConfig().defaultQueryParameterCharset()), true);
        }

        if (this.logDetailSet.isEmpty()) {
            SafePrinter.safePrint(requestSpec, requestSpec.getMethod(), uri, this.logDetail, this.blacklistedHeaders,
                    this.stream, this.shouldPrettyPrint);
        } else {
            SafePrinter.safePrint(requestSpec, requestSpec.getMethod(), uri, this.logDetailSet, this.blacklistedHeaders,
                    this.stream, this.shouldPrettyPrint);
        }

        return ctx.next(requestSpec, responseSpec);
    }

    public static SafeRequestLoggingFilter logRequestTo(PrintStream stream) {
        return new SafeRequestLoggingFilter(stream);
    }

    public static SafeRequestLoggingFilter with(LogDetail... logDetails) {
        return (new SafeRequestLoggingFilter()).addLog(logDetails);
    }

    private SafeRequestLoggingFilter addLog(LogDetail... logDetails) {
        this.logDetailSet.addAll(Arrays.asList(logDetails));
        return this;
    }
}
