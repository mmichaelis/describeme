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

/**
 * <p>
 * Core Module of <em>DescribeMe</em> providing some default description behavior. This module
 * provides the Java service for plugging additional description services to your project, for
 * example to describe your own objects.
 * </p>
 * <p>
 * While you might argue that one could modify the {@code toString()} method, this is not always
 * applicable. Some old projects rely on the toString() values in a certain format for parsing
 * purpose (thus their format is part of the public API), other toString() methods come from
 * thirdparty libraries. And {@code toString()} actually has a different purpose than
 * <em>DescribeMe</em>:
 * </p>
 * <p>
 * While {@code toString()} should evaluate fast and is especially helpful as first description of
 * an object while debugging in your IDE, <em>DescribeMe</em> is focused on providing as much
 * debugging information available when the information is dumped to a file like in a test result
 * file.
 * </p>
 * <dl>
 * <dt><strong>Extending:</strong></dt>
 * <dd>
 * <p>
 * To add your own describers for your own objects or to override the default behavior
 * you will have to:
 * </p>
 * <ul>
 * <li>
 * <p>
 * implement either {@link com.github.mmichaelis.describeme.core.Describer} or
 * {@link com.github.mmichaelis.describeme.core.RecursiveDescriber}
 * </p>
 * </li>
 * <li>
 * <p>
 * create the service description listing your own Describers with high priority
 * describers first; the service description file should be located at:
 * </p>
 * <pre>{@code
 * resources/META-INF/services/com.github.mmichaelis.describeme.core.Describer
 *         }</pre>
 * </li>
 * </ul>
 * </dd>
 * </dl>
 *
 * @since $SINCE$
 */
package com.github.mmichaelis.describeme.core;
