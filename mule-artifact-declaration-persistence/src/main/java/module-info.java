/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
/**
 * Mule Artifact Declaration persistence layer.
 * 
 * @moduleGraph
 * @since 1.5
 */
module org.mule.runtime.artifact.declaration.persistence {

  requires org.mule.runtime.artifact.declaration;

  requires com.google.gson;

  exports org.mule.runtime.app.declaration.serialization.api;

}
