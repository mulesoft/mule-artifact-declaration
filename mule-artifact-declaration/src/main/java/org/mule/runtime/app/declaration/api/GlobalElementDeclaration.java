/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;

/**
 * An {@link ElementDeclaration} that can be present as a direct child of an {@link ArtifactDeclaration}. This implies that the
 * {@link ElementDeclaration} has to be also {@link IdentifiableElementDeclaration identifiable} and
 * {@link ReferableElementDeclaration referable}.
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@NoImplement
@Deprecated
public interface GlobalElementDeclaration
    extends CustomizableElementDeclaration, ReferableElementDeclaration, IdentifiableElementDeclaration,
    ElementDeclarationContainer, MetadataPropertiesAwareElementDeclaration {

  /**
   * Dispatches to the method with prefix "visit" with the specific value type as argument. Example
   * {@code visitObjectValue(ParameterObjectValue objectValue) } will be called when this value is an
   * {@link GlobalElementDeclaration}.
   *
   * @param visitor the visitor
   */
  void accept(GlobalElementDeclarationVisitor visitor);

}
