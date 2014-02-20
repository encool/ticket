import java.awt.Component;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.swing.*;
public class GUI {
	private static boolean ishttps=false;
	static double random=Math.random();
	static String uname="liqiao100%40yahoo.cn";
	static String pword="815720q";
	static String passcodevalue;
	static String postdata="loginUserDTO.user_name="+uname+"&"+"&userDTO.password="+pword+"randCode="+passcodevalue;
	static String URLLogin="http://kyfw.12306.cn/otn/login/loginAysnSuggest";
	static String URLpasscode="http://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew.do?module=login&rand=sjrand&"+String.valueOf(random);
	static String URLGetTrain="http://kyfw.12306.cn/otn/leftTicket/query?leftTicketDTO.train_date=2014-01-22&leftTicketDTO.from_station=SZQ&leftTicketDTO.to_station=LDQ&purpose_codes=ADULT";
	private static JLabel  label=null;
	private static JFrame frame;
	private static String session="";
	static byte[] bytesimage = new byte[3072];//verification code

	/**{
     * 创建并显示GUI。出于线程安全的考虑，
     * 这个方法在事件调用线程中调用。
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
 
        //Create and set up the window.
        frame = new JFrame("Helloticket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
//        DoLogin();
        GetTrain();
    }
    static void GetTrain(){
    	BigCon con=new BigCon(URLGetTrain,null,null,false,2,null);
    	con.Init();
    	con.DoConnect();
    	PrintResult(con.getCon());
    }
    static void PrintResult(HttpURLConnection con){
		try {
			InputStream in = null;
	    	if(con.getHeaderField("Content-Encoding")=="gzip")
	    	{
	    		in=new GZIPInputStream(con.getInputStream());

	    	}else{
//	    		in=con.getInputStream();
	    		in=new GZIPInputStream(con.getInputStream());

	    	}
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String inputLine;
			while ((inputLine = buffer.readLine()) != null)
			System.out.println(inputLine);
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    static void DoLogin(){
    	BigCon con=new BigCon(URLpasscode, postdata, null, ishttps,2,null);
        con.Init();
        con.DoConnect();
        HttpURLConnection con1 = con.getCon();
		Map<String, List<String>> map=con.getCon().getHeaderFields();
		System.out.println(con1.getHeaderField("Content-Type"));
		System.out.println(con1.getHeaderField("Content-Length"));
		List<String> list=map.get("Set-Cookie");
		for(int i=0;i<list.size();i++){
			session=session+list.get(i).substring(0, list.get(i).indexOf(";")+1);
		}
		System.out.println(session);
		InputStream connectionIn = null;
		
		int returnCode;
		try {
			returnCode = con1.getResponseCode();
			if (returnCode==200){
				connectionIn = con1.getInputStream();
				int i=0;
				
				do{
					i=connectionIn.read(bytesimage);
				}while(i==-1);
				System.out.println(bytesimage);
//		        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//		            public void run() {
//		                ShowInput(false,new ImageIcon(bytesimage));
//		            }
//		        });
//				ShowInput(false,new ImageIcon(bytesimage));
				ShowInput(false,new ImageIcon(bytesimage));
			}else
				connectionIn = con1.getErrorStream();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		con1.disconnect();
		postdata="loginUserDTO.user_name="+uname+"&"+"userDTO.password="+pword+"&randCode="+passcodevalue;
		System.out.println(postdata);
		BigCon conl=new BigCon(URLLogin,postdata,null,false,1,session);
		conl.Init();
		conl.DoConnect();
		PrintResult(conl.getCon());
		
    }
    static void ShowInput(Boolean islogin,ImageIcon icon){
    	if(islogin){
    		label = new JLabel("login");
    		JOptionPane.showInputDialog(frame,label);
    	}else{
    		passcodevalue=JOptionPane.showInputDialog(frame,icon);
    	}
    	
    }
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    public static class PassCode{

		String method;
		String codevalue;
		String session="";
		public String getCodevalue() {
			return codevalue;
		}
		public String getSession() {
			return session;
		}
		double randon;
		byte[] bytesimage=new byte[3072];
//		private boolean ishttps;
		void excute(){
			try {
				HttpURLConnection con = null;
				randon=Math.random();
				if(ishttps){
					SSLContext sc = SSLContext.getInstance("SSL");
					sc.init(null, new TrustManager[] { new BigCon.TrustAnyTrustManager() },
							new java.security.SecureRandom());
					System.out.println(randon);
					URL console = new URL(
							"https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew.do?module=login&rand=sjrand&"+String.valueOf(randon));
					con = (HttpsURLConnection) console
							.openConnection();
					((HttpsURLConnection) con).setSSLSocketFactory(sc.getSocketFactory());
					((HttpsURLConnection) con).setHostnameVerifier(new BigCon.TrustAnyHostnameVerifier());					
				}else{
					URL console = new URL(
							"http://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew.do?module=login&rand=sjrand&"+String.valueOf(randon));
					con=(HttpURLConnection) console.openConnection();
				}

				con.connect();
				
				int returnCode = con.getResponseCode();
				
				Map<String, List<String>> map=con.getHeaderFields();
				System.out.println(con.getHeaderField("Content-Type"));
				System.out.println(con.getHeaderField("Content-Length"));
				List<String> list=map.get("Set-Cookie");
				for(int i=0;i<list.size();i++){
					session=session+list.get(i).substring(0, list.get(i).indexOf(";")+1);
				}
				System.out.println(session);
				InputStream connectionIn = null;
				
				if (returnCode==200){
					connectionIn = con.getInputStream();
					int i=0;
					do{
						i=connectionIn.read(bytesimage);
					}while(i==-1);
					System.out.println(bytesimage);
					ImageIcon icon=new ImageIcon(bytesimage);
					ShowInput(false,icon);
				}else
					connectionIn = con.getErrorStream();
				// print resulting stream
//				BufferedReader buffer = new BufferedReader(new InputStreamReader(connectionIn));
//				String inputLine;
//				while ((inputLine = buffer.readLine()) != null)
//				System.out.println(inputLine);
//				buffer.close();
				}catch (ConnectException e1) {
					System.out.println("ConnectException");
					System.out.println(e1);

			  	}catch (IOException e) {
			  		System.out.println("IOException");
				   	System.out.println(e);

			  	} catch (KeyManagementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		
		}
		
	
    }
}