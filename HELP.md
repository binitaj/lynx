# Lynx Server

### Setup
The following links were very helpful
<ul>
<li>
https://medium.com/ing-tech-romania/a-simple-mtls-guide-for-spring-boot-microservices-c6bfc9878369
<li></li>https://grantlittle.me/2024/08/16/mtls-client-authentication-with-spring-boot/
</ul>

### Server Side:
<pre>
keytool -genkeypair -alias server -keyalg RSA -keysize 4096 -validity 365 -dname "CN=localhost,OU=Server,O=binitajha.com,L=Georgia,S=GA,C=US" -keypass changeit -keystore server.p12 -storeType PKCS12 -storepass changeit

// export public keys
keytool -exportcert -alias server -file server.cer -keystore server.p12 -storepass changeit

//import public keys to trust stores.
keytool -importcert -keystore client-truststore.p12 -alias server-public -file server.cer -storepass changeit -noprompt
</pre>

### Client Side:
<pre>
keytool -genkeypair -alias client -keyalg RSA -keysize 4096 -validity 365 -dname "CN=LynxClient,OU=Client,O=binitajha.com,L=Georgia,S=GA,C=US" -keypass changeit -keystore client.p12 -storeType PKCS12 -storepass changeit
// export public keys
keytool -exportcert -alias client -file client.cer -keystore client.p12 -storepass changeit

//import public keys to trust stores.
keytool -importcert -keystore server-truststore.p12 -alias client-public -file client.cer -storepass changeit -noprompt
</pre>

### CA
<pre>
docker pull smallstep/step-ca

docker run -d -v step:/home/step -p 9000:9000 -e DOCKER_STEPCA_INIT_NAME=binitajha.com -e DOCKER_STEPCA_INIT_DNS_NAMES=localhost,$(hostname -f) smallstep/step-ca

step ca init

step-ca -config /path/to/your/config.json
</pre>