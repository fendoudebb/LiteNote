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
