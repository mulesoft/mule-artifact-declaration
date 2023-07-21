/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
/**
 * Mule Artifact Declaration.
 * 
 * @deprecated use {@code org.mule.runtime.artifact.ast} instead.
 * 
 * @moduleGraph
 * @since 1.5
 */
module org.mule.runtime.artifact.declaration {
  
  requires org.mule.runtime.api.annotations;
  requires org.mule.runtime.metadata.model.api;
  
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
