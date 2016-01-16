package grails.plugin.slack

import grails.validation.Validateable
import groovy.transform.ToString

@ToString(includeNames=true)
class SlackMessageAttachment implements Serializable, Validateable {

	String fallback
	String color
	String pretext
	String author_name
	String author_link
	String author_icon
	String title
	String title_link
	String text
	String image_url
	String thumb_url
	List<String> mrkdwn_in
	List<SlackMessageAttachmentField> fields = []

	static constraints = {
		fallback nullable:true
		color nullable:true, validator: { !it || it in ['good','warning','danger'] || it =~ /^#(?:[0-9a-fA-F]{3}){1,2}$/ }
		pretext nullable:true
		author_name nullable:true
		author_link nullable:true, url:true
		author_icon nullable:true, url:true
		title nullable:true
		title_link nullable:true, url:true
		text nullable:true
		image_url nullable:true, url:true
		thumb_url nullable:true, url:true
		mrkdwn_in nullable:true, validator: { list -> list.every { it in ['pretext','text','fields'] } }
		fields minSize:0
	}

	 static {
	     grails.converters.JSON.registerObjectMarshaller(SlackMessageAttachment) { that ->
			 return that.class.declaredFields.findAll { that[it.name] && !java.lang.reflect.Modifier.isStatic(it.modifiers) && !it.synthetic && it.name != 'errors' }.collectEntries { [ it.name, that[it.name]] }
	     }
	 }

}