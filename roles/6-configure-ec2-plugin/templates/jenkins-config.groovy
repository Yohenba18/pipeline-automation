import com.amazonaws.services.ec2.model.InstanceType
import com.cloudbees.jenkins.plugins.awscredentials.AWSCredentialsImpl
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.Domain
import hudson.model.*
import hudson.plugins.ec2.AmazonEC2Cloud
import hudson.plugins.ec2.AMITypeData
import hudson.plugins.ec2.EC2Tag
import hudson.plugins.ec2.SlaveTemplate
import hudson.plugins.ec2.SpotConfiguration
import hudson.plugins.ec2.UnixData
import jenkins.model.Jenkins
import hudson.plugins.ec2.HostKeyVerificationStrategyEnum
import hudson.plugins.ec2.ConnectionStrategy
import hudson.plugins.ec2.Tenancy
import hudson.plugins.ec2.EbsEncryptRootVolume

def sshPortToConnectWith = '22'

// store parameters
def slaveTemplateUsEast1Parameters = [
  ami:                           'ami-0c5f4de412b0b6c4a',
  associatePublicIp:             false,
  spotConfig:                    null,
  connectBySSHProcess:           false,
  connectUsingPublicIp:          false,
  customDeviceMapping:           '',
  deleteRootOnTermination:       false,
  description:                   'ec2 plugin',
  ebsOptimized:                  false,
  iamInstanceProfile:            '',
  idleTerminationMinutes:        '10',
  initScript:                    '''sudo apt update
  sudo apt install openjdk-11-jdk -y
  sudo apt install maven -y''',
  instanceCapStr:                '3',
  javaPath:                      'java',
  jvmopts:                       '',
  labelString:                   'ec2',
  launchTimeoutStr:              '',
  numExecutors:                  '1',
  unixData:                      new UnixData(null, null, null, sshPortToConnectWith, null),
  remoteFS:                      '',
  remoteAdmin:                   'ubuntu',
  tmpDir:                        '',
  securityGroups:                'sg-04683ffd977e4bffb',
  stopOnTerminate:               false,
  subnetId:                      'subnet-db5a4787, subnet-6ce84821, subnet-c66e71e8, subnet-bf567281, subnet-b7c1d0d0, subnet-fe5c97f0',
  tags:                          new EC2Tag('Name', 'ec2-slave'),
  type:                          't2.micro',
  useDedicatedTenancy:           false,
  useEphemeralDevices:           false,
  usePrivateDnsName:             false,
  userData:                      '',
  zone:                          '',
  metadataEndpointEnabled:       true,
  metadataTokensRequired:        false, // `true` enforces IMDSv2 only (over IMDSv1), an important AWS security best practice
  metadataHopsLimit:             1,
  minimumNumberOfInstances:      1,
  minimumNumberOfSpareInstances: 1,
  maxTotalUses:                  -1,
  monitoring:                    false,
  t2Unlimited:                   false,
  connectionStrategy:            ConnectionStrategy.valueOf('PRIVATE_IP'),
  hostKeyVerificationStrategy:   HostKeyVerificationStrategyEnum.valueOf('ACCEPT_NEW'),
  tenancy:                       Tenancy.valueOf('Default'),
  ebsEncryptRootVolume:          EbsEncryptRootVolume.valueOf('DEFAULT'),
  nodeProperties:                null
]

def AmazonEC2CloudParameters = [
  cloudName:      'ec2',
  credentialsId:  'ubuntu',
  instanceCapStr: '1',
  privateKey:     '''-----BEGIN RSA PRIVATE KEY-----
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
-----END RSA PRIVATE KEY-----''',
  sshKeysCredentialsId: 'ubuntu',
  region: 'us-east-1',
  useInstanceProfileForCredentials: false
]

// def AWSCredentialsImplParameters = [
//   id:           'yohenba',
//   description:  'Jenkins AWS IAM key',
//   accessKey:    'AKIAR44PCRWKPJEYNDXB',
//   secretKey:    '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'
// ]

// // https://github.com/jenkinsci/aws-credentials-plugin/blob/aws-credentials-1.23/src/main/java/com/cloudbees/jenkins/plugins/awscredentials/AWSCredentialsImpl.java
// AWSCredentialsImpl aWSCredentialsImpl = new AWSCredentialsImpl(
//   CredentialsScope.GLOBAL,
//   AWSCredentialsImplParameters.id,
//   AWSCredentialsImplParameters.accessKey,
//   AWSCredentialsImplParameters.secretKey,
//   AWSCredentialsImplParameters.description
// )

