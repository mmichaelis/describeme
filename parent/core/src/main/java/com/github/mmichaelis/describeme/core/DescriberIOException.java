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

/**
 * @since $$SINCE:2015-03-18$$
 */
public class DescriberIOException extends DescriberException {

  private static final long serialVersionUID = -2610615491164970753L;

  public DescriberIOException() {
  }

  public DescriberIOException(String message) {
    super(message);
  }

  public DescriberIOException(String message, Throwable cause) {
    super(message, cause);
  }

  public DescriberIOException(Throwable cause) {
    super(cause);
  }

  protected DescriberIOException(String message, Throwable cause, boolean enableSuppression,
                                 boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
