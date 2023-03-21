/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

module org.mule.artifactDeclaration {
  
  requires org.mule.runtime.apiAnnotations;
  requires org.mule.metadata.model.api;
  
  requires org.apache.commons.lang3;
  
  exports org.mule.runtime.app.declaration.api;
  exports org.mule.runtime.app.declaration.api.fluent;
  exports org.mule.runtime.app.declaration.api.component.location;

  // Allow introspection for serialization/deserialization by Gson
  opens org.mule.runtime.app.declaration.api to
      com.google.gson;
  opens org.mule.runtime.app.declaration.api.fluent to
      com.google.gson;

}