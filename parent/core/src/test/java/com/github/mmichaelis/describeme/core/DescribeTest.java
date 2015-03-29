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

import static com.github.mmichaelis.describeme.core.IsUtilityClassMatcher.isUtilityClass;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests {@link Describe}.
 *
 * @since 1.0.0
 */
@SuppressWarnings("TooBroadScope")
public class DescribeTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void matchesUtilityClassRequirements() throws Exception {
    assertThat(Describe.class, isUtilityClass());
  }

  @Test
  public void describeTo_AppendableObject_appends() throws Exception {
    StringBuilder sb = new StringBuilder();
    String value = "Lorem";
    Describe.describeTo(sb, value);
    assertThat(sb.toString(), is(equalTo(value)));
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void describeTo_AppendableObject_failsOnNull() throws Exception {
    StringBuilder sb = null;
    String value = "Lorem";
    expectedException.expect(NullPointerException.class);
    expectedException.expectMessage(not(isEmptyString()));
    Describe.describeTo(sb, value);
  }

  @Test
  public void describeTo_AppendableObjectMaxCount_appends() throws Exception {
    StringBuilder sb = new StringBuilder();
    String value = "Lorem";
    Describe.describeTo(sb, value, 1);
    assertThat(sb.toString(), is(equalTo(value)));
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void describeTo_AppendableObjectMaxCount_failsOnNull() throws Exception {
    StringBuilder sb = null;
    String value = "Lorem";
    expectedException.expect(NullPointerException.class);
    expectedException.expectMessage(not(isEmptyString()));
    Describe.describeTo(sb, value, 1);
  }

  @Test
  public void describeTo_AppendableObjectMaxCountMaxDepth_appends() throws Exception {
    StringBuilder sb = new StringBuilder();
    String value = "Lorem";
    Describe.describeTo(sb, value, 1, 1);
    assertThat(sb.toString(), is(equalTo(value)));
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  public void describeTo_AppendableObjectMaxCountMaxDepth_failsOnNull() throws Exception {
    StringBuilder sb = null;
    String value = "Lorem";
    expectedException.expect(NullPointerException.class);
    expectedException.expectMessage(not(isEmptyString()));
    Describe.describeTo(sb, value, 1, 1);
  }

  @Test
  public void describe_Object() throws Exception {
    String value = "Lorem";
    assertThat(Describe.describe(value), is(equalTo(value)));
  }

  @Test
  public void describe_ObjectMaxCount() throws Exception {
    String value = "Lorem";
    assertThat(Describe.describe(value, 1), is(equalTo(value)));
  }

}
