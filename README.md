## ml-stress-test

MarkLogic Application Stress Testing Tool

[![Build Status](https://travis-ci.org/ableasdale/ml-stress-test.svg?branch=master)](https://travis-ci.org/ableasdale/ml-stress-test)
[![Dependency Status](https://www.versioneye.com/user/projects/5870c49a3ab1480033d8c19d/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5870c49a3ab1480033d8c19d)

- TODO - add dateTime rangeindex using ml-gradle or similar...
- TODO - dynamically update graphs on dashboard

### Usage

1. Use Gradle to launch the application:

Linux
```bash
./gradlew run
```

Windows
```cmd
D:\workspace\ml-stress-test>gradlew run
```

2. Go to the application server to view test results [http://localhost:9999](http://localhost:9999)

### Configuration

The sample configuration file can be found in **resources/config.xml**:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <uris>
        <uri>xcc://q:q@localhost:8000</uri>
    </uris>
    <durationInMinutes>1</durationInMinutes>
    <testLabel>v0.4</testLabel>
    <jobs>
        <job>
            <classname>HTTPEndpointGetRequest</classname>
            <username>user</username>
            <password>password</password>
            <accepts>text/html</accepts>
            <group>ML_Admin</group>
            <endpoint>http://localhost:8001</endpoint>
            <interval>every-second</interval>
        </job>
        <job>
            <classname>HTTPEndpointGetRequest</classname>
            <username>user</username>
            <password>password</password>
            <accepts>application/json</accepts>
            <group>ReST_Request</group>
            <endpoint>http://localhost:8002/manage/v2/forests/</endpoint>
            <interval>every-second</interval>
        </job>
    </jobs>
</configuration>
```