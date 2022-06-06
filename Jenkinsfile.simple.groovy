node('docker') {
    stage('Source') {
        git 'https://github.com/beaserter/unir-cicd.git'
    }
    stage('Build') {
        echo 'Building stage!'
    }
}