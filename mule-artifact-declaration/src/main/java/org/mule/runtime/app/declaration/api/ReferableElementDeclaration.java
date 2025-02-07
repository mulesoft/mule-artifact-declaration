/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;

/**
 * Adds {@link this#getRefName} and {@link this#setRefName} capabilities to an {@link ElementDeclaration} that represents a global
 * element that can be referenced by its name.
 *
 * @see ComponentElementDeclaration#getConfigRef
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@NoImplement
@Deprecated
public interface ReferableElementDeclaration {

  /**
   * @return the configured name of the element that can be used to reference it in the context of an {@link ArtifactDeclaration}
   */
  String getRefName();

  /**
   * Sets the configured name of the element that can be used to reference it in the context of an {@link ArtifactDeclaration}
   * 
   * @param referableName the configured name of the element
   */
  void setRefName(String referableName);
}
