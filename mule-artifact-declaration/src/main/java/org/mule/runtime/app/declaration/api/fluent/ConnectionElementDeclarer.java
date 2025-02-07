/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.ConnectionElementDeclaration;

/**
 * Allows configuring an {@link ConnectionElementDeclaration} through a fluent API
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@Deprecated
public final class ConnectionElementDeclarer
    extends ParameterizedElementDeclarer<ConnectionElementDeclarer, ConnectionElementDeclaration> {

  /**
   * Creates a new instance
   */
  ConnectionElementDeclarer(ConnectionElementDeclaration declaration) {
    super(declaration);
  }

}
