package org.invest.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();
    @Override
    public Exception decode(String s, Response response) {

        int statusCode = response.status();
        String message = String.format("Error %s status code = %d",s,statusCode);

        return switch (statusCode) {
            case 400 -> new BadRequestException(message);
            case 404 -> new NotFoundException(message);
            case 429 -> new NotFoundException("Слишком большое количество дат," +
                                                "попробуйте еще раз через пару минут!");
            case 500 -> new InternalServerErrorException(message);
            default -> defaultDecoder.decode(s, response);
        };
    }
}