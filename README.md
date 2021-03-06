# Hammock

[![Typelevel incubator](https://img.shields.io/badge/typelevel-incubator-F51C2B.svg)](http://typelevel.org/projects)
[![Join the chat at https://gitter.im/pepegar/hammock](https://badges.gitter.im/pepegar/hammock.svg)](https://gitter.im/pepegar/hammock?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/pepegar/hammock.svg?branch=master)](https://travis-ci.org/pepegar/hammock)
[![codecov](https://codecov.io/gh/pepegar/hammock/branch/master/graph/badge.svg)](https://codecov.io/gh/pepegar/hammock)
[![Maven Central](https://img.shields.io/maven-central/v/com.pepegar/hammock-core_2.12.svg)]()

Hammock is yet another HTTP client for Scala.  It tries to be typeful,
purely functional, and work along other technologies that you're
already using such as akka-http, circe, or cats.

## Installation

Add the following to your `build.sbt`.

```scala
// For Scala 2.10, 2.11, or 2.12
libraryDependencies += "com.pepegar" %% "hammock-core" % "0.7.1"

// For ScalaJS
libraryDependencies += "com.pepegar" %%% "hammock-core" % "0.7.1"
```


## Rationale

1. It's easy to use, has a high level API
2. It's typeful, tries to represent effects at type level.
3. It does not force a specific target context. You can run your computations in any type `F[_]` that has an instance of cats-effect's `Sync[F]`.
4. It has good [documentation][docs].
5. [It's modular](#modules)

[httpcommons]: http://hc.apache.org/
[xhr]: https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest
[docs]: http://pepegar.com/hammock
[circe]: http://circe.io
[akka-http]: https://doc.akka.io/docs/akka-http/current/scala/http/


## Modules

| Module name          | Description                                | Version |
| -------------------- | ------------------------------------------ | ------- |
| `hammock-core`      | the core functionality of hammock, using [Apache HTTP commons][httpcommons] for HTTP in JVM and [XHR][xhr] in JS | `0.7.1` |
| `hammock-circe`      | encode and decode HTTP entities with [Circe][circe] | `0.7.1` |
| `hammock-akka-http`  | run your HTTP requests with [akka-http][akka-http] | `0.7.1` |


## How does Hammock look in action?

```scala
import cats._
import cats.implicits._
import cats.effect.IO
import io.circe._
import io.circe.generic.auto._
import hammock._
import hammock.Uri._
import hammock.jvm.free.Interpreter
import hammock.circe.implicits._


object HttpClient {
  implicit val interpreter = Interpreter[IO]

  val response = Hammock
    .request(Method.GET, Uri.unsafeParse("https://api.fidesmo.com/apps"), Map()) // In the `request` method, you describe your HTTP request
    .exec[IO]
    .as[List[String]]
}
```

## Code of conduct

People are expected to follow the [Typelevel Code of Conduct](http://typelevel.org/conduct.html) when discussing Hammock on the Github page, Gitter channel, or other venues.
