/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.TopLevelParameterDeclaration;

/**
 * Allows configuring an {@link TopLevelParameterDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class TopLevelParameterDeclarer
    extends EnrichableElementDeclarer<TopLevelParameterDeclarer, TopLevelParameterDeclaration> {

  TopLevelParameterDeclarer(TopLevelParameterDeclaration declaration) {
    super(declaration);
  }

  public TopLevelParameterDeclarer withRefName(String name) {
    declaration.setRefName(name);
    return this;
  }

  public TopLevelParameterDeclarer withValue(ParameterObjectValue value) {
    declaration.setValue(value);
    return this;
  }
}
