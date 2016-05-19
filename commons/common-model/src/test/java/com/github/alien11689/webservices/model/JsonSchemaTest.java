package com.github.alien11689.webservices.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import org.junit.Test;

import javax.xml.bind.JAXBException;

public class JsonSchemaTest {

    @Test
    public void shouldMarshalProjectToXml() throws JAXBException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JaxbAnnotationModule());
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(Projects.class, visitor);
        JsonSchema schema = visitor.finalSchema();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
    }
}