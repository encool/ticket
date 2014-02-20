import java.awt.Dimension;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import javax.net.ssl.*;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BigCon {
	Boolean ishttps=false;
	String url;
	String postdata;
	HttpURLConnection urlcon;
	private HttpURLConnection con;
	private String session=null;
	int method=1;// 1--post , 2-- get
	private String urlstring;
	public BigCon(String urlstring, String postdata, HttpURLConnection urlcon,Boolean ishttps,int method,String session) {
		super();
		this.session=session;
		this.ishttps=ishttps;
		this.urlstring = urlstring;
		this.postdata = postdata;
		this.urlcon = urlcon;
		this.method=method;
	}
	public HttpURLConnection getCon() {
		// TODO Auto-generated method stub
		return con;
	}
	static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
		public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		}
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}
		static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
		return true;
		}
	}
	void DoConnect(){
		if(method==1){
			try{
				OutputStreamWriter outw = new OutputStreamWriter(con.getOutputStream());
			
				if (postdata != null) {
					outw.write(postdata,0,postdata.length()); 					
					outw.flush(); 
					outw.close();
				}
			}catch (ConnectException e) {
				   System.out.println("ConnectException");
				   System.out.println(e);
//				   throw e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}else{
			try {
				con.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	void Init(){
		InputStream connectionIn = null;
		OutputStreamWriter outw = null;
		try {
			if(ishttps){
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
			new java.security.SecureRandom());
				URL console = new URL(
						urlstring);
				HttpsURLConnection con1 = (HttpsURLConnection) console
						.openConnection();
				con1.setSSLSocketFactory(sc.getSocketFactory());
				con1.setHostnameVerifier(new TrustAnyHostnameVerifier());
				con=con1;
			}else{
				URL console = new URL(
						urlstring);
				con=(HttpURLConnection) console.openConnection();
			}

	    //加入数据 
			if(method==1){
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Length", String.valueOf(postdata.length()));
			}
			 
			con.setDoOutput(true); 
			//头设置
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("Host", "kyfw.12306.cn");
			con.setRequestProperty("Accept", "*/*");
			con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			con.setRequestProperty("Origin", "https://kyfw.12306.cn");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			con.setRequestProperty("Referer", "https://kyfw.12306.cn/otn/login/init");
			con.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
			con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
			con.setRequestProperty("Accept", "*/*");
			
			//Cookie Conten_length
			if(session!=null){
				con.setRequestProperty("Cookie",session);
			}			

//			DoConnect();
//			outw=new OutputStreamWriter(con.getOutputStream());
//			if (postdata != null) 
//				outw.write(postdata,0,postdata.length()); 
//
//			outw.flush(); 
//			outw.close();
		
//			con.connect();
			//打印输出流
//			int returnCode = con.getResponseCode();	
//			connectionIn = null;		
//			if (returnCode==200)
//			connectionIn = con.getInputStream();
//			else
//			connectionIn = con.getErrorStream();
//			// print resulting stream
//			BufferedReader buffer1 = new BufferedReader(new InputStreamReader(connectionIn,"utf-8"));
//			BufferedReader buffer1 = new BufferedReader(new InputStreamReader(connectionIn));		
//			String inputLine;
//			while ((inputLine = buffer1.readLine()) != null)
//			System.out.println(inputLine);
//			buffer1.close();
//		    con.disconnect();
		} catch (ConnectException e) {
			   System.out.println("ConnectException");
			   System.out.println(e);
//			   throw e;
		} catch (IOException e) {
			   System.out.println("IOException");
			   System.out.println(e);
//			   throw e;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			   try {
				   connectionIn.close();
			   		} 
			   	catch (Exception e) {
			   	}
		    try {
		    	outw.close();
		    	} 
		    	catch (Exception e) {
		    	}
		   }
	}
//    public static void main(String[] args) throws Exception {
//	String uname="liqiao100@yahoo.cn";
//	String pword="815720q";
//	String rancode="1111";
//	String postdata="loginUserDTO.user_name="+uname+"&"+"&userDTO.password="+pword+"randCode="+rancode;
//	InputStream in = null;
//	OutputStream out = null;
//	PassCode pc;
//	String str_return = "";
//	String session;
//	HttpURLConnection con = null;
//	try {
//		if(ishttps){
//			SSLContext sc = SSLContext.getInstance("SSL");
//			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
//		new java.security.SecureRandom());
//			URL console = new URL(
//					"https://kyfw.12306.cn/otn/login/loginAysnSuggest");
//			HttpsURLConnection con1 = (HttpsURLConnection) console
//					.openConnection();
//			con1.setSSLSocketFactory(sc.getSocketFactory());
//			con1.setHostnameVerifier(new TrustAnyHostnameVerifier());
//			con=con1;
//		}else{
//			URL console = new URL(
//					"http://kyfw.12306.cn/otn/login/loginAysnSuggest");
//			con=(HttpURLConnection) console.openConnection();
//		}
//
//    //加入数据 
//		con.setRequestMethod("POST"); 
//		con.setDoOutput(true); 
//		//头设置
//		con.setRequestProperty("Connection", "keep-alive");
//		con.setRequestProperty("Host", "kyfw.12306.cn");
//		con.setRequestProperty("Accept", "*/*");
//		con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//		con.setRequestProperty("Origin", "https://kyfw.12306.cn");
//		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
//		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		con.setRequestProperty("Referer", "https://kyfw.12306.cn/otn/login/init");
//		con.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
//		con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4");
//		con.setRequestProperty("Accept", "*/*");
//		
//		pc=new PassCode();
//		pc.excute();
//		session=pc.getSession();
//		//Cookie Conten_length
//		con.setRequestProperty("Cookie",session);
//		con.setRequestProperty("Content-Length", String.valueOf(postdata.length()));
//		
//		OutputStreamWriter outw=new OutputStreamWriter(con.getOutputStream());
//		if (postdata != null) 
//			outw.write(postdata,0,postdata.length()); 
//
//		outw.flush(); 
//		outw.close();
//	
////		con.connect();
//		//打印输出流
//		int returnCode = con.getResponseCode();	
//		InputStream connectionIn = null;		
//		if (returnCode==200)
//		connectionIn = con.getInputStream();
//		else
//		connectionIn = con.getErrorStream();
//		// print resulting stream
//		BufferedReader buffer1 = new BufferedReader(new InputStreamReader(connectionIn,"utf-8"));
////		BufferedReader buffer1 = new BufferedReader(new InputStreamReader(connectionIn));		
//		String inputLine;
//		while ((inputLine = buffer1.readLine()) != null)
//		System.out.println(inputLine);
//		buffer1.close();
//	    con.disconnect();
//		} catch (ConnectException e) {
//		   System.out.println("ConnectException");
//		   System.out.println(e);
//		   throw e;
//	   } catch (IOException e) {
//		   System.out.println("IOException");
//		   System.out.println(e);
//		   throw e;
//	   } finally {
//		   try {
//		   	in.close();
//		   		} 
//		   	catch (Exception e) {
//		   	}
//	    try {
//	    	out.close();
//	    	} 
//	    	catch (Exception e) {
//	    	}
//	   }
//	   System.out.println(str_return);
//	}
	public  class PassCode{
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
		void excute(){
			try {
				HttpURLConnection con = null;
				randon=Math.random();
				if(ishttps){
					SSLContext sc = SSLContext.getInstance("SSL");
					sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
							new java.security.SecureRandom());
					System.out.println(randon);
					URL console = new URL(
							"https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew.do?module=login&rand=sjrand&"+String.valueOf(randon));
					con = (HttpsURLConnection) console
							.openConnection();
					((HttpsURLConnection) con).setSSLSocketFactory(sc.getSocketFactory());
					((HttpsURLConnection) con).setHostnameVerifier(new TrustAnyHostnameVerifier());					
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
					createAndShowGUI(bytesimage);
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
    private static void createAndShowGUI(byte[] bytesimage) {
        //Make sure we have nice window decorations.

        JFrame.setDefaultLookAndFeelDecorated(true);
        
        ImageIcon icon=new ImageIcon(bytesimage);
        //Create and set up the window.
        JFrame frame = new JFrame("Helloticket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500,500));

//        jl.
        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel();
        label.setIcon(icon);
//      frame.getContentPane().add(label);
//        JOptionPane pane = new JOptionPane(label,JOptionPane.PLAIN_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
//        JDialog jl=pane.createDialog(frame,"");
//        jl.show();
        JOptionPane.showInputDialog(label);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}