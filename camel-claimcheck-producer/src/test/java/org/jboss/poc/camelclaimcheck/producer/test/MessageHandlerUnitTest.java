/*
 * Copyright (C) Red Hat, Inc.
 * http://www.redhat.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.poc.camelclaimcheck.producer.test;

import java.util.UUID;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.jboss.poc.camel.store.MessageStore;
import org.junit.Test;

public class MessageHandlerUnitTest extends ProducerUnitTestSupport {

  @Produce(uri = "direct:handleMessage")
  private ProducerTemplate handleMessagePT;

  @EndpointInject(uri = "mock:jms:queue:org.jboss.poc.camelclaimcheck")
  private MockEndpoint mockJmsEP;

  @Override
  public String isMockEndpointsAndSkip() {
    return "jms:*";
  }

  @Test
  public void testFileConsumerRoute() throws Exception {

    String id = UUID.randomUUID().toString();
    String fname = id + ".txt";
    handleMessagePT.sendBodyAndHeader(id, "CamelFileName", fname);

    mockJmsEP.expectedMessageCount(1);
    MockEndpoint.assertIsSatisfied(mockJmsEP);

    String storeId = (String) mockJmsEP.getExchanges().get(0).getIn().getBody();
    MessageStore store = getOsgiService(MessageStore.class);
    assertEquals(store.claim(storeId), id);
  }
}
