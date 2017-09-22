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
import static org.mule.runtime.app.declaration.api.component.location.Location.PARAMETERS;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A programmatic descriptor of a {@link org.mule.runtime.api.meta.model.parameter.ParameterizedModel} configuration.
 *
 * @since 1.0
 */
public abstract class ParameterizedElementDeclaration extends EnrichableElementDeclaration
    implements ElementDeclarationContainer {

  private Map<String, ParameterGroupElementDeclaration> groups = new LinkedHashMap<>();

  public ParameterizedElementDeclaration() {}

  /**
   * @return the {@link ParameterElementDeclaration group} with given {@code name}
   * or {@link Optional#empty()} if none is found.
   */
  public Optional<ParameterGroupElementDeclaration> getParameterGroup(String name) {
    return Optional.ofNullable(groups.get(name));
  }

  /**
   * @return the {@link List} of {@link ParameterElementDeclaration groups} associated with
   * {@code this} 
   */
  public List<ParameterGroupElementDeclaration> getParameterGroups() {
    return unmodifiableList(new ArrayList<>(groups.values()));
  }

  /**
   * Adds a {@link ParameterGroupElementDeclaration group} to {@code this} parametrized element declaration
   *
   * @param group the {@link ParameterGroupElementDeclaration} to associate to {@code this} element declaration
   */
  public void addParameterGroup(ParameterGroupElementDeclaration group) {
    group.setDeclaringExtension(this.declaringExtension);
    if (groups.containsKey(group.getName())) {
      groups.get(group.getName()).getParameters().addAll(group.getParameters());
    } else {
      this.groups.put(group.getName(), group);
    }
  }

  /**
   * Looks for a {@link ParameterElementDeclaration} contained by {@code this} declaration
   * based on the {@code parts} of a {@link Location}.
   *
   * @param parts the {@code parts} of a {@link Location} relative to {@code this} element
   * @return the {@link ElementDeclaration} located in the path created by the {@code parts}
   * or {@link Optional#empty()} if no {@link ElementDeclaration} was found in that location.
   */
  @Override
  public <T extends ElementDeclaration> Optional<T> findElement(List<String> parts) {
    if (parts.isEmpty()) {
      return Optional.of((T) this);
    }

    if (isParameterLocation(parts)) {
      String identifier = parts.get(1);
      if (isNumeric(identifier) && parseInt(identifier) < groups.size()) {
        return Optional.of((T) groups.get(parseInt(identifier)));
      } else {
        return (Optional<T>) groups.values().stream()
            .flatMap(g -> g.getParameters().stream())
            .filter(p -> p.getName().equals(identifier)).findFirst();
      }
    }

    return empty();
  }

  private boolean isParameterLocation(List<String> parts) {
    return parts.get(0).equals(PARAMETERS) && parts.size() == 2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ParameterizedElementDeclaration) || !super.equals(o)) {
      return false;
    }

    ParameterizedElementDeclaration that = (ParameterizedElementDeclaration) o;
    return groups.equals(that.groups);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + groups.hashCode();
    return result;
  }
}
