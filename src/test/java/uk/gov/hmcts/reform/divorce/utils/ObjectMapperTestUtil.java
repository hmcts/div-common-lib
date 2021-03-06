package uk.gov.hmcts.reform.divorce.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;

public final class ObjectMapperTestUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T retrieveFileContentsAsObject(final String absoluteFilePath, Class<T> type)
        throws IOException, URISyntaxException {
        String json = retrieveFileContents(absoluteFilePath);
        return convertJsonToObject(json, type);
    }

    public static String retrieveFileContents(final String absoluteFilePath) throws URISyntaxException, IOException {
        URL resource = ObjectMapperTestUtil.class.getClassLoader().getResource(absoluteFilePath);
        if (resource != null) {
            URI uri = resource.toURI();
            return new String(Files.readAllBytes(Paths.get(uri)), Charset.forName("utf-8"));
        } else {
            throw new RuntimeException(format("File %s not found", absoluteFilePath));
        }
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T convertJsonToObject(final String json, Class<T> type) throws IOException {
        return objectMapper.readValue(json, type);
    }

    public static ObjectMapper getObjectMapperInstance() {
        return objectMapper;
    }

}
