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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestName;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.github.mmichaelis.describeme.core.AppendableUtil.silentAppend;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Tests {@link AppendableUtil}.
 *
 * @since $SINCE$
 */
public class AppendableUtilTest {

  private static final Pattern SPLIT_TO_CHARACTERS = Pattern.compile("");

  @Rule
  public TestName testName = new TestName();
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @SuppressWarnings("NullArgumentToVariableArgMethod")
  @Test
  public void robustnessForNullValues() throws Exception {
    StringBuilder builder = new StringBuilder();
    silentAppend(builder, (Object[]) null);
    assertThat(builder.toString(), is(equalTo("null")));
  }

  @Test
  public void passThroughForNoException() throws Exception {
    StringBuilder builder = new StringBuilder();
    String testString = testName.getMethodName();
    String[] testArray = SPLIT_TO_CHARACTERS.split(testString);
    // silentAppend(builder, (Object[]) testArray);
    silentAppend(builder, testArray);
    assertThat(builder.toString(), is(equalTo(testString)));
  }

  @Test
  public void wrapIOException() throws Exception {
    IOException exceptionToThrow = new IOException(testName.getMethodName());
    Appendable appendable = new FailingAppendable(exceptionToThrow);

    expectedException.expect(DescriberIOException.class);
    expectedException.expectCause(sameInstance(exceptionToThrow));
    expectedException.expectMessage(not(isEmptyOrNullString()));

    silentAppend(appendable, testName.getMethodName());
  }

  private static class FailingAppendable implements Appendable {

    private final IOException exceptionToThrow;

    FailingAppendable(IOException exceptionToThrow) {
      this.exceptionToThrow = exceptionToThrow;
    }

    @Override
    public Appendable append(CharSequence csq) throws IOException {
      throw exceptionToThrow;
    }

    @Override
    public Appendable append(CharSequence csq, int start, int end) throws IOException {
      throw exceptionToThrow;
    }

    @Override
    public Appendable append(char c) throws IOException {
      throw exceptionToThrow;
    }
  }
}
