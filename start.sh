git pull
mvn clean package
APPLICATION="logistics"
echo "Start Condition Experiment Time: ${APPLICATION}"
nohup java -jar -server  -Xmx100M  mf-gateway/target/mf-gateway-1.0-SNAPSHOT.jar  >/dev/null 2>&1 &
nohup java -jar -server  -Xmx100M  mf-registry/target/mf-registry-1.0-SNAPSHOT.jar >/dev/null 2>&1 &
nohup java   -Xms1506m -Xmx1560m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=test.hprof -jar mf-item/mf-item-service/target/mf-item-service-1.0-SNAPSHOT.jar  >/dev/null 2>&1 &

echo "Start Condition Experiment Time: ${APPLICATION}"
