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

import org.jetbrains.annotations.NotNull;

/**
 * Configuration options for the Stream Describer.
 *
 * @since 1.0.0
 */
public interface StreamDescriberConfiguration {

  /**
   * Marker string to place at the start of the stream description.
   *
   * @return start marker
   */
  @NotNull
  String startMarker();

  /**
   * Marker string to place at the end of the stream description.
   *
   * @return end marker
   */
  @NotNull
  String endMarker();

  /**
   * String to place between the elements of a stream.
   *
   * @return element separator
   */
  @NotNull
  String elementSeparator();
}
