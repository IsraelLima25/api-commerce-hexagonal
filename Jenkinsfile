pipeline{

    agent any
    options {
        ansiColor('xterm')
    }

    stages{

        stage('Build') {
            steps{
               sh 'mvn -Dmaven.test.skip=true -Dtests.unit.skip=true clean package'
            }
        }

        stage('Unit Tests') {
            steps{
               sh 'mvn test -Dmaven.test.skip=false'
            }
        }

        stage('Integration Tests') {
            steps{
               sh 'mvn -Dtests.unit.skip=true verify'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, target/failsafe-reports/*.xml'            
        }        
    }
}