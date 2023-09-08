/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.fluent;

import static java.util.Collections.unmodifiableList;
import org.mule.runtime.app.declaration.api.ParameterElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.api.ParameterValueVisitor;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the configured {@link List} of {@link ParameterValue}s of a given {@link ParameterElementDeclaration}.
 *
 * @since 1.0
 */
public final class ParameterListValue implements ParameterValue {

  private List<ParameterValue> values = new LinkedList<>();

  ParameterListValue() {}

  public List<ParameterValue> getValues() {
    return unmodifiableList(values);
  }

  public void setValues(List<ParameterValue> values) {
    this.values = values;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(ParameterValueVisitor valueVisitor) {
    valueVisitor.visitListValue(this);
  }

  public static class Builder {

    private ParameterListValue listValue = new ParameterListValue();

    private Builder() {}

    public Builder withValue(String value) {
      listValue.values.add(ParameterSimpleValue.of(value));
      return this;
    }

    public Builder withValue(ParameterValue value) {
      listValue.values.add(value);
      return this;
    }

    public ParameterListValue build() {
      return listValue;
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

    ParameterListValue that = (ParameterListValue) o;

    return values.equals(that.values);
  }

  @Override
  public int hashCode() {
    return values.hashCode();
  }

}
