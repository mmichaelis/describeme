[![Build Status][travisci-badge]][travisci]
[![Versions][versioneye-badge]][versioneye]

<!--
[![][travis img]][travis]
[![][coverage img]][coverage]
[![][mavenbadge img]][mavenbadge]
[![][versioneye img]][versioneye]
[![][sonar img]][sonar]
-->

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

## License

[![][license-badge]][license]

This software is licensed under the terms in the file [LICENSE.md][license] in this directory.

## Thanks

* [shields.io][]

    for providing out-of-the-box badges like the license badge

<!-- Links -->

[java-spi]: <https://docs.oracle.com/javase/tutorial/ext/basics/spi.html> "Creating Extensible Applications (The Java™ Tutorials > The Extension Mechanism > Creating and Using Extensions)"
[travis-ci.org]: <https://travis-ci.org/repositories> "Travis CI - Free Hosted Continuous Integration Platform for the Open Source Community"
[shippable.com]: <http://www.shippable.com/> "Shippable - Continuous integration, evolved."
[shields.io]: <http://shields.io/> "Shields.io: Quality metadata badges for open source projects"

<!-- Badges -->

[license]: <LICENSE.md> "Apache License, Version 2.0"
[license-badge]: <https://img.shields.io/badge/license-Apache%20License%2C%20Version%202.0-lightgrey.svg> "Apache License, Version 2.0"
[versioneye]: <https://www.versioneye.com/user/projects/550dd711bc1c12efc3000022<
[versioneye-badge]: <https://www.versioneye.com/user/projects/550dd711bc1c12efc3000022/badge.svg>
[travisci]: <https://travis-ci.org/mmichaelis/describeme>
[travisci-badge]: <https://travis-ci.org/mmichaelis/describeme.svg?branch=master>
