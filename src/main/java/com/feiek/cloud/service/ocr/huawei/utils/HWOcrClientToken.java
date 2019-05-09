/**
* Copyright 2018 Huawei Technologies Co.,Ltd.
* 
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use
* this file except in compliance with the License.  You may obtain a copy of the
* License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software distributed
* under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
* CONDITIONS OF ANY KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations under the License.
**/
package com.feiek.cloud.service.ocr.huawei.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huawei.ais.sdk.util.HttpClientUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


public class HWOcrClientToken {
	private  String domainName;  //domainName the same with username for common user.different for iam user
	private  String username;    //username of the user
	private  String passwd;      //password of the user 
	private  String regionName;  //region for ocr service(cn-north-1,cn-south-1 etc.)
	private  String token;       //A serial of character returned by iam server for authentication
	private  String httpEndpoint; //Http endpoint for the service

	/**
	 * 
	 * @param domain  domainName for iam user.If it's common user ,domainName is the same with usrname
	 * @param usrname  username of the user
	 * @param password password of the user 
	 * @param region   region for ocr service(cn-north-1,cn-south-1 etc.)
	 * @param endpoint http endpoint for the url
	 */
	public HWOcrClientToken(String domain,String usrname,String password,String region,String endpoint) {
		if (domain== null|domain == ""|usrname == null| usrname == ""| password == null| password == ""| region == null|
			region == "" | endpoint == null| endpoint == "")
				throw new IllegalArgumentException("the parameter for HWOcrClientToken's Constructor cannot be empty");
		domainName=domain;
		username=usrname;
		passwd=password;
		regionName=region;
		token="";
		httpEndpoint=endpoint;
		
	}
	/**
	 * Construct a token request object for accessing the service using token authentication.
	 * For details, see the following document:
	 * https://support.huaweicloud.com/en-us/api-ocr/ocr_03_0005.html
	 * 
	 * @return Construct the JSON object of the access. 
	 */	
	private  String requestBody() {
		JSONObject auth = new JSONObject();

		JSONObject identity = new JSONObject();

		JSONArray methods = new JSONArray();
		methods.add("password");
		identity.put("methods", methods);

		JSONObject password = new JSONObject();

		JSONObject user = new JSONObject();
		user.put("name", username);
		user.put("password", passwd);

		JSONObject domain = new JSONObject();
		domain.put("name", domainName);
		user.put("domain", domain);

		password.put("user", user);

		identity.put("password", password);

		JSONObject scope = new JSONObject();

		JSONObject scopeProject = new JSONObject();
		scopeProject.put("name", regionName);

		scope.put("project", scopeProject);

		auth.put("identity", identity);
		auth.put("scope", scope);

		JSONObject params = new JSONObject();
		params.put("auth", auth);
		return params.toJSONString();
	}
	
	/**
	 * Obtain the token parameter. Note that this function aims to extract the token from the header in the HTTP request response body. 
	 * The parameter name is X-Subject-Token.
	 * 
	 * @throws URISyntaxException
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	private  void getToken()
			throws URISyntaxException, UnsupportedOperationException, IOException {
		    //if token is not empty return token directly
		    if(0!=token.length())
		    	return ;
			// 1.构建获取Token所需要的参数
			String requestBody = requestBody();
			int iamSuccessResponseCode=201;
			String url = String.format("https://iam.%s.myhuaweicloud.com/v3/auth/tokens",regionName);
			Header[] headers = new Header[] { new BasicHeader("Content-Type", ContentType.APPLICATION_JSON.toString()) };
			StringEntity stringEntity = new StringEntity(requestBody,"utf-8");

			// 2.传入IAM服务对应的参数, 使用POST方法调用服务并解析出Token value
			HttpResponse response = HttpClientUtils.post(url, headers, stringEntity);
			//判断token获取http状态码，获取成功后才取出token，失败返回null
			if (iamSuccessResponseCode == response.getStatusLine().getStatusCode())
			{
			     Header[] xst = response.getHeaders("X-Subject-Token");
			     token=xst[0].getValue();
			}
			else
			{
			    System.out.println("Get Token Failed!");
			    String content = IOUtils.toString(response.getEntity().getContent());
			    System.out.println(content);
			    token="";
			}
	}
	   /**
     * Use the Base64-encoded file and access VAT Invoice OCR using token authentication.
     * @param endPoint httpendpoint for the requested service
     * @param url      url for the requested service
     * @param filepath Full File path
	 * @throws URISyntaxException 
	 * @throws UnsupportedOperationException 
     * @throws IOException
     */
    public HttpResponse requestOcrServiceBase64(String uri, String filepath) throws UnsupportedOperationException, URISyntaxException, IOException {
        // 1. Construct the parameters required for accessing VAT Invoice OCR.
    	if(uri == null | uri == "" | filepath == null | filepath == "")
    		throw new IllegalArgumentException("the parameter for requestOcrServiceBase64 cannot be empty");
		String completeurl = "https://"+httpEndpoint+uri;
		getToken();
		Header[] headers = new Header[] {new BasicHeader("X-Auth-Token", token), new BasicHeader("Content-Type", ContentType.APPLICATION_JSON.toString()) };
		try {
			byte[] fileData = FileUtils.readFileToByteArray(new File(filepath));
			String fileBase64Str = Base64.encodeBase64String(fileData);
			JSONObject json = new JSONObject();
			json.put("image", fileBase64Str);
			StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");

			// 2. Pass the parameters of VAT Invoice OCR, invoke the service using the POST method, and parse and output the recognition result.
			HttpResponse response = HttpClientUtils.post(completeurl, headers, stringEntity);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
