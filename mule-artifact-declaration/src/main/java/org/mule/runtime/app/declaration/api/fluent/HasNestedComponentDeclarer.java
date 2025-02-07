/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;

/**
 * Allows configuring a nested {@link ComponentElementDeclaration} through a fluent API
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@NoImplement
@Deprecated
public interface HasNestedComponentDeclarer<T extends BaseElementDeclarer> {

  /**
   * Adds a {@link ComponentElementDeclaration component} to the declaration being built
   *
   * @param component the {@link ComponentElementDeclaration component} to add
   * @return {@code this} declarer
   */
  T withComponent(ComponentElementDeclaration component);

}
