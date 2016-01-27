package grails.plugin.slack

import grails.validation.Validateable
import groovy.transform.ToString

@ToString(includeNames=true)
class SlackMessageAttachmentField implements Serializable, Validateable {

	String title
	String value
	boolean isShort = false

	static constraints = {
		title nullable:true
		value nullable:true
	}

	static {
	    grails.converters.JSON.registerObjectMarshaller(SlackMessageAttachmentField) { that ->
			return that.class.declaredFields.findAll { that[it.name] && !java.lang.reflect.Modifier.isStatic(it.modifiers) && !it.synthetic && it.name != 'errors' }.collectEntries { [ "${it.name == 'isShort' ? 'short' : it.name }", that[it.name]] }
	    }
	}

}