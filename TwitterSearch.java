

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterObjectFactory;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import twitter4j.conf.ConfigurationBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ir.project.JsonClass;
import com.vdurmont.emoji.EmojiParser;

public class TwitterSearch {

	static FileWriter fw = null;
	static BufferedWriter bw = null;
	static int statusCount = 0;
	public static String hashtagRegex = "^#\\w+|\\s#\\w+";
	public static Pattern hashtagPattern = Pattern.compile(hashtagRegex);

	public static String urlRegex = "http+://[\\S]+|https+://[\\S]+";
	public static Pattern urlPattern = Pattern.compile(urlRegex);

	public static String mentionRegex = "^@\\w+|\\s@\\w+";
	public static Pattern mentionPattern = Pattern.compile(mentionRegex);

	public static String emoRegex = "[\uD83D\uDE01-\uD83D\uDE4F]" /*"[^\\x00-\\x7f-\\x80-\\xad]"*//*"[^\\x00-\\x7f-\\x80-\\xad]|[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|[\u2600-\u26FF]|[\u2700-\u27BF]"*/;
	public static Pattern emoPattern = Pattern.compile(emoRegex);

	public static void main(String[] args) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("zzShrSc5iI4skO5YGOh3HXaMl");
		cb.setOAuthConsumerSecret("kJC1LQ9OAHRMSVVyLNZRUMxwbNJ9HKyYInk09Jn3Y3NCehP4te");
		cb.setOAuthAccessToken("771375033264513024-L1nsSLcB0MBYsDaenvEar0zP6y3koLH");
		cb.setOAuthAccessTokenSecret("xnNDdECm9CNfwXTHAT0qNVGRVm6OfJeopWZLevhKsnA6k");
		cb.setJSONStoreEnabled(true);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		// The factory instance is re-useable and thread safe.
		
		String tweetString = null;
		String lang = null;
		String topic = null;
		File file = new File("F:\\eclipse\\tweets_demonetistion_16.json");
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());

			bw = new BufferedWriter(fw);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		long lowestStatusId = Long.MAX_VALUE;
		/* For searching queries */
