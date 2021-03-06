# div-common-lib
Re-usable features for all divorce java projects

### Prerequisites

- [JDK 8](https://www.oracle.com/java)

## Usage

Just include the library as your dependency. 

## Building

The project uses [Gradle](https://gradle.org) as a build tool but you don't have install it locally since there is a
`./gradlew` wrapper script.  

To build project please execute the following command:

```bash
    ./gradlew build
```

## Developing

### Coding style tests

To run all checks (including unit tests) please execute the following command:

```bash
    ./gradlew check
```

## Where it's referenced

Divorce projects using `div-common-lib`:
- div-cos: https://github.com/hmcts/div-case-orchestration-service
- div-cfs: https://github.com/hmcts/div-case-data-formatter

## Versioning

We use [SemVer](http://semver.org/) for versioning.
For the versions available, see the tags on this repository.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
