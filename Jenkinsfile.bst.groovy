library(
      identifier: 'unir-jsl@master',
      retriever: modernSCM(
        [
          $class: 'GitSCMSource',
          remote: "https://github.com/srayuso/unir-jsl.git"
        ]
      )
    ) _

pipeline {
    agent any
    stages {
        stage('Commit info') {
            steps {
                cicdInfo()
            }
        }
        stage('Repo details') {
            steps {
                echo "Repository name: ${jslGit.getRemoteRepoName()}"
                echo "Repository owner: ${jslGit.getRemoteRepoOwner()}"
            }
        }
        stage('Source') {
            steps {
                git 'https://github.com/beaserter/unir-cicd.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building stage!'
                sh 'make build'
            }
        }

        stage('Unit tests') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }

        stage('Unit api') {
            steps {
                sh 'make test-api'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }
    }
    
    post {
        always {
            junit 'results/*_result.xml'
            cleanWs()
        }
        success {
            emailext body: 'Test Message Success', subject: "Pipeline successful", to: "devs@unir.net"
        }
        unstable {
            emailext body: 'Test Message Unstable', subject: "Pipeline tests not successful", to: "devs@unir.net"
        }
        failure {
            emailext body: 'Test Message Fail', subject: "Pipeline error", to: "devops@unir.net,devs@unir.net"
        }
    }
}