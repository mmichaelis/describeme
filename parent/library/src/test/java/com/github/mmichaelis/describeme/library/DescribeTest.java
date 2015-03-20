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

package com.github.mmichaelis.describeme.library;

import com.github.mmichaelis.describeme.core.Describe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Formattable;
import java.util.Formatter;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class DescribeTest {

  private static final Object SOME_OBJECT = new Object();
  private static final Object[] SOME_OBJECT_ARRAY = new Object[]{};
  private static final Consumer<String> SOME_CONSUMER = (Consumer<String>) s -> {
  };
  private final Object toDescribe;
  private final String expectedDescription;

  public DescribeTest(@Nullable Object toDescribe, @Nonnull String expectedDescription) {
    this.toDescribe = toDescribe;
    this.expectedDescription = expectedDescription;
  }

  @Nonnull
  @Parameters(name = "Test {index}: {0}, expecting toString: {1}")
  public static Collection<Object[]> data() {
    //noinspection RedundantCast
    return Arrays.asList(
        new Object[][]{
            {null, "null"},
            {true, "true"},
            {false, "false"},
            {(byte) 0b0010_0101, "37"},
            {(short) 0b0010_0101, "37"},
            {0b11010010_01101001_10010100_10010010, "-764,832,622"},
            {12, "12"},
            {12L, "12"},
            {'c', "'c'"},
            {"Lorem Ipsum", "\"Lorem Ipsum\""},
            {new StringBuilder("Lorem Ipsum Dolor"), "\"Lorem Ipsum ...\""},
            {new StringBuffer("Lorem Ipsum"), "\"Lorem Ipsum\""},
            {1.23456789123456789f, "1.235"},
            {1.23456789123456789d, "1.235"},
            {1.234e2, "123.4"},
            {SOME_OBJECT, "java.lang.Ob..."},
            {SOME_OBJECT_ARRAY, "[]"},
            {new Integer[]{1, 2}, "[1, 2]"},
            // Deep Test
            {new Object[]{1, new Object[]{2, new Object[]{3, new Integer[]{4}}}}, "[1, [2, [3, [...]]]]"},
            {new Object[]{1, new Object[]{2, new Object[]{3, "Test"}}}, "[1, [2, [3, \"Test\"]]]"},
            {Collections.<Integer>emptyList(), "[]"},
            {Arrays.asList(1, 2), "[1, 2]"},
            {Arrays.asList(1, "Test"), "[1, \"Test\"]"},
            {Arrays.asList(1, "Test").iterator(), "[1, \"Test\"]"},
            {Arrays.asList(1, "Test").stream(), "[1, \"Test\"]"},
            // Can we do better for consumers?
            {SOME_CONSUMER, String.valueOf(SOME_CONSUMER)},
            // Lambdas can format themselves being Formattable
            {(ToStringInterface) () -> "Lorem Ipsum Dolor", "Lorem Ipsum ..."},
            {new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...]"},
            {Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13),
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...]"},
            {Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13).stream(),
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...]"},
            {Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13).iterator(),
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...]"},
            // No truncation to apply here. It is mainly meant for length of elements
            // rather than the length of Strings.
            {SOME_OBJECT.getClass(), String.valueOf(SOME_OBJECT.getClass())},
            {SOME_OBJECT_ARRAY.getClass(), String.valueOf(SOME_OBJECT_ARRAY.getClass())},
        }
    );
  }

  @Test
  public void test() throws Exception {
    int maxDepth = 3;
    int maxCount = 12;
    assertThat("expecting toString value", Describe.describe(toDescribe, maxDepth, maxCount),
               is(expectedDescription));
  }

  // Idea taken from:
  // http://stackoverflow.com/questions/23628631/how-to-make-a-lambda-expression-define-tostring-in-java-8
  @FunctionalInterface
  private interface ToStringInterface extends Formattable {

    String asString();

    @Override
    default void formatTo(Formatter formatter, int flags, int width, int precision) {
      String str = asString();
      //noinspection resource
      formatter.format(MessageFormat.format("%1${0}.{1}s", width, precision), str);
      if (str.length() > precision) {
        try {
          formatter.out().append("...");
        } catch (IOException ignored) {
        }
      }
    }
  }
}
