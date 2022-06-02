pipeline {
    agent {
        label 'docker'
    }

    stages {
            stage('Source') {
                steps {
                    git 'https://github.com/beaserter/unir-test.git'
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