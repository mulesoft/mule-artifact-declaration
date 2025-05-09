/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.SourceElementDeclaration;

/**
 * Allows configuring an {@link SourceElementDeclaration} through a fluent API
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@Deprecated
public final class SourceElementDeclarer
    extends ComponentElementDeclarer<SourceElementDeclarer, SourceElementDeclaration> {

  /**
   * Creates a new instance
   */
  SourceElementDeclarer(SourceElementDeclaration declaration) {
    super(declaration);
  }
}
