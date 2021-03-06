# Overview

The Ideal Graph Visualizer is a tool developed to help examine the intermediate
representation of C2 which is commonly referred to as the "ideal graph". It was
developed in collaboration with the University of Linz in Austria and has been
included as part of hotspot since that was the primary target of the tool. The
tool itself is fairly general with only a few modules that contain C2 specific
elements.

The tool is built on top of the NetBeans 7 rich client infrastructure and so
requires NetBeans to build. It currently requires at least Java 6 to run as it
needs support for JavaScript for its filtering mechanism and assumes it's built
into the platform.  It should build out of the box with NetBeans 7.0 and Java 6
or later.

# Building and Running

The build system used for IGV is ant. To download all required libraries and
build IGV, issue `ant build`. To run IGV, use the `igv.sh` command; it will put
all log messages generated by the run to the file `.igv.log`. To see all log
messages generated during an IGV run, use `ant run`.

# Usage

The JVM support is controlled by the flag `-XX:PrintIdealGraphLevel=#` where `#`
is:

* 0: no output, the default
* 1: dumps graph after parsing, before matching, and final code (also dumps
     graphs for failed compilations, if available)
* 2: more detail, including after loop opts
* 3: even more detail
* 4: prints graph after parsing every bytecode (very slow)

By default the JVM expects that it will connect to a visualizer on the local
host on port 4444. This can be configured using the options
`-XX:PrintIdealGraphAddress=` and `-XX:PrintIdealGraphPort=`.
`PrintIdealGraphAddress` can actually be a hostname.

It is advisable to run the JVM with background compilation disabled (-Xbatch).
Compilations going on in the background may be cancelled when the VM terminates,
which can lead to incomplete dumps being sent to IGV.

Alternatively the output can be sent to a file using
`-XX:PrintIdealGraphFile=filename`. Each compiler thread will get it's own file
with unique names being generated by adding a number onto the provided file
name.

More information about the tool is available at
https://wiki.openjdk.java.net/display/HotSpot/IdealGraphVisualizer.
