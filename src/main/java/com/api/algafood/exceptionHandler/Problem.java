package com.api.algafood.exceptionHandler;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL) //só inclui no json se propriedade não estiver null
@Getter
@Builder
public class Problem {

	//padrão da especificação
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	//especialização
	private String userMessage;
	private LocalDateTime timestamp;
}
