
pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    tools {
        maven 'Maven'
    }

    environment {
        DOCKERHUB_REPO = 'sdadadasdas/inclass'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn -B test'
            }
        }

        stage('Code Coverage') {
            steps {
                bat 'mvn -B jacoco:report'
            }
        }

        stage('Publish Test Results') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Archive Coverage Report') {
            steps {
                archiveArtifacts artifacts: 'target/site/jacoco/**', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build --pull -t %DOCKERHUB_REPO%:latest -t %DOCKERHUB_REPO%:%BUILD_NUMBER% .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-credentials',
                    usernameVariable: 'DOCKERHUB_USERNAME',
                    passwordVariable: 'DOCKERHUB_TOKEN'
                )]) {
                    powershell '''
                        $env:DOCKERHUB_TOKEN | docker login --username $env:DOCKERHUB_USERNAME --password-stdin
                        if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

                        docker push "$($env:DOCKERHUB_REPO):latest"
                        if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

                        docker push "$($env:DOCKERHUB_REPO):$env:BUILD_NUMBER"
                        if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

                        docker logout
                    '''
                }
            }
        }
    }
}
