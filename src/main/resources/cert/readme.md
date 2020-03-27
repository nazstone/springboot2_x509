# Server certificate
## Private key
```shell
openssl genrsa -aes256 -out serverprivate.key 2048
```
## Create cert from private key
```shell
openssl req -x509 -new -nodes -key serverprivate.key -sha256 -days 1024 -out serverCA.crt
```
Data set

```
Country Name (2 letter code) [AU]:fr
State or Province Name (full name) [Some-State]:paca
Locality Name (eg, city) []:aix
Organization Name (eg, company) [Internet Widgits Pty Ltd]:nazstone
Organizational Unit Name (eg, section) []:dev
Common Name (e.g. server FQDN or YOUR name) []:naz
Email Address []:tada@po.po
```
## Truststore to jks format
```shell
keytool -import -file serverCA.crt -alias serverCA -keystore truststore.jks
```
## Keystore to p12 format
```shell
openssl pkcs12 -export -in serverCA.crt -inkey serverprivate.key -certfile serverCA.crt -out keystore.p12
```
## Keystore to jks format
```shell
keytool -importkeystore -srckeystore keystore.p12 -srcstoretype pkcs12 -destkeystore keystore.jks -deststoretype JKS
```
# Client certificate
## private key
```shell
openssl genrsa -aes256 -out clientprivate.key 2048
```
## Create CSR
```shell
openssl req -new -key clientprivate.key -out client.csr
```
Data set

```
Country Name (2 letter code) [AU]:fr
State or Province Name (full name) [Some-State]:
Locality Name (eg, city) []:
Organization Name (eg, company) [Internet Widgits Pty Ltd]:naz
Organizational Unit Name (eg, section) []:dev
Common Name (e.g. server FQDN or YOUR name) []:naz
Email Address []:nazstone@gmail.com
```
## Create CERT from server cert
```shell
openssl x509 -req -in client.csr -CA serverCA.crt -CAkey serverprivate.key -CAcreateserial -out client.crt -days 365 -sha256
```

# Test
```shell
curl -ik https://naz:8443/admin --cert client.crt:tadatada --key clientprivate.key
curl -ik https://naz:8443/
```