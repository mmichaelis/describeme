## Mission

DescribeMe's mission is to provide an easy to use and extensible way to describe your Java objects
in log statements and especially in test assertions.

DescribeMe is not verified against production use. At least currently it misses important
internationalization requirements like for example number formats. Nevertheless you can easily
extend DescribeMe in such a way, that it takes care of internationalization issues.

## Requirements

DescribeMe requires Java 8 as it relies on Suppliers, Predicates, Consumers and Functions as well
as the Java 8 Streaming API.

## Terms

<dl>
  <dt><strong>Describer</strong></dt>
  <dd>
    <p>
      The interfaces containing the strategies how to describe your Java objects. You will typically
      have a number of describers which will be able to describe one or more value types.
    </p>
  </dd>
  <dt><strong>SPI - Service Provider Interface</strong></dt>
  <dd>
    <p>
      The Java technology DescribeMe is based on. It mainly consists of files listing all
      available describer strategies and a service loader which is able to locate each
      available describer through any such file in the classpath.
    </p>
  </dd>
</dl>

## Use Cases

1. Add DescribeMe to your Maven project
2. Use DescribeMe for assertion messages
3. Use DescribeMe as subformat in `MessageFormat`
4. Extend DescribeMe by own describers
5. Override existing describers
6. Combine describers
7. Using DescribeMe BOM POM
8. Using DescribeMe Thirdparty BOM POM
9. Combine describers with Guava
10. Using describers in custom Hamcrest matchers

<!-- Links -->
