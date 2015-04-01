/*
 * Copyright 2015 Mark Michaelis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mmichaelis.describeme.core.config;

import com.google.common.base.MoreObjects;

import org.jetbrains.annotations.NotNull;

/**
 * Some typical stream describer configurations.
 *
 * @since 1.0.0
 */
public enum DefaultStreamDescriberConfiguration implements StreamDescriberConfiguration {
  /**
   * Typical configuration for list types which also includes arrays.
   *
   * @since 1.0.0
   */
  LIST_CONFIGURATION("[", "]", ", "),
  /**
   * Typical configuration for maps. Key/Value separators are defined in the context
   * of entries.
   *
   * @since 1.0.0
   */
  MAP_CONFIGURATION("{", "}", ", "),;

  private final String startMarker;
  private final String endMarker;
  private final String elementSeparator;

  DefaultStreamDescriberConfiguration(
      @NotNull String startMarker,
      @NotNull String endMarker,
      @NotNull String elementSeparator) {
    this.startMarker = startMarker;
    this.endMarker = endMarker;
    this.elementSeparator = elementSeparator;
  }

  @NotNull
  @Override
  public String startMarker() {
    return startMarker;
  }

  @NotNull
  @Override
  public String endMarker() {
    return endMarker;
  }

  @NotNull
  @Override
  public String elementSeparator() {
    return elementSeparator;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("startMarker", startMarker)
        .add("endMarker", endMarker)
        .add("elementSeparator", elementSeparator)
        .toString();
  }
}
