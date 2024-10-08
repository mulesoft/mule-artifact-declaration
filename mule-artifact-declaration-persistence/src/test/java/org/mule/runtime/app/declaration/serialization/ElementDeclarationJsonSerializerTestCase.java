/*
 * Copyright 2023 Salesforce, Inc. All rights reserved.
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.app.declaration.serialization;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newArtifact;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newListValue;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newObjectValue;
import static org.mule.runtime.app.declaration.api.fluent.ElementDeclarer.newParameterGroup;
import static org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue.cdata;
import static org.mule.runtime.app.declaration.api.fluent.ParameterSimpleValue.plain;
import static org.mule.runtime.app.declaration.api.fluent.SimpleValueType.BOOLEAN;
import static org.mule.runtime.app.declaration.api.fluent.SimpleValueType.NUMBER;
import static org.mule.runtime.app.declaration.api.fluent.SimpleValueType.STRING;
import static org.mule.runtime.app.declaration.serialization.impl.gson.GsonElementDeclarationJsonSerializer.configureGsonForElementDeclaration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.mule.runtime.app.declaration.api.ArtifactDeclaration;
import org.mule.runtime.app.declaration.api.ComponentElementDeclaration;
import org.mule.runtime.app.declaration.api.ConfigurationElementDeclaration;
import org.mule.runtime.app.declaration.api.ConstructElementDeclaration;
import org.mule.runtime.app.declaration.api.ElementDeclaration;
import org.mule.runtime.app.declaration.api.GlobalElementDeclarationVisitor;
import org.mule.runtime.app.declaration.api.ParameterElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterGroupElementDeclaration;
import org.mule.runtime.app.declaration.api.ParameterValue;
import org.mule.runtime.app.declaration.api.SourceElementDeclaration;
import org.mule.runtime.app.declaration.api.TopLevelParameterDeclaration;
import org.mule.runtime.app.declaration.api.component.location.Location;
import org.mule.runtime.app.declaration.api.fluent.ElementDeclarer;
import org.mule.runtime.app.declaration.api.fluent.ParameterObjectValue;
import org.mule.runtime.app.declaration.api.fluent.SimpleValueType;
import org.mule.runtime.app.declaration.serialization.api.ElementDeclarationJsonSerializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

public class ElementDeclarationJsonSerializerTestCase {

  private static final String CONNECTION = "Connection";
  private static final String NS_MULE_DOCUMENTATION = "http://www.mulesoft.org/schema/mule/documentation";
  private static final String DECLARING_EXTENSION_KEY = "declaringExtension";
  private static final String NAME_KEY = "name";

  private ArtifactDeclaration applicationDeclaration;

  @Before
  public void setup() {
    applicationDeclaration = createArtifact();
  }

  @Test
  public void serializationTest() {
    JsonParser parser = new JsonParser();
    JsonReader reader = new JsonReader(new InputStreamReader(Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(getExpectedArtifactDeclarationJson())));
    JsonElement expected = parser.parse(reader);
    JsonElement json = parser.parse(ElementDeclarationJsonSerializer.getDefault(true).serialize(applicationDeclaration));
    assertThat(json, is(equalTo(expected)));
  }

  @Test
  public void whenConfigRefIsNullIntoComponentElementDeclarationThenItShouldNotFailWhenItIsSerializedOrDeserialized()
      throws IOException {
    Gson gson = configureGsonForElementDeclaration(new GsonBuilder()).create();
    String elementString = IOUtils.toString(Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(getComponentElementDeclarationWithoutConfigRef()));
    Map<String, Object> expectedValues = gson.fromJson(elementString, Map.class);

    // Deserialization
    ComponentElementDeclaration componentElementDeclaration = gson.fromJson(elementString, ComponentElementDeclaration.class);
    assertThat(componentElementDeclaration instanceof SourceElementDeclaration, is(true));
    assertThat(componentElementDeclaration.getDeclaringExtension(), is(expectedValues.get(DECLARING_EXTENSION_KEY)));
    assertThat(componentElementDeclaration.getName(), is(expectedValues.get(NAME_KEY)));

    // Serialization
    String elementJsonString = gson.toJson(componentElementDeclaration);
    assertThat(elementJsonString, is(notNullValue()));
    assertThat(elementJsonString.contains(expectedValues.get(DECLARING_EXTENSION_KEY).toString()), is(true));
    assertThat(elementJsonString.contains(expectedValues.get(NAME_KEY).toString()), is(true));
    assertThat(elementJsonString.contains(Location.SOURCE.toUpperCase()), is(true));
  }

  @Test
  public void serializeDeserializeTest() {
    ElementDeclarationJsonSerializer serializer = ElementDeclarationJsonSerializer.getDefault(true);
    String json = serializer.serialize(applicationDeclaration);

    ArtifactDeclaration artifactDeclaration = serializer.deserialize(json, ArtifactDeclaration.class);
    assertThat(json, applicationDeclaration, is(equalTo(artifactDeclaration)));
  }

  @Test
  public void serializeDeserializeSerializeTest() throws JSONException {
    ElementDeclarationJsonSerializer serializer = ElementDeclarationJsonSerializer.getDefault(true);
    String originalJson = serializer.serialize(applicationDeclaration);

    ArtifactDeclaration loadedDeclaration = serializer.deserialize(originalJson, ArtifactDeclaration.class);

    JsonParser parser = new JsonParser();
    JsonElement jsonElement = parser.parse(ElementDeclarationJsonSerializer.getDefault(true).serialize(loadedDeclaration));
    String generatedJson = jsonElement.toString();

    JSONAssert.assertEquals(originalJson, generatedJson, true);
  }

  @Test
  public void equalsTopLevelParameterShouldIncludeParameters() {
    ElementDeclarer core = ElementDeclarer.forExtension("mule");
    ParameterObjectValue parameterObjectValue = ParameterObjectValue.builder()
        .withParameter("otherName", "simpleParam")
        .build();

    String globalParameterName = "globalParameterName";
    String globalTemplateRefName = "globalTemplateRefName";

    ParameterObjectValue modifiedParameterObjectValue = ParameterObjectValue.builder()
        .withParameter("otherName", "simpleParam")
        .withParameter("myCamelCaseName", "someContent")
        .build();

    assertThat(newArtifact().withGlobalElement(core.newGlobalParameter(globalParameterName)
        .withRefName(globalTemplateRefName)
        .withValue(parameterObjectValue)
        .getDeclaration()).getDeclaration(),
               not(equalTo(newArtifact().withGlobalElement(core.newGlobalParameter(globalParameterName)
                   .withRefName(globalTemplateRefName)
                   .withValue(modifiedParameterObjectValue)
                   .getDeclaration()).getDeclaration())));

  }

  @Test
  public void serializeIndividualItems() {
    GlobalElementDeclarationVisitor visitor = new GlobalElementDeclarationVisitor() {

      @Override
      public void visit(ConfigurationElementDeclaration declaration) {
        validateSerialization(declaration, ConfigurationElementDeclaration.class);
        // TODO(MULE-18578): uncomment when fixed
        // testParameters(declaration.getParameterGroups());
        // declaration.getConnection().ifPresent(conn -> {
        // validateSerialization(conn, ConnectionElementDeclaration.class);
        // testParameters(conn.getParameterGroups());
        // });
      }

      @Override
      public void visit(TopLevelParameterDeclaration declaration) {
        validateSerialization(declaration, TopLevelParameterDeclaration.class);
      }

      @Override
      public void visit(ConstructElementDeclaration declaration) {
        validateSerialization(declaration, ConstructElementDeclaration.class);
        testComponents(declaration.getComponents());
        // TODO(MULE-18578): uncomment when fixed
        // testParameters(declaration.getParameterGroups());
      }

      private void testComponents(List<ComponentElementDeclaration> componentElementDeclarations) {
        componentElementDeclarations.forEach(c -> {
          validateSerialization(c, c.getClass());
          // TODO(MULE-18578): uncomment when fixed
          // testParameters(c.getParameterGroups());
        });
      }

      private void testParameters(List<ParameterGroupElementDeclaration> parameterGroups) {
        parameterGroups.forEach(pg -> {
          validateSerialization(pg, ParameterGroupElementDeclaration.class);
          pg.getParameters().forEach(p -> validateSerialization(p, ParameterElementDeclaration.class));
        });
      }
    };

    applicationDeclaration.getGlobalElements().forEach(ge -> ge.accept(visitor));
  }

  private <T extends ElementDeclaration> void validateSerialization(ElementDeclaration elementDeclaration,
                                                                    Class<T> expectedClass) {
    ElementDeclarationJsonSerializer serializer = ElementDeclarationJsonSerializer.getDefault(true);
    ElementDeclaration deserialized = serializer.deserialize(serializer.serialize(elementDeclaration), expectedClass);
    deserialized.setDeclaringExtension(elementDeclaration.getDeclaringExtension());
    assertThat(elementDeclaration, equalTo(deserialized));
  }

  private ArtifactDeclaration createArtifact() {

    ElementDeclarer core = ElementDeclarer.forExtension("mule");
    ElementDeclarer db = ElementDeclarer.forExtension("Database");
    ElementDeclarer http = ElementDeclarer.forExtension("HTTP");
    ElementDeclarer sockets = ElementDeclarer.forExtension("Sockets");
    ElementDeclarer wsc = ElementDeclarer.forExtension("Web Service Consumer");
    ElementDeclarer file = ElementDeclarer.forExtension("File");
    ElementDeclarer os = ElementDeclarer.forExtension("ObjectStore");

    return newArtifact()
        .withCustomParameter("xmlns:doc", NS_MULE_DOCUMENTATION)
        .withGlobalElement(core.newConstruct("configuration")
            .withParameterGroup(group -> group.withParameter("defaultErrorHandler-ref",
                                                             createStringParameter("referableHandler")))
            .getDeclaration())
        .withGlobalElement(core.newConstruct("errorHandler")
            .withRefName("referableHandler")
            .withComponent(core.newRoute("onErrorContinue")
                .withParameterGroup(group -> group
                    .withParameter("type", createStringParameter("MULE:SOURCE_RESPONSE"))
                    .withParameter("logException", createBooleanParameter("false"))
                    .withParameter("enableNotifications", createBooleanParameter("false")))
                .withComponent(core.newOperation("logger")
                    .withParameterGroup(group -> group
                        .withParameter("level", createStringParameter("TRACE")))
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(os.newGlobalParameter("objectStore")
            .withRefName("persistentStore")
            .withValue(newObjectValue()
                .ofType("org.mule.extension.objectstore.api.TopLevelObjectStore")
                .withParameter("entryTtl", createNumberParameter("1"))
                .withParameter("entryTtlUnit", createStringParameter("HOURS"))
                .withParameter("maxEntries", createNumberParameter("10"))
                .withParameter("persistent", createBooleanParameter("true"))
                .withParameter("expirationInterval", createNumberParameter("2"))
                .withParameter("expirationIntervalUnit", createStringParameter("HOURS"))
                .withParameter("config-ref", createStringParameter("persistentConfig"))
                .build())
            .getDeclaration())
        .withGlobalElement(os.newConfiguration("config")
            .withRefName("persistentConfig")
            .getDeclaration())
        .withGlobalElement(file.newConfiguration("config")
            .withRefName("fileConfig")
            .withConnection(file.newConnection("connection").getDeclaration())
            .getDeclaration())
        .withGlobalElement(wsc.newConfiguration("config")
            .withRefName("wscConfig")
            .withParameterGroup(newParameterGroup()
                .withParameter("expirationPolicy", newObjectValue()
                    .ofType("org.mule.runtime.extension.api.ExpirationPolicy")
                    .withParameter("maxIdleTime", createNumberParameter("1"))
                    .withParameter("timeUnit", createStringParameter(MINUTES.name()))
                    .build())
                .getDeclaration())
            .withConnection(wsc.newConnection("connection")
                .withParameterGroup(newParameterGroup()
                    .withParameter("soapVersion", createStringParameter("SOAP11"))
                    .withParameter("mtomEnabled", createBooleanParameter("false"))
                    .getDeclaration())
                .withParameterGroup(newParameterGroup("Connection")
                    .withParameter("wsdlLocation", createStringParameter("http://www.webservicex.com/globalweather.asmx?WSDL"))
                    .withParameter("address", createStringParameter("http://www.webservicex.com/globalweather.asmx"))
                    .withParameter("service", createStringParameter("GlobalWeather"))
                    .withParameter("port", createStringParameter("GlobalWeatherSoap"))
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(db.newConfiguration("config")
            .withRefName("dbConfig")
            .withConnection(db
                .newConnection("derby").withParameterGroup(newParameterGroup()
                    .withParameter("poolingProfile", newObjectValue()
                        .withParameter("maxPoolSize", createNumberParameter("10"))
                        .build())
                    .getDeclaration())
                .withParameterGroup(newParameterGroup(CONNECTION)
                    .withParameter("connectionProperties", newObjectValue()
                        .withParameter("first", createStringParameter("propertyOne"))
                        .withParameter("second", createStringParameter("propertyTwo"))
                        .build())
                    .withParameter("reconnection", newObjectValue()
                        .ofType("Reconnection")
                        .withParameter("failsDeployment", createBooleanParameter("true"))
                        .withParameter("reconnectionStrategy", newObjectValue()
                            .ofType("reconnect")
                            .withParameter("count", createNumberParameter("1"))
                            .withParameter("frequency", createNumberParameter("0"))
                            .build())
                        .build())
                    .withParameter("database", createStringParameter("target/muleEmbeddedDB"))
                    .withParameter("create", createBooleanParameter("true"))
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(http.newConfiguration("listenerConfig")
            .withRefName("httpListener")
            .withParameterGroup(newParameterGroup()
                .withParameter("basePath", createStringParameter("/"))
                .getDeclaration())
            .withConnection(http.newConnection("listener")
                .withParameterGroup(newParameterGroup()
                    .withParameter("tlsContext", newObjectValue()
                        .withParameter("key-store", newObjectValue()
                            .withParameter("path", createStringParameter("ssltest-keystore.jks"))
                            .withParameter("password", createStringParameter("changeit"))
                            .withParameter("keyPassword", createStringParameter("changeit"))
                            .build())
                        .withParameter("trust-store", newObjectValue()
                            .withParameter("insecure", createBooleanParameter("true"))
                            .build())
                        .withParameter("revocation-check",
                                       newObjectValue()
                                           .ofType("standard-revocation-check")
                                           .withParameter("onlyEndEntities", createBooleanParameter("true"))
                                           .build())
                        .build())
                    .getDeclaration())
                .withParameterGroup(group -> group.withName(CONNECTION)
                    .withParameter("host", createStringParameter("localhost"))
                    .withParameter("port", createNumberParameter("49019"))
                    .withParameter("protocol", createStringParameter("HTTPS")))
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(http.newConfiguration("requestConfig")
            .withRefName("httpRequester")
            .withConnection(http.newConnection("request")
                .withParameterGroup(group -> group.withParameter("authentication",
                                                                 newObjectValue()
                                                                     .ofType("org.mule.extension.http.api.request.authentication.BasicAuthentication")
                                                                     .withParameter("username", createStringParameter("user"))
                                                                     .withParameter("password", createStringParameter("pass"))
                                                                     .build()))
                .withParameterGroup(newParameterGroup(CONNECTION)
                    .withParameter("host", createStringParameter("localhost"))
                    .withParameter("port", createNumberParameter("49020"))
                    .withParameter("clientSocketProperties",
                                   newObjectValue()
                                       .withParameter("connectionTimeout", createNumberParameter("1000"))
                                       .withParameter("keepAlive", createBooleanParameter("true"))
                                       .withParameter("receiveBufferSize", createNumberParameter("1024"))
                                       .withParameter("sendBufferSize", createNumberParameter("1024"))
                                       .withParameter("clientTimeout", createNumberParameter("1000"))
                                       .withParameter("linger", createNumberParameter("1000"))
                                       .build())
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(core.newConstruct("flow")
            .withRefName("testFlow")
            .withCustomParameter("doc:id", "docUUID")
            .withParameterGroup(group -> group.withParameter("initialState", createStringParameter("stopped")))
            .withComponent(http.newSource("listener")
                .withConfig("httpListener")
                .withCustomParameter("doc:id", "docUUID")
                .withParameterGroup(newParameterGroup()
                    .withParameter("path", createStringParameter("testBuilder"))
                    .withParameter("redeliveryPolicy",
                                   newObjectValue()
                                       .withParameter("maxRedeliveryCount", createNumberParameter("2"))
                                       .withParameter("useSecureHash", createBooleanParameter("true"))
                                       .build())
                    .getDeclaration())
                .withParameterGroup(group -> group.withName(CONNECTION)
                    .withParameter("reconnectionStrategy",
                                   newObjectValue()
                                       .ofType("reconnect")
                                       .withParameter("count", createNumberParameter("1"))
                                       .withParameter("frequency", createNumberParameter("0"))
                                       .build()))
                .withParameterGroup(newParameterGroup("Response")
                    .withParameter("headers", createStringCdataParameter("<![CDATA[#[{{'content-type' : 'text/plain'}}]]]>"))
                    .withParameter("body", createStringCdataParameter(
                                                                      "<![CDATA[#[\n"
                                                                          + "                    %dw 2.0\n"
                                                                          + "                    output application/json\n"
                                                                          + "                    input payload application/xml\n"
                                                                          + "                    var baseUrl=\"http://sample.cloudhub.io/api/v1.0/\"\n"
                                                                          + "                    ---\n"
                                                                          + "                    using (pageSize = payload.getItemsResponse.PageInfo.pageSize) {\n"
                                                                          + "                         links: [\n"
                                                                          + "                            {\n"
                                                                          + "                                href: fullUrl,\n"
                                                                          + "                                rel : \"self\"\n"
                                                                          + "                            }\n"
                                                                          + "                         ],\n"
                                                                          + "                         collection: {\n"
                                                                          + "                            size: pageSize,\n"
                                                                          + "                            items: payload.getItemsResponse.*Item map {\n"
                                                                          + "                                id: $.id,\n"
                                                                          + "                                type: $.type,\n"
                                                                          + "                                name: $.name\n"
                                                                          + "                            }\n"
                                                                          + "                         }\n"
                                                                          + "                    }\n"
                                                                          + "                ]]>"))
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newConstruct("choice")
                .withRoute(core.newRoute("when")
                    .withParameterGroup(newParameterGroup()
                        .withParameter("expression", createStringParameter("#[true]"))
                        .getDeclaration())
                    .withComponent(db.newOperation("bulkInsert")
                        .withParameterGroup(newParameterGroup("Query")
                            .withParameter("sql",
                                           createStringParameter("INSERT INTO PLANET(POSITION, NAME) VALUES (:position, :name)"))
                            .withParameter("parameterTypes",
                                           newListValue()
                                               .withValue(newObjectValue()
                                                   .withParameter("key", createStringParameter("name"))
                                                   .withParameter("type", createStringParameter("VARCHAR"))
                                                   .build())
                                               .withValue(newObjectValue()
                                                   .withParameter("key", createNumberParameter("position"))
                                                   .withParameter("type", createStringParameter("INTEGER"))
                                                   .build())
                                               .build())
                            .getDeclaration())
                        .getDeclaration())
                    .getDeclaration())
                .withRoute(core.newRoute("otherwise")
                    .withComponent(core.newConstruct("foreach")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("collection", createStringParameter("#[myCollection]"))
                            .getDeclaration())
                        .withComponent(core.newOperation("logger")
                            .withParameterGroup(newParameterGroup()
                                .withParameter("message", createStringParameter("#[payload]"))
                                .getDeclaration())
                            .getDeclaration())
                        .getDeclaration())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(db.newOperation("bulkInsert")
                .withParameterGroup(newParameterGroup("Query")
                    .withParameter("sql", createStringParameter("INSERT INTO PLANET(POSITION, NAME) VALUES (:position, :name)"))
                    .withParameter("parameterTypes",
                                   newListValue()
                                       .withValue(newObjectValue()
                                           .withParameter("key", createStringParameter("name"))
                                           .withParameter("type", createStringParameter("VARCHAR")).build())
                                       .withValue(newObjectValue()
                                           .withParameter("key", createStringParameter("position"))
                                           .withParameter("type", createStringParameter("INTEGER")).build())
                                       .build())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(http.newOperation("request")
                .withConfig("httpRequester")
                .withParameterGroup(newParameterGroup("URI Settings")
                    .withParameter("path", createStringParameter("/nested"))
                    .getDeclaration())
                .withParameterGroup(newParameterGroup()
                    .withParameter("method", createStringParameter("POST"))
                    .getDeclaration())
                .getDeclaration())
            .withComponent(db.newOperation("insert")
                .withConfig("dbConfig")
                .withParameterGroup(newParameterGroup("Query")
                    .withParameter("sql",
                                   createStringParameter("INSERT INTO PLANET(POSITION, NAME, DESCRIPTION) VALUES (777, 'Pluto', :description)"))
                    .withParameter("parameterTypes",
                                   newListValue()
                                       .withValue(newObjectValue()
                                           .withParameter("key", createStringParameter("description"))
                                           .withParameter("type", createStringParameter("CLOB")).build())
                                       .build())
                    .withParameter("inputParameters", createStringParameter("#[{{'description' : payload}}]"))
                    .getDeclaration())
                .getDeclaration())
            .withComponent(sockets.newOperation("sendAndReceive")
                .withParameterGroup(newParameterGroup()
                    .withParameter("streamingStrategy",
                                   newObjectValue()
                                       .ofType("repeatable-in-memory-stream")
                                       .withParameter("bufferSizeIncrement", createNumberParameter("8"))
                                       .withParameter("bufferUnit", createStringParameter("KB"))
                                       .withParameter("initialBufferSize", createNumberParameter("51"))
                                       .withParameter("maxBufferSize", createNumberParameter("1000"))
                                       .build())
                    .getDeclaration())
                .withParameterGroup(newParameterGroup("Output")
                    .withParameter("target", createStringParameter("myVar"))
                    .withParameter("targetValue", createStringParameter("#[message]"))
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newConstruct("try")
                .withComponent(wsc.newOperation("consume")
                    .withParameterGroup(newParameterGroup()
                        .withParameter("operation", createStringParameter("GetCitiesByCountry"))
                        .getDeclaration())
                    .withParameterGroup(newParameterGroup("Message")
                        .withParameter("attachments", createStringParameter("#[{}]"))
                        .withParameter("headers",
                                       createStringParameter("#[{\"headers\": {con#headerIn: \"Header In Value\",con#headerInOut: \"Header In Out Value\"}]"))
                        .withParameter("body", createStringParameter("#[payload]"))
                        .getDeclaration())
                    .getDeclaration())
                .withComponent(core.newConstruct("errorHandler")
                    .withComponent(core.newRoute("onErrorContinue")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("type", createStringParameter("MULE:ANY"))
                            .getDeclaration())
                        .withComponent(core.newOperation("logger").getDeclaration())
                        .getDeclaration())
                    .withComponent(core.newRoute("onErrorPropagate")
                        .withParameterGroup(newParameterGroup()
                            .withParameter("type", createStringParameter("WSC:CONNECTIVITY"))
                            .withParameter("when", createStringParameter("#[e.cause == null]"))
                            .getDeclaration())
                        .getDeclaration())
                    .getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(core.newConstruct("flow").withRefName("schedulerFlow")
            .withComponent(core.newSource("scheduler")
                .withParameterGroup(newParameterGroup()
                    .withParameter("schedulingStrategy", newObjectValue()
                        .ofType("org.mule.runtime.core.api.source.scheduler.FixedFrequencyScheduler")
                        .withParameter("frequency", createNumberParameter("50"))
                        .withParameter("startDelay", createNumberParameter("20"))
                        .withParameter("timeUnit", createStringParameter("SECONDS"))
                        .build())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newOperation("logger")
                .withParameterGroup(newParameterGroup()
                    .withParameter("message", createStringParameter("#[payload]")).getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .withGlobalElement(core.newConstruct("flow").withRefName("cronSchedulerFlow")
            .withComponent(core.newSource("scheduler")
                .withParameterGroup(newParameterGroup()
                    .withParameter("schedulingStrategy", newObjectValue()
                        .ofType("org.mule.runtime.core.api.source.scheduler.CronScheduler")
                        .withParameter("expression", createStringParameter("0/1 * * * * ?"))
                        .build())
                    .getDeclaration())
                .getDeclaration())
            .withComponent(core.newOperation("logger")
                .withParameterGroup(newParameterGroup()
                    .withParameter("message", createStringParameter("#[payload]")).getDeclaration())
                .getDeclaration())
            .getDeclaration())
        .getDeclaration();
  }

  protected String getExpectedArtifactDeclarationJson() {
    return "declaration/artifact-declaration.json";
  }

  private String getComponentElementDeclarationWithoutConfigRef() {
    return "declaration/component-element-declaration-without-config-ref.json";
  }

  private ParameterValue createNumberParameter(String value) {
    return createPlainParameter(value, NUMBER);
  }

  private ParameterValue createBooleanParameter(String value) {
    return createPlainParameter(value, BOOLEAN);
  }

  private ParameterValue createStringParameter(String value) {
    SimpleValueType type = STRING;
    return createPlainParameter(value, type);
  }

  private ParameterValue createStringCdataParameter(String value) {
    return createCdataParameter(value, STRING);
  }

  private ParameterValue createPlainParameter(String value, SimpleValueType type) {
    return plain(value, type);
  }

  private ParameterValue createCdataParameter(String value, SimpleValueType type) {
    return cdata(value, type);
  }

}
