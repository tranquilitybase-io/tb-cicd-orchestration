pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/tranquilitybase-io/tb-gcp/tree/issue-398---Jenkins-CasC/tb-jenkins'
            }
        }
        stage('Login') {
            steps {
                sh 'aws ecr get-login-password --region eu-west-1 | docker login --username AWS --password-stdin 122072647213.dkr.ecr.eu-west-1.amazonaws.com' 
            }
        }
        stage('Build') {
            steps {
                sh 'docker build -t dac .'
                sh 'docker tag dac:latest 122072647213.dkr.ecr.eu-west-1.amazonaws.com/dac:latest'
                sh 'docker push 122072647213.dkr.ecr.eu-west-1.amazonaws.com/dac:latest'
            }
        }
    }
}