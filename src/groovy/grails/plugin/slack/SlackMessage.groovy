package grails.plugin.slack

import grails.validation.Validateable
import groovy.transform.ToString

@Validateable
@ToString(includeNames=true)
class SlackMessage implements Serializable {

	String text
	String username
	String icon_url
	String icon_emoji
	String channel
	boolean mrkdwn = true
	int link_names = 1
	String parse = 'full'
	boolean unfurl_links = true
	boolean unfurl_media = true
	List<SlackMessageAttachment> attachments = []

	static constraints = {
		text nullable:false
		username nullable:true
		icon_url nullable:true, url:true
		icon_emoji nullable:true, matches: "^:.+:\$"
		channel nullable:true, blank:false
		parse nullable:true, validator: { !it || it in ['full','none'] }
		link_names min:0, max:1
		attachments minSize:0
	}

	 static {
	     grails.converters.JSON.registerObjectMarshaller(SlackMessage) { that ->
			 return that.class.declaredFields.findAll { that[it.name] && !java.lang.reflect.Modifier.isStatic(it.modifiers) && !it.synthetic && it.name != 'errors' }.collectEntries { [ it.name, that[it.name]] }
	     }
	 }

}