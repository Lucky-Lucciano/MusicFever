package net.etfbl.rss.reader;

import net.etfbl.rss.model.Feed;
import net.etfbl.rss.model.FeedMessage;

public class RSSReadTest {
	public static void main(String[] args) {
		RSSFeedParser parser = new RSSFeedParser(
				"http://www.b92.net/info/rss/sport.xml");
		Feed feed = parser.readFeed();
		System.out.println(feed);
		for (FeedMessage message : feed.getMessages()) {
			System.out.println(message);

		}

	}
}
