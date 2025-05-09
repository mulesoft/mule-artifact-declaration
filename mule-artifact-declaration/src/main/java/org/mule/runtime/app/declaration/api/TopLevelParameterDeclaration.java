/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

import static org.mule.runtime.app.declaration.internal.utils.Preconditions.checkArgument;

import org.mule.metadata.api.model.ObjectType;
import org.mule.runtime.app.declaration.api.fluent.ParameterObjectValue;

import java.util.List;
import java.util.Optional;

/**
 * A programmatic descriptor of an {@link ObjectType} configuration that will be used as a global element of the mule application.
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@Deprecated
public final class TopLevelParameterDeclaration extends EnrichableElementDeclaration
    implements ReferableElementDeclaration, GlobalElementDeclaration {

  private String elementName;
  private ParameterObjectValue value;

  public TopLevelParameterDeclaration() {}

  public TopLevelParameterDeclaration(String extension, String name) {
    setDeclaringExtension(extension);
    setName(name);
  }

  /**
   * Associates a {@link ParameterObjectValue} as part of {@code this} parameter configuration declaration
   * <p>
   * Since a {@link TopLevelParameterDeclaration} represents an {@link ObjectType} global declaration, only a
   * {@link ParameterObjectValue} can be associated to it.
   *
   * @param value the {@link ParameterObjectValue} to associate with {@code this} parameter configuration
   */
  public void setValue(ParameterObjectValue value) {
    checkArgument(value != null, "The value of the parameter cannot be null");
    this.value = value;
  }

  /**
   * @return the {@link ParameterObjectValue} configured for {@code this} parameter declaration
   */
  public ParameterObjectValue getValue() {
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRefName() {
    return elementName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRefName(String referableName) {
    checkArgument(referableName != null && !referableName.trim().isEmpty(),
                  "Element referableName cannot be blank");
    this.elementName = referableName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TopLevelParameterDeclaration) || !super.equals(o)) {
      return false;
    }

    TopLevelParameterDeclaration that = (TopLevelParameterDeclaration) o;
    return declaringExtension.equals(that.declaringExtension) && elementName.equals(that.elementName) && value.equals(that.value);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + declaringExtension.hashCode();
    result = 31 * result + elementName.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }

  @Override
  public void accept(GlobalElementDeclarationVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public <T extends ElementDeclaration> Optional<T> findElement(List<String> parts) {
    if (parts.isEmpty()) {
      return Optional.of((T) this);
    }

    return Optional.empty();
  }
}
