/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api.fluent;

import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newParameterGroup;
import org.mule.runtime.app.declaration.api.ParameterGroupElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterizedElementDeclaration;

import java.util.function.Consumer;

/**
 * Allows configuring a {@link ParameterizedElementDeclaration} through a fluent API
 *
 * @since 1.0
 */
public abstract class ParameterizedElementDeclarer<E extends ParameterizedElementDeclarer, D extends ParameterizedElementDeclaration>
    extends EnrichableElementDeclarer<E, D> {

  ParameterizedElementDeclarer(D declaration) {
    super(declaration);
  }

  /**
   * Adds a {@link ParameterGroupElementDeclaration parameter group} to {@code this} {@link ParameterizedElementDeclaration}
   *
   * @param group the {@link ParameterGroupElementDeclarer group} to add in {@code this} {@link ParameterizedElementDeclaration}
   * @return {@code this} declarer
   */
  public E withParameterGroup(ParameterGroupElementDeclaration group) {
    declaration.addParameterGroup(group);
    return (E) this;
  }

  /**
   * Adds a {@link ParameterGroupElementDeclaration parameter group} to {@code this} {@link ParameterizedElementDeclaration}
   *
   * @param groupEnricher the enricher that will configure the given {@link ParameterGroupElementDeclarer group} to add in
   *                      {@code this} {@link ParameterizedElementDeclaration}
   * @return {@code this} declarer
   */
  public E withParameterGroup(Consumer<ParameterGroupElementDeclarer> groupEnricher) {
    ParameterGroupElementDeclarer group = newParameterGroup();
    groupEnricher.accept(group);
    return withParameterGroup(group.getDeclaration());
  }

}
