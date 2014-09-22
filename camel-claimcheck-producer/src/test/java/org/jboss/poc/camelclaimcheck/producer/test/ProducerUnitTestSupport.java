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

import java.util.Dictionary;
import java.util.Map;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.jboss.poc.camel.store.MessageStore;

public class ProducerUnitTestSupport extends CamelBlueprintTestSupport {

  @Override
  protected String getBlueprintDescriptor() {
    return "OSGI-INF/blueprint/blueprintContext.xml";
  }

  @Override
  protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
    services.put(MessageStore.class.getName(), asService(new MessageStoreMockImpl(), null));
  }

  @Override
  protected String useOverridePropertiesWithConfigAdmin(Dictionary props) throws Exception {
    props.put("file.consumer.path", "target/data/store");
    props.put("jms.broker.url", "vm://amq?broker.persistent=false");
    return "org.jboss.poc.camelclaimcheck.producer";
  }
}
