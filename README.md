# kafkademo

## Usage
### Producer
```cd kafkademo```

```java -jar kafkademo-0.0.1-Producer.jar producer.properties```
```

output:
    producer begin......server: localhost:9092, topic: topic_1, key.serializer: Long, value.serializer: String
    producer: key: 0 value: {info_type=C06001, p
    producer: key: 1 value: {info_type=C06001, p
    producer: key: 2 value: {info_type=C06001, p

```

### Consumer
```cd kafkademo```

```
java -jar kafkademo-0.0.1-Consumer.jar producer.properties
```

```
output:
    consumer begin......server: localhost:9092, topic: topic_1, key.serializer: Long, value.serializer: String
    offset = 0, partition = 0,key = 0, value = {info_type=C06001, public_date
    offset = 1, partition = 0,key = 1, value = {info_type=C06001, public_date
    offset = 2, partition = 0,key = 2, value = {info_type=C06001, public_date
```

## Config
### producer.properties
```
kafka_host=localhost:9092
topic=topic_1
xml_file=land.xml    # path of xml_file, xml from mysqldump
value_limit=20       # length of value in output
```
### consumer.properties
```
kafka_host=localhost:9092
topic=topic_1
group=topic_t
value_limit=30
```