package grails.plugin.slack

import grails.validation.Validateable
import groovy.transform.ToString

@Validateable
@ToString(includeNames=true)
class SlackMessageAttachment implements Serializable {

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
	String footer
	String footer_icon
	Integer ts
	List<String> mrkdwn_in
	List<SlackMessageAttachmentField> fields = []

	static constraints = {
		fallback nullable:true
		color nullable:true, validator: { !it || it in ['good', 'warning', 'danger'] || it =~ /^#(?:[0-9a-fA-F]{3}){1,2}$/ }
		pretext nullable:true
		author_name nullable:true
		author_link nullable:true, url:true
		author_icon nullable:true, url:true
		title nullable:true
		title_link nullable:true, url:true
		text nullable:true
		image_url nullable:true, url:true
		thumb_url nullable:true, url:true
		mrkdwn_in nullable:true, validator: { list -> list.every { it in ['pretext', 'text', 'fields'] } }
		fields minSize:0
		footer nullable: true
		footer_icon nullable: true
		ts nullable: true
	}

}