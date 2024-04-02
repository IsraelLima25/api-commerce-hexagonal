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
}