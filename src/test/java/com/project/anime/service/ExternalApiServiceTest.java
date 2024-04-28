package com.project.anime.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.ContextAttributes;
import com.fasterxml.jackson.databind.cfg.DeserializerFactoryConfig;
import com.fasterxml.jackson.databind.cfg.SerializerFactoryConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.DefaultAccessorNamingStrategy;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.StdSubtypeResolver;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.project.anime.dto.externalapi.ExternalApiRequest;
import com.project.anime.dto.externalapi.ExternalApiResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

@ContextConfiguration(classes = {ExternalApiService.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisabledInAotMode
class ExternalApiServiceTest {
  @Autowired
  private ExternalApiService externalApiService;

  @MockBean
  private ObjectMapper objectMapper;

  /**
   * Method under test:
   * {@link ExternalApiService#getAnimeByTitle(ExternalApiRequest)}
   */
  @Test
  void testGetAnimeByTitle() {
    // Arrange and Act
    ExternalApiResponse actualAnimeByTitle =
        externalApiService.getAnimeByTitle(new ExternalApiRequest("Dr"));

    // Assert
    RestTemplate restTemplate = externalApiService.restTemplate;
    List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    assertEquals(6, messageConverters.size());
    HttpMessageConverter<?> getResult = messageConverters.get(5);
    ObjectMapper objectMapper2 =
        ((MappingJackson2HttpMessageConverter) getResult).getObjectMapper();
    SerializationConfig config = ((ObjectMapper) objectMapper2.getJsonFactory().getCodec())
        .getSerializerProviderInstance()
        .getConfig();
    assertTrue(config.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper2.getDeserializationConfig();
    assertTrue(deserializationConfig.getAttributes() instanceof ContextAttributes.Impl);
    assertTrue(config.getAttributes() instanceof ContextAttributes.Impl);
    DeserializationContext deserializationContext = objectMapper2.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ObjectCodec codec = factory.getCodec();
    SerializerProvider serializerProviderInstance =
        ((ObjectMapper) codec).getSerializerProviderInstance();
    assertTrue(
        serializerProviderInstance.getAnnotationIntrospector() instanceof AnnotationIntrospectorPair);
    AnnotationIntrospector annotationIntrospector =
        deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof AnnotationIntrospectorPair);
    assertTrue(deserializationConfig.getClassIntrospector() instanceof BasicClassIntrospector);
    assertTrue(config.getClassIntrospector() instanceof BasicClassIntrospector);
    assertTrue(
        deserializationConfig.getAccessorNaming() instanceof DefaultAccessorNamingStrategy.Provider);
    assertTrue(config.getAccessorNaming() instanceof DefaultAccessorNamingStrategy.Provider);
    assertTrue(config.getDefaultVisibilityChecker() instanceof VisibilityChecker.Std);
    JsonFactory factory3 = codec.getFactory();
    ObjectCodec codec2 = factory3.getCodec();
    assertTrue(
        ((ObjectMapper) codec2).getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(config.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(((ObjectMapper) codec2).getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(deserializationConfig.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(config.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(((ObjectMapper) codec2).getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(
        ((ObjectMapper) codec2).getSerializerProvider() instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance2 = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance2 instanceof DefaultSerializerProvider.Impl);
    assertTrue(
        ((ObjectMapper) codec2).getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProvider = ((ObjectMapper) codec).getSerializerProvider();
    assertTrue(serializerProvider.getDefaultNullKeySerializer() instanceof FailingSerializer);
    assertTrue(serializerProvider.getDefaultNullValueSerializer() instanceof NullSerializer);
    assertTrue(deserializationConfig.getDateFormat() instanceof StdDateFormat);
    assertTrue(config.getDateFormat() instanceof StdDateFormat);
    assertTrue(restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory);
    assertTrue(restTemplate.getErrorHandler() instanceof DefaultResponseErrorHandler);
    UriTemplateHandler uriTemplateHandler = restTemplate.getUriTemplateHandler();
    assertTrue(uriTemplateHandler instanceof DefaultUriBuilderFactory);
    assertEquals(" ", factory3.getRootValueSeparator());
    HttpMessageConverter<?> getResult2 = messageConverters.get(1);
    assertEquals("ISO-8859-1",
        ((StringHttpMessageConverter) getResult2).getDefaultCharset().name());
    assertEquals("RU", serializerProviderInstance.getLocale().getCountry());
    assertEquals("RU", deserializationConfig.getLocale().getCountry());
    assertNull(deserializationContext.getParser());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper2.getInjectableValues());
    assertNull(((ObjectMapper) codec2).getInjectableValues());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(config.getFullRootName());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(config.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(config.getHandlerInstantiator());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(actualAnimeByTitle);
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(config.getDefaultMergeable());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(((ObjectMapper) codec).getSerializationConfig().getActiveView());
    assertNull(config.getActiveView());
    assertNull(((ObjectMapper) codec).getTypeFactory().getClassLoader());
    assertNull(serializerProviderInstance.getTypeFactory().getClassLoader());
    assertNull(deserializationConfig.getTypeFactory().getClassLoader());
    assertNull(restTemplate.getObservationConvention());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, serializerProviderInstance.getTimeZone().getDSTSavings());
    assertEquals(0, deserializationConfig.getTimeZone().getDSTSavings());
    assertEquals(2, messageConverters.get(0).getSupportedMediaTypes().size());
    assertEquals(2, getResult2.getSupportedMediaTypes().size());
    assertEquals(2, getResult.getSupportedMediaTypes().size());
    assertEquals(2079, factory3.getGeneratorFeatures());
    assertEquals(21771068, config.getSerializationFeatures());
    assertEquals(237020288, deserializationConfig.getDeserializationFeatures());
    HttpMessageConverter<?> getResult3 = messageConverters.get(4);
    assertEquals(3, getResult3.getSupportedMediaTypes().size());
    assertEquals(3, ((ObjectMapper) codec).getRegisteredModuleIds().size());
    assertEquals(65537, factory3.getParserFeatures());
    assertEquals(9999, ((ObjectMapper) codec).getNodeFactory().getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, config.getSerializationInclusion());
    SerializationConfig config2 = serializerProviderInstance2.getConfig();
    assertEquals(Nulls.DEFAULT, config2.getDefaultSetterInfo().getContentNulls());
    assertEquals(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT,
        ((DefaultUriBuilderFactory) uriTemplateHandler).getEncodingMode());
    DeserializerFactoryConfig factoryConfig =
        ((BeanDeserializerFactory) factory2).getFactoryConfig();
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    SerializerFactoryConfig factoryConfig2 =
        ((BeanSerializerFactory) ((ObjectMapper) codec).getSerializerFactory())
            .getFactoryConfig();
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(((Jaxb2RootElementHttpMessageConverter) getResult3).isProcessExternalEntities());
    assertFalse(((Jaxb2RootElementHttpMessageConverter) getResult3).isSupportDtd());
    assertFalse(((DefaultUriBuilderFactory) uriTemplateHandler).hasBaseUri());
    assertTrue(annotationIntrospector.version().isUknownVersion());
    assertTrue(objectMapper2.getSerializationConfig().getAnnotationIntrospector().version()
        .isUknownVersion());
    assertTrue(factoryConfig.hasDeserializers());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(factoryConfig.hasValueInstantiators());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(config.isAnnotationProcessingEnabled());
    assertTrue(factoryConfig2.hasKeySerializers());
    assertTrue(factoryConfig2.hasSerializers());
    assertTrue(
        ((ArrayIterator<Serializers>) ((BeanSerializerFactory) objectMapper2.getSerializerFactory()).getFactoryConfig()
            .serializers()).hasNext());
    assertTrue(restTemplate.getObservationRegistry().isNoop());
    assertTrue(restTemplate.getClientHttpRequestInitializers().isEmpty());
    assertTrue(restTemplate.getInterceptors().isEmpty());
    assertEquals(Integer.MAX_VALUE, deserializationConfig.getBase64Variant().getMaxLineLength());
    assertEquals(Integer.MAX_VALUE, config2.getBase64Variant().getMaxLineLength());
  }

  /**
   * Method under test:
   * {@link ExternalApiService#getAnimeByTitle(ExternalApiRequest)}
   */
  @Test
  void testGetAnimeByTitle2() {
    // Arrange and Act
    ExternalApiResponse actualAnimeByTitle =
        externalApiService.getAnimeByTitle(new ExternalApiRequest("Mr"));

    // Assert
    RestTemplate restTemplate = externalApiService.restTemplate;
    List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    assertEquals(6, messageConverters.size());
    HttpMessageConverter<?> getResult = messageConverters.get(5);
    ObjectMapper objectMapper2 =
        ((MappingJackson2HttpMessageConverter) getResult).getObjectMapper();
    SerializationConfig config = ((ObjectMapper) objectMapper2.getJsonFactory().getCodec())
        .getSerializerProviderInstance()
        .getConfig();
    assertTrue(config.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper2.getDeserializationConfig();
    assertTrue(deserializationConfig.getAttributes() instanceof ContextAttributes.Impl);
    assertTrue(config.getAttributes() instanceof ContextAttributes.Impl);
    DeserializationContext deserializationContext = objectMapper2.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ObjectCodec codec = factory.getCodec();
    SerializerProvider serializerProviderInstance =
        ((ObjectMapper) codec).getSerializerProviderInstance();
    assertTrue(
        serializerProviderInstance.getAnnotationIntrospector() instanceof AnnotationIntrospectorPair);
    AnnotationIntrospector annotationIntrospector =
        deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof AnnotationIntrospectorPair);
    assertTrue(deserializationConfig.getClassIntrospector() instanceof BasicClassIntrospector);
    assertTrue(config.getClassIntrospector() instanceof BasicClassIntrospector);
    assertTrue(
        deserializationConfig.getAccessorNaming() instanceof DefaultAccessorNamingStrategy.Provider);
    assertTrue(config.getAccessorNaming() instanceof DefaultAccessorNamingStrategy.Provider);
    assertTrue(config.getDefaultVisibilityChecker() instanceof VisibilityChecker.Std);
    JsonFactory factory3 = codec.getFactory();
    ObjectCodec codec2 = factory3.getCodec();
    assertTrue(
        ((ObjectMapper) codec2).getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(config.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(((ObjectMapper) codec2).getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(deserializationConfig.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(config.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(((ObjectMapper) codec2).getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(
        ((ObjectMapper) codec2).getSerializerProvider() instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance2 = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance2 instanceof DefaultSerializerProvider.Impl);
    assertTrue(
        ((ObjectMapper) codec2).getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProvider = ((ObjectMapper) codec).getSerializerProvider();
    assertTrue(serializerProvider.getDefaultNullKeySerializer() instanceof FailingSerializer);
    assertTrue(serializerProvider.getDefaultNullValueSerializer() instanceof NullSerializer);
    assertTrue(deserializationConfig.getDateFormat() instanceof StdDateFormat);
    assertTrue(config.getDateFormat() instanceof StdDateFormat);
    assertTrue(restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory);
    assertTrue(restTemplate.getErrorHandler() instanceof DefaultResponseErrorHandler);
    UriTemplateHandler uriTemplateHandler = restTemplate.getUriTemplateHandler();
    assertTrue(uriTemplateHandler instanceof DefaultUriBuilderFactory);
    assertEquals(" ", factory3.getRootValueSeparator());
    HttpMessageConverter<?> getResult2 = messageConverters.get(1);
    assertEquals("ISO-8859-1",
        ((StringHttpMessageConverter) getResult2).getDefaultCharset().name());
    assertEquals("RU", serializerProviderInstance.getLocale().getCountry());
    assertEquals("RU", deserializationConfig.getLocale().getCountry());
    assertNull(deserializationContext.getParser());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper2.getInjectableValues());
    assertNull(((ObjectMapper) codec2).getInjectableValues());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(config.getFullRootName());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(config.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(config.getHandlerInstantiator());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(actualAnimeByTitle);
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(config.getDefaultMergeable());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(((ObjectMapper) codec).getSerializationConfig().getActiveView());
    assertNull(config.getActiveView());
    assertNull(((ObjectMapper) codec).getTypeFactory().getClassLoader());
    assertNull(serializerProviderInstance.getTypeFactory().getClassLoader());
    assertNull(deserializationConfig.getTypeFactory().getClassLoader());
    assertNull(restTemplate.getObservationConvention());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, serializerProviderInstance.getTimeZone().getDSTSavings());
    assertEquals(0, deserializationConfig.getTimeZone().getDSTSavings());
    assertEquals(2, messageConverters.get(0).getSupportedMediaTypes().size());
    assertEquals(2, getResult2.getSupportedMediaTypes().size());
    assertEquals(2, getResult.getSupportedMediaTypes().size());
    assertEquals(2079, factory3.getGeneratorFeatures());
    assertEquals(21771068, config.getSerializationFeatures());
    assertEquals(237020288, deserializationConfig.getDeserializationFeatures());
    HttpMessageConverter<?> getResult3 = messageConverters.get(4);
    assertEquals(3, getResult3.getSupportedMediaTypes().size());
    assertEquals(3, ((ObjectMapper) codec).getRegisteredModuleIds().size());
    assertEquals(65537, factory3.getParserFeatures());
    assertEquals(9999, ((ObjectMapper) codec).getNodeFactory().getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, config.getSerializationInclusion());
    SerializationConfig config2 = serializerProviderInstance2.getConfig();
    assertEquals(Nulls.DEFAULT, config2.getDefaultSetterInfo().getContentNulls());
    assertEquals(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT,
        ((DefaultUriBuilderFactory) uriTemplateHandler).getEncodingMode());
    DeserializerFactoryConfig factoryConfig =
        ((BeanDeserializerFactory) factory2).getFactoryConfig();
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    SerializerFactoryConfig factoryConfig2 =
        ((BeanSerializerFactory) ((ObjectMapper) codec).getSerializerFactory())
            .getFactoryConfig();
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(((Jaxb2RootElementHttpMessageConverter) getResult3).isProcessExternalEntities());
    assertFalse(((Jaxb2RootElementHttpMessageConverter) getResult3).isSupportDtd());
    assertFalse(((DefaultUriBuilderFactory) uriTemplateHandler).hasBaseUri());
    assertTrue(annotationIntrospector.version().isUknownVersion());
    assertTrue(objectMapper2.getSerializationConfig().getAnnotationIntrospector().version()
        .isUknownVersion());
    assertTrue(factoryConfig.hasDeserializers());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(factoryConfig.hasValueInstantiators());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(config.isAnnotationProcessingEnabled());
    assertTrue(factoryConfig2.hasKeySerializers());
    assertTrue(factoryConfig2.hasSerializers());
    assertTrue(
        ((ArrayIterator<Serializers>) ((BeanSerializerFactory) objectMapper2.getSerializerFactory()).getFactoryConfig()
            .serializers()).hasNext());
    assertTrue(restTemplate.getObservationRegistry().isNoop());
    assertTrue(restTemplate.getClientHttpRequestInitializers().isEmpty());
    assertTrue(restTemplate.getInterceptors().isEmpty());
    assertEquals(Integer.MAX_VALUE, deserializationConfig.getBase64Variant().getMaxLineLength());
    assertEquals(Integer.MAX_VALUE, config2.getBase64Variant().getMaxLineLength());
  }

  /**
   * Method under test:
   * {@link ExternalApiService#getAnimeByTitle(ExternalApiRequest)}
   */
  @Test
  void testGetAnimeByTitle3() {
    // Arrange and Act
    ExternalApiResponse actualAnimeByTitle = externalApiService.getAnimeByTitle(null);

    // Assert
    RestTemplate restTemplate = externalApiService.restTemplate;
    List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    assertEquals(6, messageConverters.size());
    HttpMessageConverter<?> getResult = messageConverters.get(5);
    ObjectMapper objectMapper2 =
        ((MappingJackson2HttpMessageConverter) getResult).getObjectMapper();
    SerializationConfig config = ((ObjectMapper) objectMapper2.getJsonFactory().getCodec())
        .getSerializerProviderInstance()
        .getConfig();
    assertTrue(config.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper2.getDeserializationConfig();
    assertTrue(deserializationConfig.getAttributes() instanceof ContextAttributes.Impl);
    assertTrue(config.getAttributes() instanceof ContextAttributes.Impl);
    DeserializationContext deserializationContext = objectMapper2.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ObjectCodec codec = factory.getCodec();
    SerializerProvider serializerProviderInstance =
        ((ObjectMapper) codec).getSerializerProviderInstance();
    assertTrue(
        serializerProviderInstance.getAnnotationIntrospector() instanceof AnnotationIntrospectorPair);
    AnnotationIntrospector annotationIntrospector =
        deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof AnnotationIntrospectorPair);
    assertTrue(deserializationConfig.getClassIntrospector() instanceof BasicClassIntrospector);
    assertTrue(config.getClassIntrospector() instanceof BasicClassIntrospector);
    assertTrue(
        deserializationConfig.getAccessorNaming() instanceof DefaultAccessorNamingStrategy.Provider);
    assertTrue(config.getAccessorNaming() instanceof DefaultAccessorNamingStrategy.Provider);
    assertTrue(config.getDefaultVisibilityChecker() instanceof VisibilityChecker.Std);
    JsonFactory factory3 = codec.getFactory();
    ObjectCodec codec2 = factory3.getCodec();
    assertTrue(
        ((ObjectMapper) codec2).getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(config.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(((ObjectMapper) codec2).getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(deserializationConfig.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(config.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(((ObjectMapper) codec2).getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(
        ((ObjectMapper) codec2).getSerializerProvider() instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance2 = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance2 instanceof DefaultSerializerProvider.Impl);
    assertTrue(
        ((ObjectMapper) codec2).getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProvider = ((ObjectMapper) codec).getSerializerProvider();
    assertTrue(serializerProvider.getDefaultNullKeySerializer() instanceof FailingSerializer);
    assertTrue(serializerProvider.getDefaultNullValueSerializer() instanceof NullSerializer);
    assertTrue(deserializationConfig.getDateFormat() instanceof StdDateFormat);
    assertTrue(config.getDateFormat() instanceof StdDateFormat);
    assertTrue(restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory);
    assertTrue(restTemplate.getErrorHandler() instanceof DefaultResponseErrorHandler);
    UriTemplateHandler uriTemplateHandler = restTemplate.getUriTemplateHandler();
    assertTrue(uriTemplateHandler instanceof DefaultUriBuilderFactory);
    assertEquals(" ", factory3.getRootValueSeparator());
    HttpMessageConverter<?> getResult2 = messageConverters.get(1);
    assertEquals("ISO-8859-1",
        ((StringHttpMessageConverter) getResult2).getDefaultCharset().name());
    assertEquals("RU", serializerProviderInstance.getLocale().getCountry());
    assertEquals("RU", deserializationConfig.getLocale().getCountry());
    assertNull(deserializationContext.getParser());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper2.getInjectableValues());
    assertNull(((ObjectMapper) codec2).getInjectableValues());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(config.getFullRootName());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(config.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(config.getHandlerInstantiator());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(actualAnimeByTitle);
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(config.getDefaultMergeable());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(((ObjectMapper) codec).getSerializationConfig().getActiveView());
    assertNull(config.getActiveView());
    assertNull(((ObjectMapper) codec).getTypeFactory().getClassLoader());
    assertNull(serializerProviderInstance.getTypeFactory().getClassLoader());
    assertNull(deserializationConfig.getTypeFactory().getClassLoader());
    assertNull(restTemplate.getObservationConvention());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, serializerProviderInstance.getTimeZone().getDSTSavings());
    assertEquals(0, deserializationConfig.getTimeZone().getDSTSavings());
    assertEquals(2, messageConverters.get(0).getSupportedMediaTypes().size());
    assertEquals(2, getResult2.getSupportedMediaTypes().size());
    assertEquals(2, getResult.getSupportedMediaTypes().size());
    assertEquals(2079, factory3.getGeneratorFeatures());
    assertEquals(21771068, config.getSerializationFeatures());
    assertEquals(237020288, deserializationConfig.getDeserializationFeatures());
    HttpMessageConverter<?> getResult3 = messageConverters.get(4);
    assertEquals(3, getResult3.getSupportedMediaTypes().size());
    assertEquals(3, ((ObjectMapper) codec).getRegisteredModuleIds().size());
    assertEquals(65537, factory3.getParserFeatures());
    assertEquals(9999, ((ObjectMapper) codec).getNodeFactory().getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, config.getSerializationInclusion());
    SerializationConfig config2 = serializerProviderInstance2.getConfig();
    assertEquals(Nulls.DEFAULT, config2.getDefaultSetterInfo().getContentNulls());
    assertEquals(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT,
        ((DefaultUriBuilderFactory) uriTemplateHandler).getEncodingMode());
    DeserializerFactoryConfig factoryConfig =
        ((BeanDeserializerFactory) factory2).getFactoryConfig();
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    SerializerFactoryConfig factoryConfig2 =
        ((BeanSerializerFactory) ((ObjectMapper) codec).getSerializerFactory())
            .getFactoryConfig();
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(((Jaxb2RootElementHttpMessageConverter) getResult3).isProcessExternalEntities());
    assertFalse(((Jaxb2RootElementHttpMessageConverter) getResult3).isSupportDtd());
    assertFalse(((DefaultUriBuilderFactory) uriTemplateHandler).hasBaseUri());
    assertTrue(annotationIntrospector.version().isUknownVersion());
    assertTrue(objectMapper2.getSerializationConfig().getAnnotationIntrospector().version()
        .isUknownVersion());
    assertTrue(factoryConfig.hasDeserializers());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(factoryConfig.hasValueInstantiators());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(config.isAnnotationProcessingEnabled());
    assertTrue(factoryConfig2.hasKeySerializers());
    assertTrue(factoryConfig2.hasSerializers());
    assertTrue(
        ((ArrayIterator<Serializers>) ((BeanSerializerFactory) objectMapper2.getSerializerFactory()).getFactoryConfig()
            .serializers()).hasNext());
    assertTrue(restTemplate.getObservationRegistry().isNoop());
    assertTrue(restTemplate.getClientHttpRequestInitializers().isEmpty());
    assertTrue(restTemplate.getInterceptors().isEmpty());
    assertEquals(Integer.MAX_VALUE, deserializationConfig.getBase64Variant().getMaxLineLength());
    assertEquals(Integer.MAX_VALUE, config2.getBase64Variant().getMaxLineLength());
  }

  /**
   * Method under test:
   * {@link ExternalApiService#getAnimeByTitle(ExternalApiRequest)}
   */
  @Test
  void testGetAnimeByTitle4() {
    // Arrange
    ExternalApiRequest request = mock(ExternalApiRequest.class);
    when(request.getTitle()).thenReturn("Dr");

    // Act
    ExternalApiResponse actualAnimeByTitle = externalApiService.getAnimeByTitle(request);

    // Assert
    verify(request).getTitle();
    RestTemplate restTemplate = externalApiService.restTemplate;
    List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    assertEquals(6, messageConverters.size());
    HttpMessageConverter<?> getResult = messageConverters.get(5);
    ObjectMapper objectMapper2 =
        ((MappingJackson2HttpMessageConverter) getResult).getObjectMapper();
    SerializationConfig config = ((ObjectMapper) objectMapper2.getJsonFactory().getCodec())
        .getSerializerProviderInstance()
        .getConfig();
    assertTrue(config.getDefaultPrettyPrinter() instanceof DefaultPrettyPrinter);
    JsonFactory factory = objectMapper2.getFactory();
    assertTrue(factory instanceof MappingJsonFactory);
    DeserializationConfig deserializationConfig = objectMapper2.getDeserializationConfig();
    assertTrue(deserializationConfig.getAttributes() instanceof ContextAttributes.Impl);
    assertTrue(config.getAttributes() instanceof ContextAttributes.Impl);
    DeserializationContext deserializationContext = objectMapper2.getDeserializationContext();
    DeserializerFactory factory2 = deserializationContext.getFactory();
    assertTrue(factory2 instanceof BeanDeserializerFactory);
    assertTrue(deserializationContext instanceof DefaultDeserializationContext.Impl);
    ObjectCodec codec = factory.getCodec();
    SerializerProvider serializerProviderInstance =
        ((ObjectMapper) codec).getSerializerProviderInstance();
    assertTrue(
        serializerProviderInstance.getAnnotationIntrospector() instanceof AnnotationIntrospectorPair);
    AnnotationIntrospector annotationIntrospector =
        deserializationConfig.getAnnotationIntrospector();
    assertTrue(annotationIntrospector instanceof AnnotationIntrospectorPair);
    assertTrue(deserializationConfig.getClassIntrospector() instanceof BasicClassIntrospector);
    assertTrue(config.getClassIntrospector() instanceof BasicClassIntrospector);
    assertTrue(
        deserializationConfig.getAccessorNaming() instanceof DefaultAccessorNamingStrategy.Provider);
    assertTrue(config.getAccessorNaming() instanceof DefaultAccessorNamingStrategy.Provider);
    assertTrue(config.getDefaultVisibilityChecker() instanceof VisibilityChecker.Std);
    JsonFactory factory3 = codec.getFactory();
    ObjectCodec codec2 = factory3.getCodec();
    assertTrue(
        ((ObjectMapper) codec2).getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(config.getPolymorphicTypeValidator() instanceof LaissezFaireSubTypeValidator);
    assertTrue(((ObjectMapper) codec2).getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(deserializationConfig.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(config.getSubtypeResolver() instanceof StdSubtypeResolver);
    assertTrue(((ObjectMapper) codec2).getSerializerFactory() instanceof BeanSerializerFactory);
    assertTrue(
        ((ObjectMapper) codec2).getSerializerProvider() instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProviderInstance2 = objectMapper2.getSerializerProviderInstance();
    assertTrue(serializerProviderInstance2 instanceof DefaultSerializerProvider.Impl);
    assertTrue(
        ((ObjectMapper) codec2).getSerializerProviderInstance() instanceof DefaultSerializerProvider.Impl);
    SerializerProvider serializerProvider = ((ObjectMapper) codec).getSerializerProvider();
    assertTrue(serializerProvider.getDefaultNullKeySerializer() instanceof FailingSerializer);
    assertTrue(serializerProvider.getDefaultNullValueSerializer() instanceof NullSerializer);
    assertTrue(deserializationConfig.getDateFormat() instanceof StdDateFormat);
    assertTrue(config.getDateFormat() instanceof StdDateFormat);
    assertTrue(restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory);
    assertTrue(restTemplate.getErrorHandler() instanceof DefaultResponseErrorHandler);
    UriTemplateHandler uriTemplateHandler = restTemplate.getUriTemplateHandler();
    assertTrue(uriTemplateHandler instanceof DefaultUriBuilderFactory);
    assertEquals(" ", factory3.getRootValueSeparator());
    HttpMessageConverter<?> getResult2 = messageConverters.get(1);
    assertEquals("ISO-8859-1",
        ((StringHttpMessageConverter) getResult2).getDefaultCharset().name());
    assertEquals("RU", serializerProviderInstance.getLocale().getCountry());
    assertEquals("RU", deserializationConfig.getLocale().getCountry());
    assertNull(deserializationContext.getParser());
    assertNull(deserializationContext.getConfig());
    assertNull(objectMapper2.getInjectableValues());
    assertNull(((ObjectMapper) codec2).getInjectableValues());
    assertNull(deserializationConfig.getFullRootName());
    assertNull(config.getFullRootName());
    assertNull(deserializationConfig.getPropertyNamingStrategy());
    assertNull(config.getPropertyNamingStrategy());
    assertNull(serializerProvider.getConfig());
    assertNull(deserializationConfig.getHandlerInstantiator());
    assertNull(config.getHandlerInstantiator());
    assertNull(serializerProviderInstance.getFilterProvider());
    assertNull(deserializationConfig.getProblemHandlers());
    assertNull(actualAnimeByTitle);
    assertNull(deserializationConfig.getDefaultMergeable());
    assertNull(config.getDefaultMergeable());
    assertNull(deserializationContext.getActiveView());
    assertNull(serializerProvider.getActiveView());
    assertNull(deserializationConfig.getActiveView());
    assertNull(((ObjectMapper) codec).getSerializationConfig().getActiveView());
    assertNull(config.getActiveView());
    assertNull(((ObjectMapper) codec).getTypeFactory().getClassLoader());
    assertNull(serializerProviderInstance.getTypeFactory().getClassLoader());
    assertNull(deserializationConfig.getTypeFactory().getClassLoader());
    assertNull(restTemplate.getObservationConvention());
    assertEquals(0, deserializationContext.getDeserializationFeatures());
    assertEquals(0, serializerProviderInstance.getTimeZone().getDSTSavings());
    assertEquals(0, deserializationConfig.getTimeZone().getDSTSavings());
    assertEquals(2, messageConverters.get(0).getSupportedMediaTypes().size());
    assertEquals(2, getResult2.getSupportedMediaTypes().size());
    assertEquals(2, getResult.getSupportedMediaTypes().size());
    assertEquals(2079, factory3.getGeneratorFeatures());
    assertEquals(21771068, config.getSerializationFeatures());
    assertEquals(237020288, deserializationConfig.getDeserializationFeatures());
    HttpMessageConverter<?> getResult3 = messageConverters.get(4);
    assertEquals(3, getResult3.getSupportedMediaTypes().size());
    assertEquals(3, ((ObjectMapper) codec).getRegisteredModuleIds().size());
    assertEquals(65537, factory3.getParserFeatures());
    assertEquals(9999, ((ObjectMapper) codec).getNodeFactory().getMaxElementIndexForInsert());
    assertEquals(JsonInclude.Include.ALWAYS, config.getSerializationInclusion());
    SerializationConfig config2 = serializerProviderInstance2.getConfig();
    assertEquals(Nulls.DEFAULT, config2.getDefaultSetterInfo().getContentNulls());
    assertEquals(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT,
        ((DefaultUriBuilderFactory) uriTemplateHandler).getEncodingMode());
    DeserializerFactoryConfig factoryConfig =
        ((BeanDeserializerFactory) factory2).getFactoryConfig();
    assertFalse(factoryConfig.hasAbstractTypeResolvers());
    assertFalse(factoryConfig.hasDeserializerModifiers());
    SerializerFactoryConfig factoryConfig2 =
        ((BeanSerializerFactory) ((ObjectMapper) codec).getSerializerFactory())
            .getFactoryConfig();
    assertFalse(factoryConfig2.hasSerializerModifiers());
    assertFalse(((Jaxb2RootElementHttpMessageConverter) getResult3).isProcessExternalEntities());
    assertFalse(((Jaxb2RootElementHttpMessageConverter) getResult3).isSupportDtd());
    assertFalse(((DefaultUriBuilderFactory) uriTemplateHandler).hasBaseUri());
    assertTrue(annotationIntrospector.version().isUknownVersion());
    assertTrue(objectMapper2.getSerializationConfig().getAnnotationIntrospector().version()
        .isUknownVersion());
    assertTrue(factoryConfig.hasDeserializers());
    assertTrue(factoryConfig.hasKeyDeserializers());
    assertTrue(factoryConfig.hasValueInstantiators());
    assertTrue(deserializationConfig.isAnnotationProcessingEnabled());
    assertTrue(config.isAnnotationProcessingEnabled());
    assertTrue(factoryConfig2.hasKeySerializers());
    assertTrue(factoryConfig2.hasSerializers());
    assertTrue(
        ((ArrayIterator<Serializers>) ((BeanSerializerFactory) objectMapper2.getSerializerFactory()).getFactoryConfig()
            .serializers()).hasNext());
    assertTrue(restTemplate.getObservationRegistry().isNoop());
    assertTrue(restTemplate.getClientHttpRequestInitializers().isEmpty());
    assertTrue(restTemplate.getInterceptors().isEmpty());
    assertEquals(Integer.MAX_VALUE, deserializationConfig.getBase64Variant().getMaxLineLength());
    assertEquals(Integer.MAX_VALUE, config2.getBase64Variant().getMaxLineLength());
  }
}
