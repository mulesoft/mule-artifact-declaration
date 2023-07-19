/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

/**
 * A programmatic descriptor of a {@link org.mule.runtime.api.meta.model.connection.ConnectionProviderModel} configuration.
 *
 * @since 1.0
 */
public final class ConnectionElementDeclaration extends ParameterizedElementDeclaration {

  public ConnectionElementDeclaration() {}

  public ConnectionElementDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  @Override
  public void accept(ParameterizedElementDeclarationVisitor visitor) {
    visitor.visitConnectionElementDeclaration(this);
  }

}
