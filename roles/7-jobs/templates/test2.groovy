import jenkins.model.*
import hudson.scm.*
import jenkins.model.Jenkins
import hudson.plugins.git.GitSCM
import hudson.triggers.SCMTrigger
import jenkins.model.Jenkins
import hudson.model.FreeStyleProject
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import hudson.plugins.git.extensions.impl.CleanBeforeCheckout
import hudson.plugins.git.BranchSpec
import hudson.plugins.git.UserRemoteConfig

def createJenkinsPipeline(projectName, repositoryUrl, branch, sshKeyFile) {
    def jenkins = Jenkins.getInstance()
    
    // Create a new workflow job
    def workflowJob = jenkins.createProject(WorkflowJob, projectName)
    
    // Configure SCM settings
    def gitScm = new GitSCM(repositoryUrl)
    gitScm.branches = [new hudson.plugins.git.BranchSpec(branch)]
    gitScm.extensions.add(new CleanBeforeCheckout())
    gitScm.userRemoteConfigs[0].credentialsId = sshKeyFile
    
    // Create pipeline definition from SCM
    def pipelineScriptPath = 'Jenkinsfile' // Path to your pipeline script in the repository
    def pipelineScriptFromScm = new CpsScmFlowDefinition(gitScm, pipelineScriptPath)
    workflowJob.definition = pipelineScriptFromScm
    
    // Save the job configuration
    workflowJob.save()
    
    println "Pipeline '${projectName}' created successfully."
}

// Example usage
def projectName = 'MyPipeline'
def repositoryUrl = 'git@github.com:Yohenba18/CredWiz_Baton.git'
def branch = 'v1.0.0'
def sshKeyFile = 'yohenba'

createJenkinsPipeline(projectName, repositoryUrl, branch, sshKeyFile)