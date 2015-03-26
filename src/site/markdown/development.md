# Development Guide

## `@since` Tag

**API users need to know when features got introduced - especially when they find the current
JavaDoc but are still using an older version.**

The problem: As developer you often forget to set versions in your JavaDoc - at least when it comes
to method or even field level.

The solution: The [Maven Replacer Plugin][] (`com.google.code.maven-replacer-plugin:replacer`).
It replaces all tokens `$SINCE$` with the current project version (`-SNAPSHOT` removed). Later on
this might become part of the release process so that the tag really fits the released version -
as for branches it might happen, that they start in 1.0.0 but get merged to master with version
1.0.1.

[Maven Replacer Plugin]: <https://code.google.com/p/maven-replacer-plugin/> "maven-replacer-plugin - Maven Plugin to replace tokens within a file with a given value"
