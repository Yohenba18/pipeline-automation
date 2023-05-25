import com.cloudbees.jenkins.plugins.sshcredentials.impl.*
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey.DirectEntryPrivateKeySource
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl


//
// Create adminuser-repo-private-key-id private key credential
//
String privateKeyText = '''-----BEGIN OPENSSH PRIVATE KEY-----
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
-----END OPENSSH PRIVATE KEY-----'''

// String privateKeyText = new File('/home/yohenba.kshetrimayum/ansible/Ansible-test-key.pem').text

DirectEntryPrivateKeySource privateKeySource = new DirectEntryPrivateKeySource(privateKeyText)
Credentials privateKeyCredential = new BasicSSHUserPrivateKey(
  CredentialsScope.GLOBAL,     // scope
  'yohenba',                    // id
  'yohenba',                  // username
  privateKeySource,            // private key source
  '',                          // passphrase
  'yohenba SSH key'         // description
)
SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), privateKeyCredential)



