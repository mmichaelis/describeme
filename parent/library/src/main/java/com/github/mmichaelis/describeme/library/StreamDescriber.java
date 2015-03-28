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

import com.github.mmichaelis.describeme.core.AbstractStreamDescriber;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

/**
 * @since $$SINCE:2015-03-16$$
 */
public class StreamDescriber extends AbstractStreamDescriber {

  @Override
  public boolean test(@Nullable Object value) {
    return value instanceof Stream<?>;
  }

  @NotNull
  @Override
  protected Stream<?> valueAsStream(@NotNull Object value) {
    return (Stream<?>) value;
  }
}
