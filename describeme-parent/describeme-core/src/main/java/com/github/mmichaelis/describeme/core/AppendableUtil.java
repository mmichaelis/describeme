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

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * <p>
 * Utility for silently appending to any appendable. As some appendables might throw
 * IOExceptions they are caught and wrapped into a dedicated
 * {@link DescriberIoException}.
 * </p>
 *
 * @since 1.0.0
 */
public final class AppendableUtil {

  /**
   * Utility class, do not create.
   */
  private AppendableUtil() {
  }

  /**
   * Appends values to appendable and wraps possible IOExceptions.
   *
   * @param appendable appendable to append values to
   * @param values     values to add, will be transformed via {@code String.valueOf()}
   */
  @Contract("null, _ -> fail")
  public static void silentAppend(@NotNull Appendable appendable, @Nullable Object... values) {
    requireNonNull(appendable, "appendable must be set.");
    try {
      if (values == null) {
        appendable.append(String.valueOf((Object) null));
      } else {
        for (Object value : values) {
          appendable.append(String.valueOf(value));
        }
      }
    } catch (IOException e) {
      throw new DescriberIoException(
          format("Unable to append values {0} to appendable {1}.", values, appendable), e);
    }
  }
}
