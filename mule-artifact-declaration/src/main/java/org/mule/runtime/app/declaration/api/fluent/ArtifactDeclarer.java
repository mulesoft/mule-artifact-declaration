/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.ArtifactDeclaration;
import org.mule.runtime.app.declaration.api.GlobalElementDeclaration;

/**
 * Allows configuring an {@link ArtifactDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ArtifactDeclarer extends EnrichableElementDeclarer<ArtifactDeclarer, ArtifactDeclaration> {

  ArtifactDeclarer(ArtifactDeclaration declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link GlobalElementDeclaration global element} to the {@link ArtifactDeclaration}
   *
   * @param element the {@link GlobalElementDeclaration global element} to add
   * @return {@code this} declarer
   */
  public ArtifactDeclarer withGlobalElement(GlobalElementDeclaration element) {
    declaration.addGlobalElement(element);
    return this;
  }
}
