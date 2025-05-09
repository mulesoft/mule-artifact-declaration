/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;

import java.util.List;
import java.util.Optional;

/**
 * Adds lookup methods to find a given {@link ElementDeclaration} that is part of {@code this} container declaration. A container
 * is defined as any {@link ElementDeclaration} that contains any another {@link ElementDeclaration} of any kind.
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@NoImplement
@Deprecated
public interface ElementDeclarationContainer {

  /**
   * Looks for an {@link ElementDeclaration} contained by {@code this} declaration based on the {@code parts} of a
   * {@link Location}.
   *
   * @param parts the {@code parts} of a {@link Location} relative to {@code this} element
   * @return the {@link ElementDeclaration} located in the path created by the {@code parts} or {@link Optional#empty()} if no
   *         {@link ElementDeclaration} was found in that location.
   */
  <T extends ElementDeclaration> Optional<T> findElement(List<String> parts);
}
