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

import static org.hamcrest.Matchers.is;

import com.google.common.collect.ImmutableMap;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.function.Predicate;

/**
 * Tests {@link DefaultDescriber}.
 *
 * @since 1.0.0
 */
@RunWith(Parameterized.class)
public class DefaultDescriberTest {

  private final String message;
  private final Object value;
  private final Matcher<? super String> matcher;

  @SuppressWarnings("UnusedParameters")
  public DefaultDescriberTest(String ignoredId, String message, @Nullable Object value,
                              @NotNull Matcher<? super String> matcher) {
    this.message = message;
    this.value = value;
    this.matcher = matcher;
  }

  @Parameterized.Parameters(name = "{0}: {1} - {2} {3}")
  public static Collection<Object[]> data() throws IOException {
    return Arrays.asList(
        new Parameters()
            .id("1").assertThat("Null passes.").value(null).isStringValueOf(),
        new Parameters()
            .id("2").assertThat("String as is.").value("Lorem").isStringValueOf(),
        new Parameters()
            .id("3").assertThat("Object as is.").value(new Object()).isStringValueOf(),
        new Parameters()
            .id("4").assertThat("Number as is.").value(123.456d).isStringValueOf(),
        new Parameters()
            .id("5").assertThat("Date as is.").value(new Date()).isStringValueOf(),
        new Parameters()
            .id("6").assertThat("List as is.").value(Collections.emptyList()).isStringValueOf(),
        new Parameters()
            .id("7").assertThat("List as is.").value(Collections.singletonList(1))
            .isStringValueOf(),
        new Parameters()
            .id("8").assertThat("List as is.").value(Arrays.asList(1, 2)).isStringValueOf(),
        new Parameters()
            .id("9").assertThat("Map as is.").value(Collections.emptyMap()).isStringValueOf(),
        new Parameters()
            .id("10").assertThat("Map as is.").value(Collections.singletonMap("k", "v"))
            .isStringValueOf(),
        new Parameters()
            .id("11").assertThat("Map as is.").value(
            ImmutableMap.builder().put("a", "b").put("c", "d").build()).isStringValueOf(),
        new Parameters()
            .id("12").assertThat("Array special handling.").value(new Object[]{1, 2})
            .isArrayStringValueOf(),
        new Parameters()
            .id("13").assertThat("Lambda as is.")
            .value((Predicate<Boolean>) aBoolean -> false).isStringValueOf(),
        new Parameters()
            .id("14").assertThat("File as is.").value(File.createTempFile("pre", ".post"))
            .isStringValueOf(),
        // Just finish and allow to duplicate previous lines
        new Parameters()
            .id("END").assertThat("Null passes.").value(null).isStringValueOf()
    );
  }

  @Test
  public void test() throws Exception {
    Describer describer = new DefaultDescriber();
    StringBuilder stringBuilder = new StringBuilder();
    Assert.assertThat("Any value should applicable.", describer.test(value), is(true));
    describer.describeTo(stringBuilder, value);
    Assert.assertThat(message, stringBuilder.toString(), matcher);
  }

  private static final class Parameters {

    private String id;
    private String message;
    private Object value;
    private Matcher<? super String> matcher;

    Parameters() {
    }

    public Parameters id(String id) {
      this.id = id;
      return this;
    }

    public Parameters assertThat(String message) {
      this.message = message;
      return this;
    }

    public Parameters value(@Nullable Object value) {
      this.value = value;
      return this;
    }

    public Object[] matches(@NotNull Matcher<? super String> matcher) {
      this.matcher = matcher;
      return build();
    }

    public Object[] is(@NotNull String str) {
      return matches(Matchers.is(Matchers.equalTo(str)));
    }

    public Object[] isStringValueOf() {
      return is(String.valueOf(value));
    }

    public Object[] isArrayStringValueOf() {
      return is(Arrays.deepToString((Object[]) value));
    }

    private Object[] build() {
      return new Object[]{id, message, value, matcher};
    }
  }
}
