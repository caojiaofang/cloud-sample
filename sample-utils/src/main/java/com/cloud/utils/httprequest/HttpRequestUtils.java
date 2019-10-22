package com.cloud.utils.httprequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.utils.json.SamJsonUtil;

public class HttpRequestUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class); // 日志记录

	/**
	 * 
	 * httpPost
	 * 
	 * @param url       路径
	 * 
	 * @param jsonParam 参数
	 * 
	 * @return
	 * 
	 */

	public static String httpPost(String url, String jsonParam) {

		return httpPost(url, jsonParam, "application/xml",false);

	}

	
	/**
	 * 
	 * httpPost
	 * 
	 * @param url       路径
	 * 
	 * @param jsonParam 参数
	 * 
	 * @param contentType 类型
	 * 
	 * @return
	 * 
	 */

	public static String httpPost(String url, String jsonParam, String contentType) {

		return httpPost(url, jsonParam, contentType, false);

	}
	
	/**
	 * 
	 * post请求
	 * 
	 * @param url            url地址
	 * 
	 * @param jsonParam      参数
	 * 
	 * @param noNeedResponse 不需要返回结果
	 * 
	 * @return
	 * 
	 */

	public static String httpPost(String url, String jsonParam, String contentType,boolean noNeedResponse) {

		// post请求返回结果
		String reStr = "";
		// DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost method = new HttpPost(url);

		try {

			if (null != jsonParam) {

				// 解决中文乱码问题

				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");

				entity.setContentEncoding("utf-8");

				entity.setContentType(contentType);

				method.setEntity(entity);

			}

			HttpResponse result = httpClient.execute(method);

			url = URLDecoder.decode(url, "utf-8");

			/** 请求发送成功，并得到响应 **/

			if (result.getStatusLine().getStatusCode() == 200) {

				String str = "";

				try {

					/** 读取服务器返回过来的json字符串数据 **/

					reStr = EntityUtils.toString(result.getEntity());

					if (noNeedResponse) {

						return null;

					}

					/** 把json字符串转换成json对象 **/

					// jsonResult = JSONObject.fromObject(str);

				} catch (Exception e) {

					logger.error("post请求提交失败:" + url, e);
				}

			}

		} catch (IOException e) {

			logger.error("post请求提交失败:" + url, e);

		}

		return reStr;

	}

	/**
	 * 
	 * 发送get请求
	 * 
	 * @param url 路径
	 * 
	 * @return
	 * 
	 */

	public static String httpGet(String url) {

		// get请求返回结果

		String jsonResult = null;

		try {

			// DefaultHttpClient client = new DefaultHttpClient();
			HttpClient client = HttpClientBuilder.create().build();
			// 发送get请求

			HttpGet request = new HttpGet(url);

			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				/** 读取服务器返回过来的json字符串数据 **/

				String strResult = EntityUtils.toString(response.getEntity());

				/** 把json字符串转换成json对象 **/
				
				jsonResult = SamJsonUtil.toJson(strResult);

				url = URLDecoder.decode(url, "UTF-8");

			} else {

				logger.error("get请求提交失败:" + url);

			}

		} catch (IOException e) {

			logger.error("get请求提交失败:" + url, e);

		}

		return jsonResult;

	}

	public static void main(String[] args) {
		String xmlStr = "<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><ns1:SACCT00020011918 xmlns:ns1=\"http://www.adtec.com.cn\"><RequestHeader><VerNo>2019051600</VerNo><ReqSysCd>001002</ReqSysCd><TxnMod>0</TxnMod><ReqSecCd></ReqSecCd><TxnCd>SACCT00020011918</TxnCd><TxnTyp>R</TxnTyp><WhlTm>60</WhlTm><ReqDt>20190516</ReqDt><WhlSeqNo>SZC201905162019051600000349</WhlSeqNo><TxnNme></TxnNme><ReqTm>1558009651409</ReqTm><ReqSeqNo>2019051600000349</ReqSeqNo><ChnlNo>SZC</ChnlNo><TlrNo>7000019</TlrNo><BrchNme></BrchNme><BrchNo>701001</BrchNo><SndFileNme></SndFileNme><AuthTlr></AuthTlr><MaxRec></MaxRec><BgnRec></BgnRec><VTlrNo></VTlrNo><ChkCd></ChkCd><Checker></Checker><AcctBrch></AcctBrch><BizTyp>SZC</BizTyp><ChkNo>1234567891234567</ChkNo><BootNo></BootNo><WinNm></WinNm><FsysFlg></FsysFlg><WinId></WinId><HMac></HMac><FileHMac></FileHMac><BkVerNo></BkVerNo><BkSubVerNo></BkSubVerNo><BkNodeId></BkNodeId><BkEntrNo></BkEntrNo><ComBranchCode></ComBranchCode><BkBusiNo></BkBusiNo><CertTyp></CertTyp><HostquestHead><OrgNodeID>001002</OrgNodeID><OrgBranchID>701001</OrgBranchID><OrgTermTyp></OrgTermTyp><OrgTermSrl></OrgTermSrl><OrgTellerID>7000019</OrgTellerID><OrgTxDate>20190516</OrgTxDate><OrgTxTime>20273141</OrgTxTime><OrgTxLogNo>2019051600000349</OrgTxLogNo><OrgTradeCode></OrgTradeCode><AuthTelPin></AuthTelPin><UpFileID></UpFileID><AgentBranch></AgentBranch><Gentel></Gentel><TermID></TermID><TelKind></TelKind><ReqAddFld1></ReqAddFld1><ReqAddFld2></ReqAddFld2><ReqAddFld3></ReqAddFld3><ReqTimOut></ReqTimOut><ReqPawFlg></ReqPawFlg><CorpCod>9999</CorpCod><Waibjymc></Waibjymc><Farendma>9999</Farendma></HostquestHead></RequestHeader><RequestBody><BatFileName></BatFileName><TranCtrlFlg>01000000000000000000</TranCtrlFlg><BusiSpNo>SZC201905162019051600000350</BusiSpNo><BusiSubTyp></BusiSubTyp><Rmrk01></Rmrk01><Rmrk02></Rmrk02><Rmrk03></Rmrk03><Rmrk04></Rmrk04><Rmrk05></Rmrk05><Loop><AccInfo><GrpSerNum></GrpSerNum><AcctCtrlFlg>10000000000000000000</AcctCtrlFlg><CcyCode1>156</CcyCode1><CashRemFlg01></CashRemFlg01><CashTrfFlg>1</CashTrfFlg><TrSrcAcct>0</TrSrcAcct><TrfCustAcctNo>62285601000002817</TrfCustAcctNo><GoAcctName>浑嫁</GoAcctName><StayCharSerNum></StayCharSerNum><TrfAcctTyp></TrfAcctTyp><PwdChkWay>0</PwdChkWay><AcctPwd1></AcctPwd1><VchBat></VchBat><VchSeq></VchSeq><CertTp></CertTp><CeIdCd2></CeIdCd2><PmtAmt>1.00</PmtAmt><OtherAmt></OtherAmt><AcctNumSrc>1</AcctNumSrc><AcctInstSrc>3</AcctInstSrc><AcctNumOrg>700003</AcctNumOrg><InBusiCode>30010004</InBusiCode><InAcctSerNo1>000001</InAcctSerNo1><TrfAcctTyp1></TrfAcctTyp1><ChargeSerNum></ChargeSerNum><CashProjCd></CashProjCd><TrCustTyp2></TrCustTyp2><TrCustAcctNo></TrCustAcctNo><RolAcctSerNo></RolAcctSerNo><TrsfOutNme></TrsfOutNme><TrfFinInstTyp></TrfFinInstTyp><TrfFinInstCode></TrfFinInstCode><TrfFinInstName></TrfFinInstName><TrfeeCustTp></TrfeeCustTp><TrfCustAcctNo1></TrfCustAcctNo1><TrfAcctSerNo></TrfAcctSerNo><TrfAcctName1></TrfAcctName1><TpFiscatutions></TpFiscatutions><TpFiscatCode></TpFiscatCode><TrfeFisInstNm></TrfeFisInstNm><SumCode>IBP004</SumCode><Summary>支付系统同城汇路渠道转账业务使用</Summary><CeRmrk></CeRmrk><Rmark></Rmark><AlteCha01></AlteCha01><AlteCha02></AlteCha02><AlteCha03></AlteCha03><AlteCha04></AlteCha04><SparedAmt01></SparedAmt01><SparedAmt02></SparedAmt02><SparedAmt03></SparedAmt03><SpareAmt04></SpareAmt04><CtrlSolu></CtrlSolu><CtrlNo2></CtrlNo2><CtrlTyp1></CtrlTyp1><FrzRang></FrzRang><SoluCtrlAmt></SoluCtrlAmt><CtrlRsn></CtrlRsn><AuCtrlDate></AuCtrlDate></AccInfo></Loop><Loop1/><Loop2/></RequestBody></ns1:SACCT00020011918></soapenv:Body></soapenv:Envelope>";
		// String httpPost =
		// HttpRequestUtils.httpPost("http://200.100.153.30:13118/SACCT00020011918",
		// xmlStr);
		String httpPost = HttpRequestUtils.httpPost("http://localhost:11211/SACCT00020011918", xmlStr);
//		String s1250 = "<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><ns1:SMNG000020011250 xmlns:ns1=\"http://www.adtec.com.cn\"><RequestHeader><VerNo>2019051600</VerNo><ReqSysCd>001002</ReqSysCd><TxnMod>0</TxnMod><ReqSecCd></ReqSecCd><TxnCd>SMNG000020011250</TxnCd><TxnTyp>R</TxnTyp><WhlTm>60</WhlTm><ReqDt>20190516</ReqDt><WhlSeqNo>SZC2019051690516283</WhlSeqNo><TxnNme></TxnNme><ReqTm>1557992003596</ReqTm><ReqSeqNo>90516283</ReqSeqNo><ChnlNo>SZC</ChnlNo><TlrNo>XN00041</TlrNo><BrchNme></BrchNme><BrchNo>000035</BrchNo><SndFileNme></SndFileNme><AuthTlr></AuthTlr><MaxRec></MaxRec><BgnRec></BgnRec><VTlrNo></VTlrNo><ChkCd></ChkCd><Checker></Checker><AcctBrch></AcctBrch><BizTyp>SZC</BizTyp><ChkNo></ChkNo><BootNo></BootNo><WinNm></WinNm><FsysFlg></FsysFlg><WinId></WinId><HMac></HMac><FileHMac></FileHMac><BkVerNo></BkVerNo><BkSubVerNo></BkSubVerNo><BkNodeId></BkNodeId><BkEntrNo></BkEntrNo><ComBranchCode></ComBranchCode><BkBusiNo></BkBusiNo><CertTyp></CertTyp><HostquestHead><OrgNodeID>001002</OrgNodeID><OrgBranchID>000035</OrgBranchID><OrgTermTyp></OrgTermTyp><OrgTermSrl></OrgTermSrl><OrgTellerID>XN00041</OrgTellerID><OrgTxDate>20190516</OrgTxDate><OrgTxTime>15332359</OrgTxTime><OrgTxLogNo>90516283</OrgTxLogNo><OrgTradeCode></OrgTradeCode><AuthTelPin></AuthTelPin><UpFileID></UpFileID><AgentBranch></AgentBranch><Gentel></Gentel><TermID></TermID><TelKind></TelKind><ReqAddFld1></ReqAddFld1><ReqAddFld2></ReqAddFld2><ReqAddFld3></ReqAddFld3><ReqTimOut></ReqTimOut><ReqPawFlg></ReqPawFlg><CorpCod>9999</CorpCod><Waibjymc></Waibjymc><Farendma>9999</Farendma><Ipdizhii></Ipdizhii></HostquestHead></RequestHeader><RequestBody><CustAcctTyp1></CustAcctTyp1><CustAcctNo5>62285601000002429</CustAcctNo5><PwdChkWay>2</PwdChkWay><Pwd2>66767D7392F411BF</Pwd2><PwdTypOf>11</PwdTypOf><CeCustNo></CeCustNo><CustVchTp6></CustVchTp6><CeldCd2></CeldCd2><CustAcctName></CustAcctName></RequestBody></ns1:SMNG000020011250></soapenv:Body></soapenv:Envelope>";
//		String httpPost = HttpRequestUtils.httpPost("http://200.100.153.30:13118/SMNG000020011250", s1250);
		try {
			String str = new String(httpPost.getBytes("ISO-8859-1"), "UTF-8");
			System.out.println(httpPost);
			System.out.println("==================================");
			System.out.println(str);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}