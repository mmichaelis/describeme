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

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.BiConsumer;
import java.util.stream.StreamSupport;

/**
 * <p>
 * The root describer which delegates to all other describers. It accesses
 * the service loader to find all available describers and uses a fallback
 * describer if for a value no describers exist.
 * </p>
 * <p>
 * Retrieve the singleton instance of the root describer via
 * {@link #rootDescriber()}.
 * </p>
 *
 * @since 1.0.0
 */
final class RootDescriber implements RecursiveDescriber {

  private static final Logger LOG = getLogger(RootDescriber.class);

  @SuppressWarnings("StaticVariableOfConcreteClass")
  private static final RecursiveDescriber INSTANCE = new RootDescriber();

  private static final ServiceLoader<Describer> DESCRIBER_SERVICE_LOADER;
  private static final Describer FALLBACK_DESCRIBER = new DefaultDescriber();

  /**
   * Retrieve the instance of the root describer.
   *
   * @return instance
   */
  @NotNull
  public static RecursiveDescriber rootDescriber() {
    return INSTANCE;
  }

  @NotNull
  private static Optional<Describer> getDescriberFor(@Nullable Object object) {
    return StreamSupport
        .stream(DESCRIBER_SERVICE_LOADER.spliterator(), false)
        .filter(describer -> describer.test(object))
        .findFirst();
  }

  @NotNull
  private static Describer describerFor(@Nullable Object object) {
    Describer describer = getDescriberFor(object).orElse(FALLBACK_DESCRIBER);
    LOG.debug("Provided describer for {}: {}", object, describer);
    return describer;
  }

  @Override
  public boolean test(@Nullable Object value) {
    return true;
  }

  @Override
  public void describeTo(@NotNull Appendable appendable, @Nullable Object value,
                         int maxCount,
                         @NotNull BiConsumer<Object, Object> recursiveMeAndOtherConsumer) {
    requireNonNull(recursiveMeAndOtherConsumer, "recursiveMeAndOtherConsumer must not be null.");
    requireNonNull(appendable, "appendable must not be null.");
    Describer describer = describerFor(value);
    if (describer instanceof RecursiveDescriber) {
      RecursiveDescriber recursiveDescriber = (RecursiveDescriber) describer;
      recursiveDescriber.describeTo(appendable, value, maxCount,
                                    recursiveMeAndOtherConsumer);
    } else {
      describer.describeTo(appendable, value, maxCount);
    }
  }

  /**
   * Initializes the ServiceLoader and lists all describers found in the
   * given priority with the first one having the highest priority and
   * the last one serving as fallback.
   */
  static {
    DESCRIBER_SERVICE_LOADER = ServiceLoader.load(Describer.class);
    if (LOG.isDebugEnabled()) {
      String startSeparator = String.format("%n\t");
      String separator = String.format(",%n\t");
      LOG.debug("Registered describers:{}{}",
                startSeparator,
                StreamSupport.stream(DESCRIBER_SERVICE_LOADER.spliterator(), false)
                    .map(obj -> obj.getClass().getName())
                    .collect(joining(separator)));
    }
  }

}
