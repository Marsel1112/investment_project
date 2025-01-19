package org.invest.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();
    @Override
    public Exception decode(String s, Response response) {
        int statusCode = response.status();
        String message = String.format("Error %s status code = %d",s,statusCode);

        switch (statusCode){
            case 400:
                return new BadRequestException(message);
            case 404:
                return new NotFoundException(message);
            case 500:
                return new InternalServerErrorException(message);
            default:
                return defaultDecoder.decode(s, response);
        }
    }
}