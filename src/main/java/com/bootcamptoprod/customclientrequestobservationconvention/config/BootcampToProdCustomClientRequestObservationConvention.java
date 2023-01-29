package com.bootcamptoprod.customclientrequestobservationconvention.config;

import io.micrometer.common.KeyValue;
import io.micrometer.common.KeyValues;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.observation.ClientHttpObservationDocumentation;
import org.springframework.http.client.observation.ClientRequestObservationContext;
import org.springframework.http.client.observation.ClientRequestObservationConvention;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The type Bootcamp to prod custom client request observation convention.
 * Useful for adding our own tags in rest template metrics. This will override the default tags that are provided by the framework.
 */
@Component
public class BootcampToProdCustomClientRequestObservationConvention implements ClientRequestObservationConvention {

    @Override
    public String getName() {
        // Will be used for the metric name
        // We can customize the metric name as per our own requirement
        return "http.client.requests";
    }

    @Override
    public KeyValues getLowCardinalityKeyValues(ClientRequestObservationContext context) {
        // Here we are adding the tags related to HTTP method, HTTP status, exception
        return KeyValues.of(method(context), status(context), exception(context)).and(additionalTags(context));
    }

    protected KeyValues additionalTags(ClientRequestObservationContext context) {
        KeyValues keyValues = KeyValues.empty();

        ClientHttpRequest request = context.getCarrier();
        String uri = request.getURI().toString();

        // Optional tag which will be present in metrics only when the condition is evaluated to true
        MultiValueMap<String, String> parameters = UriComponentsBuilder.fromUriString(uri).build().getQueryParams();

        if (parameters.containsKey("id")) {
            keyValues = keyValues.and(KeyValue.of("userId", parameters.get("id").get(0)));
        }

        // Custom tag which will be present in all the controller metrics
        keyValues = keyValues.and(KeyValue.of("tag", "value"));

        return keyValues;
    }

    // Adding info related to HTTP Method
    protected KeyValue method(ClientRequestObservationContext context) {
        // You should reuse as much as possible the corresponding ObservationDocumentation for key names
        return KeyValue.of(ClientHttpObservationDocumentation.LowCardinalityKeyNames.METHOD, context.getCarrier().getMethod().toString());
    }

    // Adding info related to HTTP Status
    protected KeyValue status(ClientRequestObservationContext context) {
        String statusCode = "";
        try {
            statusCode = Integer.toString(context.getResponse().getStatusCode().value());
        } catch (Exception e) {

        }
        // You should reuse as much as possible the corresponding ObservationDocumentation for key names
        return KeyValue.of(ClientHttpObservationDocumentation.LowCardinalityKeyNames.STATUS, statusCode);
    }

    // Adding info related to exception
    protected KeyValue exception(ClientRequestObservationContext context) {
        if (context.getError() != null) {
            // You should reuse as much as possible the corresponding ObservationDocumentation for key names
            return KeyValue.of(ClientHttpObservationDocumentation.LowCardinalityKeyNames.EXCEPTION, context.getError().getClass().getName());
        } else {
            return KeyValue.of(ClientHttpObservationDocumentation.LowCardinalityKeyNames.EXCEPTION, "none");
        }
    }
}
