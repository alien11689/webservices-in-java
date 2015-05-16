package com.github.alien11689.webservices.restwithspring03.exceptions.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ExceptionMapperImpl implements ExceptionMapper {

    @Override
    public Response toResponse(Throwable exception) {
        if(exception instanceof ProjectNotFoundException){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if(exception instanceof ProjectDoesNotExistException){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(exception instanceof ProjectAlreadyExistsException){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return null;
    }
}
