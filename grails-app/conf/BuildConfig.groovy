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
        compile 'org.grails.plugins:rest-client-builder:2.1.1'
        build('org.grails.plugins:release:3.1.2') {
            export = false
        }
    }
}
