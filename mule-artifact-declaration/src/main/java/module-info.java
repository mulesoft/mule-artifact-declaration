/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/**
 * Mule Artifact Declaration.
 * 
 * @deprecated use {@code org.mule.runtime.artifact.ast} instead.
 * 
 * @moduleGraph
 * @since 1.6
 */
module org.mule.runtime.artifact.declaration {
  
  requires org.mule.runtime.api.annotations;
  requires org.mule.runtime.metadata.model.api;
  
  requires org.apache.commons.lang3;
  
  exports org.mule.runtime.app.declaration.api;
  exports org.mule.runtime.app.declaration.api.fluent;
  exports org.mule.runtime.app.declaration.api.component.location;

  exports org.mule.runtime.app.declaration.internal.utils to
      org.mule.runtime.artifact;

  // Allow introspection for serialization/deserialization by Gson
  opens org.mule.runtime.app.declaration.api to
      com.google.gson;
  opens org.mule.runtime.app.declaration.api.fluent to
      com.google.gson;

}