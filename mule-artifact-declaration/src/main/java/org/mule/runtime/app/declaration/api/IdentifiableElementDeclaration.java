/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;

/**
 * Extends the concept of a named element to provides a unique way of identifying an element declaration for a given
 * {@link org.mule.runtime.api.meta.model.ExtensionModel}
 *
 * @since 1.0
 */
@NoImplement
public interface IdentifiableElementDeclaration extends NamedElementDeclaration {

  /**
   * @return the {@link org.mule.runtime.api.meta.model.ExtensionModel#getName name} of the extension containing the model
   *         associated to {@code this} element declaration
   */
  String getDeclaringExtension();

  /**
   * Sets the {@link org.mule.runtime.api.meta.model.ExtensionModel#getName name} of the extension containing the model associated
   * to {@code this} element declaration
   *
   * @param declaringExtension the extension's {@link org.mule.runtime.api.meta.model.ExtensionModel#getName name}
   */
  void setDeclaringExtension(String declaringExtension);

}
