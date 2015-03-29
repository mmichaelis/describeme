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

import static com.github.mmichaelis.describeme.core.config.DefaultStreamDescriberConfiguration.MAP_CONFIGURATION;
import static java.util.Objects.requireNonNull;

import com.github.mmichaelis.describeme.core.AbstractStreamDescriber;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @since 1.0.0
 */
public class MapDescriber extends AbstractStreamDescriber {

  public MapDescriber() {
    super(MAP_CONFIGURATION);
  }

  @Override
  public boolean test(@Nullable Object value) {
    return value instanceof Map<?, ?>;
  }

  @NotNull
  @Override
  @Contract("null -> fail")
  protected Stream<?> valueAsStream(@NotNull Object value) {
    Map<?, ?> map = (Map<?, ?>) requireNonNull(value, "value must not be null.");
    return map.entrySet().stream();
  }

}
