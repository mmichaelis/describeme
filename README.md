# DescribeMe

DescribeMe's goal is to provide an easy to use API to describe values in any message like in
logs or test assertion failures.

DescribeMe offers an alternative way to overriding `toString()` methods which is especially
helpful if you want to describe objects from third party libraries or Java core.

## Extensible

DescribeMe uses Java's Service Loader (see [Creating Extensible Applications][java-spi]). So if
you happen to have your very own classes which you need to describe you can simple define your
own *Describers* and list them in a provider configuration file.

## CI

Currently different hosted CI systems are under test which are:

* [Travis CI][travis-ci.org]: [![Build Status](https://travis-ci.org/mmichaelis/describeme.svg?branch=master)](https://travis-ci.org/mmichaelis/describeme)
* [Shippable][shippable.com]: [![Build Status](https://api.shippable.com/projects/550d3de65ab6cc1352a6e3b4/badge?branchName=master)](https://app.shippable.com/projects/550d3de65ab6cc1352a6e3b4/builds/latest)

## History

<!-- Links -->

[java-spi]: <https://docs.oracle.com/javase/tutorial/ext/basics/spi.html> "Creating Extensible Applications (The Java™ Tutorials > The Extension Mechanism > Creating and Using Extensions)"
[travis-ci.org]: <https://travis-ci.org/repositories> "Travis CI - Free Hosted Continuous Integration Platform for the Open Source Community"
[shippable.com]: <http://www.shippable.com/> "Shippable - Continuous integration, evolved."
