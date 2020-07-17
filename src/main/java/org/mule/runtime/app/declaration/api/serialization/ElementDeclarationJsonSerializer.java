/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.api.serialization;

import org.mule.api.annotation.NoImplement;
import org.mule.runtime.app.declaration.api.ElementDeclaration;
import org.mule.runtime.app.declaration.internal.serialization.DefaultElementDeclarationJsonSerializer;

/**
 * Serializer that can convert an {@link ElementDeclaration} into a readable and processable JSON representation and from a JSON
 * {@link String} to an {@link ElementDeclaration} instance
 *
 * @since 1.4.0
 */
@NoImplement
public interface ElementDeclarationJsonSerializer {

  /**
   * Creates a new instance of the {@link ElementDeclarationJsonSerializer}.
   * This serializer is capable of serializing and deserializing {@link ElementDeclaration}s
   * from JSON ({@link #deserialize(String, Class)} and to JSON ( {@link #serialize(ElementDeclaration)}
   */
  static ElementDeclarationJsonSerializer getDefault(boolean prettyPrint) {
    return new DefaultElementDeclarationJsonSerializer(prettyPrint);
  }

  /**
   * Serializes an {@link ElementDeclaration} into JSON
   *
   * @param declaration {@link ElementDeclaration} to be serialized
   * @return {@link String} JSON representation of the {@link ElementDeclaration}
   */
  String serialize(ElementDeclaration declaration);

  /**
   * Deserializes a JSON representation of an {@link ElementDeclaration}, to an actual instance of it.
   *
   * @param declaration serialized {@link ElementDeclaration}
   * @return an instance of {@link ElementDeclaration} based in the JSON
   */
  <T extends ElementDeclaration> T deserialize(String declaration, Class<T> clazz);

}

