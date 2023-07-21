/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import org.mule.runtime.app.declaration.api.ConfigurationElementDeclaration;
import org.mule.runtime.app.declaration.api.ConnectionElementDeclaration;

/**
 * Allows configuring an {@link ConfigurationElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public final class ConfigurationElementDeclarer
    extends ParameterizedElementDeclarer<ConfigurationElementDeclarer, ConfigurationElementDeclaration> {

  /**
   * Creates a new instance
   */
  ConfigurationElementDeclarer(ConfigurationElementDeclaration declaration) {
    super(declaration);
  }

  public ConfigurationElementDeclarer withConnection(ConnectionElementDeclaration connection) {
    declaration.setConnection(connection);
    return this;
  }

  public ConfigurationElementDeclarer withRefName(String name) {
    declaration.setRefName(name);
    return this;
  }

}
