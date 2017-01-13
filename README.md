## ml-stress-test

MarkLogic Application Stress Testing Tool

[![Build Status](https://travis-ci.org/ableasdale/ml-stress-test.svg?branch=master)](https://travis-ci.org/ableasdale/ml-stress-test)
[![Dependency Status](https://www.versioneye.com/user/projects/5870c49a3ab1480033d8c19d/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5870c49a3ab1480033d8c19d)

- TODO - add dateTime rangeindex using ml-gradle or similar...
- TODO - dynamically update graphs on dashboard

### Usage

1. Use Gradle to launch the application:

```bash
./gradlew run
```

2. Go to the application server to view test results [http://localhost:9999](http://localhost:9999)

### Configuration

The sample configuration file can be found in **resources/config.xml**:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <uris>
        <uri>xcc://user:passwd@hostname:port</uri>
    </uris>
    <durationInMinutes>2</durationInMinutes>
    <testLabel>v0.3</testLabel>
</configuration>
```