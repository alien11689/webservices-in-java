@XmlSchema(
        elementFormDefault = XmlNsForm.QUALIFIED,
        attributeFormDefault = XmlNsForm.UNQUALIFIED,
        namespace = "http://github.com/alien11689/webservices/model"
)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSchemaType(type = LocalDateTime.class, name = "dateTime")
package com.github.alien11689.webservices.model;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Date;