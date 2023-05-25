import jenkins.model.Jenkins
import hudson.model.FreeStyleProject
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import hudson.plugins.git.GitSCM
import hudson.plugins.git.BranchSpec
import hudson.plugins.git.UserRemoteConfig

// Specify the job name and configuration
String jobName = "My Jenkins Pipeline Job"
String jobDescription = "This is a sample Jenkins pipeline job created via Groovy script"

// Create a new Pipeline job
WorkflowJob newJob = Jenkins.instance.createProject(WorkflowJob, jobName)

// Set the job description
newJob.setDescription(jobDescription)

// Define the pipeline script
String pipelineScript = '''
    pipeline {
        agent {label 'ec2'}
        stages {
            stage('Build') {
                steps {
                    echo 'Building...'
                    // Add your build steps here
                }
            }
            stage('Test') {
                steps {
                    echo 'Testing...'
                    // Add your test steps here
                }
            }
            stage('Deploy') {
                steps {
                    echo 'Deploying...'
                    // Add your deployment steps here
                }
            }
        }
    }
'''

// Set the pipeline definition
newJob.setDefinition(new CpsFlowDefinition(pipelineScript, true))

// Save the new job
newJob.save() 