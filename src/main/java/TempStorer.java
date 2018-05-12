import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

class Temp{
	void getTemp(){
		LocalDateTime time = LocalDateTime.now();
		String data;
		try{			
		URL url = new URL("http://192.168.1.105:8080");		
		URLConnection conn = null;
		conn = url.openConnection();
		BufferedReader br;			
		InputStreamReader inputStream;
		inputStream = new InputStreamReader(conn.getInputStream());
		br = new BufferedReader(inputStream);
		data = time + ": " + br.readLine() + "ll";
		System.out.println(data);
		
		try{
			File file = new File("data.log");
			FileWriter fr = null;
			BufferedWriter bw;
			fr = new FileWriter(file, true);
			bw = new BufferedWriter(fr);			
			bw.write(data);
			bw.newLine();
			bw.close();
		} catch (IOException e){
			e.printStackTrace();
		} 
		
		
		
		br.close();
		} catch (MalformedURLException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}



public class TempStorer{
	public static void main(String[] args){
		
		Temp temp = new Temp();		
		Timer timer = new Timer();
			
		timer.schedule(new TimerTask(){
			@Override
			public void run(){
				temp.getTemp();
			}
		}, 0, 1000 * 60 * 5);
		
	}
}