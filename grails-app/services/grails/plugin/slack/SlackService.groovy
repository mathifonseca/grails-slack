package grails.plugin.slack

import grails.plugin.slack.builder.SlackMessageBuilder
import grails.plugin.slack.exception.SlackMessageException
import grails.plugins.rest.client.RestBuilder

class SlackService {

	def grailsApplication

    void send(Closure closure) throws SlackMessageException {

    	def message = buildMessage(closure)

    	def webhook = grailsApplication.config.slack.webhook

    	if (!webhook) throw new SlackMessageException("Slack webhook is not valid")

    	try {
    		webhook.toURL()
		} catch (Exception ex) {
			throw new SlackMessageException("Slack webhook is not valid")
		}

    	def jsonMessage = message.encodeAsJson()

    	log.debug "Sending message : ${jsonMessage}"

    	def rest = new RestBuilder()

		def resp = rest.post(webhook.toString()) {
			json jsonMessage
		}

		if (resp.status != 200 || resp.text != 'ok') {
			throw new SlackMessageException("Error while calling Slack -> ${resp.text}")
		}

    }

    private SlackMessage buildMessage(Closure closure) throws SlackMessageException {

    	def builder = new SlackMessageBuilder()
        closure.delegate = builder
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.call(builder)

        def message = builder?.message

        if (!message) throw new SlackMessageException("Cannot send empty message")

        return message

    }

}