# KAFKA EXAMPLE with Java and multiple instances

## Requirements

* Java 21 (JDK amazon-corretto-21)
* Kafka cluster v4.0.0

## Purpose
Show how multiple independent instances that work with same kafka cluster will work, and show kafka's fault tolerance, if we off one consumer and on it back after some time.

## Setups (Ubuntu | Ubuntu on WSL2)

1. Install Java JDK
```bash
sudo apt install openjdk-21-jdk
```

2. Get archive:
```bash
wget https://dlcdn.apache.org/kafka/4.0.0/kafka-4.0.0-src.tgz
```

3. Extract it:
```bash
 tar -xzf kafka-4.0.0-src.tgz
```

4. Go to extracted foler:
```bash
cd kafka-4.0.0-src/
```

5. Build it skipping tests (this might take a while):
```bash
./gradlew jar -x test
```
6. Add metadata configuration:
```bash
bin/kafka-storage.sh format --config config/server.properties --cluster-id {your_name} --standalone
```

7. Run kafka-cluster:
```bash
bin/kafka-server-start.sh config/server.properties 
```

8. In separate terminal while cluster is active, create required for this project topic with 2 partitions:
```bash
bin/kafka-topics.sh --create --topic orders --partitions 2 --replication-factor 1 --bootstrap-server localhost:9092
```

> Note: for WSL2 you should make kafka's cluster port accessible for IDE and Windows environment, for that run following command in WSL:
> ```bash
> ip addr show eth0
> ```
> 
> Select the ip address from this line `***.**.***.***/20` without `/20`
> 
> Run Windows' cmd with as Administrator and enter following command:
> ```bash
> netsh interface portproxy add v4tov4 listenport=9092 listenaddress=0.0.0.0 connectport=9092 connectaddress=***.**.***.***
> ```
> 
> Now you should be able to access the kafka cluster from Windows ENV

9. Go to any IDE for Java (IntelliJIdea in example) and open project
10. In project structure select jdk as `amazon-corretto-21`
11. Language level `set to 21 (Preview)`
12. In gradle helper from IntelliJIdea run following task: `Tasks` -> `build` -> `clean` task and after in same place run `build` task. 
13. Or use command for it in project root:
```bash
./gradlew clean build
```
14. In IntelliJIdea in `Run/Debug configurations` select `Edit configuration`
15. Create two new instances of Spring Boot
16. Copy auto-created by IDE instances' properties for `OrderConsumerServiceApplication` and `OrderProducerServiceApplication`
17. In same menu for `OrderProducerServiceApplication` in `Active Profiles` input, set its value to `p0` and for your duplicate set `p1`
14. In IntelliJIdea press `Alt+8` and select `SpringBoot` and press `Run` or `Ctrl+Shift+F10`

## Congrats!
If you do not see errors, you should see multiple logs in diff instances that are working with your Kafka cluster

> Note: If something is not okay, ask ChatGPT) Java and Gradle is a miracle every time XD