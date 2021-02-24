package com.api.algafood.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile>{//Nome da  anotation, tipo que valida 

	private DataSize maxSize;//classe do Spring que representa um tamanho de dado

	public void initialize(FileSize constraintAnnotation) {
		this.maxSize = DataSize.parse(constraintAnnotation.max());		
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {	
		return value == null || value.getSize() <= this.maxSize.toBytes();
	} 

}
