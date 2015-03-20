# DescribeMe

DescribeMe's goal is to provide an easy to use API to describe values in any message like in
logs or test assertion failures.

DescribeMe offers an alternative way to overriding `toString()` methods which is especially
helpful if you want to describe objects from third party libraries or Java core.

## Extensible

DescribeMe uses Java's Service Loader (see [Creating Extensible Applications][java-spi]). So if
you happen to have your very own classes which you need to describe you can simple define your
own *Describers* and list them in a provider configuration file.

## History

<!-- Links -->

[java-spi]: <https://docs.oracle.com/javase/tutorial/ext/basics/spi.html> "Creating Extensible Applications (The Java™ Tutorials > The Extension Mechanism > Creating and Using Extensions)"
