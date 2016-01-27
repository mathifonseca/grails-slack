package grails.plugin.slack.builder

import grails.plugin.slack.exception.SlackMessageException
import grails.plugin.slack.SlackMessage

class SlackMessageBuilder {

	private SlackMessage msg

	SlackMessageBuilder() {
		msg = new SlackMessage()
	}

	SlackMessage getMessage() throws SlackMessageException {
		if (!msg.validate()) {
			throw new SlackMessageException("Validation errors in SlackMessage : " + msg.errors.allErrors.field, msg)
		}
		return msg
	}

	void text(String text) {
		msg.text = text
	}

	void username(String username) {
		msg.username = username
	}

	void iconUrl(String iconUrl) {
		msg.icon_url = iconUrl
	}

	void iconEmoji(String iconEmoji) {
		msg.icon_emoji = iconEmoji
	}

	void channel(String channel) {
		msg.channel = channel
	}

	void markdown(boolean mrkdwn) {
		msg.mrkdwn = mrkdwn
	}

	void linkNames(int linkNames) {
		msg.link_names = linkNames
	}

	void parse(String parse) {
		msg.parse = parse
	}

	void unfurlLinks(boolean unfurlLinks) {
		msg.unfurl_links = unfurlLinks
	}

	void unfurlMedia(boolean unfurlMedia) {
		msg.unfurl_media = unfurlMedia
	}

	void attachment(Closure closure) {
		def builder = new SlackMessageAttachmentBuilder()
        closure.delegate = builder
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call(builder)

    	msg.attachments += builder.attachment
	}

}