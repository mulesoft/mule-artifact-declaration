/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
