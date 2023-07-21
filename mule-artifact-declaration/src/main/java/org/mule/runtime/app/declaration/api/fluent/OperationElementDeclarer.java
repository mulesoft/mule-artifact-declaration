/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.OperationElementDeclaration;

/**
 * Allows configuring an {@link OperationElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class OperationElementDeclarer
    extends ComponentElementDeclarer<OperationElementDeclarer, OperationElementDeclaration> {

  OperationElementDeclarer(OperationElementDeclaration declaration) {
    super(declaration);
  }
}
