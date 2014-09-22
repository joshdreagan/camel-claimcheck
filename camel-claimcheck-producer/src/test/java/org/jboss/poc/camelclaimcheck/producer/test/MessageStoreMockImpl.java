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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.jboss.poc.camel.store.MessageStore;

public class MessageStoreMockImpl implements MessageStore {

  private final Map<String, Object> mapStore = new HashMap<>();
  
  @Override
  public String store(Object message) {
    String id = UUID.randomUUID().toString();
    mapStore.put(id, message);
    return id;
  }

  @Override
  public Object claim(String id) {
    return mapStore.get(id);
  }
}
