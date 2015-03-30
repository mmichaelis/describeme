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

import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * Wraps IOExceptions which might be raised while appending to appendables.
 * </p>
 *
 * @since 1.0.0
 */
public class DescriberIoException extends DescriberException {

  private static final long serialVersionUID = -2610615491164970753L;

  /**
   * <p>
   * Constructs a new describer IO exception with the specified detail message and
   * cause.
   * </p>
   *
   * @param message the detail message (which is saved for later retrieval
   *                by the {@link #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the
   *                {@link #getCause()} method).  (A {@code null} value is
   *                permitted, and indicates that the cause is nonexistent or
   *                unknown.)
   * @since $SINCE$
   */
  public DescriberIoException(@Nullable String message, @Nullable Throwable cause) {
    super(message, cause);
  }

}
