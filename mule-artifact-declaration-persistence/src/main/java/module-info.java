/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
