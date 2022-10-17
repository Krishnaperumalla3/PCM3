s#localhost
store.pass = store@localhost
alias = pcm-localhost
key.pass = pass@localhost
jks-file = /localhost-keystore.jks

keytool -genkeypair -alias pcm-localhost -dname "CN=pcm localhost, OU=pcm localhost Apps, O=pcm localhost, L=Hyderaba, ST=Telangana, C=IN" -keypass pass@localhost -storetype JKS -keystore localhost-keystore.jks -storepass store@localhost -keyalg RSA -sigalg SHA256withRSA -validity 7300
keytool -importcert -alias pcm-amfam-alias -keystore localhost-keystore.jks -storepass store@localhost -keypass pass@localhost -file signature.cer
keytool -list -v -keystore localhost-keystore.jks (then enter store password)

# To generate the keysize 4096 use: -keysize 4096 , like
keytool -genkeypair -alias pcm-localhost -dname "CN=pcm localhost, OU=pcm localhost Apps, O=pcm localhost, L=Hyderaba, ST=Telangana, C=IN" -keypass pass@localhost -storetype JKS -keystore localhost-keystore.jks -storepass store@localhost -keyalg RSA -sigalg SHA256withRSA -validity 7300 -keysize 4096
