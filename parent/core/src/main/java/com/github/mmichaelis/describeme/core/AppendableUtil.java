/*
 * Copyright 2015 Mark Michaelis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mmichaelis.describeme.core;

import java.io.IOException;

import javax.annotation.Nonnull;

import static java.text.MessageFormat.format;

/**
 * @since $$SINCE:2015-03-19$$
 */
public final class AppendableUtil {

  private AppendableUtil() {
  }

  public static void silentAppend(@Nonnull Appendable appendable, Object... values) {
    try {
      for (Object value : values) {
        appendable.append(String.valueOf(value));
      }
    } catch (IOException e) {
      throw new DescriberIOException(
          format("Unable to append values {0} to appendable {1}.", values, appendable), e);
    }
  }
}
