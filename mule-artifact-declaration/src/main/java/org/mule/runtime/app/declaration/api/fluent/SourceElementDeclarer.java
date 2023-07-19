/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.SourceElementDeclaration;

/**
 * Allows configuring an {@link SourceElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class SourceElementDeclarer
    extends ComponentElementDeclarer<SourceElementDeclarer, SourceElementDeclaration> {

  /**
   * Creates a new instance
   */
  SourceElementDeclarer(SourceElementDeclaration declaration) {
    super(declaration);
  }
}
