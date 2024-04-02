pipeline{

    agent any
    options {
        ansiColor('xterm')
    }

    stages{

        stage('Build') {
            steps{
               sh 'mvn -Dmaven.test.skip=true clean package'
            }
        }

        stage('Unit Tests') {
            steps{
               sh 'mvn test'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'            
        }        
    }
}