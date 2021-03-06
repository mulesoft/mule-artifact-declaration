/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

import static java.lang.Integer.parseInt;
import static java.util.Collections.unmodifiableList;
import static java.util.Optional.empty;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;
import static org.mule.runtime.app.declaration.api.component.location.Location.ERROR_HANDLER;
import static org.mule.runtime.app.declaration.api.component.location.Location.PROCESSORS;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * A programmatic descriptor of a {@link org.mule.runtime.api.meta.model.ComponentModel} configuration.
 *
 * @since 1.0
 */
public abstract class ComponentElementDeclaration<T extends ComponentElementDeclaration>
    extends ParameterizedElementDeclaration implements ElementDeclarationContainer {

  private String configRef;
  private List<ComponentElementDeclaration> components = new LinkedList<>();

  public ComponentElementDeclaration() {}

  /**
   * Sets the {@code config-ref} parameter required for {@code this} component
   * 
   * @param configRef the {@link ReferableElementDeclaration#getRefName()} of a {@link ConfigurationElementDeclaration}
   */
  public void setConfigRef(String configRef) {
    this.configRef = configRef;
  }

  /**
   * @return the {@code config-ref} parameter associated to {@code this} component
   */
  public String getConfigRef() {
    return configRef;
  }

  /**
   * @return the {@link List} of {@link ComponentElementDeclaration flows} contained by {@code this}
   *         {@link ComponentElementDeclaration}
   */
  public List<ComponentElementDeclaration> getComponents() {
    return unmodifiableList(components);
  }

  /**
   * Adds a {@link ComponentElementDeclaration} as a component contained by {@code this} {@link ComponentElementDeclaration scope}
   * 
   * @param declaration the {@link ComponentElementDeclaration} child of {@code this} {@link ComponentElementDeclaration scope}
   * @return {@code this} {@link ComponentElementDeclaration scope}
   */
  public T addComponent(ComponentElementDeclaration declaration) {
    components.add(declaration);
    return (T) this;
  }

  /**
   * Adds a {@link ComponentElementDeclaration} as a component contained by {@code this} {@link ComponentElementDeclaration scope}
   * 
   * @param declaration the {@link ComponentElementDeclaration} child of {@code this} {@link ComponentElementDeclaration scope}
   * @return {@code this} {@link ComponentElementDeclaration scope}
   */
  public T addComponent(int index, ComponentElementDeclaration declaration) {
    components.add(index, declaration);
    return (T) this;
  }

  /**
   * Looks for an {@link ElementDeclaration} contained by {@code this} declaration based on the {@code parts} of a
   * {@link Location}.
   *
   * @param parts the {@code parts} of a {@link Location} relative to {@code this} element
   * @return the {@link ElementDeclaration} located in the path created by the {@code parts} or {@link Optional#empty()} if no
   *         {@link ElementDeclaration} was found in that location.
   */
  @Override
  public <T extends ElementDeclaration> Optional<T> findElement(List<String> parts) {
    if (parts.isEmpty()) {
      return Optional.of((T) this);
    }

    if (!components.isEmpty()) {
      String identifier = parts.get(0);
      if (isNumeric(identifier) && parseInt(identifier) < components.size()) {
        return components.get(parseInt(identifier)).findElement(parts.subList(1, parts.size()));

      } else if (identifier.equals(ERROR_HANDLER)) {
        ComponentElementDeclaration last = components.get(components.size() - 1);
        return last instanceof ComponentElementDeclaration ? last.findElement(parts.subList(1, parts.size())) : empty();

      } else if (isProcessorLocation(parts)) {
        int processorIndex = parseInt(parts.get(1));

        if (components.get(0) instanceof SourceElementDeclaration) {
          if (components.size() == 1) {
            return empty();
          }

          processorIndex += 1;
        }

        return components.get(processorIndex).findElement(parts.subList(2, parts.size()));
      }
    }

    return super.findElement(parts);
  }

  @Override
  public void accept(ParameterizedElementDeclarationVisitor visitor) {
    visitor.visitComponentElementDeclaration(this);
  }

  private boolean isProcessorLocation(List<String> parts) {
    return parts.get(0).equals(PROCESSORS) && parts.size() > 1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass() || !super.equals(o)) {
      return false;
    }

    return reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return reflectionHashCode(this);
  }
}
