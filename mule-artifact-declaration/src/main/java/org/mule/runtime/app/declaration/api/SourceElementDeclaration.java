/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

/**
 * A programmatic descriptor of a {@link org.mule.runtime.api.meta.model.source.SourceModel} configuration.
 *
 * @since 1.0
 */
public final class SourceElementDeclaration extends ComponentElementDeclaration {

  public SourceElementDeclaration() {}

  public SourceElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  @Override
  public void accept(ComponentElementDeclarationVisitor visitor) {
    visitor.visitSourceElementDeclaration(this);
  }

}
