
pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
    }

    tools {
        maven 'Maven'
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
    }
}
