package com.github.mmichaelis.describeme.core;

/**
 * @since $$SINCE:2015-03-17$$
 */
@SuppressWarnings("AccessOfSystemProperties")
public final class DescriberProperties {

  public static final int UNLIMITED = -1;
  public static final String P_DESCRIBE_MAX_DEPTH = "describeme.max.depth";
  public static final String P_DESCRIBE_MAX_COUNT = "describeme.max.count";
  public static final String P_DESCRIBE_MAX_DEPTH_FORCE = "describeme.max.depth.force";
  public static final int MAX_DEPTH =
      Integer.parseInt(System.getProperty(P_DESCRIBE_MAX_DEPTH, "100"));
  public static final int MAX_COUNT =
      Integer.parseInt(System.getProperty(P_DESCRIBE_MAX_COUNT, "-1"));
  public static final boolean MAX_DEPTH_FORCE =
      Boolean.parseBoolean(System.getProperty(P_DESCRIBE_MAX_DEPTH_FORCE, "false"));
  public static final String ELLIPSIS = "...";
  public static final String RECURSION_PLACEHOLDER = "[...]";

  private DescriberProperties() {
  }
}
