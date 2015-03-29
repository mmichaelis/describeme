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

import static com.github.mmichaelis.describeme.core.config.DefaultStreamDescriberConfiguration.LIST_CONFIGURATION;
import static java.util.Objects.requireNonNull;

import com.github.mmichaelis.describeme.core.AbstractStreamDescriber;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @since 1.0.0
 */
public class IteratorDescriber extends AbstractStreamDescriber {

  public IteratorDescriber() {
    super(LIST_CONFIGURATION);
  }

  @Override
  public boolean test(@Nullable Object value) {
    return value instanceof Iterator<?>;
  }

  @NotNull
  @Override
  @Contract("null -> fail")
  protected Stream<?> valueAsStream(@NotNull Object value) {
    Iterator<?> iterator = (Iterator<?>) requireNonNull(value, "value must not be null.");
    Spliterator<Object>
        spliterator =
        Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);
    return StreamSupport.stream(spliterator, false);
  }

}
