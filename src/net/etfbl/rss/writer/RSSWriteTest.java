package net.etfbl.rss.writer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.etfbl.rss.model.Feed;
import net.etfbl.rss.model.FeedMessage;

public class RSSWriteTest {

	public static void main(String[] args) {
		String copyright = "Copyright ETF BL"; 
		String title = "Test Feed"; 
		String description = "Test Feed Description";
		String language = "en";
		String link = "http://www.b92.net/sport/vesti.php?yyyy=2011&mm=04&dd=26&nav_id=508565"; 
		Calendar cal = new GregorianCalendar();
		Date creationDate = cal.getTime();
		SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yyyy.");
		String pubdate = date_format.format(creationDate);
		Feed rssFeeder = new Feed(title, link, description, language, copyright, pubdate);
		

		FeedMessage feed = new FeedMessage();
		feed.setTitle("RSSFeed no 1");
		feed.setDescription("This is a description");
		feed.setAuthor("test@etfbl.net");
		feed.setGuid("http://www.etfbl.net/test.html");
		feed.setLink("http://www.etfbl.net/test.html");
		rssFeeder.getMessages().add(feed);

		RSSFeedWriter writer = new RSSFeedWriter(rssFeeder, "articles.rss");
		try {
			writer.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
