pipeline {
    //agent any
    agent {
        label 'docker'
    }

    stages {
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

        stage('test-unit') {
            steps {
                sh 'make test-unit'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }

        stage('test-api') {
            steps {
                sh 'make test-api'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }

        stage('test-behavior') {
            steps {
                sh 'make test-behavior'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }

        stage('test-e2e') {
            steps {
                sh 'make test-e2e'
                archiveArtifacts artifacts: 'results/*.xml'
            }
        }

        stage('test-e2e-wiremock') {
            steps {
                sh 'make test-e2e-wiremock'
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
            emailext body: "Pipeline '${JOB_NAME}' finalise succesful in execution number '${EXECUTOR_NUMBER}'", subject: "Pipeline successful", to: "devs@unir.net"
        }
        /*
        unstable {
            emailext body: "Pipeline ${JOB_NAME} finalise not succesful in execution number ${EXECUTOR_NUMBER}", subject: "Pipeline not successful", to: "devs@unir.net"
        }
        */
        failure {
            emailext body: "Pipeline '${JOB_NAME}' failed in execution number '${EXECUTOR_NUMBER}'", subject: "Pipeline error", to: "devops@unir.net,devs@unir.net"
        }
    }
}