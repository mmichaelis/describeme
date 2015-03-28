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

import com.github.mmichaelis.describeme.core.Describe;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class DescribeTest {

  private static final Object SOME_OBJECT = new Object();
  private static final Object[] SOME_OBJECT_ARRAY = {};
  private static final Consumer<String> SOME_CONSUMER = (Consumer<String>) s -> {
  };
  private final Object toDescribe;
  private final String expectedDescription;

  public DescribeTest(@Nullable Object toDescribe, @NotNull String expectedDescription) {
    this.toDescribe = toDescribe;
    this.expectedDescription = expectedDescription;
  }

  @NotNull
  @Parameters(name = "Test {index}: {0}, expecting toString: {1}")
  public static Collection<Object[]> data() {
    //noinspection RedundantCast
    return Arrays.asList(
        new Object[][]{
            {null, "null"}, // 0
            {true, "true"}, // 1
            {false, "false"}, // 2
            {(byte) 0b0010_0101, "37"}, // 3
            {(short) 0b0010_0101, "37"}, // 4
            {0b11010010_01101001_10010100_10010010, "-764,832,622"}, // 5
            {12, "12"}, // 6
            {12L, "12"}, // 7
            {'c', "'c'"}, // 8
            {"Lorem Ipsum", "\"Lorem Ipsum\""}, // 9
            {new StringBuilder("Lorem Ipsum Dolor"), "\"Lorem Ipsum ...\""}, // 10
            {new StringBuffer("Lorem Ipsum"), "\"Lorem Ipsum\""}, // 11
            {1.23456789123456789f, "1.235"}, // 12
            {1.23456789123456789d, "1.235"}, // 13
            {1.234e2, "123.4"}, // 14
            {SOME_OBJECT, String.valueOf(SOME_OBJECT)}, // 15
            {SOME_OBJECT_ARRAY, "[]"}, // 16
            {new Integer[]{1, 2}, "[1, 2]"}, // 17
            // Deep Test
            {new Object[]{1, new Object[]{2, new Object[]{3, new Integer[]{4}}}},
             "[1, [2, [3, [[...]]]]]"}, // 18
            {new Object[]{1, new Object[]{2, new Object[]{3, "Test"}}}, "[1, [2, [3, \"Test\"]]]"},
            // 19
            {Collections.<Integer>emptyList(), "[]"}, // 20
            {Arrays.asList(1, 2), "[1, 2]"}, // 21
            {Arrays.asList(1, "Test"), "[1, \"Test\"]"}, // 22
            {Arrays.asList(1, "Test").iterator(), "[1, \"Test\"]"}, // 23
            {Arrays.asList(1, "Test").stream(), "[1, \"Test\"]"}, // 24
            // Can we do better for consumers?
            {SOME_CONSUMER, String.valueOf(SOME_CONSUMER)}, // 25
            // Lambdas can format themselves being Formattable
            {(ToStringInterface) () -> "Lorem Ipsum Dolor", "Lorem Ipsum ..."}, // 26
            {new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13},
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...]"}, // 27
            {Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13),
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...]"}, // 28
            {Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13).stream(),
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...]"}, // 29
            {Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13).iterator(),
             "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, ...]"}, // 30
            // No truncation to apply here. It is mainly meant for length of elements
            // rather than the length of Strings.
            {SOME_OBJECT.getClass(), String.valueOf(SOME_OBJECT.getClass())}, // 31
            {SOME_OBJECT_ARRAY.getClass(), String.valueOf(SOME_OBJECT_ARRAY.getClass())}, // 32
            {SomeEnum.class, String.valueOf(SomeEnum.class)}, // 33
            {SomeEnum.values(), "[A_ENUM, B_ENUM]"}, // 34
        }
    );
  }

  @Test
  public void test() throws Exception {
    int maxDepth = 3;
    int maxCount = 12;
    assertThat("expecting toString value", Describe.describe(toDescribe, maxCount, maxDepth),
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
      String widthFormat = (width >= 0) ? Integer.toString(width) : "";
      String precisionFormat = (precision >= 0) ? String.format(".%d", precision) : "";
      String format = MessageFormat.format("%1${0}{1}s",
                                           widthFormat,
                                           precisionFormat);
      //noinspection resource
      formatter.format(format, str);
      if (str.length() > precision) {
        try {
          formatter.out().append("...");
        } catch (IOException ignored) {
        }
      }
    }
  }

  private enum SomeEnum {
    A_ENUM,
    B_ENUM
  }
}
