library(
      //identifier: 'unir-jsl@master',
      identifier: 'unir-cicd@master',
      retriever: modernSCM(
        [
          $class: 'GitSCMSource',
          //remote: "https://github.com/srayuso/unir-jsl.git"
          remote: 'https://github.com/beaserter/unir-cicd.git'
        ]
      )
    ) _

pipeline {
    agent any
    stages {
        stage('Commit info') {
            steps {
                jslInfo()
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
    }
}