for(int i = 1 ; i<= 50; i++)
{
	
		// twitter = TwitterFactory.getSingleton();
		Query query = new Query("lang:en #NoteBandi"/* #Demonetization OR #DeMonetisation OR #Demonetisation OR #DeMonetization  AND #PMModi"*/ /*OR #notesban OR #CurrencyBan OR #DemonetizationScam OR #currencydemonetization OR" +
				"#DeMonetisation OR #DemonetizationBenefits OR #demonetized OR #demonetization OR #EconomicsBehind"*//*"lang:en #SaveSyria OR #Convoy OR #Syrianarmy OR #Syria OR #Civil War OR #refugees OR #CivilWarMap OR #jordan OR #ceasefire OR #Syriaceasefire OR #Savechildren"*//*"lang:en #USOC2016 OR #NErevs OR #usopencup OR #USOpenSeries OR #Tennis OR #Nole OR #Novak OR #Djokovic OR #CTOpen16 OR #stanwawrinka OR #Stanimal OR #StanTheMan"*/ /*"lang:ko #USElection 2016 OR #USElection  OR #CivilWarMap OR #jordan  #USOpen OR #USOpen2016 OR #Syria" +
				"OR #Civil War OR iPhone7"*/
				/*"lang:en #Syria OR #Civil War OR #refugees OR #CivilWarMap OR #jordan"*/ /*"lang:en #USOpen OR #USOpen2016"  *//*OR (Game
														 * Of Thrones) OR (US
														 * Elections) OR (Syrian
														 * Civil War) OR
														 * (Appleevent) "
														 */);
		query.setCount(100);
		query.setMaxId(lowestStatusId - 1);
		// query.lang("es");
		// query.setResultType(ResultType.recent);
		// query.sinceId(100);
		// query.setMaxId(1000);
		// query.setResultType("recent");// Get all tweets
		// query.setLang("en");

		// query.setGeoCode(location, radius, unit);
		QueryResult result = null;
		try {
			// System.out.println("Hello");

			result = twitter.search(query);
			// System.out.println(" Fhhhh  Hello");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("Hello2");
		System.out.println("count of tweets : " + result.getCount());
		String coordinates = "";

		final String TWITTER = "yyyy-MM-dd'T'HH:00:00'Z'";
		final String TWITTER_DEFAULT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(TWITTER_DEFAULT);
		SimpleDateFormat sf2 = new SimpleDateFormat(TWITTER);
		sf.setLenient(true);
		Date date = null;
		for (Status status : result.getTweets())
		{
			
			if (!status.isRetweet())
			// System.out.println(TwitterObjectFactory.getRawJSON(status));
			{
				lowestStatusId = Math.min(status.getId(), lowestStatusId);
				Gson gson = new GsonBuilder().disableHtmlEscaping().create();
				JsonClass jsonObj = new JsonClass();
				String emojis = "";
				lang = status.getLang();
				tweetString = TwitterObjectFactory.getRawJSON(status);
				
				// String tweetText = status.getText();
				// tweetText = tweetText.replaceAll("\u0027", "'");
				String text_final = status.getText().replaceAll(hashtagRegex,
						"");
				
				text_final = text_final.replaceAll(urlRegex, "");
				text_final = text_final.replaceAll(mentionRegex, "");
				text_final = EmojiParser.removeAllEmojis(text_final);
				System.out.println("text_final === " + text_final);
				//text_final = text_final.replaceAll(emoRegex, "");
				//text_final = text_final.replaceAll("\u0026", "")
				
				Matcher matcher = null;
				List<String> matchList = new ArrayList<String>();
				try {
					if( "ko".equals(lang) || "es".equals(lang))
					matcher = emoPattern.matcher(new String(status.getText()
							.getBytes("UTF-16")));
					else if( "tr".equals(lang))
						matcher = emoPattern.matcher(new String(status.getText()
								.getBytes("UTF-16")));
					else
						matcher = emoPattern.matcher(new String(status.getText()
								.getBytes("UTF-8")));
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				while (matcher.find()) {
					matchList.add(matcher.group());
				}
				
				for ( int i1 = 0; i1 < matchList.size(); i1++) {
					//System.out.println("matchList.get(i) == "+matchList.get(i));
					emojis = emojis + matchList.get(i1);

				}
				
				//String htmlifiedText = EmojiUtils.htmlify(status.getText());
				// regex to identify html entitities in htmlified text
				//Matcher matcherEmo = emoPattern.matcher(htmlifiedText);

				/*while (matcherEmo.find()) {
				    String emojiCode = matcherEmo.group();
				    if (EmojiUtils.isEmoji(emojiCode)) {

				    	emojis = emojis +EmojiUtils.getEmoji(emojiCode).getEmoji();
				    }
				}*/
				// System.out.println("FINAL == "+text_final);
				JSONObject obj = new JSONObject(tweetString);
				String tweetDate = obj.getString("created_at");

				try {

					try {
						date = sf.parse(tweetDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					tweetDate = sf2.format(date);
				} catch (FormatterClosedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				/*
				 * JSONObject geo = obj. getJSONObject("geo"); if(null!= geo) {
				 * coordinates =
				 * obj.getJSONObject("geo").getString("coordinates"); }
				 */
				if (null != status.getGeoLocation()) {
					coordinates = status.getGeoLocation().getLatitude()
							+ "," + status.getGeoLocation().getLongitude()
							;
				}
				/*JSONArray userMentionsArr = obj.getJSONObject("entities")
						.getJSONArray("user_mentions");
				String post_id = "";
				String url = "";
				String hashtag = "";
				if (null != userMentionsArr) {
					for (int i = 0; i < userMentionsArr.length(); i++) {
						post_id = post_id
								+ ","
								+ userMentionsArr.getJSONObject(i).getString(
										"screen_name");

					}
				}
				JSONArray urlsArr = obj.getJSONObject("entities").getJSONArray(
						"urls");
				if (null != urlsArr) {
					for (int i = 0; i < urlsArr.length(); i++) {
						// System.out.println("url == "+urlsArr.getJSONObject(i));
						url = url + ","
								+ urlsArr.getJSONObject(i).getString("url");

					}
				}
				JSONArray hashtagsArr = obj.getJSONObject("entities")
						.getJSONArray("hashtags");
				if (null != hashtagsArr) {
					for (int i = 0; i < hashtagsArr.length(); i++) {
						hashtag = hashtag
								+ ","
								+ hashtagsArr.getJSONObject(i)
										.getString("text");

					}
				}*/
				String post_id = "";
				String url = "";
				String hashtag = "";
				for(UserMentionEntity usermen :status.getUserMentionEntities())
				{
					post_id = post_id + "," + usermen.getScreenName();
				}
				for(URLEntity s : status.getURLEntities())
				{
					url = url + "," + s.getURL();
				}
				for( MediaEntity s : status.getMediaEntities())
				{
					url = url + "," + s.getURL();
				}
				for(HashtagEntity s : status.getHashtagEntities())
				{
					hashtag = hashtag + "," + s.getText();
				}
				
				jsonObj.setVerified(status.getUser().isVerified());
				jsonObj.setFollowerCount((long) status.getUser().getFollowersCount());
				topic = getTopic(status.getText());
				jsonObj.setTweet_emoticons(emojis);
				jsonObj.setTweet_loc(coordinates);
				jsonObj.setTopic(topic);
				jsonObj.setTweet_text(status.getText());
				jsonObj.setTweet_lang(lang);
				jsonObj.setId(status.getId());
				for(SymbolEntity s : status.getSymbolEntities())
				{
					System.out.println("sym   " +s.getText());
				}
				
				
				byte ptext[];
				try {
					if("en".equals(lang))

					{
					ptext = text_final.getBytes("ISO-8859-1");
					text_final = new String(ptext, "UTF-8");
					}
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if ("en".equalsIgnoreCase(lang)) {
					jsonObj.setText_en(text_final);
				} else if ("es".equalsIgnoreCase(lang)) {
					jsonObj.setText_es(text_final);
				} else if ("ko".equalsIgnoreCase(lang)) {
					jsonObj.setText_ko(text_final);
				} else if ("tr".equalsIgnoreCase(lang))
				{
					jsonObj.setText_tr(text_final);
				}

				if ("" != post_id)
					jsonObj.setMentions(post_id.substring(1));

				else
					jsonObj.setMentions("");
				if ("" != url)
					jsonObj.setTweet_urls(url.substring(1));
				else
					jsonObj.setTweet_urls("");
				jsonObj.setTweet_date(tweetDate);
				if ("" != hashtag)
					jsonObj.setHashtags(hashtag.substring(1));
				else
					jsonObj.setHashtags("");

				
				String jsonTweet = gson.toJson(jsonObj);
				// writeToFile(tweetString);
				try {
					bw.write(jsonTweet);
					// System.out.println("lang = "+ status.getLang() +"  " +
					// "geolocation  ="+ status. + "  "+
					// status.getHashtagEntities().toString());
					statusCount++;
					// System.out.println("status count == "+ statusCount);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*if (statusCount == 100) {
				System.out.println("Done");

				try {
					bw.close();
					fw.close();
					System.exit(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			// System.out.println(status.getUser().getName() + " : " +
			// status.getText());
		}
}

		System.out.println("Done");

		try {
			bw.close();
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getTopic(String text) {
		String topic = "politics";
		/*if (text.contains("#iphone7") || text.contains("#Appleevent"))
			topic = "tech";
		else if (text.contains("#CivilWar")|| text.contains("#Syria")||(text.contains("#Civil War"))|| (text.contains("#CivilWarMap") )|| (text.contains("#refugees"))|| (text.contains("#jordan")))
			topic = "news";
		else if (text.contains("#USOpen") || text.contains("#USOpen2016"))
			topic = "sports";
		else if (text.contains("#GameofThrones"))
			topic = "entertainment";
		else if (text.contains("#USElection 2016") || text.contains("#USElection") || text.contains("#HillaryClinton") || text.contains("Trump"))
			topic = "politics";*/

		return topic;
	}

	// The factory instance is re-useable and thread safe.
	// Twitter twitter = TwitterFactory.getSingleton();
	/*
	 * List<Status> statuses = null; try { statuses = twitter.getHomeTimeline();
	 * } catch (TwitterException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } System.out.println("Showing home timeline."); for
	 * (Status status : statuses) {
	 * System.out.println(status.getUser().getName() + ":" + status.getText());
	 * } }
	 */
}

