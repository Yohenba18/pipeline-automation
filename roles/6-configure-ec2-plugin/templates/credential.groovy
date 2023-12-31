import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey.DirectEntryPrivateKeySource
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl


//
// Create adminuser-repo-private-key-id private key credential
//
String privateKeyText = '''-----BEGIN RSA PRIVATE KEY-----
MIIEogIBAAKCAQEAmxxeaRJI3I1nbRl0fwwuxedxtBjU7ISoE8DOnPXFs5mn/hXl
UN6ZTUenFxxiydrZk8lsp/Pf6xPxCp6GF7TDZ7jEmlCF6lQgZzwZWKMi6lqWXwH5
tKyNyOAnWwoZmvZUGAv2ksXcZsd+n/OeIJFrkodBsJwkvbEo9doWk0BcrxmCPfiO
0UWBPG6Cv7+e4thRT4eU6vNtxdXjlH2Ej6JVLAF6FnNp3H4zTW9JxSU5BRLD2OhW
hjkdVeK6QV60vQdX0PjJQg3qEMTL+TdoUbfrLMFsFYXzJgIbeUEHOomqrwgQZuxF
QA/kd59FY3OLXgrLi7RpZkkzYIMzsInQ5KYEAQIDAQABAoIBAGnGist0YKOm+mT3
+u24zRzAnFWIqYsxW708uqlnIQ89Ncab/+mKI1kEB/pGrUeiKsYRkjtmn8ibrMDl
IeSArNDKu6fQScJm5EICRfagKWtPo0PfAP4ovffChJQc6yE14lsTrW6h549fdAZ4
B9mF0an2+uEJWjTR33Do7IQ2W/47f5pbJnfFfq2GCSEwoI85EwVUU+S2xYYnpeYa
qBcosApxlHqrh9c5dn7JXwe2DCt31hvQd1rUWhzZjoRWif9oMzn9983aZOz7o26w
fYgjdXRbGJFDJIsIQdV0eUd+mWuUzwjyXUeAvhpgFiMhBdAcS75vTQXkIkG0XPXz
TmRw91ECgYEA6ruRlrj97rCugven5l1Jnzq05ti0pBHsh7SQzPE9usDM5Cg7f8Gj
9+6TVExFtyy9YOLdF5C7JACWDprqHh4104TR6bAMDuyZomFl/YqpD/72c3/Jflt0
YKAN41Srku98aNUtdpG/++Ix3z/8c/yiv4Mvp+wRvBD69hGNpKhjU0cCgYEAqSoJ
THEE+ZyEjJsDkh8Vee6nmQJClBr81YeGidRTAtXgTBUfoskpkVA96/rwwsr/ndMz
zjvtKY6PNjDFeoTHAgFQLI6UqaGWdXFNgl+r9QxKs8boZE4FMKdxm0tkmylAHtF0
5vUTr4OWxpqpAxLZyO4hBXOcfGL+Ie38QzHuQncCgYBJOydanHAsbVakaVsqVWRI
luolMup4XVcrpzudlI2rm8bQT3TwkJr1GKtCG2GMmhqjBWPwnCimPMCT4eS0bLxi
Nwe+HjxE06/CpxxIk1/XK7X7ppxYHXQbSRPMHnhAqRJpKki6uxHoJVNl0PKTxPeJ
RAY0H0QRJkMFTcig3B/QtQKBgASYM7Bz0WLGMYNwImSxm2+4X6z0NsK3CO7zw1+y
+J43GOR4HmfZdtuPhl8Gbcucn9y1g86yKfra+GDMalCtkoonZvoO1VLWJLQD48sK
tiztrIm9eUpgJBvyME+7eHwOXsN3hOlQ9rvpowq54KVIucmPTi5LuZBXT/ez8woQ
LRx5AoGALRkIu7Qf67E1ieXy8hLJ1B4/zsVMALJM/b7oF98+QS+3hAtROtbVaLXe
wVyKddrcRttzb+zvtzfWyJneBMBp4oFx2z/AtK31h5bn4ZcOoBNfdJfkwi2w9DgU
jztrVdKeQ8QHXUe81jzfM2GQHhdm+Zih0YhTxkYT1Spj70eMGKA=
-----END RSA PRIVATE KEY-----'''

// String privateKeyText = new File('/home/yohenba.kshetrimayum/ansible/Ansible-test-key.pem').text

DirectEntryPrivateKeySource privateKeySource = new DirectEntryPrivateKeySource(privateKeyText)
Credentials privateKeyCredential = new BasicSSHUserPrivateKey(
  CredentialsScope.GLOBAL,     // scope
  'ubuntu',                    // id
  'ubuntu',                  // username
  privateKeySource,            // private key source
  '',                          // passphrase
  'Admin user SSH key'         // description
)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), privateKeyCredential)



