package com.github.mmichaelis.describeme.core;

/**
 * @since $$SINCE:2015-03-17$$
 */
public final class DescriberProperties {

  public static final int UNLIMITED = -1;
  @SuppressWarnings("AccessOfSystemProperties")
  public static final
  int MAX_DEPTH = Integer.parseInt(System.getProperty("describe.max.depth", "-1"));
  @SuppressWarnings("AccessOfSystemProperties")
  public static final
  int MAX_COUNT = Integer.parseInt(System.getProperty("describe.max.count", "-1"));
  public static final String ELLIPSIS = "...";
  static final int ELLIPSIS_LENGTH = ELLIPSIS.length();
  public static final String RECURSION_PLACEHOLDER = "[...]";

  private DescriberProperties() {
  }
}
