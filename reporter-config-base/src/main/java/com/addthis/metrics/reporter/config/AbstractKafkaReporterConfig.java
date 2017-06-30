/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.addthis.metrics.reporter.config;

import javax.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public abstract class AbstractKafkaReporterConfig extends AbstractHostPortReporterConfig {

    protected String name = "kafka";

    @NotNull
    protected String hostname = "";

    @NotNull
    protected String ip = "";

    @NotNull
    protected String serializer = "kafka.serializer.StringEncoder";

    @NotNull
    protected String partitioner = "kafka.producer.DefaultPartitioner";

    @NotNull
    protected String requiredAcks = "1";

    @NotNull
    protected String topic;

    protected Map<String, String> labels = new HashMap<>();
    protected Map<String, String> resolvedLabels = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getHostName() {
        return this.hostname;
    }

    public void setHostName(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSerializer() {
        return this.serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getPartitioner() {
        return this.partitioner;
    }

    public void setPartitioner(String partitioner) {
        this.partitioner = partitioner;
    }

    public String getRequiredAcks() {
        return this.requiredAcks;
    }

    public void setRequiredAcks(String requiredAcks) {
        this.requiredAcks = requiredAcks;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            this.resolvedLabels.put(entry.getKey(), resolvePrefix(entry.getValue()));
        }
    }

    public Map<String, String> getResolvedLabels() {
        return resolvedLabels;
    }

    @Override
    public List<HostPort> getFullHostList()
    {
        return getHostListAndStringList();
    }
}
