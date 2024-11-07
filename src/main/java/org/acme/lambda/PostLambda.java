package org.acme.lambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import org.acme.DTO.PostDTO;
import org.acme.services.PostService;

import java.util.Map;

public class PostLambda extends BaseLambdaHandler {

    @Inject
    PostService postService;

    @Override
    protected APIGatewayProxyResponseEvent processRequest(APIGatewayProxyRequestEvent input) {
        try {
            String httpMethod = input.getHttpMethod();
            String path = input.getPath();

            if ("GET".equals(httpMethod)) {
                if (path.matches("/posts/\\d+")) {
                    Long id = Long.parseLong(path.split("/")[2]);
                    String responseBody = JsonbBuilder.create().toJson(postService.getPost(id));
                    return new APIGatewayProxyResponseEvent()
                            .withStatusCode(200)
                            .withBody(responseBody)
                            .withHeaders(Map.of("Content-Type", "application/json"));
                } else {
                    String responseBody = JsonbBuilder.create().toJson(postService.getAllPosts());
                    return new APIGatewayProxyResponseEvent()
                            .withStatusCode(200)
                            .withBody(responseBody)
                            .withHeaders(Map.of("Content-Type", "application/json"));
                }
            } else if ("POST".equals(httpMethod)) {
                PostDTO post = JsonbBuilder.create().fromJson(input.getBody(), PostDTO.class);
                String responseBody = JsonbBuilder.create().toJson(postService.createPost(post));
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(201)
                        .withBody(responseBody)
                        .withHeaders(Map.of("Content-Type", "application/json"));
            } else {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(405)
                        .withBody("{\"error\": \"Método HTTP no soportado\"}")
                        .withHeaders(Map.of("Content-Type", "application/json"));
            }
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("{\"error\": \"" + e.getMessage() + "\"}")
                    .withHeaders(Map.of("Content-Type", "application/json"));
        }
    }
}
