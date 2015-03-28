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

package com.github.mmichaelis.describeme.core;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Properties within DescribeMe.
 *
 * @since $SINCE$
 */
public final class DescriberProperties {

  /**
   * System property to set to define a maximum recursion depth. Only applies
   * when not explicitly set in code.
   *
   * @since $SINCE$
   */
  public static final String P_DESCRIBE_MAX_DEPTH = "describe.max.depth";
  /**
   * System property to set to reduce the count of elements to display. Some Decribers
   * might ignore it. Only applies when count is not explicitly set in code.
   *
   * @since $SINCE$
   */
  public static final String P_DESCRIBE_MAX_COUNT = "describe.max.count";
  /**
   * Signals unlimited depth or count.
   *
   * @since $SINCE$
   */
  public static final int UNLIMITED = -1;
  /**
   * Default maximum depth. Can be controlled via system property {@link #P_DESCRIBE_MAX_DEPTH}.
   *
   * @since $SINCE$
   */
  public static final int MAX_DEPTH;
  /**
   * Default maximum count. Can be controlled via system property {@link #P_DESCRIBE_MAX_COUNT}.
   *
   * @since $SINCE$
   */
  public static final int MAX_COUNT;
  /**
   * Used to represent truncated values because of length restriction.
   *
   * @since $SINCE$
   */
  public static final String ELLIPSIS = "...";
  /**
   * Used to represent truncated values either because of self-reference in recursive structures
   * or because maximum depth is reached.
   *
   * @since $SINCE$
   */
  public static final String RECURSION_PLACEHOLDER = "[...]";
  private static final Logger LOG = getLogger(DescriberProperties.class);

  /**
   * Utility class only.
   *
   * @since $SINCE$
   */
  private DescriberProperties() {
  }

  /**
   * Tries to get a system property as robust as possible. Default value if not set
   * or on error is {@link #UNLIMITED}.
   *
   * @param key key to read from system properties
   * @return value successfully read from system property or default
   * @since $SINCE$
   */
  private static int getSystemNumberProperty(@NotNull String key) {
    int maxDepth;
    try {
      maxDepth = Integer.parseInt(System.getProperty(key, Integer.toString(UNLIMITED)));
    } catch (SecurityException | NumberFormatException e) {
      LOG.warn("Failed to retrieve and parse system property {}. Fallback to {}.",
               key, UNLIMITED, e);
      maxDepth = UNLIMITED;
    }
    return maxDepth;
  }

  /**
   * Securely initialized values from system properties.
   * @since $SINCE$
   */
  static {
    MAX_DEPTH = getSystemNumberProperty(P_DESCRIBE_MAX_DEPTH);
    MAX_COUNT = getSystemNumberProperty(P_DESCRIBE_MAX_COUNT);
  }
}
