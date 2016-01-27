package grails.plugin.slack

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import spock.lang.Specification
import grails.plugin.slack.exception.SlackMessageException

@TestMixin(ControllerUnitTestMixin) //for codecs to work
@TestFor(SlackService)
class SlackServiceSpec extends Specification {

    void "empty message throws exception"() {
    	given:
    		def closure = {}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message without text"() {
    	given:
    		def closure = {
    			username 'mathi'
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message with invalid icon link"() {
    	given:
    		def closure = {
    			text 'text'
    			iconUrl 'invalidurl'
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message with invalid icon emoji"() {
    	given:
    		def closure = {
    			text 'text'
    			iconEmoji 'wrongemoji:'
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message with invalid channel"() {
    	given:
    		def closure = {
    			text 'text'
    			channel '!invalidchannel'
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message with invalid parse value"() {
    	given:
    		def closure = {
    			text 'text'
    			parse 'invalidvalue'
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message with invalid link names value"() {
    	given:
    		def closure = {
    			text 'text'
    			linkNames 2
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message attachment with invalid color name"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				color 'none'
    			}
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message attachment with invalid color hex"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				color '#34343M'
    			}
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message attachment with invalid author link"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				authorLink 'invalidlink'
    			}
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message attachment with invalid author icon"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				authorIcon 'invalidlink'
    			}
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message attachment with invalid title link"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				titleLink 'invalidlink'
    			}
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message attachment with invalid image url"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				imageUrl 'invalidlink'
    			}
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message attachment with invalid thumb url"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				thumbUrl 'invalidlink'
    			}
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message attachment with invalid markdown value"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				markdownIn(['wrongvalue'])
    			}
    		}
		when:
			service.buildMessage(closure)
		then:
			thrown SlackMessageException
    }

    void "build message without attachments"() {
    	given:
    		def closure = {
    			text 'text'
    			username 'username'
    			iconUrl 'http://example.com/'
    			iconEmoji ':emoji:'
    			channel '#channel'
    			markdown false
    			linkNames 0
    			parse 'none'
    			unfurlLinks false
    			unfurlMedia false
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.username == 'username'
			msg.icon_url == 'http://example.com/'
			msg.icon_emoji == ':emoji:'
			msg.channel == '#channel'
			msg.mrkdwn == false
			msg.link_names == 0
			msg.parse == 'none'
			msg.unfurl_links == false
			msg.unfurl_links == false
			msg.attachments.size() == 0
    }

    void "build message with one attachment"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				fallback 'fallback'
    				color 'good'
    				pretext 'pretext'
    				authorName 'authorName'
    				authorLink 'http://example.com/'
    				authorIcon 'http://example.com/'
    				title 'title'
    				titleLink 'http://example.com/'
    				text 'text'
    				imageUrl 'http://example.com/'
    				thumbUrl 'http://example.com/'
    				markdownIn(['text','fields'])
    			}
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.attachments.size() == 1
			msg.attachments[0].fallback == 'fallback'
			msg.attachments[0].color == 'good'
			msg.attachments[0].pretext == 'pretext'
			msg.attachments[0].author_name == 'authorName'
			msg.attachments[0].author_link == 'http://example.com/'
			msg.attachments[0].author_icon == 'http://example.com/'
			msg.attachments[0].title == 'title'
			msg.attachments[0].title_link == 'http://example.com/'
			msg.attachments[0].text == 'text'
			msg.attachments[0].image_url == 'http://example.com/'
			msg.attachments[0].thumb_url == 'http://example.com/'
			msg.attachments[0].mrkdwn_in == ['text','fields']
			msg.attachments[0].fields.size() == 0
    }

    void "build message with two attachments"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				text 'othertext1'
    			}
    			attachment {
    				text 'othertext2'
    			}
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.attachments.size() == 2
			msg.attachments[0].text == 'othertext1'
			msg.attachments[1].text == 'othertext2'
    }

    void "build message with one attachment and one field"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				text 'othertext1'
    				field {
    					title 'title'
    					value 'value'
    					isShort true
    				}
    			}
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.attachments.size() == 1
			msg.attachments[0].text == 'othertext1'
			msg.attachments[0].fields.size() == 1
			msg.attachments[0].fields[0].title == 'title'
			msg.attachments[0].fields[0].value == 'value'
			msg.attachments[0].fields[0].isShort == true
    }

    void "build message with one attachment and one field without title"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				text 'othertext1'
    				field {
    					value 'value'
    					isShort true
    				}
    			}
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.attachments.size() == 1
			msg.attachments[0].text == 'othertext1'
			msg.attachments[0].fields.size() == 1
			msg.attachments[0].fields[0].title == null
			msg.attachments[0].fields[0].value == 'value'
			msg.attachments[0].fields[0].isShort == true
    }

    void "build message with one attachment and one field without value"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				text 'othertext1'
    				field {
    					title 'title'
    					isShort true
    				}
    			}
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.attachments.size() == 1
			msg.attachments[0].text == 'othertext1'
			msg.attachments[0].fields.size() == 1
			msg.attachments[0].fields[0].title == 'title'
			msg.attachments[0].fields[0].value == null
			msg.attachments[0].fields[0].isShort == true
    }

    void "build message with one attachment and two fields"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				text 'othertext1'
    				field {
    					title 'title1'
    				}
    				field {
    					title 'title2'
    				}
    			}
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.attachments.size() == 1
			msg.attachments[0].text == 'othertext1'
			msg.attachments[0].fields.size() == 2
			msg.attachments[0].fields[0].title == 'title1'
			msg.attachments[0].fields[1].title == 'title2'
    }

    void "build message with two attachments and two fields"() {
    	given:
    		def closure = {
    			text 'text'
    			attachment {
    				text 'othertext1'
    				field {
    					title 'title1'
    				}
    				field {
    					title 'title2'
    				}
    			}
    			attachment {
    				text 'othertext2'
    				field {
    					title 'title3'
    				}
    				field {
    					title 'title4'
    				}
    			}
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.attachments.size() == 2
			msg.attachments[0].text == 'othertext1'
			msg.attachments[0].fields.size() == 2
			msg.attachments[0].fields[0].title == 'title1'
			msg.attachments[0].fields[1].title == 'title2'
			msg.attachments[1].text == 'othertext2'
			msg.attachments[1].fields.size() == 2
			msg.attachments[1].fields[0].title == 'title3'
			msg.attachments[1].fields[1].title == 'title4'
    }

    void "build an exaggerated message"() {
    	given:
    		def closure = {
				text 'text'
				username 'username'
				iconUrl 'http://example.com/icon.png'
				iconEmoji ':emoji:'
				channel '#channel'
				markdown true
				linkNames 1
				parse 'full'
				unfurlLinks true
				unfurlMedia true
			    attachment {
			        fallback 'fallback'
					color 'good'
					pretext 'pretext'
					authorName 'authorName'
					authorLink 'http://example.com/author'
					authorIcon 'http://example.com/author.png'
					title 'title'
					titleLink 'http://example.com/title'
					text 'text'
					imageUrl 'http://example.com/image.png'
					thumbUrl 'http://example.com/thumb.png'
					markdownIn(['text','fields'])
			        field {
						title 'title1'
						value 'some long attachment field value'
						isShort false
					}
			        field {
						title 'title2'
						value 'value'
						isShort true
					}
			    }
			    attachment {
			        text 'other attachment'
			    }
    		}
		when:
			def msg = service.buildMessage(closure)
		then:
			msg
			msg.text == 'text'
			msg.username == 'username'
			msg.icon_url == 'http://example.com/icon.png'
			msg.icon_emoji == ':emoji:'
			msg.channel == '#channel'
			msg.mrkdwn == true
			msg.link_names == 1
			msg.parse == 'full'
			msg.unfurl_links == true
			msg.unfurl_links == true
			msg.attachments.size() == 2
			msg.attachments[0].fallback == 'fallback'
			msg.attachments[0].color == 'good'
			msg.attachments[0].pretext == 'pretext'
			msg.attachments[0].author_name == 'authorName'
			msg.attachments[0].author_link == 'http://example.com/author'
			msg.attachments[0].author_icon == 'http://example.com/author.png'
			msg.attachments[0].title == 'title'
			msg.attachments[0].title_link == 'http://example.com/title'
			msg.attachments[0].text == 'text'
			msg.attachments[0].image_url == 'http://example.com/image.png'
			msg.attachments[0].thumb_url == 'http://example.com/thumb.png'
			msg.attachments[0].mrkdwn_in == ['text','fields']
			msg.attachments[0].fields.size() == 2
			msg.attachments[0].fields[0].title == 'title1'
			msg.attachments[0].fields[0].value == 'some long attachment field value'
			msg.attachments[0].fields[0].isShort == false
    }

}