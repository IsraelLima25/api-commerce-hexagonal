pipeline{

    agent any

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