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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;

/**
 * Tests {@link Describer}.
 *
 * @since $SINCE$
 */
public class DescriberTest {

  @Rule
  public ErrorCollector errorCollector = new ErrorCollector();

  @Test
  public void basicUseCase() throws Exception {
    TestedDescriber describer = new TestedDescriber(o -> true, String::valueOf);
    String someValue = "Lorem";
    Appendable appendable = new StringBuilder();

    if (describer.test(someValue)) {
      describer.describeTo(appendable, someValue);
    }

    errorCollector.checkThat(
        "Appendable should be handed over.",
        describer.getLastAppendable(),
        is(theInstance(appendable)));
    errorCollector.checkThat(
        "Value should be handed over.",
        describer.getLastValue(),
        is(theInstance(someValue)));
    errorCollector.checkThat(
        "Should default to unlimited count.",
        describer.getLastMaxCount(),
        is(DescriberProperties.UNLIMITED));
  }

  private static final class TestedDescriber implements Describer {

    private final Predicate<? super Object> applicable;
    private final Function<Object, String> describer;
    private Object lastAppendable;
    private Object lastValue;
    private int lastMaxCount;

    TestedDescriber(Predicate<? super Object> applicable,
                    Function<Object, String> describer) {
      this.applicable = applicable;
      this.describer = describer;
    }

    @Override
    public boolean test(@Nullable Object value) {
      return applicable.test(value);
    }

    @Override
    public void describeTo(@NotNull Appendable appendable,
                           @Nullable Object value,
                           int maxCount) {
      lastAppendable = appendable;
      lastValue = value;
      lastMaxCount = maxCount;

      try {
        appendable.append(describer.apply(value));
      } catch (IOException e) {
        throw new DescriberIOException("Failure.", e);
      }
    }

    public Object getLastAppendable() {
      return lastAppendable;
    }

    public Object getLastValue() {
      return lastValue;
    }

    public int getLastMaxCount() {
      return lastMaxCount;
    }
  }
}