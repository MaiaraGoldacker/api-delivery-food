package com.api.algafood.core.config.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;



@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>>{
	//configurar propriedades que retornam do Pageable - (Na resposta da consulta)
	@Override
	public void serialize(Page<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject(); //chave para abrir o page
		
		gen.writeObjectField("content", value.getContent()); //envelopa o conteudo no content
		
		gen.writeNumberField("size", value.getSize()); //tamanho da pagina - quantos elementos em cada pagina
		gen.writeNumberField("totalElements", value.getTotalElements());
		gen.writeNumberField("totalPages", value.getTotalPages());
		gen.writeNumberField("number", value.getNumber()); //qual pagina você se encontra quando está navegando
		
		gen.writeEndObject();//chave para fechar o page
	}

}
