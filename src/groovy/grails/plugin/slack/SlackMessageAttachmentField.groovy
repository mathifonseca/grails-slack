package grails.plugin.slack

import grails.validation.Validateable
import groovy.transform.ToString

@Validateable
@ToString(includeNames=true)
class SlackMessageAttachmentField implements Serializable {

	String title
	String value
	boolean isShort = false

	static constraints = {
		title nullable:true
		value nullable:true
	}

}