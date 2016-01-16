package grails.plugin.slack.exception

import org.codehaus.groovy.grails.exceptions.GrailsException

class SlackMessageException extends GrailsException {

    public SlackMessageException() {
        super()
    }

    public SlackMessageException(String msg, obj = null) {
        super("${msg}${obj ? ' -> ' + obj.toString() : ''}")
    }
	
}