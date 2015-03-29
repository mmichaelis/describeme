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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.github.mmichaelis.describeme.core.config.StreamDescriberConfiguration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Tests {@link AbstractStreamDescriber}.
 *
 * @since 1.0.0
 */
public class AbstractStreamDescriberTest {

  private static final StreamDescriberConfiguration
      SOME_CONFIGURATION =
      new MyStreamDescriberConfiguration();
  private static final Function<Object, Stream<?>> TO_STREAM_FUNCTION = new ToStreamFunction();

  @Test
  public void basicUseCase() throws Exception {
    Describer describer = new MyAbstractStreamDescriber(SOME_CONFIGURATION, TO_STREAM_FUNCTION);
    StringBuilder sb = new StringBuilder();
    describer.describeTo(sb, new Object[]{1, 2});
    assertThat("Normal array transformed.", sb.toString(), is(equalTo("<1|2>")));
  }

  private static class MyStreamDescriberConfiguration implements StreamDescriberConfiguration {

    MyStreamDescriberConfiguration() {
    }

    @NotNull
    @Override
    public String startMarker() {
      return "<";
    }

    @NotNull
    @Override
    public String endMarker() {
      return ">";
    }

    @NotNull
    @Override
    public String elementSeparator() {
      return "|";
    }
  }

  private static class MyAbstractStreamDescriber extends AbstractStreamDescriber {

    private final Function<Object, Stream<?>> function;

    MyAbstractStreamDescriber(StreamDescriberConfiguration configuration,
                              Function<Object, Stream<?>> function) {
      super(configuration);
      this.function = function;
    }

    @Override
    public boolean test(@Nullable Object value) {
      return true;
    }

    @NotNull
    @Override
    protected Stream<?> valueAsStream(@NotNull Object value) {
      return function.apply(value);
    }
  }

  private static class ToStreamFunction implements Function<Object, Stream<?>> {

    ToStreamFunction() {
    }

    @Override
    public Stream<?> apply(Object o) {
      if (o != null) {
        if (o.getClass().isArray()) {
          return Arrays.stream((Object[]) o);
        }
      }
      throw new IllegalStateException(format("Cannot find mapping for {0}", o));
    }
  }
}
