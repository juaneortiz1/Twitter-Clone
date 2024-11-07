package org.acme;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LambdaHandler extends QuarkusStreamHandler implements RequestStreamHandler {
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        super.handleRequest(input, output, context);
    }
}

