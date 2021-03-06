/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

/**
 * Extends the concept of a named element to provides a unique way of identifying an element declaration for a given
 * {@link org.mule.runtime.api.meta.model.ExtensionModel}
 *
 * @since 1.0
 */
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
