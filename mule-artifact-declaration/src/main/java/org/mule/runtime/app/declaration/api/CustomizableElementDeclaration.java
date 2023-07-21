/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 */
package org.mule.runtime.app.declaration.api;

import org.mule.api.annotation.NoImplement;

import java.util.List;

/**
 * A declaration which can be augmented with custom pieces of information that are not part of the actual element model.
 * <p>
 * This is useful for pieces of data that might be specific to particular representations of the declared artifact, like custom
 * XML properties or conventions that are not related to the artifact model but necessary for not loosing information during its
 * serialization/deserialization.
 * <p>
 *
 * @since 1.0
 */
@NoImplement
public interface CustomizableElementDeclaration {

  /**
   * The {@code customConfigurationParameters} refer to parameters that are part of a declaration configuration but do not match
   * to an element in the model, thus being custom of how the declaration was created.
   *
   * An example for this would be having a {@code <ns:identifier doc:name="myCustomName">}, where {@code doc:name} is a custom
   * attribute that enriches the XML representation of an {@link ElementDeclaration}
   *
   * @return the {@link List} of {@link ParameterElementDeclaration parameters} associated with {@code this}
   */
  List<ParameterElementDeclaration> getCustomConfigurationParameters();

  /**
   * Adds a {@link ParameterElementDeclaration custom parameter} to {@code this} enrichable element declaration.
   *
   * The {@code customConfigurationParameters} refer to parameters that are part of a declaration configuration but do not match
   * to an element in the model, thus being custom of how the declaration was created.
   *
   * An example for this would be having a {@code <ns:identifier doc:name="myCustomName">}, where {@code doc:name} is a custom
   * attribute that enriches the XML representation of an {@link ElementDeclaration}
   *
   * No validation of any kind will be performed over this {@code customParameter} and its value.
   *
   * @param customParameter the {@link ParameterElementDeclaration} to associate to {@code this} element declaration
   */
  void addCustomConfigurationParameter(ParameterElementDeclaration customParameter);

}
