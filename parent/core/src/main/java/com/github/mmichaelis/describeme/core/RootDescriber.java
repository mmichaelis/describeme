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

import org.slf4j.Logger;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.BiConsumer;
import java.util.stream.StreamSupport;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.stream.Collectors.joining;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @since $SINCE$
 */
final class RootDescriber implements RecursiveDescriber {

  private static final Logger LOG = getLogger(RootDescriber.class);

  @SuppressWarnings("StaticVariableOfConcreteClass")
  private static final RecursiveDescriber INSTANCE = new RootDescriber();

  private static final ServiceLoader<Describer> DESCRIBER_SERVICE_LOADER;
  private static final Describer FALLBACK_DESCRIBER = new DefaultDescriber();

  static {
    DESCRIBER_SERVICE_LOADER = ServiceLoader.load(Describer.class);
    LOG.info("Registered describers:\n\t{}",
             StreamSupport.stream(DESCRIBER_SERVICE_LOADER.spliterator(), false)
                 .map(obj -> obj.getClass().getName())
                 .collect(joining(",\n\t")));
  }

  @Nonnull
  public static RecursiveDescriber rootDescriber() {
    return INSTANCE;
  }

  @Nonnull
  private static Optional<Describer> getDescriberFor(@Nullable Object object) {
    return StreamSupport
        .stream(DESCRIBER_SERVICE_LOADER.spliterator(), false)
        .filter(describer -> describer.test(object))
        .findFirst();
  }

  @Nonnull
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
  public void recursiveDescribeTo(@Nonnull Appendable appendable, @Nullable Object value,
                                  int maxCount,
                                  @Nonnull BiConsumer<Object, Object> recursiveConsumer) {
    Describer describer = describerFor(value);
    if (describer instanceof RecursiveDescriber) {
      RecursiveDescriber recursiveDescriber = (RecursiveDescriber) describer;
      recursiveDescriber.describeTo(appendable, value, maxCount, recursiveConsumer);
    } else {
      describer.describeTo(appendable, value, maxCount);
    }
  }

}
