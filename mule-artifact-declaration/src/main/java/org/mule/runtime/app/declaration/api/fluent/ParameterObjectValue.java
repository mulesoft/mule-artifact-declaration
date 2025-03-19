/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.fluent;

import static java.util.Collections.unmodifiableMap;

import org.mule.runtime.app.declaration.api.ParameterElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.api.ParameterValueVisitor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents the configured complex value of a given {@link ParameterElementDeclaration}. An ObjectValue parameter contains
 * multiple parameter values.
 *
 * @since 1.0
 * @deprecated Use mule-artifact-ast instead.
 */
@Deprecated
public final class ParameterObjectValue implements ParameterValue {

  private Map<String, ParameterValue> parameters = new LinkedHashMap<>();
  private String typeId;

  ParameterObjectValue() {}

  public String getTypeId() {
    return typeId;
  }

  public Map<String, ParameterValue> getParameters() {
    return unmodifiableMap(parameters);
  }

  public void setParameters(Map<String, ParameterValue> parameters) {
    this.parameters = parameters;
  }

  public void setTypeId(String typeId) {
    this.typeId = typeId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(ParameterValueVisitor valueVisitor) {
    valueVisitor.visitObjectValue(this);
  }

  public static class Builder implements ParameterizedBuilder<String, ParameterValue, Builder> {

    private ParameterObjectValue objectValue = new ParameterObjectValue();

    private Builder() {}

    public Builder ofType(String typeId) {
      objectValue.typeId = typeId;
      return this;
    }

    public Builder withParameter(String name, String value) {
      objectValue.parameters.put(name, ParameterSimpleValue.of(value));
      return this;
    }

    @Override
    public Builder withParameter(String name, ParameterValue value) {
      objectValue.parameters.put(name, value);
      return this;
    }

    public ParameterObjectValue build() {
      return objectValue;
    }
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ParameterObjectValue that = (ParameterObjectValue) o;

    return parameters.equals(that.parameters) && (typeId != null ? typeId.equals(that.typeId) : that.typeId == null);
  }

  @Override
  public int hashCode() {
    int result = parameters.hashCode();
    result = 31 * result + (typeId != null ? typeId.hashCode() : 0);
    return result;
  }

}
