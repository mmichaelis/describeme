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

/**
 * @since $$SINCE:2015-03-17$$
 */
public final class DescriberProperties {

  public static final int UNLIMITED = -1;
  @SuppressWarnings("AccessOfSystemProperties")
  public static final
  int MAX_DEPTH = Integer.parseInt(System.getProperty("describe.max.depth", "-1"));
  @SuppressWarnings("AccessOfSystemProperties")
  public static final
  int MAX_COUNT = Integer.parseInt(System.getProperty("describe.max.count", "-1"));
  public static final String ELLIPSIS = "...";
  static final int ELLIPSIS_LENGTH = ELLIPSIS.length();
  public static final String RECURSION_PLACEHOLDER = "[...]";

  private DescriberProperties() {
  }
}
