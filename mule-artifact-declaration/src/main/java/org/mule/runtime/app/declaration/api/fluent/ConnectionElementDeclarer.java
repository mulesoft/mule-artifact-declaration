/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.ConnectionElementDeclaration;

/**
 * Allows configuring an {@link ConnectionElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ConnectionElementDeclarer
    extends ParameterizedElementDeclarer<ConnectionElementDeclarer, ConnectionElementDeclaration> {

  /**
   * Creates a new instance
   */
  ConnectionElementDeclarer(ConnectionElementDeclaration declaration) {
    super(declaration);
  }

}
