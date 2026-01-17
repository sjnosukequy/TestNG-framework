package org.QAHexagon.testng.filters;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class errorThrow implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);
        if (response.getStatusCode() >= 400) {
            throw new RuntimeException("HTTP Error: " + response.getStatusCode() + " - " + response.getStatusLine());
        }
        return response;
    }
}