// https://javadoc.jenkins.io/plugin/ec2/hudson/plugins/ec2/SlaveTemplate.html
SlaveTemplate slaveTemplateUsEast1 = new SlaveTemplate(
  slaveTemplateUsEast1Parameters.ami,
  slaveTemplateUsEast1Parameters.zone,
  slaveTemplateUsEast1Parameters.spotConfig,
  slaveTemplateUsEast1Parameters.securityGroups,
  slaveTemplateUsEast1Parameters.remoteFS,
  InstanceType.fromValue(slaveTemplateUsEast1Parameters.type),
  slaveTemplateUsEast1Parameters.ebsOptimized,
  slaveTemplateUsEast1Parameters.labelString,
  Node.Mode.NORMAL,
  slaveTemplateUsEast1Parameters.description,
  slaveTemplateUsEast1Parameters.initScript,
  slaveTemplateUsEast1Parameters.tmpDir,
  slaveTemplateUsEast1Parameters.userData,
  slaveTemplateUsEast1Parameters.numExecutors,
  slaveTemplateUsEast1Parameters.remoteAdmin,
  slaveTemplateUsEast1Parameters.unixData,
  slaveTemplateUsEast1Parameters.javaPath,
  slaveTemplateUsEast1Parameters.jvmopts,
  slaveTemplateUsEast1Parameters.stopOnTerminate,
  slaveTemplateUsEast1Parameters.subnetId,
  [slaveTemplateUsEast1Parameters.tags],
  slaveTemplateUsEast1Parameters.idleTerminationMinutes,
  slaveTemplateUsEast1Parameters.minimumNumberOfInstances,
  slaveTemplateUsEast1Parameters.minimumNumberOfSpareInstances,
  slaveTemplateUsEast1Parameters.instanceCapStr,
  slaveTemplateUsEast1Parameters.iamInstanceProfile,
  slaveTemplateUsEast1Parameters.deleteRootOnTermination,
  slaveTemplateUsEast1Parameters.useEphemeralDevices,
  slaveTemplateUsEast1Parameters.launchTimeoutStr,
  slaveTemplateUsEast1Parameters.associatePublicIp,
  slaveTemplateUsEast1Parameters.customDeviceMapping,
  slaveTemplateUsEast1Parameters.connectBySSHProcess,
  slaveTemplateUsEast1Parameters.monitoring,
  slaveTemplateUsEast1Parameters.t2Unlimited,
  slaveTemplateUsEast1Parameters.connectionStrategy,
  slaveTemplateUsEast1Parameters.maxTotalUses,
  slaveTemplateUsEast1Parameters.nodeProperties,
  slaveTemplateUsEast1Parameters.hostKeyVerificationStrategy,
  slaveTemplateUsEast1Parameters.tenancy,
  slaveTemplateUsEast1Parameters.ebsEncryptRootVolume,
  slaveTemplateUsEast1Parameters.metadataEndpointEnabled,
  slaveTemplateUsEast1Parameters.metadataTokensRequired,
  slaveTemplateUsEast1Parameters.metadataHopsLimit,
)

// https://javadoc.jenkins.io/plugin/ec2/index.html?hudson/plugins/ec2/AmazonEC2Cloud.html
AmazonEC2Cloud amazonEC2Cloud = new AmazonEC2Cloud(
  AmazonEC2CloudParameters.cloudName,
  AmazonEC2CloudParameters.useInstanceProfileForCredentials,
  AmazonEC2CloudParameters.credentialsId,
  AmazonEC2CloudParameters.region,
  AmazonEC2CloudParameters.privateKey,
  AmazonEC2CloudParameters.sshKeysCredentialsId,
  AmazonEC2CloudParameters.instanceCapStr,
  [slaveTemplateUsEast1],
  '',
  ''
)

// get Jenkins instance
Jenkins jenkins = Jenkins.getInstance()

// // get credentials domain
// def domain = Domain.global()

// // get credentials store
// def store = jenkins.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

// // add credential to store
// store.addCredentials(domain, aWSCredentialsImpl)

// add cloud configuration to Jenkins
jenkins.clouds.add(amazonEC2Cloud)

// save current Jenkins state to disk
println(amazonEC2Cloud.getRegion())

