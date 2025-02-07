/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
/**
 * Mule Artifact Declaration persistence layer.
 * 
 * @deprecated use {@code org.mule.runtime.artifact.ast} instead.
 * 
 * @moduleGraph
 * @since 1.5
 */
@Deprecated(since = "4.10", forRemoval = true)
module org.mule.runtime.artifact.declaration.persistence {

  requires org.mule.runtime.artifact.declaration;

  requires com.google.gson;

  exports org.mule.runtime.app.declaration.serialization.api;

}
