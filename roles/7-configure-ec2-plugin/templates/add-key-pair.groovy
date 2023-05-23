import jenkins.model.*
import hudson.plugins.ec2.*
import hudson.plugins.ec2.AmazonEC2Cloud
import com.amazonaws.services.ec2.model.KeyPairInfo

// Get the Jenkins instance
def instance = Jenkins.getInstance()

// Get the EC2 plugin instance
def ec2 = instance.getDescriptorOrDie(EC2Cloud.DescriptorImpl.class)

// Get the key-pair info from AWS
AmazonEC2Cloud aws = new AmazonEC2Cloud()
aws.setRegion(RegionUtils.getRegion("us-east-1"))
def keypairInfo = aws.describeKeyPairs(new DescribeKeyPairsRequest().withKeyNames("${keypair_name}")).getKeyPairs()[0]

// Configure the EC2 plugin to use the key-pair
def privateKey = CredentialsHelper.findPrivateKey("${keypair_name}", null, null)
def sshCredential = new BasicSSHUserPrivateKey(CredentialsScope.GLOBAL, null, "${keypair_name}", new BasicSSHUserPrivateKey.UsersPrivateKey("${keypair_name}", "${keypairInfo.getKeyFingerprint()}", new BasicSSHUserPrivateKey.DirectEntryPrivateKeySource(privateKey)))
ec2.setSshCredentials(sshCredential)
ec2.save()