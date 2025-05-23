/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

import static org.mule.runtime.app.declaration.internal.utils.Preconditions.checkArgument;

import org.mule.api.annotation.NoExtend;

/**
 * Base of a programmatic descriptor of the configuration for any element present in a mule application.
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@NoExtend
@Deprecated
public abstract class ElementDeclaration implements IdentifiableElementDeclaration {

  protected String name;
  protected String declaringExtension;

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setName(String name) {
    checkArgument(name != null && !name.trim().isEmpty(), "Element name cannot be blank");
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDeclaringExtension() {
    return declaringExtension;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDeclaringExtension(String declaringExtension) {
    checkArgument(declaringExtension != null && !declaringExtension.trim().isEmpty(),
                  "Element declaringExtension cannot be blank");
    this.declaringExtension = declaringExtension;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ElementDeclaration)) {
      return false;
    }

    ElementDeclaration that = (ElementDeclaration) o;
    return name.equals(that.name) &&
        declaringExtension.equals(that.declaringExtension);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + declaringExtension.hashCode();
    return result;
  }
}
