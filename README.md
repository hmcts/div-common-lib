# div-common-lib
Re-usable features for all divorce java projects

### Prerequisites

- [JDK 17](https://www.oracle.com/java)

## Usage

This library is hosted on Azure DevOps Artifacts and can be used in your project by adding the following to your `build.gradle` file:

```gradle
repositories {
    maven {
        url 'https://pkgs.dev.azure.com/hmcts/Artifacts/_packaging/hmcts-lib/maven/v1'
    }
}
dependencies {
  implementation 'com.github.hmcts:div-common-lib:LATEST_TAG'
}
```

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
