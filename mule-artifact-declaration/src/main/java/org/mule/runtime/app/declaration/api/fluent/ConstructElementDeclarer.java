/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.ConstructElementDeclaration;

/**
 * Allows configuring a {@link ConstructElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ConstructElementDeclarer
    extends ComponentElementDeclarer<ConstructElementDeclarer, ConstructElementDeclaration> {

  /**
   * Creates a new instance of {@link ConstructElementDeclarer}
   */
  ConstructElementDeclarer(ConstructElementDeclaration declaration) {
    super(declaration);
  }

  public ConstructElementDeclarer withRefName(String name) {
    declaration.setRefName(name);
    return this;
  }

}
