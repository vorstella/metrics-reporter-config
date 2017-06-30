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

package com.addthis.metrics3.reporter.config;

import java.util.Properties;

import com.codahale.metrics.MetricRegistry;
import com.addthis.metrics.reporter.config.AbstractKafkaReporterConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.hengyunabc.metrics.KafkaReporter;
import kafka.producer.ProducerConfig;

public class KafkaReporterConfig extends AbstractKafkaReporterConfig implements MetricsReporterConfigThree
{
    private static final Logger log = LoggerFactory.getLogger(KafkaReporterConfig.class);

    private MetricRegistry registry;

    private KafkaReporter reporter;

    @Override
    public boolean enable(MetricRegistry registry) {
        this.registry = registry;

        log.info("Enabling KafkaReporter to {}", "");
        try
        {
            String hostName = "localhost";
            String topic = "test-kafka-reporter";
            Properties props = new Properties();
            props.put("metadata.broker.list", getHostsString());
            props.put("serializer.class", getSerializer());
            props.put("partitioner.class", getPartitioner());
            props.put("request.required.acks", getRequiredAcks());
            ProducerConfig config = new ProducerConfig(props);

            reporter = KafkaReporter.forRegistry(registry)
                       .config(config)
                       .topic(getTopic())
                       .hostName(getHostName())
                       .ip(getIp())
                       .prefix(getResolvedPrefix())
                       .filter(MetricFilterTransformer.generateFilter(getPredicate()))
                       .build();

            reporter.start(getPeriod(), getRealTimeunit());
        }
        catch (Exception e)
        {
            log.error("Failure while Enabling KafkaReporter", e);
            return false;
        }
        return true;
    }

    @Override
    public void report() {
        if (reporter != null) {
            reporter.report();
        }
    }

}
