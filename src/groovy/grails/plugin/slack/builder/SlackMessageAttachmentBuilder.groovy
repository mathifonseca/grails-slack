package grails.plugin.slack.builder

import grails.plugin.slack.exception.SlackMessageException
import grails.plugin.slack.SlackMessageAttachment

class SlackMessageAttachmentBuilder {

	private SlackMessageAttachment att

	SlackMessageAttachmentBuilder() {
		att = new SlackMessageAttachment()
	}

	SlackMessageAttachment getAttachment() throws SlackMessageException {
		if (!att.validate()) {
			throw new SlackMessageException("Validation errors in SlackMessageAttachment : " + att.errors.allErrors.field, att)
		}
		return att
	}

	void fallback(String fallback) {
		att.fallback = fallback
	}

	void color(String color) {
		att.color = color
	}

	void pretext(String pretext) {
		att.pretext = pretext
	}

	void authorName(String authorName) {
		att.author_name = authorName
	}

	void authorLink(String authorLink) {
		att.author_link = authorLink
	}

	void authorIcon(String authorIcon) {
		att.author_icon = authorIcon
	}

	void title(String title) {
		att.title = title
	}

	void titleLink(String titleLink) {
		att.title_link = titleLink
	}

	void text(String text) {
		att.text = text
	}

	void imageUrl(String imageUrl) {
		att.image_url = imageUrl
	}

	void thumbUrl(String thumbUrl) {
		att.thumb_url = thumbUrl
	}

	void markdownIn(List<String> mrkdwnIn) {
		att.mrkdwn_in = mrkdwnIn
	}
	
	void footer(String footer) {
		att.footer = footer
	}
	
	void footerIcon(String icon) {
		att.footer_icon = icon
	}
	
	void ts(Date date) {
		att.ts = date.getTime() / 1000
	}

	void field(Closure closure) {
		def builder = new SlackMessageAttachmentFieldBuilder()
        closure.delegate = builder
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call(builder)

    	att.fields += builder.field
	}

}