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

package com.github.mmichaelis.describeme.library;

import static com.github.mmichaelis.describeme.core.AppendableUtil.silentAppend;
import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Objects.requireNonNull;

import com.github.mmichaelis.describeme.core.RecursiveDescriber;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

/**
 * Describes throwables with their message and cause.
 *
 * @since $SINCE$
 */
public class ThrowableDescriber implements RecursiveDescriber {

  @Override
  public void describeTo(@NotNull Appendable appendable,
                         @Nullable Object value,
                         int maxCount,
                         @NotNull BiConsumer<Object, Object> recursiveMeAndOtherConsumer) {
    requireNonNull(appendable, "appendable must not be null.");
    Throwable throwable = (Throwable) validatedValue(value);
    assert throwable != null : "Validation should have prevented null value.";
    silentAppend(appendable, throwable.getClass().getName());
    Throwable cause = throwable.getCause();
    String throwableMessage = throwable.getMessage();
    // Quirk: If only cause but no message is given, the throwable message defaults to the
    // toString() result of the cause especially starts with the cause's class name. As we
    // want to recurse on our own, this state has to be guessed and message output prevented in
    // order not to duplicate the message.
    boolean hasMessage = !isNullOrEmpty(throwableMessage)
                         && ((cause == null)
                             || !throwableMessage.startsWith(cause.getClass().getName()));
    if (hasMessage) {
      silentAppend(appendable, ": ");
      silentAppend(appendable, throwableMessage);
    }
    if (cause != null) {
      if (!hasMessage) {
        silentAppend(appendable, ":");
      }
      silentAppend(appendable, " (cause: ");
      recursiveMeAndOtherConsumer.accept(throwable, cause);
      silentAppend(appendable, ")");
    }
  }

  @Override
  public boolean test(@Nullable Object value) {
    return value instanceof Throwable;
  }
}
