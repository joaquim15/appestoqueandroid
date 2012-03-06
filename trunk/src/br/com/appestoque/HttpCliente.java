package br.com.appestoque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class HttpCliente {

	private static final String TAG = "HttpClient";

	public static JSONObject SendHttpPost(String URL, JSONObject jsonObjSend) {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httpPostRequest = new HttpPost(URL);
			httpPostRequest.setEntity(new StringEntity(jsonObjSend.toString()));
			
			httpPostRequest.setHeader("Accept", "application/json");
			httpPostRequest.setHeader("Content-type", "application/json");
			httpPostRequest.setHeader("Accept-Encoding", "gzip"); // only set this parameter if you would like to use gzip compression

			long t = System.currentTimeMillis();
			HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);
			Log.i(TAG, "HTTPResponse received in [" + (System.currentTimeMillis()-t) + "ms]");

			// Get hold of the response entity (-> the data):
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// Read the content stream
				InputStream instream = entity.getContent();
				Header contentEncoding = response.getFirstHeader("Content-Encoding");
				if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					instream = new GZIPInputStream(instream);
				}

				// convert content stream to a String
				String resultString= convertStreamToString(instream);
				instream.close();
				resultString = resultString.substring(1,resultString.length()-1); // remove wrapping "[" and "]"

				// Transform the String into a JSONObject
				JSONObject jsonObjRecv = new JSONObject(resultString);
				// Raw DEBUG output of our received JSON object:
				Log.i(TAG,"<JSONObject>\n"+jsonObjRecv.toString()+"\n</JSONObject>");

				return jsonObjRecv;
			} 

		}catch (Exception e){
			// More about HTTP exception handling in another tutorial.
			// For now we just print the stack trace.
			e.printStackTrace();
		}
		return null;
	}


	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 * 
		 * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static JSONArray ReceiveHttpPost(String URL, Context context) {
	
		JSONArray objetos = null;
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			HttpResponse httpResponse = httpclient.execute(httpGet);
			
			switch(httpResponse.getStatusLine().getStatusCode()){
				case HttpStatus.SC_ACCEPTED:	
					Log.e(Constantes.TAG,"202 Accepted (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_BAD_GATEWAY:	
					Log.e(Constantes.TAG,"502 Bad Gateway (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_BAD_REQUEST:	
					Log.e(Constantes.TAG,"400 Bad Request (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_CONFLICT:	
					Log.e(Constantes.TAG,"409 Conflict (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_CONTINUE:	
					Log.e(Constantes.TAG,"100 Continue (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_CREATED:	
					Log.e(Constantes.TAG,"201 Created (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_EXPECTATION_FAILED:	
					Log.e(Constantes.TAG,"417 Expectation Failed (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_FAILED_DEPENDENCY:	
					Log.e(Constantes.TAG,"424 Failed Dependency (WebDAV - RFC 2518)");
					break;
				case HttpStatus.SC_FORBIDDEN:	
					Log.e(Constantes.TAG,"403 Forbidden (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_GATEWAY_TIMEOUT:	
					Log.e(Constantes.TAG,"504 Gateway Timeout (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_GONE:	
					Log.e(Constantes.TAG,"410 Gone (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED:	
					Log.e(Constantes.TAG,"505 HTTP Version Not Supported (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_INSUFFICIENT_SPACE_ON_RESOURCE:	
					Log.e(Constantes.TAG,"Static constant for a 419 error.");
					break;
				case HttpStatus.SC_INSUFFICIENT_STORAGE:	
					Log.e(Constantes.TAG,"507 Insufficient Storage (WebDAV - RFC 2518)");
					break;
				case HttpStatus.SC_INTERNAL_SERVER_ERROR:	
					Log.e(Constantes.TAG,"500 Server Error (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_LENGTH_REQUIRED:	
					Log.e(Constantes.TAG,"411 Length Required (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_LOCKED:	
					Log.e(Constantes.TAG,"423 Locked (WebDAV - RFC 2518)");
					break;					
				case HttpStatus.SC_METHOD_FAILURE:	
					Log.e(Constantes.TAG,"Static constant for a 420 error.");
					break;
				case HttpStatus.SC_METHOD_NOT_ALLOWED:	
					Log.e(Constantes.TAG,"405 Method Not Allowed (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_MOVED_PERMANENTLY:	
					Log.e(Constantes.TAG,"301 Moved Permanently (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_MOVED_TEMPORARILY:	
					Log.e(Constantes.TAG,"302 Moved Temporarily (Sometimes Found) (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_MULTIPLE_CHOICES:	
					Log.e(Constantes.TAG,"300 Mutliple Choices (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_MULTI_STATUS:	
					Log.e(Constantes.TAG,"207 Multi-Status (WebDAV - RFC 2518) or 207 Partial Update OK (HTTP/1.1 - draft-ietf-http-v11-spec-rev-01?)");
					break;
				case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION:	
					Log.e(Constantes.TAG,"203 Non Authoritative Information (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_NOT_ACCEPTABLE:
					Log.e(Constantes.TAG,"406 Not Acceptable (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_NOT_FOUND:	
					Log.e(Constantes.TAG, "404 Not Found (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_NOT_IMPLEMENTED:	
					Log.e(Constantes.TAG,"501 Not Implemented (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_NOT_MODIFIED:	
					Log.e(Constantes.TAG,"304 Not Modified (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_NO_CONTENT:	
					Log.e(Constantes.TAG,"204 No Content (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_OK:	
					//200 OK (HTTP/1.0 - RFC 1945)
					HttpEntity httpEntity = httpResponse.getEntity();
					InputStream inputStream = httpEntity.getContent();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					String data = bufferedReader.readLine();
					objetos = new JSONArray(data);
					break;
				case HttpStatus.SC_PARTIAL_CONTENT:	
					Log.e(Constantes.TAG,"206 Partial Content (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_PAYMENT_REQUIRED:	
					Log.e(Constantes.TAG,"402 Payment Required (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_PRECONDITION_FAILED:	
					Log.e(Constantes.TAG,"412 Precondition Failed (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_PROCESSING:	
					Log.e(Constantes.TAG,"102 Processing (WebDAV - RFC 2518)");
					break;
				case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED:	
					Log.e(Constantes.TAG,"407 Proxy Authentication Required (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE:	
					Log.e(Constantes.TAG,"416 Requested Range Not Satisfiable (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_REQUEST_TIMEOUT:	
					Log.e(Constantes.TAG,"408 Request Timeout (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_REQUEST_TOO_LONG:	
					Log.e(Constantes.TAG,"413 Request Entity Too Large (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_REQUEST_URI_TOO_LONG:	
					Log.e(Constantes.TAG,"414 Request-URI Too Long (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_RESET_CONTENT:	
					Log.e(Constantes.TAG,"205 Reset Content (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_SEE_OTHER:	
					Log.e(Constantes.TAG,"303 See Other (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_SERVICE_UNAVAILABLE:	
					Log.e(Constantes.TAG,"503 Service Unavailable (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_SWITCHING_PROTOCOLS:	
					Log.e(Constantes.TAG,"101 Switching Protocols (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_TEMPORARY_REDIRECT:	
					Log.e(Constantes.TAG,"307 Temporary Redirect (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_UNAUTHORIZED:	
					Log.e(Constantes.TAG,"401 Unauthorized (HTTP/1.0 - RFC 1945)");
					break;
				case HttpStatus.SC_UNPROCESSABLE_ENTITY:	
					Log.e(Constantes.TAG,"422 Unprocessable Entity (WebDAV - RFC 2518)");
					break;
				case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE:	
					Log.e(Constantes.TAG,"415 Unsupported Media Type (HTTP/1.1 - RFC 2616)");
					break;
				case HttpStatus.SC_USE_PROXY:	
					Log.e(Constantes.TAG,"305 Use Proxy (HTTP/1.1 - RFC 2616)");
					break;
				default:
					Log.e(Constantes.TAG,"StatuS Code desconhecido: " + httpResponse.getStatusLine().getStatusCode() );
					break;
			}
			
		} catch (ClientProtocolException e) {
			Util.dialogo(context, String.valueOf(R.string.mensagem_clientProtocolException));
		} catch (IOException e) {
			Util.dialogo(context,String.valueOf(R.string.mensagem_ioexception));
		} catch (JSONException e) {
			Util.dialogo(context,String.valueOf(R.string.mensagem_jsonexception));
		}
		return objetos;
		
	}
	
}