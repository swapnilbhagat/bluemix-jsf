package com.talcraft.util;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonHelper {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static final String jsonToString(Object value)
			throws JsonGenerationException, JsonMappingException, IOException {
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, value);
		return stringWriter.toString();
	}

}