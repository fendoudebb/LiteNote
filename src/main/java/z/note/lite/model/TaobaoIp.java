package z.note.lite.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TaobaoIp {

    private String code;

    private Data data;

    @Setter
    @Getter
    @ToString
    public static class Data {

        @JsonProperty("QUERY_IP")
        private String queryIp; // 113.87.160.228

        @JsonProperty("COUNTRY_CODE")
        private String countryCode; // CN

        @JsonProperty("COUNTRY_EN")
        private String countryEn; // China

        @JsonProperty("COUNTRY_CN")
        private String countryCn; // 中国

        @JsonProperty("AREA_CODE")
        private String areaCode; // 80

        @JsonProperty("AREA_EN")
        private String areaEn; // HuaNan

        @JsonProperty("AREA_CN")
        private String areaCn; // 华南

        @JsonProperty("PROVINCE_CODE")
        private String provinceCode; // 440000

        @JsonProperty("PROVINCE_EN")
        private String provinceEn; // Guangdong

        @JsonProperty("PROVINCE_CN")
        private String provinceCn; // 广东

        @JsonProperty("CITY_CODE")
        private String cityCode; // 440300

        @JsonProperty("CITY_EN")
        private String cityEn; // Shenzhen

        @JsonProperty("CITY_CN")
        private String cityCn; // 深圳

        @JsonProperty("COUNTY_CODE")
        private String countyCode; // null

        @JsonProperty("COUNTY_EN")
        private String countyEn; // null

        @JsonProperty("COUNTY_CN")
        private String countyCn; // null

        @JsonProperty("ISP_CODE")
        private String ispCode; // 100017

        @JsonProperty("ISP_EN")
        private String ispEn; // China-Telecom

        @JsonProperty("ISP_CN")
        private String ispCn; // 电信

        @JsonProperty("LONGITUDE")
        private String longitude; // ""

        @JsonProperty("LATITUDE")
        private String latitude; // ""

        @JsonProperty("TZONE")
        private String tzone; // ""

        @JsonProperty("ASN")
        private String asn; // null

    }

}

/*
 * {
 *     "code": "0",
 *     "data": {
 *         "CITY_EN": "Shenzhen",
 *         "QUERY_IP": "113.87.160.228",
 *         "CITY_CODE": "440300",
 *         "CITY_CN": "深圳",
 *         "COUNTY_EN": "null",
 *         "LONGITUDE": "",
 *         "PROVINCE_CN": "广东",
 *         "TZONE": "",
 *         "PROVINCE_EN": "Guangdong",
 *         "ISP_EN": "China-Telecom",
 *         "AREA_CODE": "80",
 *         "PROVINCE_CODE": "440000",
 *         "ISP_CN": "电信",
 *         "AREA_CN": "华南",
 *         "COUNTRY_CN": "中国",
 *         "AREA_EN": "HuaNan",
 *         "COUNTRY_EN": "China",
 *         "COUNTY_CN": "null",
 *         "COUNTY_CODE": "null",
 *         "ASN": "null",
 *         "LATITUDE": "",
 *         "COUNTRY_CODE": "CN",
 *         "ISP_CODE": "100017"
 *     }
 * }
 */

// curl -vv -X GET --location "https://ip.taobao.com/getIpInfo.php?ip=103.116.121.43"
//* About to connect() to ip.taobao.com port 443 (#0)
//*   Trying 59.82.122.172...
//* Connected to ip.taobao.com (59.82.122.172) port 443 (#0)
//* Initializing NSS with certpath: sql:/etc/pki/nssdb
//*   CAfile: /etc/pki/tls/certs/ca-bundle.crt
//  CApath: none
//* SSL connection using TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
//* Server certificate:
//*       subject: CN=*.taobao.com,O="Alibaba (China) Technology Co., Ltd.",L=HangZhou,ST=ZheJiang,C=CN
//*       start date: Mar 06 09:22:04 2024 GMT
//*       expire date: Jun 08 02:06:47 2024 GMT
//*       common name: *.taobao.com
//*       issuer: CN=GlobalSign Organization Validation CA - SHA256 - G3,O=GlobalSign nv-sa,C=BE
//> GET /getIpInfo.php?ip=103.116.121.43 HTTP/1.1
//> User-Agent: curl/7.29.0
//> Host: ip.taobao.com
//> Accept: */*
//>
//< HTTP/1.1 404 Not Found
//< Date: Mon, 01 Apr 2024 13:25:56 GMT
//< Content-Type: text/html
//< Transfer-Encoding: chunked
//< Connection: keep-alive
//< Vary: Accept-Encoding
//< Vary: Accept-Encoding
//< Ups-Target-Key: ip.taobao.com
//< X-protocol: HTTP/1.1
//< EagleEye-TraceId: 2150b4b817119779565901578e1274
//<
//<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
//<html>
//<head><title>404 Not Found</title></head>
//<body bgcolor="white"><script>
//with(document)with(body)with(insertBefore(createElement("script"),firstChild))setAttribute("exparams","category=&userid=&aplus&yunid=&&trid=2150b4b817119779565901578e1274&asid=AQAAAADktQpmBigIPwAAAAAie8QATi3Qqw==",id="tb-beacon-aplus",src=(location>"https"?"//g":"//g")+".alicdn.com/alilog/mlog/aplus_v2.js")
//</script>
//
//<h1>404 Not Found</h1>
//<p>The requested URL was not found on this server.</body>
//</html>
//* Connection #0 to host ip.taobao.com left intact
