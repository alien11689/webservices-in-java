package com.github.alien11689.webservices.restwithspring03.webapplicationexceptionsmapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ExceptionMapperImpl implements ExceptionMapper {

    @Override
    public Response toResponse(Throwable exception) {
        if(exception instanceof ProjectNotFoundException){
            return Response.status(404).build();
        }
        if(exception instanceof ProjectDoesNotExistException){
            return Response.status(400).build();
        }
        if(exception instanceof ProjectAlreadyExistsException){
            return Response.status(400).build();
        }
        return null;
    }
}
