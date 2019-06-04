package com.cloud.utils.json;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommonInf {
	
	@NotBlank
	private String bsSrcChl;
	@NotBlank
	private String bsDstChl;
	@Length(max = 4)
	private String payChl;
	@NotBlank
	private String ibsFlg;
	@Length(max = 4)
	private String tradCd;
	@Length(max = 20)
	private String msgTp;
	@NotBlank
	@Length(max = 35)
	private String tradId;
	@NotBlank
	@Length(min = 14, max = 14)
	private String sndTm;
	@NotBlank
	@Length(max = 30)
	private String pfId;
	@Length(max = 14)
	private String sndBnk;
	@Length(max = 14)
	private String rcvBnk;
	@NotBlank
	@Length(min = 8, max = 8)
	private String tradDt;
	@NotBlank
	@Length(min = 1, max = 1)
	private String srFlg;
	@Length(min = 8, max = 8)
	private String wrkDt;
	
	@Override
	public String toString() {
		return "PfId["+ pfId +"]PayChl["+ payChl +"]TradCd["+ tradCd +"]TradId["+ tradId +"]WrkDt["+ wrkDt +"]";
	}
}
