package org.acme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Map;

public abstract class BaseLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        try {
            return processRequest(input);
        } catch (Exception e) {
            // Manejar la excepción y devolver un error con el código HTTP apropiado
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("{\"error\": \"" + e.getMessage() + "\"}")
                    .withHeaders(Map.of("Content-Type", "application/json"));
        }
    }

    protected abstract APIGatewayProxyResponseEvent processRequest(APIGatewayProxyRequestEvent input) throws Exception;
}
