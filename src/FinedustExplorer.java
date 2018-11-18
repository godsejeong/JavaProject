import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FinedustExplorer extends Thread{
	String location;
	String data;
	String ServiceKey = "DXpwTc0VFcnpT6grVOvVKe6CFwKqd4kOtJsRPrYk0YR%2BxtbmNep5SrDPZJr5%2B5Lm5C8XaTZS4geK9O6Svg6m5Q%3D%3D";
	
	public FinedustExplorer(String location) {
		this.location = location;
	}
	
	@Override
	public void run() {
		super.run();
		try {		
			StringBuilder urlBuilder = new StringBuilder("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst");
			urlBuilder.append("?" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode(location, "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") +"=" + URLEncoder.encode("10", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("_returnType","UTF-8") +"=" +  URLEncoder.encode("json", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("searchCondition","UTF-8") + "=" + URLEncoder.encode("DAILY", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") +"=" + ServiceKey);
			urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") +"=" + 1.3);
			
			URL url = new URL(urlBuilder.toString());
			System.out.println(url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			BufferedReader rd;
			
			    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		       
		        StringBuilder sb = new StringBuilder();
		        String line;
		        while ((line = rd.readLine()) != null) {
		            sb.append(line);
		        }
		        rd.close();
		        conn.disconnect();
		        data = sb.toString();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public String returndata() {
		return data;
	}
}
