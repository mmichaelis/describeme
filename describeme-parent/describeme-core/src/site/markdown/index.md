Core Module of DescribeMe providing some default description behavior. This module provides the
Java service for plugging additional description services to your project, for example to describe
your own objects.

While you might argue that one could modify the `toString()` method, this is not always applicable.
Some old projects rely on the `toString()` values in a certain format for parsing purpose, other
`toString()` methods come from thirdparty libraries. And `toString()` actually has a different
purpose than DescribeMe:

While `toString()` should evaluate fast and is especially helpful as first description of an object
while debugging in your IDE, DescribeMe is focused on providing as much debugging information
available when the information is dumped to a file like in a test result file.
