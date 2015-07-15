grails.project.work.dir = 'target'
grails.project.dependency.resolver = 'maven'
grails.project.dependency.resolution = {
    inherits 'global'
    log 'warn'
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }
    plugins {
        compile ":rest-client-builder:1.0.3"
        build(":release:3.0.1") {
            export = false
        }
    }
}
